package au.com.iwsoftware.sqlDbcon.receive_all_data;

import java.sql.Timestamp;

public class DataReceivedResponse {

	private Timestamp receivedRequestTime;
	private String receivedRequestBody;
	private String eventType;
	private String alertType;
	private short receivedRequestFaultStatus;
	private short receivedRequestFaultDetails;
	private String uniteLogId;
	private Timestamp processedRequestTime;
	private short port;
	private String OAPVersion;
	private int callId;
	private String processedRequestBody;
	private String subject;
	private byte beepCode;
	private byte priority;
	private String tag;
	private String oapType;
	private short processedRequestFaultStatus;
	private String processedRequestFaultDetails;
	private short receivedResponseStatus;
	private Timestamp responsereceivedTime;
	private short receivedResponseFaultStatus;
	private String receivedResponseFaultDetails;
	
	
	@Override
	public String toString() {
		return "DataReceived{" 
				+ "receivedRequestBody            =" +      receivedRequestBody
				+ "eventType                      =" +      eventType
				+ "alertType                      =" +      alertType
				+ "receivedRequestFaultStatus     =" +      receivedRequestFaultStatus
				+ "receivedRequestFaultDetails    =" +      receivedRequestFaultDetails
				+ "uniteLogId                     =" +      uniteLogId
				+ "processedRequestTime           =" +      processedRequestTime
				+ "port                           =" +      port
				+ "OAPVersion                     =" +      OAPVersion
				+ "callId                         =" +      callId
				+ "processedRequestBody           =" +      processedRequestBody
				+ "subject                        =" +      subject
				+ "beepCode                       =" +      beepCode
				+ "priority                       =" +      priority
				+ "tag                            =" +      tag
				+ "oapType                        =" +      oapType
				+ "processedRequestFaultStatus    =" +      processedRequestFaultStatus
				+ "processedRequestFaultDetails   =" +      processedRequestFaultDetails
				+ "receivedResponseStatus         =" +      receivedResponseStatus
				+ "responsereceivedTime           =" +      responsereceivedTime
				+ "receivedResponseFaultStatus    =" +      receivedResponseFaultStatus
				+ "receivedResponseFaultDetails   =" +      receivedResponseFaultDetails + '}';
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((OAPVersion == null) ? 0 : OAPVersion.hashCode());
		result = prime * result + ((alertType == null) ? 0 : alertType.hashCode());
		result = prime * result + beepCode;
		result = prime * result + callId;
		result = prime * result + ((eventType == null) ? 0 : eventType.hashCode());
		result = prime * result + ((oapType == null) ? 0 : oapType.hashCode());
		result = prime * result + port;
		result = prime * result + priority;
		result = prime * result + ((processedRequestBody == null) ? 0 : processedRequestBody.hashCode());
		result = prime * result
				+ ((processedRequestFaultDetails == null) ? 0 : processedRequestFaultDetails.hashCode());
		result = prime * result + processedRequestFaultStatus;
		result = prime * result + ((processedRequestTime == null) ? 0 : processedRequestTime.hashCode());
		result = prime * result + ((receivedRequestBody == null) ? 0 : receivedRequestBody.hashCode());
		result = prime * result + receivedRequestFaultDetails;
		result = prime * result + receivedRequestFaultStatus;
		result = prime * result + ((receivedRequestTime == null) ? 0 : receivedRequestTime.hashCode());
		result = prime * result
				+ ((receivedResponseFaultDetails == null) ? 0 : receivedResponseFaultDetails.hashCode());
		result = prime * result + receivedResponseFaultStatus;
		result = prime * result + receivedResponseStatus;
		result = prime * result + ((responsereceivedTime == null) ? 0 : responsereceivedTime.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		result = prime * result + ((tag == null) ? 0 : tag.hashCode());
		result = prime * result + ((uniteLogId == null) ? 0 : uniteLogId.hashCode());
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
		DataReceivedResponse other = (DataReceivedResponse) obj;
		if (OAPVersion == null) {
			if (other.OAPVersion != null)
				return false;
		} else if (!OAPVersion.equals(other.OAPVersion))
			return false;
		if (alertType == null) {
			if (other.alertType != null)
				return false;
		} else if (!alertType.equals(other.alertType))
			return false;
		if (beepCode != other.beepCode)
			return false;
		if (callId != other.callId)
			return false;
		if (eventType == null) {
			if (other.eventType != null)
				return false;
		} else if (!eventType.equals(other.eventType))
			return false;
		if (oapType == null) {
			if (other.oapType != null)
				return false;
		} else if (!oapType.equals(other.oapType))
			return false;
		if (port != other.port)
			return false;
		if (priority != other.priority)
			return false;
		if (processedRequestBody == null) {
			if (other.processedRequestBody != null)
				return false;
		} else if (!processedRequestBody.equals(other.processedRequestBody))
			return false;
		if (processedRequestFaultDetails == null) {
			if (other.processedRequestFaultDetails != null)
				return false;
		} else if (!processedRequestFaultDetails.equals(other.processedRequestFaultDetails))
			return false;
		if (processedRequestFaultStatus != other.processedRequestFaultStatus)
			return false;
		if (processedRequestTime == null) {
			if (other.processedRequestTime != null)
				return false;
		} else if (!processedRequestTime.equals(other.processedRequestTime))
			return false;
		if (receivedRequestBody == null) {
			if (other.receivedRequestBody != null)
				return false;
		} else if (!receivedRequestBody.equals(other.receivedRequestBody))
			return false;
		if (receivedRequestFaultDetails != other.receivedRequestFaultDetails)
			return false;
		if (receivedRequestFaultStatus != other.receivedRequestFaultStatus)
			return false;
		if (receivedRequestTime == null) {
			if (other.receivedRequestTime != null)
				return false;
		} else if (!receivedRequestTime.equals(other.receivedRequestTime))
			return false;
		if (receivedResponseFaultDetails == null) {
			if (other.receivedResponseFaultDetails != null)
				return false;
		} else if (!receivedResponseFaultDetails.equals(other.receivedResponseFaultDetails))
			return false;
		if (receivedResponseFaultStatus != other.receivedResponseFaultStatus)
			return false;
		if (receivedResponseStatus != other.receivedResponseStatus)
			return false;
		if (responsereceivedTime == null) {
			if (other.responsereceivedTime != null)
				return false;
		} else if (!responsereceivedTime.equals(other.responsereceivedTime))
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		if (tag == null) {
			if (other.tag != null)
				return false;
		} else if (!tag.equals(other.tag))
			return false;
		if (uniteLogId == null) {
			if (other.uniteLogId != null)
				return false;
		} else if (!uniteLogId.equals(other.uniteLogId))
			return false;
		return true;
	}
	
	
	
	public Timestamp getReceivedRequestTime() {
		return receivedRequestTime;
	}
	public void setReceivedRequestTime(Timestamp receivedRequestTime) {
		this.receivedRequestTime = receivedRequestTime;
	}
	public String getReceivedRequestBody() {
		return receivedRequestBody;
	}
	public void setReceivedRequestBody(String receivedRequestBody) {
		this.receivedRequestBody = receivedRequestBody;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getAlertType() {
		return alertType;
	}
	public void setAlertType(String alertType) {
		this.alertType = alertType;
	}
	public short getReceivedRequestFaultStatus() {
		return receivedRequestFaultStatus;
	}
	public void setReceivedRequestFaultStatus(short receivedRequestFaultStatus) {
		this.receivedRequestFaultStatus = receivedRequestFaultStatus;
	}
	public short getReceivedRequestFaultDetails() {
		return receivedRequestFaultDetails;
	}
	public void setReceivedRequestFaultDetails(short receivedRequestFaultDetails) {
		this.receivedRequestFaultDetails = receivedRequestFaultDetails;
	}
	public String getUniteLogId() {
		return uniteLogId;
	}
	public void setUniteLogId(String uniteLogId) {
		this.uniteLogId = uniteLogId;
	}
	public Timestamp getProcessedRequestTime() {
		return processedRequestTime;
	}
	public void setProcessedRequestTime(Timestamp processedRequestTime) {
		this.processedRequestTime = processedRequestTime;
	}
	public short getPort() {
		return port;
	}
	public void setPort(short port) {
		this.port = port;
	}
	public String getOAPVersion() {
		return OAPVersion;
	}
	public void setOAPVersion(String oAPVersion) {
		OAPVersion = oAPVersion;
	}
	public int getCallId() {
		return callId;
	}
	public void setCallId(int callId) {
		this.callId = callId;
	}
	public String getProcessedRequestBody() {
		return processedRequestBody;
	}
	public void setProcessedRequestBody(String processedRequestBody) {
		this.processedRequestBody = processedRequestBody;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public byte getBeepCode() {
		return beepCode;
	}
	public void setBeepCode(byte beepCode) {
		this.beepCode = beepCode;
	}
	public byte getPriority() {
		return priority;
	}
	public void setPriority(byte priority) {
		this.priority = priority;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getOapType() {
		return oapType;
	}
	public void setOapType(String oapType) {
		this.oapType = oapType;
	}
	public short getProcessedRequestFaultStatus() {
		return processedRequestFaultStatus;
	}
	public void setProcessedRequestFaultStatus(short processedRequestFaultStatus) {
		this.processedRequestFaultStatus = processedRequestFaultStatus;
	}
	public String getProcessedRequestFaultDetails() {
		return processedRequestFaultDetails;
	}
	public void setProcessedRequestFaultDetails(String processedRequestFaultDetails) {
		this.processedRequestFaultDetails = processedRequestFaultDetails;
	}
	public short getReceivedResponseStatus() {
		return receivedResponseStatus;
	}
	public void setReceivedResponseStatus(short receivedResponseStatus) {
		this.receivedResponseStatus = receivedResponseStatus;
	}
	public Timestamp getResponsereceivedTime() {
		return responsereceivedTime;
	}
	public void setResponsereceivedTime(Timestamp responsereceivedTime) {
		this.responsereceivedTime = responsereceivedTime;
	}
	public short getReceivedResponseFaultStatus() {
		return receivedResponseFaultStatus;
	}
	public void setReceivedResponseFaultStatus(short receivedResponseFaultStatus) {
		this.receivedResponseFaultStatus = receivedResponseFaultStatus;
	}
	public String getReceivedResponseFaultDetails() {
		return receivedResponseFaultDetails;
	}
	public void setReceivedResponseFaultDetails(String receivedResponseFaultDetails) {
		this.receivedResponseFaultDetails = receivedResponseFaultDetails;
	}
	
	

	
	
}
