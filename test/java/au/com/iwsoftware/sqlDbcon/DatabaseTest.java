package au.com.iwsoftware.sqlDbcon;

import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Assert;
import org.junit.Test;

public class DatabaseTest {//extends TestCase{

	public int FaultStatus = 0;
	public String FaultDetails = null;
	public String AIEId= null;
	public java.sql.Timestamp Timestamp = null;
	public String Body = "";
	public String Eventtype = null;
	public String AlertType = null;
	public java.util.Date today = new Date();
	public int State ;
	public Connection databaseConnection;

	
	public void valuesToInsert() {

		FaultStatus = 22;
		FaultDetails = "Calling Error";
		AIEId = "78fa9f69-d027-4fa5-b8c9-552d18445c67";
		Timestamp = new java.sql.Timestamp(new Date().getTime()); // getCurrentTimeStamp();
		Body = "<dependency>\n" + 
				"			<groupId>junit</groupId>\n" + 
				"			<artifactId>junit</artifactId>\n" + 
				"			<version>4.8.1</version>\n" + 
				"			<scope>test</scope>\n" + 
				"		</dependency>";
		Eventtype = "TagalertNotification";
		AlertType = "T001";
	}
	
/*	private static java.sql.Timestamp getCurrentTimeStamp() {

		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime());

	}
	*/
	
	
    private Random generator = new Random();
    int randomGenerator() {
            return generator.nextInt(10000);
    }
    
	/*public void createFreshDb() {

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
	}*/
	

    
//	@Test
	public void InsertBulkToProcessedRequest() {

		AIESqlCreation accessDb = new AIESqlCreation();

		valuesToInsert();
		Connection databaseConnection;
		try {
			databaseConnection = accessDb.openDbconnection();
			accessDb.InsertToProcessedRequest(databaseConnection, FaultStatus, FaultDetails, AIEId, Timestamp, Body,
					Eventtype, AlertType, State);

			accessDb.closeDbconnection(databaseConnection);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Test
	public void InsertANDReadToProcessedRequest() {

		try {
			CountDownLatch latch = new CountDownLatch(2);

//			DbInsertThread dbInsertThread = new DbInsertThread(latch);
//			DbReadThread dbReadThread = new DbReadThread(latch);

			ExecutorService executor = Executors.newFixedThreadPool(3); // 3 Threads in pool

	        for(int i=0; i < 1; i++) {
	        	executor.submit(new DbInsertThread(latch));
	        	executor.submit(new DbReadThread(latch));
	        }
//			new Thread(dbInsertThread).start();
//			new Thread(dbReadThread).start();
			latch.await();

			System.out.println("Latch count" + latch.getCount());

			Assert.assertEquals(0, latch.getCount());
			// Thread.sleep(400);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
//	@Test
	public void ReadDataRequestReceived() {
		CountDownLatch latch = new CountDownLatch(3);
		DbReadThread dbReadThread = new DbReadThread(latch);
//		dbReadThread.queryData();
		dbReadThread.run();
	}

//	@Test
	public void InsertMultiRowToProcessedRequest() {

		AIESqlCreation accessDb = new AIESqlCreation();
		int result = 100;
		valuesToInsert();
		AIEId = "78fa9f69-d027-7fa5-b8c9-752d18445A85";
//		AIEId = "78fa9f69-d027-4fa5-b8c9-552d18442961";
		Connection databaseConnection;
		try {
			databaseConnection = accessDb.openDbconnection();
//			createFreshDb();
			for (int i = 0; i < 100; i++) {
				AIEId = "78fa9f69-d027-4fa5-b8c9-552d1844" + randomGenerator();
//				System.out.println("No Gen : " + AIEId);
				result = accessDb.InsertToProcessedRequest(databaseConnection, FaultStatus, FaultDetails, AIEId,
						Timestamp, Body, Eventtype, AlertType, State);

			}

			System.out.println("RESULT" + result);
			Assert.assertEquals(1, result);
			accessDb.closeDbconnection(databaseConnection);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
//	@Test
	public void InsertRowToProcessedRequest() {

		AIESqlCreation accessDb = new AIESqlCreation();
		valuesToInsert();
		AIEId = "78fa9f69-d027-7fa5-b8c9-752d18445A85";
		Connection databaseConnection;
		try {
			databaseConnection = accessDb.openDbconnection();
//			createFreshDb();
			int result = accessDb.InsertToProcessedRequest(databaseConnection, FaultStatus, FaultDetails, AIEId, Timestamp, Body,
					Eventtype, AlertType, State);
			
			System.out.println("RESULT" + result);
			Assert.assertEquals(1, result);
			accessDb.closeDbconnection(databaseConnection);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
/*		@Test
	public void queryData() {
		// TODO Auto-generated method stub
		AIESqlCreation accessDb = new AIESqlCreation();
		Connection databaseConnection;
		Statement statement = null;

		try {
			databaseConnection = accessDb.openDbconnection();
			String selectTableSQL = "SELECT AIEId, Timestamp, Body, EventType, AlertType, FaultStatus, FaultDetails from  [AIE].[RequestReceived]";

			statement = databaseConnection.createStatement();

			ResultSet rs = statement.executeQuery(selectTableSQL);

			while (rs.next()) {

				String aieId = rs.getString("AIEId");
//				String username = rs.getString("USERNAME");

				System.out.println("aieId : " + aieId);
//				System.out.println("username : " + username);

			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/		
	
	
	@Test
	public void getDates() {
		 String months[] = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
			        "Sep", "Oct", "Nov", "Dec" };

			    Calendar calendar = Calendar.getInstance();

			    // Set the time and date information and display it.
			    calendar.add(Calendar.DATE,-2);
			    calendar.set(Calendar.HOUR, 10);
			    calendar.set(Calendar.MINUTE, 29);
			    calendar.set(Calendar.SECOND, 22);

			    System.out.print("Updated time: ");
			    System.out.print(calendar.get(Calendar.DATE) + ":");
			    System.out.print(calendar.get(Calendar.HOUR) + ":");
			    System.out.print(calendar.get(Calendar.MINUTE) + ":");
			    System.out.println(calendar.get(Calendar.SECOND));
			  }
	
	@Test
	public void testSelectStatement() {
		
		AIESqlCreation accessDb = new AIESqlCreation();
		valuesToInsert();
		
		Connection databaseConnection;
		try {
			databaseConnection = accessDb.openDbconnection();
//			createFreshDb();
//			accessDb.querySelectStatement(databaseConnection);
			accessDb.querySelectStatementForJoin(databaseConnection);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	@Test
	public void InsertNullToProcessedRequest() {

		AIESqlCreation accessDb = new AIESqlCreation();
		valuesToInsert();
		AIEId = "78fa9f69-d027-7fa5-b8c9-752d18445A85";
		Connection databaseConnection;
		try {
			databaseConnection = accessDb.openDbconnection();
//			createFreshDb();
			int result = accessDb.InsertToProcessedRequest(databaseConnection, FaultStatus, "", AIEId, Timestamp, "",
					"", "", State);
			System.out.println("RESULT" + result);
			Assert.assertEquals(1, result);
			accessDb.closeDbconnection(databaseConnection);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


//	@Test
	public void InsertNullToNotNullAIEIdProcessedRequest() {

		AIESqlCreation accessDb = new AIESqlCreation();
		valuesToInsert();
		AIEId = "NULL";

		try {
//			createFreshDb();
			databaseConnection = accessDb.openDbconnection();

			int result = accessDb.InsertToProcessedRequest(databaseConnection, FaultStatus, "", AIEId, Timestamp, "",
					"", "", State);

			System.out.println("RESULT" + result);
			if (result == 1)
				Assert.assertEquals(1, result);
			else
				Assert.assertEquals(-1, result);

//			 createFreshDb();
			accessDb.closeDbconnection(databaseConnection);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
//	@Test
	public void InsertLargeStringToBodyProcessedRequest() {

		AIESqlCreation accessDb = new AIESqlCreation();
		valuesToInsert();
		AIEId = "78fa9f69-d027-7fa5-b8c9-752d18225A85";
		String body = "<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" + 
				"	xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n" + 
				"	<modelVersion>4.0.0</modelVersion>\n" + 
				"\n" + 
				"	<groupId>au.com.iwsoftware</groupId>\n" + 
				"	<artifactId>sqlDbcon</artifactId>\n" + 
				"	<version>0.0.1-SNAPSHOT</version>\n" + 
				"	<packaging>jar</packaging>\n" + 
				"\n" + 
				"	<name>sqlDbcon</name>\n" + 
				"	<url>http://maven.apache.org</url>\n" + 
				"\n" + 
				"	<properties>\n" + 
				"		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>\n" + 
				"	</properties>\n" + 
				"\n" + 
				"	<dependencies>\n" + 
				"		<dependency>\n" + 
				"			<groupId>junit</groupId>\n" + 
				"			<artifactId>junit</artifactId>\n" + 
				"			<version>3.8.1</version>\n" + 
				"			<scope>test</scope>\n" + 
				"		</dependency>\n" + 
				"\n" + 
				"		<dependency>\n" + 
				"			<groupId>org.apache.ibatis</groupId>\n" + 
				"			<artifactId>ibatis-sqlmap</artifactId>\n" + 
				"			<version>2.3.0</version>\n" + 
				"		</dependency>\n" + 
				"\n" + 
				"		<dependency>\n" + 
				"			<groupId>junit</groupId>\n" + 
				"			<artifactId>junit</artifactId>\n" + 
				"			<version>4.8.1</version>\n" + 
				"			<scope>test</scope>\n" + 
				"		</dependency>\n" + 
				"		<!-- https://mvnrepository.com/artifact/com.microsoft.sqlserver/sqljdbc4 -->\n" + 
				"		<dependency>\n" + 
				"			<groupId>com.microsoft.sqlserver</groupId>\n" + 
				"			<artifactId>sqljdbc4</artifactId>\n" + 
				"			<version>4.0</version>\n" + 
				"			<scope>test</scope>\n" + 
				"		</dependency>\n" + 
				"\n" + 
				"		<!-- https://mvnrepository.com/artifact/org.hamcrest/hamcrest-core -->\n" + 
				"<!-- 		<dependency>\n" + 
				"			<groupId>org.hamcrest</groupId>\n" + 
				"			<artifactId>hamcrest-core</artifactId>\n" + 
				"			<version>1.3</version>\n" + 
				"			<scope>test</scope>\n" + 
				"		</dependency> -->\n" + 
				"\n" + 
				"		<!-- https://mvnrepository.com/artifact/net.sourceforge.jtds/jtds -->\n" + 
				"		<dependency>\n" + 
				"			<groupId>net.sourceforge.jtds</groupId>\n" + 
				"			<artifactId>jtds</artifactId>\n" + 
				"			<version>1.3.1</version>\n" + 
				"		</dependency>\n" + 
				"\n" + 
				"		<!-- <dependency> <groupId>org.hamcrest</groupId> <artifactId>hamcrest-all</artifactId> \n" + 
				"			<version>1.3</version> <scope>test</scope> </dependency> -->\n" + 
				"\n" + 
				"	</dependencies>\n" + 
				"</project>"
				+ "";

		try {
//			createFreshDb();
			databaseConnection = accessDb.openDbconnection();

			int result = accessDb.InsertToProcessedRequest(databaseConnection, FaultStatus, "", AIEId, Timestamp, body,
					"", "", State);

			System.out.println("RESULT" + result);
			if (result == 1)
				Assert.assertEquals(1, result);
			else
				Assert.assertEquals(1, result);

//			 createFreshDb();
			accessDb.closeDbconnection(databaseConnection);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testErrorToAddErrorDetails() {

		AIESqlCreation accessDb = new AIESqlCreation();
		valuesToInsert();
		AIEId = "78fa9f69-d027-7fa5-b8c9-752d18445A85";
		int result = 0;
		Connection databaseConnection;
		try {
//			createFreshDb();
			databaseConnection = accessDb.openDbconnection();

			result = accessDb.InsertToProcessedRequest(databaseConnection, FaultStatus, "", AIEId, Timestamp, "", "",
					"", State);
//			Assert.assertEquals(1, result);
			/*result = accessDb.InsertToProcessedRequest(databaseConnection, FaultStatus, "", AIEId, Timestamp, "", "",
					"", State);*/
			System.out.println("RESULT" + result);

			/*if (result == 1) {
				Assert.assertEquals(1, result);
			} else if (result == -1) {
				System.out.println("In ERROR");
				Assert.assertEquals(-1, result);
			}*/

			// createFreshDb();
			accessDb.closeDbconnection(databaseConnection);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
/*	@Test
	public void ConnectAndDisconnectFromDatabase()
			{
//			    DataAccess da = new DataAccess();
			    AIESqlCreation accessDb = new AIESqlCreation();
			    Connection databaseConnection;
				
				Boolean	cnn = accessDb.openDbconnection();

			    bool connected = da.Connect();

			    bool disconnected = da.Disconnect();

			    Assert.IsTrue(connected);

			    Assert.IsTrue(disconnected);
			}
	*/
/*	public void procInsert() {
//		ResultSet rs = null;
//		String insertTableSQCL = "INSERT INTO ProcessedRequest "
		
	      @FaultStatus int,
	      @FaultDetails nvarchar(250),
		  @AIEId nvarchar(40),
		  @Timestamp datetime,
		  @Body xml,
		  @Eventtype nvarchar(50),
		  @AlertType nvarchar(50)
		
		Connection conn;
		try {
			conn = accessDb.openDbconnection();
		
//		String SPsql = "EXEC AIE.AddRequestReceived ?,?";
		
		CallableStatement proc_stmt = conn.prepareCall("{ call AIE.AddRequestReceived(?,?,?,?,?,?,?) }");

		proc_stmt.setInt(1, 5);
		proc_stmt.setString(2, "Calling Error");
	    proc_stmt.setString(3, "78fa9f69-d027-4fa5-b8c9-552d18445c80");
	    proc_stmt.setString(4, "2018-03-05 15:14:01.727");
			proc_stmt.setString(5,"");
	    proc_stmt.setString(6, "TagalertNotification");
	    proc_stmt.setString(7, "T001");
	    
//	    ResultSet rs = proc_stmt.executeQuery();
	    
	    proc_stmt.executeUpdate();

		// get cursor and cast it to ResultSet
//		rs = (ResultSet) proc_stmt.getObject();
	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ClassNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	}*/
}
