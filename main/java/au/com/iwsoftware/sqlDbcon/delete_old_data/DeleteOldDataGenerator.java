package au.com.iwsoftware.sqlDbcon.delete_old_data;

import java.util.Date;

public class DeleteOldDataGenerator {

	public DeleteDataRequest generateValidDeleteRequest(Date date) {
		
		DeleteDataRequest request = new DeleteDataRequest();

//		request.setDateToBeDeleted(new Timestamp((System.currentTimeMillis() / 10) * 10));
		
		System.out.println(" validProcess :" + date);
//		request.setDateToBeDeleted(new Timestamp(date.getTime()));
		request.setDateToBeDeleted(date);

		return request;
	}

	

}
