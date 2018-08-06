/*
 *  DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *  
 *  Copyright (c) 2018 IWSoftware Pty Ltd.
 *  All rights reserved.
 */

package au.com.iwsoftware.sqlDbcon.request_received;

import au.com.iwsoftware.sqlDbcon.StoredProcedureOperator;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Types;


/**
 *
 */
public class AddRequestReceivedProcOperator extends StoredProcedureOperator
{
    public static final String NAME = "AddRequestReceived";
    
    public static final String PARAM_FAULT_STATUS = "FaultStatus";
    public static final String PARAM_FAULT_DETAILS = "FaultDetails";
    public static final String PARAM_AIE_ID = "AIEId";
    public static final String PARAM_TIMESTAMP = "Timestamp";
    public static final String PARAM_BODY = "Body";
    public static final String PARAM_EVENT_TYPE = "Eventtype";
    public static final String PARAM_ALERT_TYPE = "AlertType";
    public static final String PARAM_ROW_ID = "rowid";
    
    public static final int PARAM_COUNT = 8;

	public static final int ROW_ID_ERROR = -1;


    public AddRequestReceivedProcOperator(String schema)
    {
        super(schema, NAME, PARAM_COUNT);
    }
    
    
/*    LTER PROCEDURE [AIE].[AddRequestReceived]
    	      @FaultStatus smallint,
    	      @FaultDetails text,
    		  @AIEId nvarchar(40),
    		  @Timestamp datetime2,
    		  @Body xml,
    		  @Eventtype nvarchar(50),
    		  @AlertType nvarchar(50),
    		  @rowid int OUTPUT
    		  
    		  
*/
    public int call(Connection dbConnection, RequestReceived request) throws Exception
    {
        return call(
            dbConnection,
            request.getFaultStatus(),
            request.getFaultDetails(),
            request.getAieId(),
            request.getTimestamp(),
            request.getBody(),
            request.getEventType(),
            request.getAlertType());
    }
    
    public int call(
            Connection dbConnection, 
            int faultStatus, 
            String faultDetails, 
            String aieId,
            Timestamp timestamp,
            String body, 
            String eventType,
            String alertType) throws Exception
    {
        try (CallableStatement statement = dbConnection.prepareCall(createCallStatement()))
        {
            
            statement.setInt(PARAM_FAULT_STATUS, faultStatus);
            statement.setString(PARAM_FAULT_DETAILS, faultDetails);
            statement.setString(PARAM_AIE_ID, aieId);
            statement.setTimestamp(PARAM_TIMESTAMP, timestamp);
            statement.setString(PARAM_BODY, body);
            statement.setString(PARAM_EVENT_TYPE, eventType);
            statement.setString(PARAM_ALERT_TYPE, alertType);
            statement.registerOutParameter(PARAM_ROW_ID, Types.INTEGER);

            statement.execute();
        
//            System.out.println("Called stored procedure: " + getProcedureReference());
            
            return statement.getInt(PARAM_ROW_ID);
        }
    }
}
