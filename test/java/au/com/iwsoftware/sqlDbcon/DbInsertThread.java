package au.com.iwsoftware.sqlDbcon;

import java.sql.Connection;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import org.junit.Assert;

/**
 * 
 * @author
 *
 */
public class DbInsertThread implements Runnable {

	public int FaultStatus = 0;
	public String FaultDetails = null;
	public String AIEId = null;
	public java.sql.Timestamp Timestamp = null;
	public String Body = "";
	public String Eventtype = null;
	public String AlertType = null;
	public java.util.Date today = new Date();
	public int State;
	public Connection databaseConnection;
	public CountDownLatch latch = null;

	public DbInsertThread(CountDownLatch latch) {
		 this.latch = latch;
	}

	public void run() {
		try {
            Thread.sleep(100);
//            this.latch.countDown();
            insertDataToRequestReceived();
//            Thread.sleep(100);
            this.latch.countDown();

//            Thread.sleep(10);
//            this.latch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	}
	public void insertDataToRequestReceived() {

		System.out.println("In thread");
		AIESqlCreation accessDb = new AIESqlCreation();
		int result = 100;
		valuesToInsert();
		AIEId = "78fa9f69-d027-7fa5-b8c9-752d18445A85";
		// AIEId = "78fa9f69-d027-4fa5-b8c9-552d18442961";
		Connection databaseConnection;
		try {
			databaseConnection = accessDb.openDbconnection();
			createFreshDb();
			for (int i = 0; i < 1000; i++) {
				AIEId = "78fa9f69-d027-4fa5-b8c9-552d1844" + randomGenerator();
				// System.out.println("No Gen : " + AIEId);
				result = accessDb.InsertToProcessedRequest(databaseConnection, FaultStatus, FaultDetails, AIEId,
						Timestamp, Body, Eventtype, AlertType, State);

			}
			
//System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"+ latch.getCount());

			System.out.println("RESULT" + result);
			Assert.assertEquals(1, result);
			accessDb.closeDbconnection(databaseConnection);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void valuesToInsert() {

		FaultStatus = 22;
		FaultDetails = "Calling Error";
		AIEId = "78fa9f69-d027-4fa5-b8c9-552d18445c67";
		Timestamp = new java.sql.Timestamp(new Date().getTime()); // getCurrentTimeStamp();
		Body = "<dependency>\n" + "			<groupId>junit</groupId>\n" + "			<artifactId>junit</artifactId>\n"
				+ "			<version>4.8.1</version>\n" + "			<scope>test</scope>\n" + "		</dependency>";
		Eventtype = "TagalertNotification";
		AlertType = "T001";
	}

	private Random generator = new Random();

	int randomGenerator() {
		return generator.nextInt(10000);
	}

	public void createFreshDb() {

		try {

			AIESqlCreation accessDb = new AIESqlCreation();
			databaseConnection = accessDb.openDbconnection();
			int result = 0;

			accessDb.deleteTable(databaseConnection, "ErrorDetails");
			result = accessDb.deleteTable(databaseConnection, "RequestReceived");

			accessDb.closeDbconnection(databaseConnection);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
