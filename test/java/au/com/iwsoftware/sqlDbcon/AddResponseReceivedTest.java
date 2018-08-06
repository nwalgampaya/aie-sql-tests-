package au.com.iwsoftware.sqlDbcon;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import au.com.iwsoftware.ThreadClass.ResponseReceivedMultipleRows;
import au.com.iwsoftware.sqlDbcon.response_received.AddResponseReceivedProcOperator;
import au.com.iwsoftware.sqlDbcon.response_received.ResponseReceived;
import au.com.iwsoftware.sqlDbcon.response_received.ResponseReceivedGenerator;
import au.com.iwsoftware.sqlDbcon.response_received.ResponseReceivedTableOperator;

public class AddResponseReceivedTest {

	private static final String SCHEMA = "AIE";

	private static final JdbcTools jdbcTools = new JdbcTools();
		private ResponseReceivedTableOperator responseReceivedTableOperator;

	private AddResponseReceivedProcOperator addResponseRecievedOperator;

	private ResponseReceivedGenerator responseReceivedGenerator;

	// private final Random random = new Random(34469733L);
	private final Random random = new Random();
	private Throwable throwable;

	@Before
	public void setUp() throws Exception {
		// databaseConnection = jdbcTools.openDbconnection();

		responseReceivedTableOperator = new ResponseReceivedTableOperator(SCHEMA);
		addResponseRecievedOperator = new AddResponseReceivedProcOperator(SCHEMA);
		responseReceivedGenerator = new ResponseReceivedGenerator(random);

	}

	/**
	 * This test case will test the AddResponseReceived stored procedure. It will
	 * test the column lengths of the table by passing invalid lengths.
	 * 
	 * @throws Exception
	 */

	@Test
	public void testResponseReceivedInvalidRecord() throws Exception {

		try (Connection connection = jdbcTools.openDbconnection()) {
			ResponseReceived responseReceived = responseReceivedGenerator.generateInvalidValidResponseReceived();
			int rowId = addResponseRecievedOperator.call(connection, responseReceived);

			Assert.assertNotEquals(AddResponseReceivedProcOperator.ROW_ID_ERROR, rowId);

			Statement outcome = responseReceivedTableOperator.getRowById(connection, rowId);
			ResultSet result = outcome.getResultSet();

			Assert.assertTrue(result.next());

			ResponseReceived insertedData = responseReceivedTableOperator.decodeRow(result);

			Assert.assertNotEquals(responseReceived, insertedData);

			outcome.close();
			connection.close();

		} catch (Throwable t) {
			t.printStackTrace();
			this.throwable = t;
		}

	}

	/**
	 * This test case will test the AddResponseReceived stored procedure. Will test
	 * the Success scenario by inserting single record to the table.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testResponseReceivedOneRecord() throws Exception {

		try (Connection connection = jdbcTools.openDbconnection()) {
			ResponseReceived responseReceived = responseReceivedGenerator.generateValidResponseReceived();
			int rowId = addResponseRecievedOperator.call(connection, responseReceived);

			Assert.assertNotEquals(AddResponseReceivedProcOperator.ROW_ID_ERROR, rowId);

			Statement outcome = responseReceivedTableOperator.getRowById(connection, rowId);
			ResultSet result = outcome.getResultSet();

			Assert.assertTrue(result.next());

			ResponseReceived insertedData = responseReceivedTableOperator.decodeRow(result);

			Assert.assertEquals(responseReceived, insertedData);

			outcome.close();
			connection.close();

		} catch (Throwable t) {
			t.printStackTrace();
			this.throwable = t;
		}

	}

	/**
	 * This test case will test the AddResponseReceived stored procedure. Will test
	 * the Success scenario by inserting record in a single thread.
	 * 
	 * @throws Exception
	 */

	@Test
	public void testResponseReceivedSingleThread() throws Exception {
		final int writerCount = 1;
		final int rowsPerWriter = 10;
		final CountDownLatch latch = new CountDownLatch(writerCount);
		ExecutorService executor = Executors.newFixedThreadPool(writerCount);

		for (int i = 0; i < writerCount; ++i) {
			ResponseReceivedMultipleRows writer = new ResponseReceivedMultipleRows(latch, rowsPerWriter);
			// ResponseReceivedMultipleRows writer = new ResponseReceivedMultipleRows(latch,
			// rowsPerWriter, responseReceivedGenerator,responseReceivedTableOperator);
			executor.execute(writer);
		}
		latch.await();
		Assert.assertEquals(0, latch.getCount());
	}

	/**
	 * This test case will test the AddResponseReceived stored procedure. Will test
	 * the Success scenario by inserting multiple records in a multiple thread.
	 * 
	 * @throws Exception
	 */

	@Test
	public void testResponseReceivedMultiThread() throws Exception {
		final int writerCount = 2;
		final int rowsPerWriter = 500;
		final CountDownLatch latch = new CountDownLatch(writerCount);
		ExecutorService executor = Executors.newFixedThreadPool(writerCount);

		for (int i = 0; i < writerCount; ++i) {
			ResponseReceivedMultipleRows writer = new ResponseReceivedMultipleRows(latch, rowsPerWriter);
			executor.execute(writer);
		}
		latch.await();
		Assert.assertEquals(0, latch.getCount());
	}

}
