/*
 *  DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *  
 *  Copyright (c) 2018 IWSoftware Pty Ltd.
 *  All rights reserved.
 */

package au.com.iwsoftware.sqlDbcon.response_received;

import java.sql.ResultSet;

import au.com.iwsoftware.sqlDbcon.TableOperator;

public class ResponseReceivedTableOperator extends TableOperator {

	public static final String COLUMN_ID = "Id";
	public static final String COLUMN_ASCOM_MSG_ID = "AscomMsgId";
	public static final String COLUMN_TIMESTAMP = "Timestamp";
	public static final String COLUMN_STATUS = "Status";
	public static final String COLUMN_PROVIDER = "Provider";
	public static final String COLUMN_TYPE = "Type";
	public static final String COLUMN_OAP_VERSION = "OAPVersion";
	public static final String COLUMN_FAULT_STATUS = "FaultStatus";
	public static final String COLUMN_FAULT_DETAILS = "FaultDetails";
	public static final String COLUMN_OAP_RESPONSE = "OAPResponse";

	public static final String NAME = "ReponseReceived";

	public ResponseReceivedTableOperator(String schemaName) {
		super(schemaName, NAME);
	}

	public ResponseReceived decodeRow(ResultSet result) throws Exception {
		ResponseReceived request = new ResponseReceived();
		request.setAscomMsgId(result.getString(COLUMN_ASCOM_MSG_ID));
		request.setTimestamp(result.getTimestamp(COLUMN_TIMESTAMP));
		request.setResponseStatus(result.getInt(COLUMN_STATUS));
		request.setProvider(result.getString(COLUMN_PROVIDER));
		request.setType(result.getString(COLUMN_TYPE));
		request.setOAPVersion(result.getString(COLUMN_OAP_VERSION));
		request.setFaultStatus(result.getInt(COLUMN_FAULT_STATUS));
		request.setFaultDetails(result.getString(COLUMN_FAULT_DETAILS));
		request.setOAPResponse(result.getString(COLUMN_OAP_RESPONSE));

		return request;
	}
}
