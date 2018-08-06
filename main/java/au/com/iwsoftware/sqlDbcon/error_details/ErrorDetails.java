/*
 *  DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *  
 *  Copyright (c) 2018 IWSoftware Pty Ltd.
 *  All rights reserved.
 */

package au.com.iwsoftware.sqlDbcon.error_details;

import java.util.Objects;

/**
 *
 */
public class ErrorDetails 
{
    private String aieId;
    private String ascomMsgId;
    private int errorNumber;
    private int errorSeverity;
    private int errorState;
    private String errorProcedure;
    private int errorLine;
    private String errorMessage;

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.aieId);
        hash = 71 * hash + Objects.hashCode(this.ascomMsgId);
        hash = 71 * hash + this.errorNumber;
        hash = 71 * hash + this.errorSeverity;
        hash = 71 * hash + this.errorState;
        hash = 71 * hash + Objects.hashCode(this.errorProcedure);
        hash = 71 * hash + this.errorLine;
        hash = 71 * hash + Objects.hashCode(this.errorMessage);
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
        final ErrorDetails other = (ErrorDetails) obj;
        if (this.errorNumber != other.errorNumber)
        {
            return false;
        }
        if (this.errorSeverity != other.errorSeverity)
        {
            return false;
        }
        if (this.errorState != other.errorState)
        {
            return false;
        }
        if (this.errorLine != other.errorLine)
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
        if (!Objects.equals(this.errorProcedure, other.errorProcedure))
        {
            return false;
        }
        if (!Objects.equals(this.errorMessage, other.errorMessage))
        {
            return false;
        }
        return true;
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
     * @return the errorNumber
     */
    public int getErrorNumber()
    {
        return errorNumber;
    }

    /**
     * @param errorNumber the errorNumber to set
     */
    public void setErrorNumber(int errorNumber)
    {
        this.errorNumber = errorNumber;
    }

    /**
     * @return the errorSeverity
     */
    public int getErrorSeverity()
    {
        return errorSeverity;
    }

    /**
     * @param errorSeverity the errorSeverity to set
     */
    public void setErrorSeverity(int errorSeverity)
    {
        this.errorSeverity = errorSeverity;
    }

    /**
     * @return the errorState
     */
    public int getErrorState()
    {
        return errorState;
    }

    /**
     * @param errorState the errorState to set
     */
    public void setErrorState(int errorState)
    {
        this.errorState = errorState;
    }

    /**
     * @return the errorProcedure
     */
    public String getErrorProcedure()
    {
        return errorProcedure;
    }

    /**
     * @param errorProcedure the errorProcedure to set
     */
    public void setErrorProcedure(String errorProcedure)
    {
        this.errorProcedure = errorProcedure;
    }

    /**
     * @return the errorLine
     */
    public int getErrorLine()
    {
        return errorLine;
    }

    /**
     * @param errorLine the errorLine to set
     */
    public void setErrorLine(int errorLine)
    {
        this.errorLine = errorLine;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage()
    {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
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
}
