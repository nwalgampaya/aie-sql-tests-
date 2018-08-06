package au.com.iwsoftware.ThreadClass;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import org.junit.Assert;

import au.com.iwsoftware.sqlDbcon.JdbcTools;
import au.com.iwsoftware.sqlDbcon.processed_request.AddProcessedRequestProcOperator;
import au.com.iwsoftware.sqlDbcon.processed_request.ProcessedRequest;
import au.com.iwsoftware.sqlDbcon.processed_request.ProcessedRequestGenerator;
import au.com.iwsoftware.sqlDbcon.processed_request.ProcessedRequestTableOperator;

public class ProcessedRequestMultipleRows implements Runnable {

	int entries;
	Throwable throwable;
	CountDownLatch latch;
	Statement outcome;
	private static final JdbcTools jdbcTools = new JdbcTools();
	private AddProcessedRequestProcOperator addProcessedRequestProcOperator;
	private ProcessedRequestGenerator processedRequestGenerator;
	private final Random random = new Random();
	private ProcessedRequestTableOperator processedRequestTableOperator;
	private static final String SCHEMA = "AIE";

	public ProcessedRequestMultipleRows(CountDownLatch latch, int entries) {
		this.latch = latch;
		this.entries = entries;
		processedRequestGenerator = new ProcessedRequestGenerator(random);
		processedRequestTableOperator = new ProcessedRequestTableOperator(SCHEMA);
		addProcessedRequestProcOperator = new AddProcessedRequestProcOperator(SCHEMA);
	}

	@Override
	public void run() {
		try {
			for (int i = 0; i < entries; ++i) {
				try (Connection connection = jdbcTools.openDbconnection()) {
					ProcessedRequest received = processedRequestGenerator.generateValidProcessedRequest();
					int rowId = addProcessedRequestProcOperator.call(connection, received);

					outcome = processedRequestTableOperator.getRowById(connection, rowId);
					ResultSet result = outcome.getResultSet();

//					Assert.assertTrue(result.next());

					while (result.next()) {

						ProcessedRequest insertedData = processedRequestTableOperator.decodeRow(result);
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