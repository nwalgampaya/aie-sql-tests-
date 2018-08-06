package au.com.iwsoftware.ThreadClass;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import au.com.iwsoftware.sqlDbcon.JdbcTools;
import au.com.iwsoftware.sqlDbcon.request_received.AddRequestReceivedProcOperator;
import au.com.iwsoftware.sqlDbcon.request_received.RequestReceived;
import au.com.iwsoftware.sqlDbcon.request_received.RequestReceivedGenerator;
import au.com.iwsoftware.sqlDbcon.request_received.RequestReceivedTableOperator;

public class RequestReceivedMultipleRows implements Runnable {

	int entries;
	Throwable throwable;
	CountDownLatch latch;
	Statement outcome;
	private static final JdbcTools jdbcTools = new JdbcTools();
	private AddRequestReceivedProcOperator addRequestRecievedOperator;	 
	private RequestReceivedGenerator requestReceivedGenerator;
	private final Random random = new Random();    
	private RequestReceivedTableOperator requestReceivedTableOperator;
	private static final String SCHEMA = "AIE";

	public RequestReceivedMultipleRows(CountDownLatch latch, int entries) {
		this.latch = latch;
		this.entries = entries;
		requestReceivedGenerator = new RequestReceivedGenerator(random);
		requestReceivedTableOperator = new RequestReceivedTableOperator(SCHEMA);
		addRequestRecievedOperator  = new AddRequestReceivedProcOperator(SCHEMA);
	}

	@Override
	public void run() {
		try {
			for (int i = 0; i < entries; ++i) {
				try (Connection connection = jdbcTools.openDbconnection()) {
					RequestReceived requestReceived = requestReceivedGenerator.generateValidRequestReceived();
					int rowId = addRequestRecievedOperator.call(connection, requestReceived);

					outcome = requestReceivedTableOperator.getRowById(connection, rowId);
					ResultSet result = outcome.getResultSet();

					while (result.next()) {

						RequestReceived insertedData = requestReceivedTableOperator.decodeRow(result);
					}
					outcome.close();
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
}
