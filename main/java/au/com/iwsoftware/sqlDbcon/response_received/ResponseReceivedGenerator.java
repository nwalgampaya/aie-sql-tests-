/*
 *  DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *  
 *  Copyright (c) 2018 IWSoftware Pty Ltd.
 *  All rights reserved.
 */

package au.com.iwsoftware.sqlDbcon.response_received;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

import util.DataGenerator;

public class ResponseReceivedGenerator {

	 /*@FaultStatus int,
	  @FaultDetails nvarchar(250),
	  @AscomMsgId nvarchar(20),
	  @Timestamp datetime,
	  @ResponseStatus int,
	  @Provider nvarchar(50),
	  @Type nvarchar(50),
	  @OAPVersion nvarchar(50),
	  @rowid int OUTPUT */

    private final Random random;
    private final DataGenerator dataGen;
    
    public ResponseReceivedGenerator(Random random)
    {
        this.random = random;
        dataGen = new DataGenerator();
    }
/*    if (random.nextBoolean())
    {
        request.setResponseStatus((short) random.nextInt(Short.MAX_VALUE + 1));
//        request.setResponseStatus((short) random.nextInt(Short.MAX_VALUE + 1));
    }*/
    public ResponseReceived generateValidResponseReceived()
    {
    	ResponseReceived request = new ResponseReceived();
    	
        request.setAscomMsgId(dataGen.generateSimpleTestString(random, 0, 20));
        request.setTimestamp(new Timestamp((System.currentTimeMillis() / 10) * 10));
	    if (random.nextBoolean())
	    {
	        request.setProvider(dataGen.generateSimpleTestString(random, 0, 50));
	    }
        if (random.nextBoolean())
        {
            request.setType(dataGen.generateSimpleTestString(random, 0, 50));
        }
        if (random.nextBoolean())
        {
            request.setResponseStatus(random.nextInt());
//            request.setResponseStatus((short) random.nextInt(Short.MAX_VALUE + 1));
        }
        if (random.nextBoolean())
        {
            request.setOAPVersion(dataGen.generateSimpleTestString(random, 0, 10));
        }
	    
        if (random.nextBoolean())
        {
            request.setFaultStatus(random.nextInt());
        }
        if (random.nextBoolean())
        {
            request.setFaultDetails(dataGen.generateSimpleTestString(random, 0, 50));
        }
        if (random.nextBoolean())
        {
            request.setOAPResponse(dataGen.generateSimpleTestString(random, 0, 50));
        }
        
        return request;
    }
    
    public ResponseReceived generateInvalidValidResponseReceived()
    {
    	ResponseReceived request = new ResponseReceived();
    	
        request.setAscomMsgId(dataGen.generateSimpleTestString(random, 20, 30));
        request.setTimestamp(new Timestamp((System.currentTimeMillis() / 10) * 10));
	    if (random.nextBoolean())
	    {
	        request.setProvider(dataGen.generateSimpleTestString(random, 52, 60));
	    }
        if (random.nextBoolean())
        {
            request.setType(dataGen.generateSimpleTestString(random, 52, 60));
        }
        if (random.nextBoolean())
        {
        	
            request.setResponseStatus(random.nextInt());
//            request.setResponseStatus((short) random.nextInt(Short.MAX_VALUE + 1));
        }
        if (random.nextBoolean())
        {
            request.setOAPVersion(dataGen.generateSimpleTestString(random, 11, 20));
        }
	    
        if (random.nextBoolean())
        {
            request.setFaultStatus(random.nextInt());
        }
        if (random.nextBoolean())
        {
            request.setFaultDetails(dataGen.generateSimpleTestString(random, 52, 50));
        }
        if (random.nextBoolean())
        {
            request.setOAPResponse(dataGen.generateSimpleTestString(random, 0, 50));
        }
        return request;
    }
    
    public ResponseReceived generateValidResponseReceived(Date date)
    {
    	ResponseReceived request = new ResponseReceived();
    	
        request.setAscomMsgId(dataGen.generateSimpleTestString(random, 0, 20));
        request.setTimestamp(new Timestamp(date.getTime()));
	    if (random.nextBoolean())
	    {
	        request.setProvider(dataGen.generateSimpleTestString(random, 0, 50));
	    }
        if (random.nextBoolean())
        {
            request.setType(dataGen.generateSimpleTestString(random, 0, 50));
        }
        if (random.nextBoolean())
        {
            request.setResponseStatus(random.nextInt());
//            request.setResponseStatus((short) random.nextInt(Short.MAX_VALUE + 1));
        }
        if (random.nextBoolean())
        {
            request.setOAPVersion(dataGen.generateSimpleTestString(random, 0, 10));
        }
	    
        if (random.nextBoolean())
        {
            request.setFaultStatus(random.nextInt());
        }
        if (random.nextBoolean())
        {
            request.setFaultDetails(dataGen.generateSimpleTestString(random, 0, 50));
        }
        if (random.nextBoolean())
        {
            request.setOAPResponse(dataGen.generateSimpleTestString(random, 0, 50));
        }
        
        return request;
    }
}
