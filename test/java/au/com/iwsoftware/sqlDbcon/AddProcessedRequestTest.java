
 *  DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *  
 *  Copyright (c) 2018 IWSoftware Pty Ltd.
 *  All rights reserved.
 

package au.com.iwsoftware.sqlDbcon;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import au.com.iwsoftware.ThreadClass.ProcessedRequestMultipleRows;
import au.com.iwsoftware.ThreadClass.QueryDataBaseMultipleRows;
import au.com.iwsoftware.ThreadClass.RequestReceivedMultipleRows;
import au.com.iwsoftware.ThreadClass.ResponseReceivedMultipleRows;
import au.com.iwsoftware.sqlDbcon.error_details.ErrorDetailsTableOperator;
import au.com.iwsoftware.sqlDbcon.processed_request.AddProcessedRequestProcOperator;
import au.com.iwsoftware.sqlDbcon.processed_request.ProcessedRequest;
import au.com.iwsoftware.sqlDbcon.processed_request.ProcessedRequestGenerator;
import au.com.iwsoftware.sqlDbcon.processed_request.ProcessedRequestTableOperator;
import au.com.iwsoftware.sqlDbcon.receive_all_data.AddReceiveAllDataProcOperator;
import au.com.iwsoftware.sqlDbcon.receive_all_data.DataReceivedGenerator;
import au.com.iwsoftware.sqlDbcon.receive_all_data.DataReceivedRequest;
import au.com.iwsoftware.sqlDbcon.receive_all_data.DataReceivedResponse;
import au.com.iwsoftware.sqlDbcon.receive_all_data.DataReceivedTableOperator;
import au.com.iwsoftware.sqlDbcon.request_received.AddRequestReceivedProcOperator;
import au.com.iwsoftware.sqlDbcon.request_received.RequestReceived;
import au.com.iwsoftware.sqlDbcon.request_received.RequestReceivedGenerator;
import au.com.iwsoftware.sqlDbcon.request_received.RequestReceivedTableOperator;
import au.com.iwsoftware.sqlDbcon.response_received.AddResponseReceivedProcOperator;
import au.com.iwsoftware.sqlDbcon.response_received.ResponseReceived;
import au.com.iwsoftware.sqlDbcon.response_received.ResponseReceivedGenerator;
import au.com.iwsoftware.sqlDbcon.response_received.ResponseReceivedTableOperator;

/**
 * Test cases for the {@code AddProcessedRequest} stored procedure.
 * 
 */
public class AddProcessedRequestTest 
{
    private static final String SCHEMA = "AIE";
    
    private static final JdbcTools jdbcTools = new JdbcTools();
    private Connection databaseConnection;
    
    private ProcessedRequestTableOperator processedRequestTable;
    private ErrorDetailsTableOperator errorDetailsTable;
    private RequestReceivedTableOperator requestReceivedTableOperator;
    private ResponseReceivedTableOperator responseReceivedTableOperator;
    private DataReceivedTableOperator dataReceivedTableOperator;
    
    private AddProcessedRequestProcOperator addProcessedRequestOperator;
    private AddRequestReceivedProcOperator addRequestRecievedOperator;
    private AddResponseReceivedProcOperator addResponseRecievedOperator; 
    private AddReceiveAllDataProcOperator addReceiveAllDataProcOperator;
    
    private RequestReceivedGenerator requestReceivedGenerator;
    private ProcessedRequestGenerator processedRequestGenerator;
    private ResponseReceivedGenerator responseReceivedGenerator;
    private DataReceivedGenerator dataReceivedGenerator;
    
//    private final Random random = new Random(34469733L);
    private final Random random = new Random();
    private Throwable throwable;
    @Before
    public void setUp() throws Exception
    {
//        databaseConnection = jdbcTools.openDbconnection();
        
        processedRequestTable = new ProcessedRequestTableOperator(SCHEMA);
        errorDetailsTable = new ErrorDetailsTableOperator(SCHEMA);
        requestReceivedTableOperator = new RequestReceivedTableOperator(SCHEMA);
        responseReceivedTableOperator = new ResponseReceivedTableOperator(SCHEMA);
        dataReceivedTableOperator = new DataReceivedTableOperator(SCHEMA);
        
        addProcessedRequestOperator = new AddProcessedRequestProcOperator(SCHEMA);
        addRequestRecievedOperator = new AddRequestReceivedProcOperator(SCHEMA);
        addResponseRecievedOperator  = new AddResponseReceivedProcOperator(SCHEMA);
        addReceiveAllDataProcOperator = new AddReceiveAllDataProcOperator(SCHEMA);
        
        requestReceivedGenerator = new RequestReceivedGenerator(random);
        processedRequestGenerator = new ProcessedRequestGenerator(random);
        responseReceivedGenerator = new ResponseReceivedGenerator(random);
        dataReceivedGenerator = new DataReceivedGenerator(random);
        
    }
    
//    @After
//    public void tearDown() throws Exception
//    {
//     databaseConnection.close();
//    }
    


    Start Select Statement Test
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



//	@Test
	public String testDataReceiveEventType(Date startDate,Date endDate , String ColumnName) throws Exception {
		String passedValue = null;
		int maxRowId;
		try (Connection connection = jdbcTools.openDbconnection()) {
			 
			 passedValue = requestReceivedTableOperator.getMaxRowIdInDateRange(connection,startDate, endDate,ColumnName );
			System.out.println(" Max Row " + passedValue);
			connection.close();

		} catch (Throwable t) {
			t.printStackTrace();
			this.throwable = t;
		}
		return passedValue;

	}
	End Select Statement Test
	
	/** Start RequestReceived Test Cases **/
	
	/**
	 * This test case will test the AddRequestReceived stored procedure.
	 * It will test the column lengths of the table by passing invalid lengths.
	 * @throws Exception
	 */
	@Test
	public void testRequestReceivedInvalidvalues() throws Exception {

		try (Connection connection = jdbcTools.openDbconnection()) {
			RequestReceived requestReceived = requestReceivedGenerator.generateInValidRequestReceived();
			int rowId = addRequestRecievedOperator.call(connection, requestReceived);
			System.out.println("requestReceived : " + requestReceived);

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
	 * This test case will test the AddRequestReceived stored procedure.
	 * Will test the Success senario by inserting single record to the table.
	 * @throws Exception
	 */
	@Test
	public void testRequestReceivedOneRecord() throws Exception {

		try (Connection connection = jdbcTools.openDbconnection()) {
			RequestReceived requestReceived = requestReceivedGenerator.generateValidRequestReceived();
			int rowId = addRequestRecievedOperator.call(connection, requestReceived);

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
	 * This test case will test the AddRequestReceived stored procedure.
	 * Will test the Success senario by inserting record in a single thread.
	 * @throws Exception
	 */
	@Test
	public void testRequestReceivedSingleThread() throws Exception {
		final int writerCount = 1;
		final int rowsPerWriter = 10;
		final CountDownLatch latch = new CountDownLatch(writerCount);
		ExecutorService executor = Executors.newFixedThreadPool(writerCount);

		for (int i = 0; i < writerCount; ++i) {
			RequestReceivedMultipleRows writer = new RequestReceivedMultipleRows(latch,rowsPerWriter);
			executor.execute(writer);
		}
		latch.await();
		Assert.assertEquals(0, latch.getCount());
	}

	/**
	 * This test case will test the AddRequestReceived stored procedure.
	 * Will test the Success senario by inserting multiple records in a multiple thread.
	 * @throws Exception
	 */
	@Test
	public void testRequestReceivedMultiThread() throws Exception {
        final int writerCount =5;
        final int rowsPerWriter = 100;
        final CountDownLatch latch = new CountDownLatch(writerCount );
        ExecutorService executor = Executors.newFixedThreadPool(writerCount );
        
        
        for (int i = 0 ; i < writerCount ; ++i)
        {
        	RequestReceivedMultipleRows writer = new RequestReceivedMultipleRows(latch, rowsPerWriter);
        	executor.execute(writer);
        }
        latch.await();
        Assert.assertEquals(0, latch.getCount());

	}

	//	End Request Received Test Cases
    

	//	Start Response Received Test Cases
	
	/**
	 * This test case will test the AddResponseReceived stored procedure.
	 * It will test the column lengths of the table by passing invalid lengths.
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
	 * This test case will test the AddResponseReceived stored procedure.
	 * Will test the Success scenario by inserting single record to the table.
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
	 * This test case will test the AddResponseReceived stored procedure.
	 * Will test the Success scenario by inserting record in a single thread.
	 * @throws Exception
	 */

  @Test
	public void testResponseReceivedSingleThread() 	throws Exception {
		final int writerCount = 1;
		final int rowsPerWriter = 10;
		final CountDownLatch latch = new CountDownLatch(writerCount);
		ExecutorService executor = Executors.newFixedThreadPool(writerCount);

		for (int i = 0; i < writerCount; ++i) {
			ResponseReceivedMultipleRows writer = new ResponseReceivedMultipleRows(latch, rowsPerWriter);
//			ResponseReceivedMultipleRows writer = new ResponseReceivedMultipleRows(latch, rowsPerWriter, responseReceivedGenerator,responseReceivedTableOperator);
			executor.execute(writer);
		}
		latch.await();
		Assert.assertEquals(0, latch.getCount());
	}

	/**
	 * This test case will test the AddResponseReceived stored procedure.
	 * Will test the Success scenario by inserting multiple records in a multiple thread.
	 * @throws Exception
	 */
  
    @Test
	public void testResponseReceivedMultiThread() 	throws Exception {
		final int writerCount = 5;
		final int rowsPerWriter = 50;
		final CountDownLatch latch = new CountDownLatch(writerCount);
		ExecutorService executor = Executors.newFixedThreadPool(writerCount);

		for (int i = 0; i < writerCount; ++i) {
			ResponseReceivedMultipleRows writer = new ResponseReceivedMultipleRows(latch, rowsPerWriter);
			executor.execute(writer);
		}
		latch.await();
		Assert.assertEquals(0, latch.getCount());
	}	
	
//	END Response Received Test Cases
	
//	START Processed RequesTest Cases
    
	/**
	 * This test case will test the AddProcessedRequest stored procedure.
	 * It will test the column lengths of the table by passing invalid lengths.
	 * @throws Exception
	 */

    @Test
    public void generateInValidProcessedRequest() throws Exception {
    	
    	
    	try (Connection connection = jdbcTools.openDbconnection()) {
			ProcessedRequest received = processedRequestGenerator.generateInValidProcessedRequest();
			int rowId = addProcessedRequestOperator.call(connection, received);
			Assert.assertNotEquals(AddProcessedRequestProcOperator.ROW_ID_ERROR, rowId);

			Statement outcome = processedRequestTable.getRowById(connection, rowId);
			ResultSet result = outcome.getResultSet();

			Assert.assertTrue(result.next());

			ProcessedRequest insertedData = processedRequestTable.decodeRow(result);
			Assert.assertNotEquals(received, insertedData);
			outcome.close();
			connection.close();
    	}	
    }
	
	/**
	 * This test case will test the ProcessedRequest stored procedure.
	 * Will test the Success scenario by inserting single record to the table.
	 * @throws Exception
	 */	
    
    @Test
	public void testProcessedRequestOneRecord() throws Exception {
		final int writerCount = 1;
		final int rowsPerWriter = 1;
		
		testProcessedRequest(writerCount,rowsPerWriter);

	}
    
	/**
	 * This test case will test the ProcessedRequest stored procedure.
	 * Will test the Success scenario by inserting record in a single thread.
	 * @throws Exception
	 */
	 
	@Test
	public void testProcessedRequestSingleThread() throws Exception {
		final int writerCount = 1;
		final int rowsPerWriter = 10;
		
		testProcessedRequest(writerCount,rowsPerWriter);

	}
	
	/**
	 * This test case will test the ProcessedRequest stored procedure.
	 * Will test the Success scenario by inserting multiple records in a multiple thread.
	 * @throws Exception
	 */
	
	@Test
	public void testProcessedRequestMultiThread() throws Exception 
	{
		final int writerCount = 5;
		final int rowsPerWriter = 10;

		testProcessedRequest(writerCount, rowsPerWriter);

	}

	public void testProcessedRequest(int writerCount, int rowsPerWriter) throws Exception {

		final CountDownLatch latch = new CountDownLatch(writerCount );
		ExecutorService executor = Executors.newFixedThreadPool(writerCount);

		for (int i = 0; i < writerCount; ++i) {
			
			ProcessedRequestMultipleRows writer = new ProcessedRequestMultipleRows(latch, rowsPerWriter);
            executor.execute(writer);
        }
        latch.await();
        
        Assert.assertEquals(0, latch.getCount());
	}
	
//	END Processed RequesTest Cases
	
	
	
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
	
	@Test
	public void testAllTablesSingleThread() throws Exception {
		
    	
    	final int threadCountWriter = 1;
		final int rowsPerWriter = 10;
		
    	
    	final CountDownLatch latch = new CountDownLatch(threadCountWriter*3);
		ExecutorService executor = Executors.newFixedThreadPool(threadCountWriter*3);
		
		multiThreadInsertExecutor(threadCountWriter, rowsPerWriter, latch, executor);
		
		latch.await();
		Assert.assertEquals(0, latch.getCount());
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
//    	startDate = dateFormat.parse("2018-03-21 11:00");
//    	endDate = dateFormat.parse("2018-03-21 13:00");
    	startTime = startDate.getTime();
    	endTime = endDate.getTime();
    	
    	
    	final CountDownLatch latch = new CountDownLatch(threadCountWriter*3+1);
		ExecutorService executor = Executors.newFixedThreadPool(threadCountWriter*3+1);
		
		incommingFailure = true;
//		eventType = testDataReceiveEventType(new Timestamp(startTime),new Timestamp(endTime),"eventType");
		
		rowCountBefore = testRunSelectFromDataBase(startTime, endTime,
				eventType, alertType, success, failure, incommingFailure);
		System.out.println("rowCountBefore " + rowCountBefore);

		System.out.println("rowCountBefore : " + eventType);
		multiThreadInsertExecutor(threadCountWriter, rowsPerWriter, latch, executor);
		
		QueryDataBaseMultipleRows writer4 = new QueryDataBaseMultipleRows(latch, rowsPerReader, startTime, endTime,
				eventType, alertType, success, failure, incommingFailure);
		
		System.out.println("Starting thread queryDataBaseMultipleRows");
		executor.execute(writer4);

		latch.await();
		System.out.println("LOOP COUNT:  " + writer4.getRowCount());
		
		Assert.assertEquals(rowCountBefore,writer4.getRowCount());
		
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
		
//    	startDate = dateFormat.parse(dateFormat.format(dayBeforeyesterday()));
//    	endDate = dateFormat.parse(dateFormat.format(yesterday()));
    	startDate = dateFormat.parse("2018-03-21 11:00");
    	endDate = dateFormat.parse("2018-03-21 13:00");
    	startTime = startDate.getTime();
    	endTime = endDate.getTime();
    	
    	
    	final CountDownLatch latch = new CountDownLatch(threadCountWriter*3+1);
		ExecutorService executor = Executors.newFixedThreadPool(threadCountWriter*3+1);
		
		failure = true;
//		eventType = testDataReceiveEventType(new Timestamp(startTime),new Timestamp(endTime),"eventType");
		
		rowCountBefore = testRunSelectFromDataBase(startTime, endTime,
				eventType, alertType, success, failure, incommingFailure);
		System.out.println("rowCountBefore " + rowCountBefore);

		System.out.println("rowCountBefore : " + eventType);
		multiThreadInsertExecutor(threadCountWriter, rowsPerWriter, latch, executor);
		
		QueryDataBaseMultipleRows writer4 = new QueryDataBaseMultipleRows(latch, rowsPerReader, startTime, endTime,
				eventType, alertType, success, failure, incommingFailure);
		
		System.out.println("Starting thread queryDataBaseMultipleRows");
		executor.execute(writer4);

		latch.await();
		System.out.println("LOOP COUNT:  " + writer4.getRowCount());
		
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
		final int rowsPerWriter = 50;
		final int rowsPerReader = 1;
		int rowCountBefore;
		Date startDate ;
		Date endDate;
		String AlertTypeBefore = null;
		String AlertTypeAfter = null;
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		
//    	startDate = dateFormat.parse(dateFormat.format(dayBeforeyesterday()));
//    	endDate = dateFormat.parse(dateFormat.format(yesterday()));
    	startDate = dateFormat.parse("2018-03-21 11:00");
    	endDate = dateFormat.parse("2018-03-21 13:00");
    	startTime = startDate.getTime();
    	endTime = endDate.getTime();
    	
    	
    	final CountDownLatch latch = new CountDownLatch(threadCountWriter*3+1);
		ExecutorService executor = Executors.newFixedThreadPool(threadCountWriter*3+1);
		
		success = true;
//		eventType = testDataReceiveEventType(new Timestamp(startTime),new Timestamp(endTime),"eventType");
		
		rowCountBefore = testRunSelectFromDataBase(startTime, endTime,
				eventType, alertType, success, failure, incommingFailure);
		System.out.println("rowCountBefore " + rowCountBefore);

		System.out.println("rowCountBefore : " + eventType);
		multiThreadInsertExecutor(threadCountWriter, rowsPerWriter, latch, executor);
		
		QueryDataBaseMultipleRows writer4 = new QueryDataBaseMultipleRows(latch, rowsPerReader, startTime, endTime,
				eventType, alertType, success, failure, incommingFailure);
		
		System.out.println("Starting thread queryDataBaseMultipleRows");
		executor.execute(writer4);

		latch.await();
		System.out.println("LOOP COUNT:  " + writer4.getRowCount());
		
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
    	
    	System.out.println("endDate :" + endDate );
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
		final int rowsPerWriter = 20;
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
    	
//    	alertType = "gR";
//    	eventType = "zzylflyLB6TfOML5Z";

    	
    	final CountDownLatch latch = new CountDownLatch(threadCountWriter*3+1);
		ExecutorService executor = Executors.newFixedThreadPool(threadCountWriter*3+1);
		
    	
//		alertType = testDataReceivedAlertype();
		alertType = testDataReceiveEventType(new Timestamp(startTime),new Timestamp(endTime),"alertType");
		
		rowCountBefore = testRunSelectFromDataBase(startTime, endTime,
				eventType, alertType, success, failure, incommingFailure);
		System.out.println("rowCountBefore " + rowCountBefore);

		System.out.println("rowCountBefore : " + alertType);
		multiThreadInsertExecutor(threadCountWriter, rowsPerWriter, latch, executor);
		
		QueryDataBaseMultipleRows writer4 = new QueryDataBaseMultipleRows(latch, rowsPerReader, startTime, endTime,
				eventType, alertType, success, failure, incommingFailure);
		
		System.out.println("Starting thread queryDataBaseMultipleRows");
		executor.execute(writer4);
//		rowCountAfter =writer4.getLoopCount();

		latch.await();
		System.out.println("LOOP COUNT:  " + writer4.getRowCount());
		
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
		final int rowsPerWriter = 100;
		final int rowsPerReader = 1;
		int rowCountBefore;
		Date startDate ;
		Date endDate;
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		
	 
//    	startTime = dateFormat.parse(dateFormat.format(dayBeforeyesterday())).getTime(); 
//    	endTime = dateFormat.parse(dateFormat.format(yesterday())).getTime(); 
    	
    	startDate = dateFormat.parse("2018-03-21 11:00");
    	endDate = dateFormat.parse("2018-03-21 13:00");
    	startTime = startDate.getTime();
    	endTime = endDate.getTime();

    	
    	final CountDownLatch latch = new CountDownLatch(threadCountWriter*3+1);
		ExecutorService executor = Executors.newFixedThreadPool(threadCountWriter*3+1);
		
//		eventType = testDataReceiveEventType(new Timestamp(startTime),new Timestamp(endTime),"eventType");
		rowCountBefore = testRunSelectFromDataBase(startTime, endTime,
				eventType, alertType, success, failure, incommingFailure);

		System.out.println("rowCountBefore : " + rowCountBefore);
		multiThreadInsertExecutor(threadCountWriter, rowsPerWriter, latch, executor);
		
		QueryDataBaseMultipleRows writer4 = new QueryDataBaseMultipleRows(latch, rowsPerReader, startTime, endTime,
				eventType, alertType, success, failure, incommingFailure);
		
		System.out.println("Starting thread queryDataBaseMultipleRows");
		executor.execute(writer4);

		latch.await();
		System.out.println("LOOP COUNT:  " + writer4.getRowCount());
		
		Assert.assertEquals(rowCountBefore,writer4.getRowCount());
		
		Assert.assertEquals(0, latch.getCount());

	}
	
	private void multiThreadInsertExecutor(int threadCountWriter, int rowsPerWriter, final CountDownLatch latch,
			ExecutorService executor) {
		for (int i = 0; i < threadCountWriter; ++i) {
			ProcessedRequestMultipleRows writer = new ProcessedRequestMultipleRows(latch, rowsPerWriter);
			System.out.println("Starting thread ProcessedRequestMultipleRows");
			executor.execute(writer);
			ResponseReceivedMultipleRows writer2 = new ResponseReceivedMultipleRows(latch, rowsPerWriter);
			System.out.println("Starting thread ResponseReceivedMultipleRows");
			executor.execute(writer2);
			RequestReceivedMultipleRows writer3 = new RequestReceivedMultipleRows(latch, rowsPerWriter);
			System.out.println("Starting thread RequestReceivedMultipleRows");
			executor.execute(writer3);
		}
	}
	
	
	//	END Processed RequesTest Cases

	

//    @Test
    public void testSimultaneousAccess() throws Exception
    {
        final int writerCount = 3;
        final int readerCount = 1;
        final int rowsPerWriter = 2;
        
        final CountDownLatch latch = new CountDownLatch(writerCount + readerCount);
        List<ProcessedRequestWriter> writers = new ArrayList<>(writerCount);
        List<ProcessedRequestReader> readers = new ArrayList<>(writerCount);
        ExecutorService executor = Executors.newFixedThreadPool(writerCount + readerCount);
        for (int i = 0 ; i < writerCount ; ++i)
        {
            ProcessedRequestWriter writer = new ProcessedRequestWriter(latch, rowsPerWriter);
            writers.add(writer);
            executor.execute(writer);
        }
        for (int i = 0 ; i < readerCount ; ++i)
        {
            ProcessedRequestReader reader = new ProcessedRequestReader(latch, rowsPerWriter * writerCount);
            readers.add(reader);
            executor.execute(reader);
        }
        latch.await(10, TimeUnit.SECONDS);
        
        for (ProcessedRequestWriter writer : writers)
        {
            if (writer.throwable != null)
            {
                writer.throwable.printStackTrace(System.err);
                System.err.flush();
            }
            Assert.assertNull(writer.throwable);
        }
        for (ProcessedRequestReader reader : readers)
        {
            if (reader.throwable != null)
            {
                reader.throwable.printStackTrace(System.err);
                System.err.flush();
            }
            Assert.assertNull(reader.throwable);
        }
        
        Assert.assertEquals(0, latch.getCount());
//        Assert.assertEquals(rowsPerWriter * writerCount, processedRequestTable.size(databaseConnection));
    }
    
 
    
    private class ProcessedRequestWriter implements Runnable
    {
        Throwable throwable;
        CountDownLatch latch;
        
        int entries;
        
        ProcessedRequestWriter(CountDownLatch latch, int entries)
        {
            this.latch = latch;
            this.entries = entries;
        }
        
        @Override
        public void run()
        {
            try
            {
                try (Connection connection = jdbcTools.openDbconnection())
                {
                    for (int i = 0 ; i < entries ; ++i)
                    {
                        RequestReceived received = requestReceivedGenerator.generateValidRequestReceived();
                        Assert.assertNotEquals(-1, addRequestRecievedOperator.call(connection, received));
//                        ProcessedRequest request = processedRequestGenerator.generateValidProcessedRequest(received.getAieId());
//                        Assert.assertNotEquals(-1, addProcessedRequestOperator.call(connection, request));
                    }
                }
            }
            catch (Throwable t)
            {
                this.throwable = t;
            }
            finally
            {
                latch.countDown();
            }
        }
    }
    
    private class ProcessedRequestReader implements Runnable
    {
        Throwable throwable;
        CountDownLatch latch;
        
        int expectedFinalCount;
        
        ProcessedRequestReader(CountDownLatch latch, int expectedFinalCount)
        {
            this.latch = latch;
            this.expectedFinalCount = expectedFinalCount;
        }
        
        @Override
        public void run()
        {
            try
            {
                try (Connection connection = jdbcTools.openDbconnection())
                {
                    while (processedRequestTable.size(connection) < expectedFinalCount)
                    {
                        try (Statement statement = processedRequestTable.getAllRows(connection))
                        {
                            System.out.println("Rows: " + statement.getResultSet().getFetchSize());
                        }
                    }
                }
            }
            catch (Throwable t)
            {
                this.throwable = t;
            }
            finally
            {
                latch.countDown();
            }
        }
    }
	
	private class ProcessedRequestMultipleRows implements Runnable {

		int entries;
		Throwable throwable;
		CountDownLatch latch;
		Statement outcome;

		ProcessedRequestMultipleRows(CountDownLatch latch, int entries) {
			this.latch = latch;
			this.entries = entries;
		}

		@Override
		public void run() {
			try {
				for (int i = 0; i < entries; ++i) {
					try (Connection connection = jdbcTools.openDbconnection()) {
						ProcessedRequest received = processedRequestGenerator.generateValidProcessedRequest();
						int rowId = addProcessedRequestOperator.call(connection, received);

						outcome = processedRequestTable.getRowById(connection, rowId);
						ResultSet result = outcome.getResultSet();

						Assert.assertTrue(result.next());
						
						while(result.next()) {

						ProcessedRequest insertedData = processedRequestTable.decodeRow(result);
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

	private class ResponseReceivedMultipleRows implements Runnable {

		int entries;
		Throwable throwable;
		CountDownLatch latch;
		Statement outcome;

		ResponseReceivedMultipleRows(CountDownLatch latch, int entries) {
			this.latch = latch;
			this.entries = entries;

		}

		@Override
		public void run() {
			try {
				for (int i = 0; i < entries; ++i) {
					try (Connection connection = jdbcTools.openDbconnection()) {
						ResponseReceived responseReceived = responseReceivedGenerator.generateValidResponseReceived();
						int rowId = addResponseRecievedOperator.call(connection, responseReceived);

						outcome = responseReceivedTableOperator.getRowById(connection, rowId);
						ResultSet result = outcome.getResultSet();


						while(result.next()) {
						ResponseReceived insertedData = responseReceivedTableOperator.decodeRow(result);
						}
						connection.close();
						
					}
				}
				outcome.close();
			} catch (Throwable t) {
				t.printStackTrace();
				this.throwable = t;
			} finally {
				latch.countDown();
			}
		}
	}
	
	private class RequestReceivedMultipleRows implements Runnable {

		int entries;
		Throwable throwable;
		CountDownLatch latch;
		Statement outcome;

		RequestReceivedMultipleRows(CountDownLatch latch, int entries) {
			this.latch = latch;
			this.entries = entries;
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

		
	private class queryDataBaseMultipleRows implements Runnable {

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

		queryDataBaseMultipleRows(CountDownLatch latch, int entries, long startTime, long endtTime, String eventType,
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
}