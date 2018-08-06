package au.com.iwsoftware.deleteRows;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
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
import au.com.iwsoftware.sqlDbcon.AddRequestReceivedTest;
import au.com.iwsoftware.sqlDbcon.JdbcTools;
import au.com.iwsoftware.sqlDbcon.TableOperator;
import au.com.iwsoftware.sqlDbcon.delete_old_data.DeleteDataRequest;
import au.com.iwsoftware.sqlDbcon.delete_old_data.DeleteOldDataGenerator;
import au.com.iwsoftware.sqlDbcon.delete_old_data.DeleteOldDataInvalidDataProcOperator;
import au.com.iwsoftware.sqlDbcon.delete_old_data.DeleteOldDataProcOperator;
import au.com.iwsoftware.sqlDbcon.delete_old_data.DeleteOldDataTableOperator;
import au.com.iwsoftware.sqlDbcon.error_details.ErrorDetailsTableOperator;
import au.com.iwsoftware.sqlDbcon.processed_request.AddProcessedRequestProcOperator;
import au.com.iwsoftware.sqlDbcon.processed_request.ProcessedRequest;
import au.com.iwsoftware.sqlDbcon.processed_request.ProcessedRequestGenerator;
import au.com.iwsoftware.sqlDbcon.processed_request.ProcessedRequestTableOperator;
import au.com.iwsoftware.sqlDbcon.request_received.AddRequestReceivedProcOperator;
import au.com.iwsoftware.sqlDbcon.request_received.RequestReceived;
import au.com.iwsoftware.sqlDbcon.request_received.RequestReceivedGenerator;
import au.com.iwsoftware.sqlDbcon.request_received.RequestReceivedTableOperator;
import au.com.iwsoftware.sqlDbcon.response_received.AddResponseReceivedProcOperator;
import au.com.iwsoftware.sqlDbcon.response_received.ResponseReceived;
import au.com.iwsoftware.sqlDbcon.response_received.ResponseReceivedGenerator;
import au.com.iwsoftware.sqlDbcon.response_received.ResponseReceivedTableOperator;
import util.DataGenerator;

public class DeleteOldDataTest 
{

//	private static final String SCHEMA = "AIE";
	private static final String KEY_SCHEMA = "jdbc_schema";
	private static final int IN_ERROR = -1;
	private static final int NO_ROWS = 0;
	private static final int ONE_ROW = 1;
	private static final int INSERT_NUMBER_OF_ROWS= 10;
	private final Random random = new Random();
    private final DataGenerator dataGen = new DataGenerator();
	
	private Throwable throwable;
	private static final JdbcTools jdbcTools = new JdbcTools();
	private String SCHEMA = null;
	
	private RequestReceivedTableOperator requestReceivedTableOperator;
	private ProcessedRequestTableOperator processedRequestTableOperator;
	private ResponseReceivedTableOperator responseReceivedTableOperator; 
	private ErrorDetailsTableOperator errorDetailsTableOperator;
	private DeleteOldDataGenerator deleteOldDataGenerator;
	private ProcessedRequestGenerator processedRequestGenerator;
	private ResponseReceivedGenerator responseReceivedGenerator;
	
	private DeleteOldDataProcOperator deleteOldDataProcOperator;
	private DeleteOldDataInvalidDataProcOperator deleteOldDataInvalidDataProcOperator;
	private DeleteOldDataTableOperator deleteOldDataTableOperator;
	private AddRequestReceivedTest addRequestReceivedTest ;
	private RequestReceivedGenerator requestReceivedGenerator;
	private AddRequestReceivedProcOperator addRequestReceivedProcOperator;
	private AddProcessedRequestProcOperator addProcessedRequestProcOperator;
	private AddResponseReceivedProcOperator addResponseReceivedProcOperator;
	
	
	@Before
	public void setUp() throws Exception 
	{
//		Properties dbProperties = jdbcTools.getDbProperties();
		SCHEMA = jdbcTools.getDbProperties().getProperty(KEY_SCHEMA);
		
//		SCHEMA="AIE";
		processedRequestTableOperator = new ProcessedRequestTableOperator(SCHEMA);
		requestReceivedTableOperator = new RequestReceivedTableOperator(SCHEMA);
		responseReceivedTableOperator = new ResponseReceivedTableOperator(SCHEMA);
		errorDetailsTableOperator= new ErrorDetailsTableOperator(SCHEMA);
		
		deleteOldDataGenerator = new DeleteOldDataGenerator();
		deleteOldDataProcOperator = new DeleteOldDataProcOperator(SCHEMA);
		deleteOldDataTableOperator = new DeleteOldDataTableOperator(SCHEMA ); 
		addRequestReceivedProcOperator = new AddRequestReceivedProcOperator(SCHEMA);
		addProcessedRequestProcOperator = new AddProcessedRequestProcOperator(SCHEMA);
		addResponseReceivedProcOperator = new AddResponseReceivedProcOperator(SCHEMA);
		deleteOldDataInvalidDataProcOperator = new DeleteOldDataInvalidDataProcOperator(SCHEMA);
		
		addRequestReceivedTest = new AddRequestReceivedTest();
		requestReceivedGenerator = new RequestReceivedGenerator(random);
		responseReceivedGenerator = new ResponseReceivedGenerator(random);
		processedRequestGenerator = new ProcessedRequestGenerator(random);
		
		
	}

	/** 
	 * This method generates the past Date using the noOfDays parameter , dates will be generated relative to current date. 
	 * @param noOfDays
	 * @return
	 */
	private Date getDate(int noOfDays) 
	{
			final Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -noOfDays);

			return cal.getTime();

		
	}
	
	/**
	 * This method will delete all the records form the 'ErrorDetails' table
	 * @throws Exception
	 */
	
	public void deleteAllRecordsErrorTable() throws Exception 
	{

		try (Connection connection = jdbcTools.openDbconnection()) 
		{
			errorDetailsTableOperator.deleteRecordsInErrorTable(connection);
		}
	}

	/**
	 * This method will get the row count of the 'ErrorDetils' table
	 * @return rowCount
	 * @throws Exception
	 */
	public int queryRecordsErrorTable() throws Exception
	{
		int rowCount = 0;
		try (Connection connection = jdbcTools.openDbconnection()) 
		{

			rowCount = errorDetailsTableOperator.queryErrorTable(connection);
		}

		return rowCount;
	}
	
	
	/**
	 * This test case will test the Success scenario.
	 * 1. Insert records to the 3 Tables.
	 * 2. Call the stored procedure to delete the inserted records.
	 * @throws Exception
	 */
	@Test
	public void testDeleteSelectedData()  
	{

		int status =0;
		try (Connection connection = jdbcTools.openDbconnection()) 
		{
			DeleteDataRequest received = deleteOldDataGenerator.generateValidDeleteRequest(getDate(8));
			testInsertData(getDate(9));
			
			
//			insertAndAsserttheTables(connection, received);
			
			Assert.assertTrue(insertDataToTables(connection, received, processedRequestTableOperator) > NO_ROWS);
			Assert.assertTrue(insertDataToTables(connection, received, requestReceivedTableOperator) > NO_ROWS);
			Assert.assertTrue(insertDataToTables(connection, received, responseReceivedTableOperator) > NO_ROWS);

			status = deleteOldDataProcOperator.call(connection, received);
			if(status ==-1)
			{
				throw new Exception();
			}else
			{
				Assert.assertNotEquals(IN_ERROR, status);
			}
			
//			int afterCountReqReceived = requestReceivedTableOperator.getRowCountForDateRange(connection, received.getDateToBeDeleted());
//			int afterCountprocessedRequest = processedRequestTableOperator.getRowCountForDateRange(connection, received.getDateToBeDeleted());
//			int afterCountResponseReceived = responseReceivedTableOperator.getRowCountForDateRange(connection, received.getDateToBeDeleted());
			
			Assert.assertEquals(insertDataToTables(connection, received, processedRequestTableOperator), NO_ROWS);
			Assert.assertEquals(insertDataToTables(connection, received, requestReceivedTableOperator), NO_ROWS);
			Assert.assertEquals(insertDataToTables(connection, received, responseReceivedTableOperator), NO_ROWS);

		} catch (Exception e) 
		{
			e.printStackTrace();
			Assert.assertNotEquals(IN_ERROR, status);
		} 
	}

	
	/**
	 * Purpose of this test case is to test the failure scenario of the delete procedure.
	 * The method is to first insert no of records into the tables and clear the 'ErrorDetails' table and assert there are no rows,
	 * Then call the stored procedure to delete the inserted records which will fail due to exception .
	 * This failure will insert a record in 'ErrorDetails' table , test case will assert this . also the inserted records into 3 tables 
	 * will still remain due to error in deletion.Finally delete the records from all the tables.   
	 * @throws Exception
	 */
	@Test
	public void testDeleteSelectedDataFailure() 
	{

		int status = 0;
		try (Connection connection = jdbcTools.openDbconnection()) {
			DeleteDataRequest received= deleteOldDataGenerator.generateValidDeleteRequest(getDate(8));
			
			testInsertData(getDate(9));			
//			insertAndAsserttheTables(connection, received);
			
			Assert.assertTrue(insertDataToTables(connection, received, processedRequestTableOperator) > NO_ROWS);
			Assert.assertTrue(insertDataToTables(connection, received, requestReceivedTableOperator) > NO_ROWS);
			Assert.assertTrue(insertDataToTables(connection, received, responseReceivedTableOperator) > NO_ROWS);

			
			deleteAllRecordsErrorTable();

			status = deleteOldDataInvalidDataProcOperator.call(connection, received); 
			if(status == -1)
			{
				Assert.assertEquals(IN_ERROR, status);
			}else
			{
				throw new Exception();
			}
			
			int errorRowCount = queryRecordsErrorTable();
			Assert.assertEquals(errorRowCount , ONE_ROW );
			System.out.println("rec count : " + errorRowCount);
//			insertAndAsserttheTables(connection, received);
			
			Assert.assertTrue(insertDataToTables(connection, received, processedRequestTableOperator) > NO_ROWS);
			Assert.assertTrue(insertDataToTables(connection, received, requestReceivedTableOperator) > NO_ROWS);
			Assert.assertTrue(insertDataToTables(connection, received, responseReceivedTableOperator) > NO_ROWS);

			
//			Delete the rows from above inserts
//			int statusDeleted = deleteOldDataProcOperator.call(connection, received);
//			Assert.assertNotEquals(IN_ERROR, statusDeleted);

		} catch (Exception e) 
		{
			e.printStackTrace();
			Assert.assertEquals(IN_ERROR, status);
		}
	}
	
	
	public int insertDataToTables(Connection connection ,DeleteDataRequest received, TableOperator operator)  throws Exception
	{
//		int beforeCount = operator.getRowCountForDateRange(connection, received.getDateToBeDeleted());
		
		return operator.getRowCountForDateRange(connection, received.getDateToBeDeleted());
	}
	/**
	 * This method will insert records in the 3 tables and assert.
	 * @param connection
	 * @param received
	 * @throws Exception
	 */
/*	public void insertAndAsserttheTables(Connection connection ,DeleteDataRequest received) throws Exception 
	{
		
		
		int beforeCountReqReceived = requestReceivedTableOperator.getRowCountForDateRange(connection, received.getDateToBeDeleted());
		int beforeCountprocessedRequest = processedRequestTableOperator.getRowCountForDateRange(connection, received.getDateToBeDeleted());
		int beforeCountResponseReceived = responseReceivedTableOperator.getRowCountForDateRange(connection, received.getDateToBeDeleted());
		
		Assert.assertTrue(beforeCountReqReceived > NO_ROWS);
		Assert.assertTrue(beforeCountprocessedRequest > NO_ROWS);
		Assert.assertTrue(beforeCountResponseReceived > NO_ROWS);
	}*/
	/**
	 * This method inserts random records to 3 tables RequestReceived , ProcessedRequest, ResponseReceived .
	 * @param date
	 */
	public void testInsertData(Date date) 
	{

		// Date date = getDate(9);
		try (Connection connection = jdbcTools.openDbconnection()) 
		{

			for (int i = 0; i < INSERT_NUMBER_OF_ROWS; i++) 
			{
				RequestReceived requestReceived = requestReceivedGenerator.generateValidRequestReceived(date);
				addRequestReceivedProcOperator.call(connection, requestReceived);

				ProcessedRequest processedRequest = processedRequestGenerator.generateValidProcessedRequest(date);
				addProcessedRequestProcOperator.call(connection, processedRequest);

				ResponseReceived responseReceived = responseReceivedGenerator.generateValidResponseReceived(date);
				addResponseReceivedProcOperator.call(connection, responseReceived);

			}

			connection.close();

		} catch (Throwable t) 
		{
			t.printStackTrace();
			this.throwable = t;
		}
	} 
	
	@Test
	public void testDeleteSelectedDataWhileSymultaniousTableInsert() 
	{

		final int threadCountWriter = 2;
		final int rowsPerWriter = 500;
		int status = 0;
		try 
		{

			final CountDownLatch latch = new CountDownLatch(threadCountWriter * 3);
			ExecutorService executor = Executors.newFixedThreadPool(threadCountWriter * 3);

			multiThreadInsertExecutor(threadCountWriter, rowsPerWriter, latch, executor);

			status = deleteAllRecordsFromTables();
			if (status == -1) 
			{
				throw new Exception();
			} else 
			{
				Assert.assertNotEquals(IN_ERROR, status);
			}

			latch.await();
			Assert.assertEquals(0, latch.getCount());
		} catch (Exception e) 
		{
			e.printStackTrace();
			Assert.assertEquals(IN_ERROR, status);
		}
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
	
	public int deleteAllRecordsFromTables() throws Exception 
	{
		int status = 0;

		try (Connection connection = jdbcTools.openDbconnection()) 
		{
			DeleteDataRequest received = deleteOldDataGenerator.generateValidDeleteRequest(getDate(8));
			testInsertData(getDate(9));
			
			
//			insertAndAsserttheTables(connection, received);
			status = deleteOldDataProcOperator.call(connection, received);
		}
		return status;
	}
 
}
