package au.com.iwsoftware.sqlDbcon.receive_all_data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;

import org.w3c.dom.events.EventException;

import au.com.iwsoftware.sqlDbcon.StoredProcedureOperator;
import au.com.iwsoftware.sqlDbcon.request_received.RequestReceived;

public class AddReceiveAllDataProcOperator extends StoredProcedureOperator{

	
			  /**	
			   @startDate datetime = NULL, 
			   @endDate datetime = NULL,
			   @eventType nvarchar(50) = NULL,
			   @alertType nvarchar(50) = NULL,
			   @success bit = false,
			   @failure bit = false,
			   @incomingFailure bit = false,
			   @state int OUTPUT
			   **/
	
	public static final String NAME = "getLogDetails";
    
	public static final String PARAM_ALERT_TYPE = "AlertType";
	public static final String PARAM_STARTDATE = "startDate";
	public static final String PARAM_ENDDATE = "endDate";
	public static final String PARAM_EVENT_TYPE = "Eventtype";
	public static final String PARAM_SUCCESS = "success";
	public static final String PARAM_FAILURE = "failure";
	public static final String PARAM_INCOMING_FAILURE = "incomingFailure"; 
	public static final String PARAM_STATE = "state";
	
    public static final int PARAM_COUNT = 8;

	public static final int ROW_ID_ERROR = -1;
    public static final String PARAM_ROW_COUNT = "rowCount";

	
	
    public AddReceiveAllDataProcOperator(String schema)
    {
        super(schema, NAME, PARAM_COUNT);
    }
	 public int call(Connection dbConnection, DataReceivedRequest request) throws Exception
	    {
	        return call(
	            dbConnection,
	            request.getStartDate(),
	            request.getEndDate(),
	            request.getEventType(),
	            request.getAlertType(),
	            request.isSuccess(),
	            request.isFailure(),
	            request.isIncommingFailure());
	    }
	 
	   public int  call(
	            Connection dbConnection, 
	            Timestamp startDate,
	            Timestamp endDate,
	            String eventType, 
	            String alertType,
	            boolean success, 
	            boolean failure,
	            boolean incomingFailure
	            ) throws Exception
	    {
		   ResultSet resultSet1;
		   ResultSet resultSet;
	        try (CallableStatement statement = dbConnection.prepareCall(createCallStatement()))
	        {
	            
	        	statement.setTimestamp(PARAM_STARTDATE, startDate );
	        	statement.setTimestamp(PARAM_ENDDATE , endDate);
	        	statement.setString(PARAM_EVENT_TYPE, eventType);
	        	statement.setString(PARAM_ALERT_TYPE, alertType);
	        	statement.setBoolean(PARAM_SUCCESS, success);
	        	statement.setBoolean(PARAM_FAILURE, failure);
	        	statement.setBoolean(PARAM_INCOMING_FAILURE, incomingFailure);
	        	statement.registerOutParameter(PARAM_ROW_COUNT, Types.INTEGER);

	            statement.execute();

	            return statement.getInt(PARAM_ROW_COUNT);
	        }
	    }
}
