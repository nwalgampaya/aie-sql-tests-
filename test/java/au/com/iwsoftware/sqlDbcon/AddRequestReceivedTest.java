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

import au.com.iwsoftware.ThreadClass.RequestReceivedMultipleRows;
import au.com.iwsoftware.sqlDbcon.request_received.AddRequestReceivedProcOperator;
import au.com.iwsoftware.sqlDbcon.request_received.RequestReceived;
import au.com.iwsoftware.sqlDbcon.request_received.RequestReceivedGenerator;
import au.com.iwsoftware.sqlDbcon.request_received.RequestReceivedTableOperator;

public class AddRequestReceivedTest {

	private static final String SCHEMA = "AIE";

	private static final JdbcTools jdbcTools = new JdbcTools();
	private RequestReceivedTableOperator requestReceivedTableOperator;

	private AddRequestReceivedProcOperator addRequestReceivedProcOperator;

	private RequestReceivedGenerator requestReceivedGenerator;

	// private final Random random = new Random(34469733L);
	private final Random random = new Random();
	private Throwable throwable;

	@Before
	public void setUp() throws Exception {
		// databaseConnection = jdbcTools.openDbconnection();

		requestReceivedTableOperator = new RequestReceivedTableOperator(SCHEMA);
		addRequestReceivedProcOperator = new AddRequestReceivedProcOperator(SCHEMA);
		requestReceivedGenerator = new RequestReceivedGenerator(random);

	}

	/**
	 * This test case will test the AddRequestReceived stored procedure. It will
	 * test the column lengths of the table by passing invalid lengths.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRequestReceivedInvalidvalues() throws Exception {

		try (Connection connection = jdbcTools.openDbconnection()) {
			RequestReceived requestReceived = requestReceivedGenerator.generateInValidRequestReceived();
			int rowId = addRequestReceivedProcOperator.call(connection, requestReceived);

			Assert.assertNotEquals(AddRequestReceivedProcOperator.ROW_ID_ERROR, rowId);

			Statement outcome = requestReceivedTableOperator.getRowById(connection, rowId);
			ResultSet result = outcome.getResultSet();

			Assert.assertTrue(result.next());

			RequestReceived insertedData = requestReceivedTableOperator.decodeRow(result);

			Assert.assertNotEquals(requestReceived, insertedData);
			outcome.close();
			connection.close();

		} catch (Throwable t) {
			t.printStackTrace();
			this.throwable = t;
		}

	}

	/**
	 * This test case will test the AddRequestReceived stored procedure. Will test
	 * the Success senario by inserting single record to the table.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRequestReceivedOneRecord() throws Exception {

		try (Connection connection = jdbcTools.openDbconnection()) {
			RequestReceived requestReceived = requestReceivedGenerator.generateValidRequestReceived();
			int rowId = addRequestReceivedProcOperator.call(connection, requestReceived);

			Assert.assertNotEquals(AddRequestReceivedProcOperator.ROW_ID_ERROR, rowId);

			Statement outcome = requestReceivedTableOperator.getRowById(connection, rowId);
			ResultSet result = outcome.getResultSet();

			Assert.assertTrue(result.next());

			RequestReceived insertedData = requestReceivedTableOperator.decodeRow(result);

			Assert.assertEquals(requestReceived, insertedData);

			outcome.close();
			connection.close();

		} catch (Throwable t) {
			t.printStackTrace();
			this.throwable = t;
		}

	}

	/**
	 * This test case will test the AddRequestReceived stored procedure. Will test
	 * the Success senario by inserting record in a single thread.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRequestReceivedSingleThread() {
		final int writerCount = 1;
		final int rowsPerWriter = 10;
		final CountDownLatch latch = new CountDownLatch(writerCount);
		ExecutorService executor = Executors.newFixedThreadPool(writerCount);
		try {

			for (int i = 0; i < writerCount; ++i) {
				RequestReceivedMultipleRows writer = new RequestReceivedMultipleRows(latch, rowsPerWriter);
				executor.execute(writer);
			}
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertEquals(0, latch.getCount());
	}

	/**
	 * This test case will test the AddRequestReceived stored procedure. Will test
	 * the Success senario by inserting multiple records in a multiple thread.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRequestReceivedMultiThread() throws Exception {
		final int writerCount = 2;
		final int rowsPerWriter = 500;
		final CountDownLatch latch = new CountDownLatch(writerCount);
		ExecutorService executor = Executors.newFixedThreadPool(writerCount);
		try {
			for (int i = 0; i < writerCount; ++i) {
				RequestReceivedMultipleRows writer = new RequestReceivedMultipleRows(latch, rowsPerWriter);
				executor.execute(writer);
			}
			latch.await();
		} catch (Throwable t) {
			t.printStackTrace();
			this.throwable = t;
		}
		Assert.assertEquals(0, latch.getCount());

	}
}
