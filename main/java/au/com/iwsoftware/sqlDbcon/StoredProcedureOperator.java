/*
 *  DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 */

package au.com.iwsoftware.sqlDbcon;

/**
 *
 */
public class StoredProcedureOperator 
{
    private final String schema;
    private final String name;
    private final int paramCount;
    
    public StoredProcedureOperator(String schema, String name, int paramCount)
    {
        this.schema = schema;
        this.name = name;
        this.paramCount = paramCount;
    }
    
    public String getProcedureReference()
    {
        return String.format("%s.%s", schema, name);
    }
    
    public String createCallStatement()
    {
        StringBuilder callBuilder = new StringBuilder();
        callBuilder.append("{ call ");
        callBuilder.append(getProcedureReference());
        callBuilder.append("(");
        
        for (int i = 0 ; i < paramCount ; ++i)
        {
            if (i != 0)
            {
                callBuilder.append(", ");
            }
            callBuilder.append("?");
        }
        callBuilder.append(") };");
        return callBuilder.toString();
    }
}
