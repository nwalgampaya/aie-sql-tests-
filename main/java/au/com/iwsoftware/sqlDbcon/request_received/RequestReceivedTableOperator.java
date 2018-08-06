/*
 *  DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *  
 *  Copyright (c) 2018 IWSoftware Pty Ltd.
 *  All rights reserved.
 */

package au.com.iwsoftware.sqlDbcon.request_received;

import java.sql.ResultSet;

import au.com.iwsoftware.sqlDbcon.TableOperator;
import au.com.iwsoftware.sqlDbcon.response_received.ResponseReceived;

/**
 *
 */
public class RequestReceivedTableOperator extends TableOperator {
	public static final String NAME = "RequestReceived";

	public static final String COLUMN_ID = "Id";
	public static final String COLUMN_AIEId = "AIEId";
	public static final String COLUMN_TIMESTAMP = "Timestamp";
	public static final String COLUMN_BODY = "Body";
	public static final String COLUMN_EVENT_TYPE = "EventType";
	public static final String COLUMN_ALERT_TYPE = "AlertType";
	public static final String COLUMN_FAULT_STATUS = "FaultStatus";
	public static final String COLUMN_FAULT_DETAILS = "FaultDetails";


	public RequestReceived decodeRow(ResultSet result) throws Exception {
		RequestReceived request = new RequestReceived();
		request.setAieId(result.getString(COLUMN_AIEId));
		request.setTimestamp(result.getTimestamp(COLUMN_TIMESTAMP));
		request.setBody(result.getString(COLUMN_BODY));
		request.setEventType(result.getString(COLUMN_EVENT_TYPE));
		request.setAlertType(result.getString(COLUMN_ALERT_TYPE));
		request.setFaultStatus(result.getInt(COLUMN_FAULT_STATUS));
		request.setFaultDetails(result.getString(COLUMN_FAULT_DETAILS));

		return request;
	}

	public RequestReceivedTableOperator(String schemaName) {
		super(schemaName, NAME);
	}
}
