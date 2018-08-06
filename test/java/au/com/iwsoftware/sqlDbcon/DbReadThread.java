package au.com.iwsoftware.sqlDbcon;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.CountDownLatch;

public class DbReadThread implements Runnable {

	// public void run() {

	/*
	 * public static void main(String[] args) { DbReadThread dbInsertThread =new
	 * DbReadThread();
	 * 
	 * dbInsertThread.queryData(); }
	 */
	
	public CountDownLatch latch = null;

	public DbReadThread(CountDownLatch latch) {
		 this.latch = latch;
	}
	
	
	public void run() {
		try {
//			latch.await();
			Thread.sleep(300);
			readDataFromRequestReceived();
			this.latch.countDown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("Waiter Released");
	}

	public void readDataFromRequestReceived() {
		// TODO Auto-generated method stub
		AIESqlCreation accessDb = new AIESqlCreation();
		Connection databaseConnection;
		Statement statement = null;
		int count ;

		try {
			databaseConnection = accessDb.openDbconnection();

//			String selectTableSQL = "SELECT TOP 5 AIEId, Timestamp, Body, EventType, AlertType, FaultStatus, FaultDetails from  [AIE].[RequestReceived] ORDER BY NEWID()";
			String selectTableSQL = "SELECT count(*) from  [AIE].[RequestReceived] ";

			statement = databaseConnection.createStatement();
			for (int i = 0; i < 3000; i++) {
				ResultSet rs = statement.executeQuery(selectTableSQL);

				while (rs.next()) {

					
					    count = rs.getInt(1);
					
//					String aieId = rs.getString("AIEId");
					// String username = rs.getString("USERNAME");

					System.out.println("count @@@@@@@@@@@@ : " + count);
					// System.out.println("username : " + username);

				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
