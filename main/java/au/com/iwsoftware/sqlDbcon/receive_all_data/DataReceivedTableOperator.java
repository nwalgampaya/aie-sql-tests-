package au.com.iwsoftware.sqlDbcon.receive_all_data;

import java.sql.ResultSet;

import au.com.iwsoftware.sqlDbcon.TableOperator;
import au.com.iwsoftware.sqlDbcon.request_received.RequestReceived;

public class DataReceivedTableOperator extends TableOperator{

	public static final String NAME = "RequestReceived";

	public static final String COLUMN_STARTDATE = "startDate";
	public static final String COLUMN_ENDDATE = "endDate";
	public static final String COLUMN_EVENT_TYPE = "Eventtype";
	public static final String COLUMN_ALERT_TYPE = "AlertType";
	public static final String COLUMN_SUCCESS = "success";
	public static final String COLUMN_FAILURE = "failure";
	public static final String COLUMN_INCOMING_FAILURE = "incommingFailure";
	public static final String COLUMN_STATE = "state";
	
	
    /*	
	@startDate datetime = NULL, 
			   @endDate datetime = NULL,
			   @eventType nvarchar(50) = NULL,
			   @alertType nvarchar(50) = NULL,
			   @success bit = false,
			   @failure bit = false,
			   @incomingFailure bit = false,
			   @state int OUTPUT*/
	
	public DataReceivedRequest decodeRow(ResultSet result) throws Exception {
		DataReceivedRequest request = new DataReceivedRequest();
		request.setStartDate(result.getTimestamp(COLUMN_STARTDATE));
		request.setEndDate(result.getTimestamp(COLUMN_ENDDATE));
		request.setEventType(result.getString(COLUMN_EVENT_TYPE));
		request.setAlertType(result.getString(COLUMN_ALERT_TYPE));
		request.setSuccess(result.getBoolean(COLUMN_SUCCESS));
		request.setSuccess(result.getBoolean(COLUMN_FAILURE));
		request.setSuccess(result.getBoolean(COLUMN_INCOMING_FAILURE));
		
		return request;
	}
	public DataReceivedTableOperator(String schemaName) {
		super(schemaName, NAME);
	}
}
