/*
 *  DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *  
 */

package au.com.iwsoftware.sqlDbcon.error_details;

import au.com.iwsoftware.sqlDbcon.TableOperator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 */
public class ErrorDetailsTableOperator extends TableOperator
{
    public static final String TABLE_NAME = "ErrorDetails";
    
    public static final String COLUMN_ID = "Id";
    public static final String COLUMN_AIE_ID = "AIEId";
    public static final String COLUMN_ASCOM_MSG_ID = "AscomMsgId";
    public static final String COLUMN_ERROR_NUMBER = "ErrorNumber";
    public static final String COLUMN_ERROR_SEVERITY = "ErrorSeverity";
    public static final String COLUMN_ERROR_STATE = "ErrorState";
    public static final String COLUMN_ERROR_PROCEDURE = "ErrorProcedure";
    public static final String COLUMN_ERROR_LINE = "ErrorLine";
    public static final String COLUMN_ERROR_MESSAGE = "ErrorMessage";
    
    public ErrorDetailsTableOperator(String schema)
    {
        super(schema, TABLE_NAME);
    }
    
    public ErrorDetails decodeRow(ResultSet results)
    {
        ErrorDetails details = new ErrorDetails();
        throw new UnsupportedOperationException();
    }
    
    /**
     * This method will delete all the records in the 'ErrorDetail' Table
     */
    
	public int deleteRecordsInErrorTable(Connection dbConnection) throws Exception
	{

		String preparedStatement = String.format("DELETE FROM %s ;", getTableReference());

		PreparedStatement stmt = dbConnection.prepareStatement(preparedStatement);

		int resultSet = stmt.executeUpdate();

		return resultSet;

	}
    
	/**
	 * This method is written to get a count of all the records in the 'ErrorDetail' table.
	 * @param dbConnection
	 * @return
	 * @throws Exception
	 */
	public int queryErrorTable(Connection dbConnection) throws Exception
	{
		int count = 0;
		String preparedStatement = String.format("SELECT count(*) recCount from AIE.ErrorDetails ;", getTableReference()); 
		PreparedStatement stmt = dbConnection.prepareStatement(preparedStatement);

		ResultSet resultSet = stmt.executeQuery();

		while (resultSet.next())
		{
			count = resultSet.getInt("recCount");
		}

		return count;

	}
}
