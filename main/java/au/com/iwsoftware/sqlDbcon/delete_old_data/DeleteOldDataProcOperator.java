package au.com.iwsoftware.sqlDbcon.delete_old_data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;

import au.com.iwsoftware.sqlDbcon.StoredProcedureOperator;

public class DeleteOldDataProcOperator extends StoredProcedureOperator {

	public static final String PROCEDURE_NAME = "DeleteOldData";
	public static final int PARAM_COUNT = 2;
	public static final String PARAM_STARTDATE = "deleteDate";
	public static final String PARAM_STATUS = "status";

	public DeleteOldDataProcOperator(String schemaName) {

		super(schemaName, PROCEDURE_NAME, PARAM_COUNT);
	}

	public int call(Connection dbConnection, DeleteDataRequest request) throws Exception {
		return call(dbConnection, request.getDateToBeDeleted());

	}

	public int call(Connection dbConnection, Date startDate) throws Exception {
		try (CallableStatement statement = dbConnection.prepareCall(createCallStatement())) {

			System.out.println("startDate :" + startDate);
			System.out.println("new java.sql.Date : " + new java.sql.Date(startDate.getTime() ));
			statement.setDate(PARAM_STARTDATE, new java.sql.Date(startDate.getTime()));
			statement.registerOutParameter(PARAM_STATUS, Types.INTEGER);
			
			statement.execute();

			return statement.getInt(PARAM_STATUS);
		}
	}
	
}
