/*package au.com.iwsoftware.sqlDbcon.receive_all_data;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import org.junit.Assert;

import au.com.iwsoftware.sqlDbcon.JdbcTools;
import au.com.iwsoftware.sqlDbcon.error_details.ErrorDetailsTableOperator;
import au.com.iwsoftware.sqlDbcon.processed_request.AddProcessedRequestProcOperator;
import au.com.iwsoftware.sqlDbcon.processed_request.ProcessedRequestGenerator;
import au.com.iwsoftware.sqlDbcon.processed_request.ProcessedRequestTableOperator;
import au.com.iwsoftware.sqlDbcon.request_received.AddRequestReceivedProcOperator;
import au.com.iwsoftware.sqlDbcon.request_received.RequestReceivedGenerator;
import au.com.iwsoftware.sqlDbcon.request_received.RequestReceivedTableOperator;
import au.com.iwsoftware.sqlDbcon.response_received.AddResponseReceivedProcOperator;
import au.com.iwsoftware.sqlDbcon.response_received.ResponseReceivedGenerator;
import au.com.iwsoftware.sqlDbcon.response_received.ResponseReceivedTableOperator;

public class QueryDataBaseMultipleRows implements Runnable {
	
	int entries;
	int returnLoopCount;
	Throwable throwable;
	CountDownLatch latch;
	Statement outcome;
	long startTime=0;
	long endtTime =0; 
	String eventType = null;
	String alertType = null;
	boolean success = false;
	boolean failure = false;
	boolean incommingFailure = false;
	long rowCountOut =0;
	
	QueryDataBaseMultipleRows(CountDownLatch latch, int entries, long startTime, long endtTime, String eventType,
			String alertType, boolean success, boolean failure, boolean incommingFailure) {
		
		this.latch = latch;
		this.entries = entries;
		this.startTime=	 startTime;
		this.endtTime =	 endtTime ;	
		this.eventType = eventType;	
		this.alertType = alertType;	
		this.success = success;		
		this.failure = failure;		
		this.incommingFailure = incommingFailure;				
	} 
	
	QueryDataBaseMultipleRows(CountDownLatch latch, int entries, long startTime, long endtTime, String eventType,
			String alertType, boolean success, boolean failure, boolean incommingFailure , long rowCount) {
		
		this.latch = latch;
		this.entries = entries;
		this.startTime=	 startTime;
		this.endtTime =	 endtTime ;	
		this.eventType = eventType;	
		this.alertType = alertType;	
		this.success = success;		
		this.failure = failure;		
		this.incommingFailure = incommingFailure;
		this.rowCountOut= rowCount;
	}

	public void run() {
		try {
			for (int i = 0; i < entries; ++i) {
				try (Connection connection = JdbcTools.openDbconnection()) {
					DataReceivedRequest dataReceived = data.dataReceivedGenerator.generateValidDataReceived(startTime, endtTime,
							eventType, alertType, success, failure, incommingFailure);

					int rowCountIn = data.addReceiveAllDataProcOperator.call(connection, dataReceived);

			            System.out.println("rowCount " + rowCountIn) ;
			            
						connection.close();
				
						Assert.assertEquals(rowCountOut, rowCountIn);
						System.out.println(" rowCount SELECT Query: " + rowCountIn);
						returnLoopCount++;
			}
			}
		} catch (Throwable t) {
			t.printStackTrace();
			this.throwable = t;
		} finally {
			latch.countDown();
		}
	}

public int getLoopCount() {
	return returnLoopCount;
}
}
}*/