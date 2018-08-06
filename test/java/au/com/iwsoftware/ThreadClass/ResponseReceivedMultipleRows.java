package au.com.iwsoftware.ThreadClass;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import au.com.iwsoftware.sqlDbcon.JdbcTools;
import au.com.iwsoftware.sqlDbcon.response_received.AddResponseReceivedProcOperator;
import au.com.iwsoftware.sqlDbcon.response_received.ResponseReceived;
import au.com.iwsoftware.sqlDbcon.response_received.ResponseReceivedGenerator;
import au.com.iwsoftware.sqlDbcon.response_received.ResponseReceivedTableOperator;

public class ResponseReceivedMultipleRows implements Runnable {

	int entries;
	Throwable throwable;
	CountDownLatch latch;
	Statement outcome;
	private static final JdbcTools jdbcTools = new JdbcTools();
	private AddResponseReceivedProcOperator addResponseRecievedOperator;	 
	private ResponseReceivedGenerator responseReceivedGenerator;
	private final Random random = new Random();    
	private ResponseReceivedTableOperator responseReceivedTableOperator;
	private static final String SCHEMA = "AIE";
	
	public ResponseReceivedMultipleRows(CountDownLatch latch, int entries) {
		this.latch = latch;
		this.entries = entries;
		responseReceivedGenerator = new ResponseReceivedGenerator(random);
		responseReceivedTableOperator = new ResponseReceivedTableOperator(SCHEMA);
		addResponseRecievedOperator  = new AddResponseReceivedProcOperator(SCHEMA);

	}
	

	@Override
	public void run() {
		try {
			for (int i = 0; i < entries; ++i) {
				try (Connection connection = jdbcTools.openDbconnection()) {
					ResponseReceived responseReceived = responseReceivedGenerator.generateValidResponseReceived();
//					System.out.println("connection" + connection);
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
