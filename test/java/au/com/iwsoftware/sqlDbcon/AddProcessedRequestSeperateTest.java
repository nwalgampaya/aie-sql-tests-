package au.com.iwsoftware.sqlDbcon;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import au.com.iwsoftware.ThreadClass.ProcessedRequestMultipleRows;
import au.com.iwsoftware.sqlDbcon.processed_request.AddProcessedRequestProcOperator;
import au.com.iwsoftware.sqlDbcon.processed_request.ProcessedRequest;
import au.com.iwsoftware.sqlDbcon.processed_request.ProcessedRequestGenerator;
import au.com.iwsoftware.sqlDbcon.processed_request.ProcessedRequestTableOperator;

public class AddProcessedRequestSeperateTest {

	private static final String SCHEMA = "AIE";

	private static final JdbcTools jdbcTools = new JdbcTools();
	private ProcessedRequestTableOperator processedRequestTableOperator;

	private AddProcessedRequestProcOperator addProcessedRequestProcOperator;

	private ProcessedRequestGenerator processedRequestGenerator;

	// private final Random random = new Random(34469733L);
	private final Random random = new Random();
	private Throwable throwable;

	@Before
	public void setUp() throws Exception {
		// databaseConnection = jdbcTools.openDbconnection();

		processedRequestTableOperator = new ProcessedRequestTableOperator(SCHEMA);
		addProcessedRequestProcOperator = new AddProcessedRequestProcOperator(SCHEMA);
		processedRequestGenerator = new ProcessedRequestGenerator(random);

	}

	/**
	 * This test case will test the AddProcessedRequest stored procedure. It will
	 * test the column lengths of the table by passing invalid lengths.
	 * 
	 * @throws Exception
	 */

	@Test
	public void generateInValidProcessedRequest() throws Exception {

		try (Connection connection = jdbcTools.openDbconnection()) {
			ProcessedRequest received = processedRequestGenerator.generateInValidProcessedRequest();
			int rowId = addProcessedRequestProcOperator.call(connection, received);
			Assert.assertNotEquals(AddProcessedRequestProcOperator.ROW_ID_ERROR, rowId);

			Statement outcome = processedRequestTableOperator.getRowById(connection, rowId);
			ResultSet result = outcome.getResultSet();

			Assert.assertTrue(result.next());

			ProcessedRequest insertedData = processedRequestTableOperator.decodeRow(result);
			Assert.assertNotEquals(received, insertedData);
			outcome.close();
			connection.close();
		}
	}

	/**
	 * This test case will test the ProcessedRequest stored procedure. Will test the
	 * Success scenario by inserting single record to the table.
	 * 
	 * @throws Exception
	 */	
	
	@Test
	public void testProcessedRequestOneRecord() throws Exception {

		try (Connection connection = jdbcTools.openDbconnection()) {
			ProcessedRequest received = processedRequestGenerator.generateValidProcessedRequest();
			int rowId = addProcessedRequestProcOperator.call(connection, received);
			Assert.assertNotEquals(AddProcessedRequestProcOperator.ROW_ID_ERROR, rowId);

			Statement outcome = processedRequestTableOperator.getRowById(connection, rowId);
			ResultSet result = outcome.getResultSet();

			Assert.assertTrue(result.next());

			ProcessedRequest insertedData = processedRequestTableOperator.decodeRow(result);
			Assert.assertEquals(received, insertedData);
			outcome.close();
			connection.close();
		}
	}



//	@Test
//	public void testProcessedRequestOneRecord() throws Exception {
//		final int writerCount = 1;
//		final int rowsPerWriter = 1;
//
//		testProcessedRequest(writerCount, rowsPerWriter);
//
//	}

	/**
	 * This test case will test the ProcessedRequest stored procedure. Will test the
	 * Success scenario by inserting record in a single thread.
	 * 
	 * @throws Exception
	 */

	@Test
	public void testProcessedRequestSingleThread() throws Exception {
		final int writerCount = 1;
		final int rowsPerWriter = 10;

		testProcessedRequest(writerCount, rowsPerWriter);

	}

	/**
	 * This test case will test the ProcessedRequest stored procedure. Will test the
	 * Success scenario by inserting multiple records in a multiple thread.
	 * 
	 * @throws Exception
	 */

	@Test
	public void testProcessedRequestMultiThread() throws Exception {
		final int writerCount = 2;
		final int rowsPerWriter = 500;

		testProcessedRequest(writerCount, rowsPerWriter);

	}

	public void testProcessedRequest(int writerCount, int rowsPerWriter) throws Exception {

		final CountDownLatch latch = new CountDownLatch(writerCount);
		ExecutorService executor = Executors.newFixedThreadPool(writerCount);

		for (int i = 0; i < writerCount; ++i) {

			ProcessedRequestMultipleRows writer = new ProcessedRequestMultipleRows(latch, rowsPerWriter);
			executor.execute(writer);
		}
		latch.await();

		Assert.assertEquals(0, latch.getCount());
	}
}
