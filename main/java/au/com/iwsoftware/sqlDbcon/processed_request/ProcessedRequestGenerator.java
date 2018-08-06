/*
 *  DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *  
 *  Copyright (c) 2018 IWSoftware Pty Ltd.
 *  All rights reserved.
 */

package au.com.iwsoftware.sqlDbcon.processed_request;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

import au.com.iwsoftware.sqlDbcon.request_received.RequestReceived;
import util.DataGenerator;

/**
 *
 */
public class ProcessedRequestGenerator 
{
    private final Random random;
    private final DataGenerator dataGen;
    
    public ProcessedRequestGenerator(Random random)
    {
        this.random = random;
        dataGen = new DataGenerator();
    }
    
    /*
	[AIEId] [nvarchar](40) NOT NULL,
	[AscomMsgId] [nvarchar](50) NOT NULL,
	[UniteLogId] [nvarchar](50) NOT NULL,
	[Timestamp] [datetime] NOT NULL,
	[Port] [int] NULL,
	[Version] [nvarchar](50) NULL,
	[CallId] [int] NULL,
	[Body] [xml] NULL,
	[Subject] [text] NULL,
	[Beepcode] [int] NULL,
	[Priority] [int] NULL,
	[Tag] [text] NULL,
	[OAPType] [text] NULL,
	[FaultStatus] [int] NULL,
	[FaultDetails] [nvarchar](50) NULL,
    */
    
    public ProcessedRequest generateValidProcessedRequest()
    {
        ProcessedRequest request = new ProcessedRequest();
        byte[] nbyte = new byte[30];
        byte rndbyte = nbyte[1];
        request.setAieId(dataGen.generateSimpleTestString(random, 0, 40));
        request.setAscomMsgId(dataGen.generateSimpleTestString(random, 0, 20));
        request.setUniteLogId(dataGen.generateSimpleTestString(random, 0, 20));
        request.setTimestamp(new Timestamp((System.currentTimeMillis() / 10) * 10));
       
        if (random.nextBoolean())
        {
//            request.setPort((short) random.nextInt(Short.MAX_VALUE + 1));
        	request.setPort(random.nextInt());
        }
        if (random.nextBoolean())
        {
            request.setVersion(dataGen.generateSimpleTestString(random, 0, 10));
        }
        if (random.nextBoolean())
        {
            request.setCallId(random.nextInt());
        }
        if (random.nextBoolean())
        {
            request.setOAPRequest(dataGen.generateSimpleTestString(random, 0, 100));
        }
        if (random.nextBoolean())
        {
            request.setSubject(dataGen.generateSimpleTestString(random, 0, 50));
        }
        if (random.nextBoolean())
        {
             request.setPriority(rndbyte);
        }
        if (random.nextBoolean())
        { 
        	 
            request.setPriority(rndbyte);
        }
        if (random.nextBoolean())
        {
            request.setTag(dataGen.generateSimpleTestString(random, 0, 50));
        }
        if (random.nextBoolean())
        {
            request.setOapType(dataGen.generateSimpleTestString(random, 0, 40));
        }
        if (random.nextBoolean())
        {
//            request.setFaultStatus((short) random.nextInt(Short.MAX_VALUE + 1));
            request.setFaultStatus(random.nextInt());
        }
        if (random.nextBoolean())
        {
            request.setFaultDetails(dataGen.generateSimpleTestString(random, 0, 50));
        }
        return request;
    }
    
    public ProcessedRequest generateInValidProcessedRequest()
    {
        ProcessedRequest request = new ProcessedRequest();
        byte[] nbyte = new byte[30];
        byte rndbyte = nbyte[1];
        request.setAieId(dataGen.generateSimpleTestString(random, 42, 50));
        request.setAscomMsgId(dataGen.generateSimpleTestString(random, 22, 30));
        request.setUniteLogId(dataGen.generateSimpleTestString(random, 22, 30));
        request.setTimestamp(new Timestamp((System.currentTimeMillis() / 10) * 10));
       
        if (random.nextBoolean())
        {
//            request.setPort((short) random.nextInt(Short.MAX_VALUE + 1));
            request.setPort(random.nextInt());
        }
        if (random.nextBoolean())
        {
            request.setVersion(dataGen.generateSimpleTestString(random, 12, 20));
        }
        if (random.nextBoolean())
        {
            request.setCallId(random.nextInt());
        }
        if (random.nextBoolean())
        {
            request.setOAPRequest(dataGen.generateSimpleTestString(random, 102, 110));
        }
        if (random.nextBoolean())
        {
            request.setSubject(dataGen.generateSimpleTestString(random, 53, 60));
        }
        if (random.nextBoolean())
        {
             request.setPriority(rndbyte);
        }
        if (random.nextBoolean())
        { 
        	 
            request.setPriority(rndbyte);
        }
        if (random.nextBoolean())
        {
            request.setTag(dataGen.generateSimpleTestString(random, 52, 60));
        }
        if (random.nextBoolean())
        {
            request.setOapType(dataGen.generateSimpleTestString(random, 42, 50));
        }
        if (random.nextBoolean())
        {
//            request.setFaultStatus((short) random.nextInt(Short.MAX_VALUE + 1));
            request.setFaultStatus(random.nextInt());
        }
        if (random.nextBoolean())
        {
            request.setFaultDetails(dataGen.generateSimpleTestString(random, 52, 60));
        }
        return request;
    }

    public ProcessedRequest generateValidProcessedRequest(Date date)
    {
        ProcessedRequest request = new ProcessedRequest();
        byte[] nbyte = new byte[30];
        byte rndbyte = nbyte[1];
        request.setAieId(dataGen.generateSimpleTestString(random, 0, 40));
        request.setAscomMsgId(dataGen.generateSimpleTestString(random, 0, 20));
        request.setUniteLogId(dataGen.generateSimpleTestString(random, 0, 20));
        request.setTimestamp(new Timestamp(date.getTime()));
        
        if (random.nextBoolean())
        {
//            request.setPort((short) random.nextInt(Short.MAX_VALUE + 1));
        	request.setPort(random.nextInt());
        }
        if (random.nextBoolean())
        {
            request.setVersion(dataGen.generateSimpleTestString(random, 0, 10));
        }
        if (random.nextBoolean())
        {
            request.setCallId(random.nextInt());
        }
        if (random.nextBoolean())
        {
            request.setOAPRequest(dataGen.generateSimpleTestString(random, 0, 100));
        }
        if (random.nextBoolean())
        {
            request.setSubject(dataGen.generateSimpleTestString(random, 0, 50));
        }
        if (random.nextBoolean())
        {
             request.setPriority(rndbyte);
        }
        if (random.nextBoolean())
        { 
        	 
            request.setPriority(rndbyte);
        }
        if (random.nextBoolean())
        {
            request.setTag(dataGen.generateSimpleTestString(random, 0, 50));
        }
        if (random.nextBoolean())
        {
            request.setOapType(dataGen.generateSimpleTestString(random, 0, 40));
        }
        if (random.nextBoolean())
        {
//            request.setFaultStatus((short) random.nextInt(Short.MAX_VALUE + 1));
            request.setFaultStatus(random.nextInt());
        }
        if (random.nextBoolean())
        {
            request.setFaultDetails(dataGen.generateSimpleTestString(random, 0, 50));
        }
        return request;
    }


}

