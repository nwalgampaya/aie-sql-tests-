/*
 *  DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *  
 *  Copyright (c) 2018 IWSoftware Pty Ltd.
 *  All rights reserved.
 */

package au.com.iwsoftware.sqlDbcon.request_received;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;
import util.DataGenerator;

/**
 *
 */
public class RequestReceivedGenerator 
{
    private final Random random;
    private final DataGenerator dataGen;
    
    public RequestReceivedGenerator(Random random)
    {
        this.random = random;
        dataGen = new DataGenerator();
    }
    
    public RequestReceived generateValidRequestReceived()
    {
        RequestReceived request = new RequestReceived();
        request.setAieId(dataGen.generateSimpleTestString(random, 1, 40));
        request.setTimestamp(new Timestamp((System.currentTimeMillis() / 10) * 10));
        if (random.nextBoolean())
        {
            request.setBody(dataGen.generateSimpleTestString(random, 0, 100));
        }
        if (random.nextBoolean())
        {
            request.setEventType(dataGen.generateSimpleTestString(random, 0, 50));
        }
        if (random.nextBoolean())
        {
            request.setAlertType(dataGen.generateSimpleTestString(random, 0, 50));
        }
        if (random.nextBoolean())
        {
        	request.setFaultStatus(random.nextInt());
        }
        if (random.nextBoolean())
        {
            request.setFaultDetails(dataGen.generateSimpleTestString(random, 0, 50));
        }
        return request;
    }
    
    public RequestReceived generateInValidRequestReceived()
    {
    	RequestReceived request = new RequestReceived();
        request.setAieId(dataGen.generateSimpleTestString(random,45, 50));
        request.setTimestamp(new Timestamp((System.currentTimeMillis() / 10) * 10));
        if (random.nextBoolean())
        {
            request.setBody(dataGen.generateSimpleTestString(random, 0, 100));
        }
        if (random.nextBoolean())
        {
            request.setEventType(dataGen.generateSimpleTestString(random, 50, 70));
        }
        if (random.nextBoolean())
        {
            request.setAlertType(dataGen.generateSimpleTestString(random, 52, 60));
        }
        if (random.nextBoolean())
        {
        	request.setFaultStatus(random.nextInt());
        }
        if (random.nextBoolean())
        {
            request.setFaultDetails(dataGen.generateSimpleTestString(random, 0, 50));
        }
        return request;
    }
     
    public RequestReceived generateValidRequestReceived(Date date)
    {
        RequestReceived request = new RequestReceived();
        request.setAieId(dataGen.generateSimpleTestString(random, 1, 40));
        request.setTimestamp(new Timestamp(date.getTime()));
        
        if (random.nextBoolean())
        {
            request.setBody(dataGen.generateSimpleTestString(random, 0, 100));
        }
        if (random.nextBoolean())
        {
            request.setEventType(dataGen.generateSimpleTestString(random, 0, 50));
        }
        if (random.nextBoolean())
        {
            request.setAlertType(dataGen.generateSimpleTestString(random, 0, 50));
        }
        if (random.nextBoolean())
        {
        	request.setFaultStatus(random.nextInt());
        }
        if (random.nextBoolean())
        {
            request.setFaultDetails(dataGen.generateSimpleTestString(random, 0, 50));
        }
        return request;
    }
}

