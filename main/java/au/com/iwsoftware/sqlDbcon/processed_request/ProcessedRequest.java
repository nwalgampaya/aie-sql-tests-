/*
 *  DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *  
 *  Copyright (c) 2018 IWSoftware Pty Ltd.
 *  All rights reserved.
 */

package au.com.iwsoftware.sqlDbcon.processed_request;

import java.sql.Timestamp;
import java.util.Objects;

/**
 *
 */
public class ProcessedRequest 
{
    private int faultStatus; 
    private String faultDetails;
    private String aieId;
    private String ascomMsgId;
    private String uniteLogId;
    private Timestamp timestamp;
    private int port;
    private String version;
    private int callId;
    private String OAPRequest;
    private String subject;
    private byte beepCode;
    private byte priority;
    private String tag;
    private String oapType;

    @Override
    public String toString()
    {
        return "ProcessedRequest{" + "faultStatus=" + faultStatus + ", faultDetails=" + faultDetails + ", aieId=" + aieId +
               ", ascomMsgId=" + ascomMsgId + ", uniteLogId=" + uniteLogId + ", timestamp=" + timestamp + ", port=" +
               port + ", version=" + version + ", callId=" + callId + ", body=" + OAPRequest + ", subject=" + subject +
               ", beepCode=" + beepCode + ", priority=" + priority + ", tag=" + tag + ", oapType=" + oapType + '}';
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 97 * hash + this.faultStatus;
        hash = 97 * hash + Objects.hashCode(this.faultDetails);
        hash = 97 * hash + Objects.hashCode(this.aieId);
        hash = 97 * hash + Objects.hashCode(this.ascomMsgId);
        hash = 97 * hash + Objects.hashCode(this.uniteLogId);
        hash = 97 * hash + Objects.hashCode(this.timestamp);
        hash = 97 * hash + this.port;
        hash = 97 * hash + Objects.hashCode(this.version);
        hash = 97 * hash + this.callId;
        hash = 97 * hash + Objects.hashCode(this.OAPRequest);
        hash = 97 * hash + Objects.hashCode(this.subject);
        hash = 97 * hash + this.beepCode;
        hash = 97 * hash + this.priority;
        hash = 97 * hash + Objects.hashCode(this.tag);
        hash = 97 * hash + Objects.hashCode(this.oapType);
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
        final ProcessedRequest other = (ProcessedRequest) obj;
        if (this.faultStatus != other.faultStatus)
        {
            return false;
        }
        if (this.port != other.port)
        {
            return false;
        }
        if (this.callId != other.callId)
        {
            return false;
        }
        if (this.beepCode != other.beepCode)
        {
            return false;
        }
        if (this.priority != other.priority)
        {
            return false;
        }
        if (!Objects.equals(this.faultDetails, other.faultDetails))
        {
            return false;
        }
        if (!Objects.equals(this.aieId, other.aieId))
        {
            return false;
        }
        if (!Objects.equals(this.ascomMsgId, other.ascomMsgId))
        {
            return false;
        }
        if (!Objects.equals(this.uniteLogId, other.uniteLogId))
        {
            return false;
        }
        if (!Objects.equals(this.version, other.version))
        {
            return false;
        }
        if (!Objects.equals(this.OAPRequest, other.OAPRequest))
        {
            return false;
        }
        if (!Objects.equals(this.subject, other.subject))
        {
            return false;
        }
        if (!Objects.equals(this.tag, other.tag))
        {
            return false;
        }
        if (!Objects.equals(this.oapType, other.oapType))
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
     * @return the ascomMsgId
     */
    public String getAscomMsgId()
    {
        return ascomMsgId;
    }

    /**
     * @param ascomMsgId the ascomMsgId to set
     */
    public void setAscomMsgId(String ascomMsgId)
    {
        this.ascomMsgId = ascomMsgId;
    }

    /**
     * @return the uniteLogId
     */
    public String getUniteLogId()
    {
        return uniteLogId;
    }

    /**
     * @param uniteLogId the uniteLogId to set
     */
    public void setUniteLogId(String uniteLogId)
    {
        this.uniteLogId = uniteLogId;
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
     * @return the port
     */
    public int getPort() {
    	return port;
    }
    
    

    /**
     * @param port the port to set
     */
    public void setPort(int port) {
    	this.port = port;
    }

    /**
     * @return the version
     */
    public String getVersion()
    {
        return version;
    }


	/**
     * @param version the version to set
     */
    public void setVersion(String version)
    {
        this.version = version;
    }

    /**
     * @return the callId
     */
    public int getCallId()
    {
        return callId;
    }

    /**
     * @param callId the callId to set
     */
    public void setCallId(int callId)
    {
        this.callId = callId;
    }

    /**
     * @return the body
     */
    public String getOAPRequest() {
		return OAPRequest;
	}


     /**
      * @param oAPRequest
      */

	public void setOAPRequest(String oAPRequest) {
		OAPRequest = oAPRequest;
	}
    /**
     * @return the subject
     */
    public String getSubject()
    {
        return subject;
    }



	/**
     * @param subject the subject to set
     */
    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    /**
     * @return the beepCode
     */
    public byte getBeepCode()
    {
        return beepCode;
    }

    /**
     * @param beepCode the beepCode to set
     */
    public void setBeepCode(byte beepCode)
    {
        this.beepCode = beepCode;
    }

    /**
     * @return the priority
     */
    public byte getPriority()
    {
        return priority;
    }

    /**
     * @param priority the priority to set
     */
    public void setPriority(byte priority)
    {
        this.priority = priority;
    }

    /**
     * @return the tag
     */
    public String getTag()
    {
        return tag;
    }

    /**
     * @param tag the tag to set
     */
    public void setTag(String tag)
    {
        this.tag = tag;
    }

    /**
     * @return the oapType
     */
    public String getOapType()
    {
        return oapType;
    }

    /**
     * @param oapType the oapType to set
     */
    public void setOapType(String oapType)
    {
        this.oapType = oapType;
    }
}
