package au.com.iwsoftware.sqlDbcon.delete_old_data;

import java.util.Date;

public class DeleteDataRequest {

	
	private Date dateToBeDeleted;

	
	
	
	@Override
	public String toString() {
		return "DeleteDataRequest [dateToBeDeleted=" + dateToBeDeleted + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateToBeDeleted == null) ? 0 : dateToBeDeleted.hashCode());
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
		DeleteDataRequest other = (DeleteDataRequest) obj;
		if (dateToBeDeleted == null) {
			if (other.dateToBeDeleted != null)
				return false;
		} else if (!dateToBeDeleted.equals(other.dateToBeDeleted))
			return false;
		return true;
	}

	public Date getDateToBeDeleted() {
		return dateToBeDeleted;
	}

	public void setDateToBeDeleted(Date dateToBeDeleted) {
		this.dateToBeDeleted = dateToBeDeleted;
	}
	
	
	
	
}