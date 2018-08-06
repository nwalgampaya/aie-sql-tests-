package au.com.iwsoftware.sqlDbcon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.ibatis.common.jdbc.ScriptRunner;

public class AIESqlCreation {
	
//	private static final String jdbcURL = "jdbc:sqlserver://localhost:1433;databasename=AdventureWorksDW2014;integratedSecurity=true;";
//	private static final String jdbcURL = "jdbc:sqlserver://10.0.7.32:1433;" + "databaseName=AIELogging1;user=sa;password=78ll78$$34hh";
	
//	private static final String jdbcURL = "jdbc:sqlserver://10.0.7.32:1433;" + "databaseName=ASCOM;user=sa;password=78ll78$$34hh";
	/*SKH-ASCN-POC-MS\ASCOM
	SKH-ASCN-POC-MS\ASCOM
*/	
//	jdbc:jtds:sqlserver
//	AIELogging1
	public static final String DB_URL = "jdbc:jtds:sqlserver://10.0.7.32/AIEJavaLoggingNew";

	   //  Database credentials
	public static final String USER = "sa";
	public static final String PASS = "78ll78$$34hh";
	   
	public static void main(String[] args) throws ClassNotFoundException, SQLException {

	
	AIESqlCreation aieDb = new AIESqlCreation();
	Connection databaseConnection = aieDb.openDbconnection();
//	aieDb.createDataBase(databaseConnection);
//	aieDb.CreateSchema(databaseConnection);
	
//	aieDb.runSqlScript(databaseConnection);
//	aieDb.CreateSchema(databaseConnection);
	aieDb.closeDbconnection(databaseConnection);
	}

	public Connection openDbconnection() throws ClassNotFoundException {

		Connection databaseConnection = null;
		try {
			// Connect to the database
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//			databaseConnection = DriverManager.getConnection(jdbcURL);
			
//			databaseConnection = java.sql.DriverManager.getConnection(getConnectionUrl(), userName, password);
			databaseConnection = DriverManager.getConnection(DB_URL, USER, PASS);
//			System.out.println("Connected to the database");

			
		} catch (SQLException err) {
			System.err.println("Error connecting to the database");
			err.printStackTrace(System.err);
			System.exit(0);
		}
//		System.out.println("Program finished");
		
		return databaseConnection;
	}
	public void closeDbconnection(Connection databaseConnection) throws ClassNotFoundException {
		try {
			databaseConnection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void querySelectStatementForJoin(Connection databaseConnection ) throws ParseException {
		
		int rs = 0;
		Calendar.getInstance();
				
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
    	Date startDate = dateFormat.parse("2018-03-14 13:00");
    	Date endDate = dateFormat.parse("2018-03-16 13:00");
    	long startTime = startDate.getTime();
    	long endtTime = endDate.getTime();
    	
		try {
//			 openDbconnection();
			CallableStatement proc_stmt = databaseConnection
					.prepareCall("{ call AIE.getLogDetails(?,?) }");
			
//			proc_stmt.setString(1,"78fa9f69d0274fa5b8c9552d18441908");
			System.out.println("Start Time : " + new Timestamp(startTime)  );
			proc_stmt.setTimestamp(1,new Timestamp(startTime) );
			proc_stmt.setTimestamp(2,new Timestamp(endtTime) );
			
			
//			System.out.println("AIEId " + AIEId);
			proc_stmt.executeQuery();
			
//			String row = proc_stmt.getString(1);
//			proc_stmt.getResultSet();
//			System.out.println("row" + row);
			ResultSet res = proc_stmt.getResultSet();
			 while (res.next()) {
				 System.out.println(res.getTimestamp("ReceivedRequestTime")+ "\n" +res.getString("AlertType") + "\n" + res.getString("CallId")  );
			 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}	
		
	}
	public void querySelectStatement(Connection databaseConnection ) {
		
		int rs = 0;
		try {
//			 openDbconnection();
			CallableStatement proc_stmt = databaseConnection
					.prepareCall("{ call AIE.TestSelect(?) }");
			
			proc_stmt.setString(1,"78fa9f69d0274fa5b8c9552d18441908");
			
//			System.out.println("AIEId " + AIEId);
			proc_stmt.executeQuery();
			
//			String row = proc_stmt.getString(1);
//			proc_stmt.getResultSet();
//			System.out.println("row" + row);
			ResultSet res = proc_stmt.getResultSet();
			 while (res.next()) {
				 System.out.println("  "+res.getString("AlertType") + "\n" + res.getString("AIEId")  );
			 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}	
		
	}
	
	public int InsertToProcessedRequest(Connection databaseConnection, int FaultStatus, String FaultDetails,
			String AIEId, Timestamp Timestamp, String Body, String Eventtype, String AlertType,int state) {
		int rs = 0;
		try {

			CallableStatement proc_stmt = databaseConnection
					.prepareCall("{ call AIE.AddRequestReceived(?,?,?,?,?,?,?,?) }");
			
			System.out.println("AIEId " + AIEId);

			proc_stmt.setInt(1, FaultStatus);
			proc_stmt.setString(2, FaultDetails);
			if(AIEId.equals("NULL"))
				proc_stmt.setNull(3, Types.VARCHAR);
			else
				proc_stmt.setString(3, AIEId);
				
			proc_stmt.setTimestamp(4, Timestamp);
			proc_stmt.setInt(5, FaultStatus);
			proc_stmt.setString(6, Eventtype);
			proc_stmt.setString(7, AlertType);
			proc_stmt.registerOutParameter(8,Types.INTEGER);
			
			proc_stmt.executeUpdate();
			
			rs = proc_stmt.getInt(8);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return rs;
	}
	
	/*
    @FaultStatus int,
    @FaultDetails nvarchar(250),
	  @AIEId nvarchar(40),
	  @Timestamp datetime,
	  @Body xml,
	  @Eventtype nvarchar(50),
	  @AlertType nvarchar(50)*/
	
	/*public int InsertToProcessedRequest(Connection databaseConnection, int FaultStatus, String FaultDetails,
			String AIEId, Timestamp Timestamp, String Body, String Eventtype, String AlertType,int state) {
		int rs = 0;
		try {

			CallableStatement proc_stmt = databaseConnection
					.prepareCall("{ call AIE.AddRequestReceived(?,?,?,?,?,?,?,?) }");

			proc_stmt.setInt(1, FaultStatus);
			proc_stmt.setString(2, FaultDetails);
			proc_stmt.setString(3, AIEId);
			proc_stmt.setTimestamp(4, Timestamp);
			proc_stmt.setString(5, Body);
			proc_stmt.setString(6, Eventtype);
			proc_stmt.setString(7, AlertType);
			proc_stmt.registerOutParameter(8,Types.INTEGER);
			
//			 ResultSet rs = proc_stmt.executeQuery();

			 proc_stmt.executeUpdate();
			 ResultSet result= proc_stmt.getGeneratedKeys();
			 System.out.println("RESULT result.getInt(1)" + result.getInt(1));
			 
			 rs =proc_stmt.getInt(8);
			 
			 System.out.println("Returned value"+proc_stmt.getInt(8));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return rs;
	}*/
	
	public int deleteTable(Connection databaseConnection,String tableName) {
		// declare the statement object
		Statement sqlStatement;
		String sql= null;
		int rs = 0;
		
		try {
			sqlStatement = databaseConnection.createStatement();
			
//			String sql = "DELETE FROM ";
			if(tableName == "ErrorDetails") {
				sql = "DELETE FROM AIE.ErrorDetails";
			}else if(tableName == "RequestReceived")
				sql = "DELETE FROM AIE.RequestReceived";
			
			rs= sqlStatement.executeUpdate(sql);
			
			/*sqlStatement = databaseConnection.prepareStatement("DELETE FROM AIE.ErrorDetails");
			sqlStatement.setString(1, tableName);
			
			ResultSet rs = sqlStatement.executeQuery();*/
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
		// declare the result set
//		ResultSet rs = null;
	}
	public void createDataBase(Connection databaseConnection) {
		// declare the statement object
		Statement sqlStatement;
		try {
			sqlStatement = databaseConnection.createStatement();
			String sql = "CREATE DATABASE AIEJavaLogging";
			sqlStatement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// declare the result set
		ResultSet rs = null;
	}
	
	public void CreateSchema(Connection databaseConnection) {

		try {
			Statement sta = databaseConnection.createStatement();
			int count = sta.executeUpdate("CREATE SCHEMA AIE");
			System.out.println("Schema created.");
			sta.close();

			databaseConnection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void runSqlScript(Connection databaseConnection) throws ClassNotFoundException,SQLException {
		
			System.out.println("in runSql Script");
//			/home/nalinwalgampaya/Nalin/Material/Work/Dev/AIE/TestCases/TestScript
			File file = new File("/home/nalinwalgampaya/Nalin/Material/Work/Dev/AIE/TestCases/TestScript/FaultDetailsTab.sql");
//			String aSQLScriptFilePath = "../nalinwalgampaya/Nalin/Material/Work/Dev/AIE/FaultDetailsTab.sql";
			String  aSQLScriptFilePath = file.getAbsolutePath();
			
			Statement stmt = null;

			try {
				// Initialize object for ScripRunner
				ScriptRunner sr = new ScriptRunner(databaseConnection, false, false);

				// Give the input file to Reader
				Reader reader = new BufferedReader(
	                               new FileReader(aSQLScriptFilePath));

				// Exctute script
				sr.runScript(reader);

			} catch (Exception e) {
				System.err.println("Failed to Execute" + aSQLScriptFilePath
						+ " The error is " + e.getMessage());
			}
		}	
	
	
	/*	public void runSqlScript() {
	try {
		
		String aSQLScriptFilePath = "path/to/sql/script.sql";
		
		Process p = Runtime.getRuntime().exec("psql -U username -d dbname -h serverhost -f scripfile.sql");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}*/

}
