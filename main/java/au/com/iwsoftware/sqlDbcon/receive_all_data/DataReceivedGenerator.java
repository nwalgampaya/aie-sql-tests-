package au.com.iwsoftware.sqlDbcon.receive_all_data;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import au.com.iwsoftware.sqlDbcon.request_received.RequestReceived;
import util.DataGenerator;

public class DataReceivedGenerator {

	 private final Random random;
	 private final DataGenerator dataGen;
	    
	/*
	  @startDate datetime = NULL,
	  @endDate datetime = NULL,
	  @eventType nvarchar(50) = NULL,
	  @alertType nvarchar(50) = NULL,
	  @success bit = false,
	  @failure bit = false,
	  @incomingFailure bit = false,
	  @state int OUTPUT
	 */
	    
	    public DataReceivedGenerator(Random random)
	    {
	        this.random = random;
	        dataGen = new DataGenerator();
	    }
	    
	public DataReceivedRequest generateValidDataReceived(long startTime, long endtTime, String eventType,
			String alertType, boolean success, boolean failure, boolean incommingFailure) throws ParseException {
		DataReceivedRequest request = new DataReceivedRequest();

		if (startTime != 0 || endtTime != 0) {
			request.setStartDate(new Timestamp(startTime));
			request.setEndDate(new Timestamp(endtTime));
		}
		if (alertType != null) {
			request.setAlertType(alertType);
		}
		if (eventType != null) {
			request.setEventType(eventType);
		}
		request.setFailure(failure);
		request.setSuccess(success);
		request.setIncommingFailure(incommingFailure);

		System.out.println("Request : " + request.toString());
		return request;
	}
}
