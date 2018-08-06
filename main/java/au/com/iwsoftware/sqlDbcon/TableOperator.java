/*
 *  DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *  
 */

package au.com.iwsoftware.sqlDbcon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

/**
 *
 */
public class TableOperator 
{
    private final String schemaName;
    private final String tableName;
    
    public TableOperator(String schemaName, String tableName)
    {
        this.schemaName = schemaName;
        this.tableName = tableName;
    }
    
    public String getTableReference()
    {
        return String.format("%s.%s", schemaName, tableName);
    }
    
    /**
     * Get a row count of the database table.
     * 
     * @param dbConnection An active database connection
     * @return The current row count
     * @throws Exception 
     */
    public int size(Connection dbConnection) throws Exception
    {
        try (Statement statement = dbConnection.createStatement())
        {
            try (ResultSet result = statement.executeQuery("SELECT COUNT(*) FROM " + getTableReference() + ";"))
            {
                result.next();
                
                return result.getInt(1);
            }
        }
    }
    
    public void clear(Connection dbConnection) throws Exception
    {
        try (Statement statement = dbConnection.createStatement())
        {
            statement.execute("DELETE FROM " + getTableReference() + ";");
        }
    }
    
    public Statement getAllRows(Connection dbConnection) throws Exception
    {
        Statement statement = dbConnection.createStatement();
//        statement.executeQuery("SELECT * FROM " + getTableReference() + ";");
        String Qru = "SELECT * FROM " + getTableReference() + ";" ;
//        statement.executeQuery("SELECT * FROM " + getTableReference() + ";");
        return statement;
    }
    
    public Statement getRowById(Connection dbConnection, int id) throws Exception
    {
        Statement statement = dbConnection.createStatement();
        statement.executeQuery(String.format("SELECT * FROM %s WHERE id=%d;", getTableReference(), id));
        return statement;
    }
    
    
    public String getMaxRowIdInDateRange(Connection dbConnection,Date startDate, Date endDate,String columnName ) throws Exception
    {
    	
    	String passedValue= null;
    	String preparedstmtInsert = String.format("SELECT %s FROM %s WHERE id= (SELECT max(id) FROM %s WHERE Timestamp between ? and ?);" ,columnName, getTableReference(), getTableReference());
    	PreparedStatement stmt= dbConnection.prepareStatement(preparedstmtInsert);
    	
    	stmt.setDate(1, new java.sql.Date(startDate.getTime()));
    	stmt.setDate(2, new java.sql.Date(endDate.getTime()));
    	
    	ResultSet resultSet =stmt.executeQuery();
    	
		while (resultSet.next()) 
		{

			passedValue = resultSet.getString(columnName);
		}
    	
		
		return passedValue;
    	
    }


    public int getRowCountForDateRange(Connection dbConnection, Date deleteDate) throws Exception
    {
    	
    	String preparedStatement = String.format("SELECT count(*) recCount FROM %s WHERE Timestamp < ? ;", getTableReference() );
    	
    	
    	PreparedStatement stmt= dbConnection.prepareStatement(preparedStatement);
    	int count =0; 
    	
    	stmt.setDate(1, new java.sql.Date(deleteDate.getTime()));
    	
    	ResultSet resultSet =stmt.executeQuery();
    	
		while (resultSet.next()) 
		{
			count = resultSet.getInt("recCount");
		}
    	return count;
    	
    }
    
/*	public int deleteRecordsInErrorTable(Connection dbConnection) throws Exception {

		String preparedStatement = String.format("DELETE FROM %s ;", getTableReference());

		PreparedStatement stmt = dbConnection.prepareStatement(preparedStatement);

		int resultSet = stmt.executeUpdate();

		return resultSet;

	}
    
	public int queryErrorTable(Connection dbConnection) throws Exception {
		int count = 0;
		String preparedStatement = String.format("SELECT count(*) recCount from AIE.ErrorDetails ;", getTableReference()); 
//				String.format(query);
		System.out.println("Query : " +String.format("SELECT count(*) recCount from AIE.ErrorDetails ;", getTableReference())); 
		PreparedStatement stmt = dbConnection.prepareStatement(preparedStatement);

		ResultSet resultSet = stmt.executeQuery();

		while (resultSet.next()) {
			count = resultSet.getInt("recCount");
		}

		return count;

	}*/
}
