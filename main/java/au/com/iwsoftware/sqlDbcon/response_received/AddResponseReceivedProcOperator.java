/*
 *  DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *  
 *  Copyright (c) 2018 IWSoftware Pty Ltd.
 *  All rights reserved.
 */
package au.com.iwsoftware.sqlDbcon.response_received;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Types;

import au.com.iwsoftware.sqlDbcon.StoredProcedureOperator;

public class AddResponseReceivedProcOperator extends StoredProcedureOperator{

	
	public static final String NAME = "AddResponseReceived";

	public static final String PARAM_FAULT_STATUS = "FaultStatus";
	public static final String PARAM_FAULT_DETAILS = "FaultDetails";
    private static final String PARAM_ASCOM_MSG_ID = "AscomMsgId";
    public static final String PARAM_TIMESTAMP = "Timestamp";
    public static final String RESPONSE_STATUS = "ResponseStatus";
	public static final String PARAM_PROVIDER = "Provider";
	public static final String PARAM_OAP_RESPONSE  = "OAPResponse";
	
	public static final String PARAM_TYPE = "Type";
	public static final String PARAM_OAP_VERSION = "OAPVersion";
	public static final String PARAM_ROW_ID = "rowid";
	
	public static final int ROW_ID_ERROR = -1;

	public static final int PARAM_COUNT = 10;
	    
	
    public AddResponseReceivedProcOperator(String schema)
    {
        super(schema, NAME, PARAM_COUNT);
    }
	
	  /*@FaultStatus int,
	  @FaultDetails nvarchar(250),
	  @AscomMsgId nvarchar(20),
	  @Timestamp datetime,
	  @ResponseStatus int,
	  @Provider nvarchar(50),
	  @Type nvarchar(50),
	  @OAPVersion nvarchar(50),
	  @OAPResponse text,
	  @rowid int OUTPUT
	 
	  */
	
    public int call(Connection dbConnection, ResponseReceived request) throws Exception
    {
        return call(
            dbConnection,
            request.getFaultStatus(),
            request.getFaultDetails(),
            request.getAscomMsgId(),
            request.getTimestamp(),
            request.getResponseStatus(),
            request.getProvider(),
            request.getType(),
            request.getOAPVersion(),
            request.getOAPResponse());
    }
    public int call(
            Connection dbConnection, 
            int faultStatus, 
            String faultDetails, 
            String ascomMsgId,
            Timestamp timestamp,
            int responseStatus,
            String provider,
            String type,
            String OAPVersion,
            String OAPResponse) throws Exception

   
    {
    	 try (CallableStatement statement = dbConnection.prepareCall(createCallStatement()))
         {
             statement.setInt(PARAM_FAULT_STATUS, faultStatus);
             statement.setString(PARAM_FAULT_DETAILS, faultDetails);
             statement.setString(PARAM_ASCOM_MSG_ID, ascomMsgId);
             statement.setTimestamp(PARAM_TIMESTAMP, timestamp);
         	 statement.setInt(RESPONSE_STATUS, responseStatus);
             statement.setString(PARAM_PROVIDER, provider);
             statement.setString(PARAM_TYPE, type);
             statement.setString(PARAM_OAP_VERSION, OAPVersion);
             statement.setString(PARAM_OAP_RESPONSE, OAPResponse);
             statement.registerOutParameter(PARAM_ROW_ID, Types.INTEGER);
             
             statement.execute();
             
             return statement.getInt(PARAM_ROW_ID);
         }
     
    }
}
