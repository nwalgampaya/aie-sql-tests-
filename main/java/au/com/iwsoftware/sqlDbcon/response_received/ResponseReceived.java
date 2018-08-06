/*
 *  DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *  
 *  Copyright (c) 2018 IWSoftware Pty Ltd.
 *  All rights reserved.
 */
package au.com.iwsoftware.sqlDbcon.response_received;

import java.sql.Timestamp;

public class ResponseReceived {

	private String ascomMsgId;
	private Timestamp timestamp;
	private int responseStatus;
	private String provider;
	private String type;
	private String OAPVersion;
	private int faultStatus;
	private String faultDetails;
	private String OAPResponse;
	

	
	
	/* @Override
	    public String toString()
	    {
	        return "ResponseReceived{" + "faultStatus=" + faultStatus + ", faultDetails=" + faultDetails +
	               ", ascomMsgId=" + ascomMsgId + ", responseStatus=" + responseStatus + ", OAPResponse=" + OAPResponse + ", timestamp=" + timestamp + ", provider=" +
	               provider + ", type=" + type + ", OAPVersion=" + OAPVersion +  '}';
	    }*/
	

	@Override
	public String toString() {
		return "ResponseReceived [ascomMsgId=" + ascomMsgId + ", timestamp=" + timestamp + ", responseStatus="
				+ responseStatus + ", provider=" + provider + ", type=" + type + ", OAPVersion=" + OAPVersion
				+ ", faultStatus=" + faultStatus + ", faultDetails=" + faultDetails + ", OAPResponse=" + OAPResponse
				+ "]";
	}
	

	public String getAscomMsgId() {
		return ascomMsgId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((OAPResponse == null) ? 0 : OAPResponse.hashCode());
		result = prime * result + ((OAPVersion == null) ? 0 : OAPVersion.hashCode());
		result = prime * result + ((ascomMsgId == null) ? 0 : ascomMsgId.hashCode());
		result = prime * result + ((faultDetails == null) ? 0 : faultDetails.hashCode());
		result = prime * result + faultStatus;
		result = prime * result + ((provider == null) ? 0 : provider.hashCode());
		result = prime * result + responseStatus;
		result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResponseReceived other = (ResponseReceived) obj;
		if (OAPResponse == null) {
			if (other.OAPResponse != null)
				return false;
		} else if (!OAPResponse.equals(other.OAPResponse))
			return false;
		if (OAPVersion == null) {
			if (other.OAPVersion != null)
				return false;
		} else if (!OAPVersion.equals(other.OAPVersion))
			return false;
		if (ascomMsgId == null) {
			if (other.ascomMsgId != null)
				return false;
		} else if (!ascomMsgId.equals(other.ascomMsgId))
			return false;
		if (faultDetails == null) {
			if (other.faultDetails != null)
				return false;
		} else if (!faultDetails.equals(other.faultDetails))
			return false;
		if (faultStatus != other.faultStatus)
			return false;
		if (provider == null) {
			if (other.provider != null)
				return false;
		} else if (!provider.equals(other.provider))
			return false;
		if (responseStatus != other.responseStatus)
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	public void setAscomMsgId(String ascomMsgId) {
		this.ascomMsgId = ascomMsgId;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public int getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(int responseStatus) {
		this.responseStatus = responseStatus;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOAPVersion() {
		return OAPVersion;
	}

	public void setOAPVersion(String oAPVersion) {
		OAPVersion = oAPVersion;
	}


	public int getFaultStatus() {
		return faultStatus;
	}

	public void setFaultStatus(int faultStatus) {
		this.faultStatus = faultStatus;
	}

	public String getFaultDetails() {
		return faultDetails;
	}

	public void setFaultDetails(String faultDetails) {
		this.faultDetails = faultDetails;
	}
	
	public String getOAPResponse() {
		return OAPResponse;
	}


	public void setOAPResponse(String oAPResponse) {
		OAPResponse = oAPResponse;
	}

}
