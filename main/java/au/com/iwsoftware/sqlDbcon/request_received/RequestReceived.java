/*
 *  DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *  
 *  Copyright (c) 2018 IWSoftware Pty Ltd.
 *  All rights reserved.
 */

package au.com.iwsoftware.sqlDbcon.request_received;

import java.sql.Timestamp;
import java.util.Objects;

/**
 *
 */
public class RequestReceived 
{
    private String aieId;
    private Timestamp timestamp;
    private String body;
    private String eventType;
    private String alertType;
    private int faultStatus;
    private String faultDetails;

    
    @Override
    public String toString()
    {
        return "RequestReceived{" + "faultStatus=" + faultStatus + ", faultDetails=" + faultDetails + ", aieId=" + aieId +
               ", timestamp=" + timestamp + ", eventType=" +
               eventType + ", alertType=" + alertType + ", faultStatus=" + faultStatus + ", faultDetails=" + faultDetails + '}';
    }
    
    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.aieId);
        hash = 31 * hash + Objects.hashCode(this.timestamp);
        hash = 31 * hash + Objects.hashCode(this.body);
        hash = 31 * hash + Objects.hashCode(this.eventType);
        hash = 31 * hash + Objects.hashCode(this.alertType);
        hash = 31 * hash + this.faultStatus;
        hash = 31 * hash + Objects.hashCode(this.faultDetails);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final RequestReceived other = (RequestReceived) obj;
        if (this.faultStatus != other.faultStatus)
        {
            return false;
        }
        if (!Objects.equals(this.aieId, other.aieId))
        {
            return false;
        }
        if (!Objects.equals(this.body, other.body))
        {
            return false;
        }
        if (!Objects.equals(this.eventType, other.eventType))
        {
            return false;
        }
        if (!Objects.equals(this.alertType, other.alertType))
        {
            return false;
        }
        if (!Objects.equals(this.faultDetails, other.faultDetails))
        {
            return false;
        }
        if (!Objects.equals(this.timestamp, other.timestamp))
        {
            return false;
        }
        return true;
    }

    
    
    /**
     * @return the aieId
     */
    public String getAieId()
    {
        return aieId;
    }

    /**
     * @param aieId the aieId to set
     */
    public void setAieId(String aieId)
    {
        this.aieId = aieId;
    }

    /**
     * @return the timestamp
     */
    public Timestamp getTimestamp()
    {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Timestamp timestamp)
    {
        this.timestamp = timestamp;
    }

    /**
     * @return the body
     */
    public String getBody()
    {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(String body)
    {
        this.body = body;
    }

    /**
     * @return the eventType
     */
    public String getEventType()
    {
        return eventType;
    }

    /**
     * @param eventType the eventType to set
     */
    public void setEventType(String eventType)
    {
        this.eventType = eventType;
    }

    /**
     * @return the alertType
     */
    public String getAlertType()
    {
        return alertType;
    }

    /**
     * @param alertType the alertType to set
     */
    public void setAlertType(String alertType)
    {
        this.alertType = alertType;
    }

    /**
     * @return the faultStatus
     */
    public int getFaultStatus() {
    	return faultStatus;
    }

    /**
     * @param faultStatus the faultStatus to set
     */
    
    public void setFaultStatus(int faultStatus) {
    	this.faultStatus = faultStatus;
    }


    /**
     * @return the faultDetails
     */
    public String getFaultDetails()
    {
        return faultDetails;
    }


	/**
     * @param faultDetails the faultDetails to set
     */
    public void setFaultDetails(String faultDetails)
    {
        this.faultDetails = faultDetails;
    }
}
