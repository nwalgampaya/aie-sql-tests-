/*
 *  DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *  
 *  Copyright (c) 2018 IWSoftware Pty Ltd.
 *  All rights reserved.
 */

package au.com.iwsoftware.sqlDbcon.processed_request;

import au.com.iwsoftware.sqlDbcon.StoredProcedureOperator;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Types;


/**
 *
 */
public class AddProcessedRequestProcOperator extends StoredProcedureOperator
{
    private static final String PROCEDURE_NAME = "AddProcessedRequest";
    
    private static final String PARAM_FAULT_STATUS = "FaultStatus";
    private static final String PARAM_FAULT_DETAILS = "FaultDetails";
    private static final String PARAM_AIE_ID = "AIEId";
    private static final String PARAM_ASCOM_MSG_ID = "AscomMsgId";
    private static final String PARAM_UNITE_LOG_ID = "UniteLogId";
    private static final String PARAM_TIMESTAMP = "Timestamp";
    private static final String PARAM_PORT = "Port";
    // OAP Version
    private static final String PARAM_VERSION = "Version";
    private static final String PARAM_CALL_ID = "callId";
    private static final String PARAM_OAPREQUEST = "OAPRequest";
    private static final String PARAM_SUBJECT = "Subject";
    private static final String PARAM_BEEP_CODE = "BeepCode";
    private static final String PARAM_PRIORITY = "Priority";
    // OAP Meta-data tag for testing
    private static final String PARAM_TAG = "Tag";
    private static final String PARAM_OAP_TYPE = "OAPType";
    private static final String PARAM_ROW_ID = "rowid";
    
    public static final int ROW_ID_ERROR = -1;
    
    private static final int PARAMETER_COUNT = 16;
    
    public AddProcessedRequestProcOperator(String schemaName)
    {
        super(schemaName, PROCEDURE_NAME, PARAMETER_COUNT);
    }
    
    /*
    
CREATE PROCEDURE [AIE].[AddProcessedRequest]
      @FaultStatus int,
      @FaultDetails nvarchar(250),
	  @AIEId nvarchar(40),
	  @AscomMsgId nvarchar(20),
	  @UniteLogId nvarchar(20),
	  @Timestamp datetime,
	  @Port int,
	  @Version nvarchar(50),
	  @callId int,
	  @Body xml,
	  @Subject text,
	  @BeepCode int,
	  @Priority int,
	  @Tag text,
	  @OAPType text
    */
    
    /**
     * Call the stored procedure with the given parameters.
     * 
     * @param dbConnection 
     * @param faultStatus
     * @param faultDetails
     * @param aieId
     * @param ascomMsgId
     * @param uniteLogId
     * @param timestamp
     * @param port
     * @param version
     * @param callId
     * @param OAPRequest
     * @param subject
     * @param beepCode
     * @param priority
     * @param tag
     * @param oapType
     * @return The ID of the newly created row
     * @throws Exception 
     */
    public int call(
            Connection dbConnection,
            int faultStatus, 
            String faultDetails,
            String aieId,
            String ascomMsgId,
            String uniteLogId,
            Timestamp timestamp,
            int port,
            String version,
            int callId,
            String OAPRequest,
            String subject,
            byte beepCode,
            byte priority,
            String tag,
            String oapType)
            throws Exception
    {
        try (CallableStatement statement = dbConnection.prepareCall(createCallStatement()))
        {
            statement.setInt(PARAM_FAULT_STATUS, faultStatus);
            statement.setString(PARAM_FAULT_DETAILS, faultDetails);
            statement.setString(PARAM_AIE_ID, aieId);
            statement.setString(PARAM_ASCOM_MSG_ID, ascomMsgId);
            statement.setString(PARAM_UNITE_LOG_ID, uniteLogId);
            statement.setTimestamp(PARAM_TIMESTAMP, timestamp);
            statement.setInt(PARAM_PORT, port);
            statement.setString(PARAM_VERSION, version);
            statement.setInt(PARAM_CALL_ID, callId);
            statement.setString(PARAM_OAPREQUEST, OAPRequest);
            statement.setString(PARAM_SUBJECT, subject);
            statement.setInt(PARAM_BEEP_CODE, beepCode);
            statement.setInt(PARAM_PRIORITY, priority);
            statement.setString(PARAM_TAG, tag);
            statement.setString(PARAM_OAP_TYPE, oapType);
            statement.registerOutParameter(PARAM_ROW_ID, Types.INTEGER);

            statement.execute();
        
//            System.out.println("Called stored procedure: " + getProcedureReference());
            
            return statement.getInt(PARAM_ROW_ID);
        }
    }
    
    public int call(Connection dbConnection, ProcessedRequest request) throws Exception
    {
        return call(
                dbConnection, 
                request.getFaultStatus(),
                request.getFaultDetails(),
                request.getAieId(),
                request.getAscomMsgId(), 
                request.getUniteLogId(),
                request.getTimestamp(),
                request.getPort(), 
                request.getVersion(), 
                request.getCallId(),
                request.getOAPRequest(),
                request.getSubject(),
                request.getBeepCode(),
                request.getPriority(),
                request.getTag(),
                request.getOapType());
    }
}
