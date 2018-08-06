package au.com.iwsoftware.sqlDbcon.receive_all_data;

import java.sql.Timestamp;

public class DataReceivedRequest {

	private Timestamp startDate;
	private Timestamp endDate;
	private String eventType;
	private String alertType;
	private boolean success;
	private boolean failure;
	private boolean incommingFailure;
	private int state;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alertType == null) ? 0 : alertType.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((eventType == null) ? 0 : eventType.hashCode());
		result = prime * result + (failure ? 1231 : 1237);
		result = prime * result + (incommingFailure ? 1231 : 1237);
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + state;
		result = prime * result + (success ? 1231 : 1237);
		return result;
	}
	
	@Override
	public String toString() {
		return "DataReceived{" + "startDate=" + startDate + ", endDate=" + endDate + ", eventType=" + eventType
				+ ", alertType=" + alertType + ", success=" + success + ", failure=" + failure + ", incommingFailure="
				+ incommingFailure + ", state=" + state + '}';
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataReceivedRequest other = (DataReceivedRequest) obj;
		if (alertType == null) {
			if (other.alertType != null)
				return false;
		} else if (!alertType.equals(other.alertType))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (eventType == null) {
			if (other.eventType != null)
				return false;
		} else if (!alertType.equals(other.alertType))
			return false;
		if (failure != other.failure)
			return false;
		if (incommingFailure != other.incommingFailure)
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (state != other.state)
			return false;
		if (success != other.success)
			return false;
		return true;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
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

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean isFailure() {
		return failure;
	}

	public void setFailure(boolean failure) {
		this.failure = failure;
	}

	public boolean isIncommingFailure() {
		return incommingFailure;
	}

	public void setIncommingFailure(boolean incommingFailure) {
		this.incommingFailure = incommingFailure;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

}
