/*
 *  DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *  
 *  Copyright (c) 2018 IWSoftware Pty Ltd.
 *  All rights reserved.
 */

package au.com.iwsoftware.sqlDbcon.processed_request;

import au.com.iwsoftware.sqlDbcon.TableOperator;
import java.sql.ResultSet;

/**
 *
 */
public class ProcessedRequestTableOperator extends TableOperator
{
    public static final String TABLE_NAME = "ProcessedRequest";
    
    public static final String COLUMN_ID = "Id";
    public static final String COLUMN_AIE_ID = "AIEId";
    public static final String COLUMN_ASCOM_MSG_ID = "AscomMsgId";
    public static final String COLUMN_UNITE_LOG_ID = "UniteLogId";
    public static final String COLUMN_TIMESTAMP = "Timestamp";
    public static final String COLUMN_PORT = "Port";
    public static final String COLUMN_VERSION = "Version";
    public static final String COLUMN_CALL_ID = "CallId";
    public static final String COLUMN_OAPREQUEST = "OAPRequest";
    public static final String COLUMN_SUBJECT = "Subject";
    public static final String COLUMN_BEEP_CODE = "Beepcode";
    public static final String COLUMN_PRIORITY = "Priority";
    public static final String COLUMN_TAG = "Tag";
    public static final String COLUMN_OAP_TYPE = "OAPType";
    public static final String COLUMN_FAULT_STATUS = "FaultStatus";
    public static final String COLUMN_FAULT_DETAILS = "FaultDetails";
    
    public ProcessedRequestTableOperator(String schemaName)
    {
        super(schemaName, TABLE_NAME);
    }
    
    public ProcessedRequest decodeRow(ResultSet result) throws Exception
    {
        ProcessedRequest request = new ProcessedRequest();
        request.setAieId(result.getString(COLUMN_AIE_ID));
        request.setAscomMsgId(result.getString(COLUMN_ASCOM_MSG_ID));
        request.setUniteLogId(result.getString(COLUMN_UNITE_LOG_ID));
        request.setTimestamp(result.getTimestamp(COLUMN_TIMESTAMP));
        request.setPort(result.getInt(COLUMN_PORT));
        request.setVersion(result.getString(COLUMN_VERSION));
        request.setCallId(result.getInt(COLUMN_CALL_ID));
        request.setOAPRequest(result.getString(COLUMN_OAPREQUEST));
        request.setSubject(result.getString(COLUMN_SUBJECT));
        request.setBeepCode(result.getByte(COLUMN_BEEP_CODE));
        request.setPriority(result.getByte(COLUMN_PRIORITY));
        request.setTag(result.getString(COLUMN_TAG));
        request.setOapType(result.getString(COLUMN_OAP_TYPE));
        request.setFaultStatus(result.getInt(COLUMN_FAULT_STATUS));
        request.setFaultDetails(result.getString(COLUMN_FAULT_DETAILS));
        return request;
    }
}
