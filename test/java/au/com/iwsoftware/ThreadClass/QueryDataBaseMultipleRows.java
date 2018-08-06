package au.com.iwsoftware.ThreadClass;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import au.com.iwsoftware.sqlDbcon.JdbcTools;
import au.com.iwsoftware.sqlDbcon.processed_request.AddProcessedRequestProcOperator;
import au.com.iwsoftware.sqlDbcon.processed_request.ProcessedRequestGenerator;
import au.com.iwsoftware.sqlDbcon.processed_request.ProcessedRequestTableOperator;
import au.com.iwsoftware.sqlDbcon.receive_all_data.AddReceiveAllDataProcOperator;
import au.com.iwsoftware.sqlDbcon.receive_all_data.DataReceivedGenerator;
import au.com.iwsoftware.sqlDbcon.receive_all_data.DataReceivedRequest;

public class QueryDataBaseMultipleRows implements Runnable {

	int entries;
	int returnLoopCount;
	Throwable throwable;
	CountDownLatch latch;
	Statement outcome;
	long startTime = 0;
	long endtTime = 0;
	String eventType = null;
	String alertType = null;
	boolean success = false;
	boolean failure = false;
	boolean incommingFailure = false;
	long rowCountOut = 0;
	int rowCountIn = 0;

	private static final JdbcTools jdbcTools = new JdbcTools();
	private AddReceiveAllDataProcOperator addReceiveAllDataProcOperator;
	private DataReceivedGenerator dataReceivedGenerator;
	private final Random random = new Random();
	private static final String SCHEMA = "AIE";

	public QueryDataBaseMultipleRows(CountDownLatch latch, int entries, long startTime, long endtTime, String eventType,
			String alertType, boolean success, boolean failure, boolean incommingFailure) {

		this.latch = latch;
		this.entries = entries;
		this.startTime = startTime;
		this.endtTime = endtTime;
		this.eventType = eventType;
		this.alertType = alertType;
		this.success = success;
		this.failure = failure;
		this.incommingFailure = incommingFailure;
		dataReceivedGenerator = new DataReceivedGenerator(random);
		addReceiveAllDataProcOperator = new AddReceiveAllDataProcOperator(SCHEMA);
	}

	public void run() {
		try {
			for (int i = 0; i < entries; ++i) {
				try (Connection connection = jdbcTools.openDbconnection()) {
					DataReceivedRequest dataReceived = dataReceivedGenerator.generateValidDataReceived(startTime,
							endtTime, eventType, alertType, success, failure, incommingFailure);

					rowCountIn = addReceiveAllDataProcOperator.call(connection, dataReceived);

					connection.close();
				}
			}
		} catch (Throwable t) {
			t.printStackTrace();
			this.throwable = t;
		} finally {
			latch.countDown();
		}
	}

	public int getRowCount() {
		return rowCountIn;
	}
}