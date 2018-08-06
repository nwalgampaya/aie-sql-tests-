package au.com.iwsoftware.sqlDbcon;

import java.sql.Connection;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import au.com.iwsoftware.ThreadClass.ProcessedRequestMultipleRows;
import au.com.iwsoftware.ThreadClass.QueryDataBaseMultipleRows;
import au.com.iwsoftware.ThreadClass.RequestReceivedMultipleRows;
import au.com.iwsoftware.ThreadClass.ResponseReceivedMultipleRows;
import au.com.iwsoftware.sqlDbcon.receive_all_data.AddReceiveAllDataProcOperator;
import au.com.iwsoftware.sqlDbcon.receive_all_data.DataReceivedGenerator;
import au.com.iwsoftware.sqlDbcon.receive_all_data.DataReceivedRequest;
import au.com.iwsoftware.sqlDbcon.receive_all_data.DataReceivedTableOperator;
import au.com.iwsoftware.sqlDbcon.request_received.RequestReceivedTableOperator;


public class GetLogDetailsTest {

	private static final String SCHEMA = "AIE";

	private static final JdbcTools jdbcTools = new JdbcTools();
	private DataReceivedTableOperator dataReceivedTableOperator;

	private AddReceiveAllDataProcOperator addReceiveAllDataProcOperator;

	private DataReceivedGenerator dataReceivedGenerator;
	private RequestReceivedTableOperator requestReceivedTableOperator;
	

	// private final Random random = new Random(34469733L);
	private final Random random = new Random();
	private Throwable throwable;

	@Before
	public void setUp() throws Exception {
		// databaseConnection = jdbcTools.openDbconnection();

		dataReceivedTableOperator = new DataReceivedTableOperator(SCHEMA);
		addReceiveAllDataProcOperator = new AddReceiveAllDataProcOperator(SCHEMA);
		dataReceivedGenerator = new DataReceivedGenerator(random);

		requestReceivedTableOperator = new RequestReceivedTableOperator(SCHEMA);
	}

	public String testDataReceiveEventType(Date startDate,Date endDate , String ColumnName) throws Exception {
		String passedValue = null;
		int maxRowId;
		try (Connection connection = jdbcTools.openDbconnection()) {
			 
			 passedValue = requestReceivedTableOperator.getMaxRowIdInDateRange(connection,startDate, endDate,ColumnName );
			connection.close();

		} catch (Throwable t) {
			t.printStackTrace();
			this.throwable = t;
		}
		return passedValue;

	}
	
	private Date dayBeforeyesterday() {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -2);
		cal.set(Calendar.HOUR_OF_DAY, 0);
//		cal.set(Calendar.MINUTE, 29);
//		cal.set(Calendar.SECOND, 22);
//		cal.add(Calendar.HOUR, 30);
		return cal.getTime();
	}
	
	private Date yesterday() {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}
	
	public int testRunSelectFromDataBase(long startTime, long endtTime, String eventType, String alertType,
			boolean success, boolean failure, boolean incommingFailure) throws Exception {

		int i = 0;
		int rowCount = 0;

		try (Connection connection = jdbcTools.openDbconnection()) {
			DataReceivedRequest dataReceived = dataReceivedGenerator.generateValidDataReceived(startTime, endtTime,
					eventType, alertType, success, failure, incommingFailure);

			rowCount = addReceiveAllDataProcOperator.call(connection, dataReceived);

			connection.close();

		} catch (Throwable t) {
			t.printStackTrace();
			this.throwable = t;
		}

		return rowCount;

	}
	
	/**
	 *
	 * This test case will test the insertion of data using multiple threads and retrieving from getLogDetails stored procedure simultaneously  
	 * 1. It will initially query the 'getLogDetails' passing value 'true' to IncommingFailure for selected date range and get the result count, 
	 * 2. then it starts inserting multiple records to the 3 tables using the 3 stored procedures , 
	 * 3. Simultaneously start querying the 'getLogDetails' passing value 'true' to IncommingFailure and check with the count received in step 1.
	 *
	 * @throws Exception
	 */
	@Test
	public void testAllTablesMultiThreadWithWhereIncommingFailure() throws Exception {

		long startTime = 0;
		long endTime = 0;
		String eventType = null;
		String alertType = null;
		boolean success = false;
		boolean failure = false;
		boolean incommingFailure = false;
		final int threadCountWriter = 2;
		final int rowsPerWriter = 100;
		final int rowsPerReader = 1;
		int rowCountBefore;
		Date startDate;
		Date endDate;
		String AlertTypeBefore = null;
		String AlertTypeAfter = null;

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");

		startDate = dateFormat.parse(dateFormat.format(dayBeforeyesterday()));
		endDate = dateFormat.parse(dateFormat.format(yesterday()));
		// startDate = dateFormat.parse("2018-03-21 11:00");
		// endDate = dateFormat.parse("2018-03-21 13:00");
		startTime = startDate.getTime();
		endTime = endDate.getTime();

		final CountDownLatch latch = new CountDownLatch(threadCountWriter * 3 + 1);
		ExecutorService executor = Executors.newFixedThreadPool(threadCountWriter * 3 + 1);

		incommingFailure = true;
		// eventType = testDataReceiveEventType(new Timestamp(startTime),new
		// Timestamp(endTime),"eventType");

		rowCountBefore = testRunSelectFromDataBase(startTime, endTime, eventType, alertType, success, failure,
				incommingFailure);

		multiThreadInsertExecutor(threadCountWriter, rowsPerWriter, latch, executor);

		QueryDataBaseMultipleRows writer4 = new QueryDataBaseMultipleRows(latch, rowsPerReader, startTime, endTime,
				eventType, alertType, success, failure, incommingFailure);

		executor.execute(writer4);

		latch.await();

		Assert.assertEquals(rowCountBefore, writer4.getRowCount());

		Assert.assertEquals(0, latch.getCount());

	}
	
	/**
	 *
	 * This test case will test the insertion of data using multiple threads and retrieving from getLogDetails stored procedure simultaneously  
	 * 1. It will initially query the 'getLogDetails' passing value 'true' to Failure for selected date range and get the result count, 
	 * 2. then it starts inserting multiple records to the 3 tables using the 3 stored procedures , 
	 * 3. Simultaneously start querying the 'getLogDetails' passing value 'true' to Failure and check with the count received in step 1.
	 *
	 * @throws Exception
	 */
	@Test
	public void testAllTablesMultiThreadWithWhereFailure() throws Exception {
		

		long startTime=0;
   	long endTime =0; 
   	String eventType = null;
   	String alertType = null;
   	boolean success = false;
   	boolean failure = false;
   	boolean incommingFailure = false;
		final int threadCountWriter = 2;
		final int rowsPerWriter = 50;
		final int rowsPerReader = 1;
		int rowCountBefore;
		Date startDate ;
		Date endDate;
		String AlertTypeBefore = null;
		String AlertTypeAfter = null;
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		
   	startDate = dateFormat.parse(dateFormat.format(dayBeforeyesterday()));
   	endDate = dateFormat.parse(dateFormat.format(yesterday()));
//   	startDate = dateFormat.parse("2018-03-21 11:00");
//   	endDate = dateFormat.parse("2018-03-21 13:00");
   	startTime = startDate.getTime();
   	endTime = endDate.getTime();
   	
   	
   	final CountDownLatch latch = new CountDownLatch(threadCountWriter*3+1);
		ExecutorService executor = Executors.newFixedThreadPool(threadCountWriter*3+1);
		
		failure = true;
//		eventType = testDataReceiveEventType(new Timestamp(startTime),new Timestamp(endTime),"eventType");
		
		rowCountBefore = testRunSelectFromDataBase(startTime, endTime,
				eventType, alertType, success, failure, incommingFailure);

		multiThreadInsertExecutor(threadCountWriter, rowsPerWriter, latch, executor);
		
		QueryDataBaseMultipleRows writer4 = new QueryDataBaseMultipleRows(latch, rowsPerReader, startTime, endTime,
				eventType, alertType, success, failure, incommingFailure);
		
		executor.execute(writer4);

		latch.await();
		
		Assert.assertEquals(rowCountBefore,writer4.getRowCount());
		
		Assert.assertEquals(0, latch.getCount());

	}
	
	/**
	 *
	 * This test case will test the insertion of data using multiple threads and retrieving from getLogDetails stored procedure simultaneously  
	 * 1. It will initially query the 'getLogDetails' passing value 'true' to 'Success' for selected date range and get the result count, 
	 * 2. then it starts inserting multiple records to the 3 tables using the 3 stored procedures , 
	 * 3. Simultaneously start querying the 'getLogDetails' passing value 'true' to 'Success' and check with the count received in step 1.
	 *
	 * @throws Exception
	 */
	@Test
	public void testAllTablesMultiThreadWithWhereSuccess() throws Exception {
		

		long startTime=0;
    	long endTime =0; 
    	String eventType = null;
    	String alertType = null;
    	boolean success = false;
    	boolean failure = false;
    	boolean incommingFailure = false;
		final int threadCountWriter = 2;
		final int rowsPerWriter = 100;
		final int rowsPerReader = 1;
		int rowCountBefore;
		Date startDate ;
		Date endDate;
		String AlertTypeBefore = null;
		String AlertTypeAfter = null;
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		
    	startDate = dateFormat.parse(dateFormat.format(dayBeforeyesterday()));
    	endDate = dateFormat.parse(dateFormat.format(yesterday()));
//    	startDate = dateFormat.parse("2018-03-21 11:00");
//    	endDate = dateFormat.parse("2018-03-21 13:00");
    	startTime = startDate.getTime();
    	endTime = endDate.getTime();
    	
    	
    	final CountDownLatch latch = new CountDownLatch(threadCountWriter*3+1);
		ExecutorService executor = Executors.newFixedThreadPool(threadCountWriter*3+1);
		
		success = true;
//		eventType = testDataReceiveEventType(new Timestamp(startTime),new Timestamp(endTime),"eventType");
		
		rowCountBefore = testRunSelectFromDataBase(startTime, endTime,
				eventType, alertType, success, failure, incommingFailure);

		multiThreadInsertExecutor(threadCountWriter, rowsPerWriter, latch, executor);
		
		QueryDataBaseMultipleRows writer4 = new QueryDataBaseMultipleRows(latch, rowsPerReader, startTime, endTime,
				eventType, alertType, success, failure, incommingFailure);
		
		executor.execute(writer4);

		latch.await();
		
		Assert.assertEquals(rowCountBefore,writer4.getRowCount());
		
		Assert.assertEquals(0, latch.getCount());

	}
	
	
	/**
	 *
	 * This test case will test the insertion of data using multiple threads and retrieving from getLogDetails stored procedure simultaneously  
	 * 1. It will initially query the 'getLogDetails' passing value 'true' to 'EventType' for selected date range and get the result count, 
	 * 2. then it starts inserting multiple records to the 3 tables using the 3 stored procedures , 
	 * 3. Simultaneously start querying the 'getLogDetails' passing value 'true' to 'EventType' and check with the count received in step 1.
	 *
	 * @throws Exception
	 */
	@Test
	public void testAllTablesMultiThreadWithWhereEventType() throws Exception {
		

		long startTime=0;
    	long endTime =0; 
    	String eventType = null;
    	String alertType = null;
    	boolean success = false;
    	boolean failure = false;
    	boolean incommingFailure = false;
		final int threadCountWriter = 2;
		final int rowsPerWriter = 100;
		final int rowsPerReader = 1;
		int rowCountBefore;
		Date startDate ;
		Date endDate;
		String AlertTypeBefore = null;
		String AlertTypeAfter = null;
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		
    	startDate = dateFormat.parse(dateFormat.format(dayBeforeyesterday()));
    	endDate = dateFormat.parse(dateFormat.format(yesterday()));
    	startTime = startDate.getTime();
    	endTime = endDate.getTime();
    	
    	final CountDownLatch latch = new CountDownLatch(threadCountWriter*3+1);
		ExecutorService executor = Executors.newFixedThreadPool(threadCountWriter*3+1);
		
    	
		eventType = testDataReceiveEventType(new Timestamp(startTime),new Timestamp(endTime),"eventType");
		
		rowCountBefore = testRunSelectFromDataBase(startTime, endTime,
				eventType, alertType, success, failure, incommingFailure);

		multiThreadInsertExecutor(threadCountWriter, rowsPerWriter, latch, executor);
		
		QueryDataBaseMultipleRows writer4 = new QueryDataBaseMultipleRows(latch, rowsPerReader, startTime, endTime,
				eventType, alertType, success, failure, incommingFailure);
		
		executor.execute(writer4);

		latch.await();
		
		Assert.assertEquals(rowCountBefore,writer4.getRowCount());
		
		Assert.assertEquals(0, latch.getCount());

	}
	
	/**
	 *
	 * This test case will test the insertion of data using multiple threads and retrieving from getLogDetails stored procedure simultaneously  
	 * 1. It will initially query the 'getLogDetails' passing value 'true' to 'AlertType' for selected date range and get the result count, 
	 * 2. then it starts inserting multiple records to the 3 tables using the 3 stored procedures , 
	 * 3. Simultaneously start querying the 'getLogDetails' passing value 'true' to 'AlertType' and check with the count received in step 1.
	 *
	 * @throws Exception
	 */
	@Test
	public void testAllTablesMultiThreadWithWhereAlertType() throws Exception {
		

		long startTime=0;
    	long endTime =0; 
    	String eventType = null;
    	String alertType = null;
    	boolean success = false;
    	boolean failure = false;
    	boolean incommingFailure = false;
		final int threadCountWriter = 2;
		final int rowsPerWriter = 100;
		final int rowsPerReader = 1;
		int rowCountBefore;
		Date startDate ;
		Date endDate;
		String AlertTypeBefore = null;
		String AlertTypeAfter = null;
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		
	 
//    	startTime = dateFormat.parse(dateFormat.format(dayBeforeyesterday())).getTime(); 
//    	endtTime = dateFormat.parse(dateFormat.format(yesterday())).getTime(); 
    	
    	
//    	startDate = dateFormat.parse("2018-03-21 11:00");
//    	endDate = dateFormat.parse("2018-03-21 13:00");
    	startDate = dateFormat.parse(dateFormat.format(dayBeforeyesterday()));
    	endDate = dateFormat.parse(dateFormat.format(yesterday()));
    	startTime = startDate.getTime();
    	endTime = endDate.getTime();
    	
    	
    	final CountDownLatch latch = new CountDownLatch(threadCountWriter*3+1);
		ExecutorService executor = Executors.newFixedThreadPool(threadCountWriter*3+1);
		
    	
		alertType = testDataReceiveEventType(new Timestamp(startTime),new Timestamp(endTime),"alertType");
		
		rowCountBefore = testRunSelectFromDataBase(startTime, endTime,
				eventType, alertType, success, failure, incommingFailure);

		multiThreadInsertExecutor(threadCountWriter, rowsPerWriter, latch, executor);
		
		QueryDataBaseMultipleRows writer4 = new QueryDataBaseMultipleRows(latch, rowsPerReader, startTime, endTime,
				eventType, alertType, success, failure, incommingFailure);
		
		executor.execute(writer4);
//		rowCountAfter =writer4.getLoopCount();

		latch.await();
		
		Assert.assertEquals(rowCountBefore,writer4.getRowCount());
		
		Assert.assertEquals(0, latch.getCount());

	}
	
	/**
	 * This test case will test the insertion of data using multiple threads and retrieving from getLogDetails stored procedure.
	 * @throws Exception
	 */
	@Test
	public void testAllTablesMultiThread() throws Exception {
		
    	String eventType = null;
    	String alertType = null;
    	final int threadCountWriter = 2;
		final int rowsPerWriter = 500;

		final CountDownLatch latch = new CountDownLatch(threadCountWriter*3);
		ExecutorService executor = Executors.newFixedThreadPool(threadCountWriter*3);
    	
		multiThreadInsertExecutor(threadCountWriter, rowsPerWriter, latch, executor);
		
		latch.await();
		
		Assert.assertEquals(0, latch.getCount());

	}
	
	/**
	 *
	 * This test case will test the insertion of data using multiple threads and retrieving from getLogDetails stored procedure simultaneously  
	 * 1. It will initially query the 'getLogDetails' selected date range and get the result count, 
	 * 2. then it starts inserting multiple records to the 3 tables using the 3 stored procedures , 
	 * 3. Simultaneously start querying the 'getLogDetails' passing the same date range as in step 1 and check with the count received in step 1.
	 *
	 * @throws Exception
	 */
	@Test
	public void testAllTablesMultiThreadWithSelect() throws Exception {
		

		long startTime=0;
    	long endTime =0; 
    	String eventType = null;
    	String alertType = null;
    	boolean success = false;
    	boolean failure = false;
    	boolean incommingFailure = false;
		final int threadCountWriter = 2;
		final int rowsPerWriter = 500;
		final int rowsPerReader = 1;
		int rowCountBefore;
		Date startDate ;
		Date endDate;
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		
	 
    	startTime = dateFormat.parse(dateFormat.format(dayBeforeyesterday())).getTime(); 
    	endTime = dateFormat.parse(dateFormat.format(yesterday())).getTime(); 
    	
//    	startDate = dateFormat.parse("2018-03-21 11:00");
//    	endDate = dateFormat.parse("2018-03-21 13:00");
//    	startTime = startDate.getTime();
//    	endTime = endDate.getTime();

    	
    	final CountDownLatch latch = new CountDownLatch(threadCountWriter*3+1);
		ExecutorService executor = Executors.newFixedThreadPool(threadCountWriter*3+1);
		
//		eventType = testDataReceiveEventType(new Timestamp(startTime),new Timestamp(endTime),"eventType");
		rowCountBefore = testRunSelectFromDataBase(startTime, endTime,
				eventType, alertType, success, failure, incommingFailure);

		multiThreadInsertExecutor(threadCountWriter, rowsPerWriter, latch, executor);
		
		QueryDataBaseMultipleRows writer4 = new QueryDataBaseMultipleRows(latch, rowsPerReader, startTime, endTime,
				eventType, alertType, success, failure, incommingFailure);
		
		executor.execute(writer4);

		latch.await();
		
		Assert.assertEquals(rowCountBefore,writer4.getRowCount());
		
		Assert.assertEquals(0, latch.getCount());

	}

	private void multiThreadInsertExecutor(int threadCountWriter, int rowsPerWriter, final CountDownLatch latch,
			ExecutorService executor) {
		for (int i = 0; i < threadCountWriter; ++i) {
			ProcessedRequestMultipleRows writer = new ProcessedRequestMultipleRows(latch, rowsPerWriter);
			executor.execute(writer);
			ResponseReceivedMultipleRows writer2 = new ResponseReceivedMultipleRows(latch, rowsPerWriter);
			executor.execute(writer2);
			RequestReceivedMultipleRows writer3 = new RequestReceivedMultipleRows(latch, rowsPerWriter);
			executor.execute(writer3);
		}
	}
}
