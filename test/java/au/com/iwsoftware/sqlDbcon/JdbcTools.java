/*
 *  DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *  
 *  Copyright (c) 2018 IWSoftware Pty Ltd.
 *  All rights reserved.
 */

package au.com.iwsoftware.sqlDbcon;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 */
public class JdbcTools 
{
    /** A properties file holding the database details */
    private static final File DB_PROPERTIES = new File("src/main/resources/database.properties");
    
    private static final String KEY_TYPE = "jdbc_type";
    private static final String KEY_HOST = "jdbc_host";
    private static final String KEY_PORT = "jdbc_port";
    private static final String KEY_DATABASE = "jdbc_database";
    private static final String KEY_USER = "jdbc_user";
    private static final String KEY_PASSWORD = "jdbc_pass";
    private static final String KEY_SCHEMA = "jdbc_schema";
    
    /**
     * Get the properties of the database from the properties file and system properties.
     * 
     * @return A {@link Properties} object populated with the database details.
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public Properties getDbProperties() throws FileNotFoundException, IOException
    {
        Properties dbProperties = new Properties();
        // TODO: Later allow db property override from system / command line properties
        dbProperties.load(new FileReader(DB_PROPERTIES));
        return dbProperties;
    }
    
    /**
     * Build a JDBC conneciton URL from the provided database properties.
     * 
     * @param dbProperties The database details
     * @return 
     */
    private String buildConnectionURL(Properties dbProperties)
    {
        StringBuilder urlBuild = new StringBuilder();
        
        urlBuild.append("jdbc:");
        urlBuild.append(dbProperties.getProperty(KEY_TYPE, "sqlserver"));
        urlBuild.append("://");
        urlBuild.append(dbProperties.getProperty(KEY_HOST, "localhost"));
        if (dbProperties.containsKey(KEY_PORT) && 
            !dbProperties.getProperty(KEY_PORT).isEmpty())
        {
            urlBuild.append(":");
            urlBuild.append(dbProperties.getProperty(KEY_PORT));
        }
        urlBuild.append(";");
        if (dbProperties.containsKey(KEY_DATABASE) && 
            !dbProperties.getProperty(KEY_DATABASE).isEmpty())
        {
            urlBuild.append("database=");
            urlBuild.append(dbProperties.getProperty(KEY_DATABASE));
            urlBuild.append(";");
        }
        return urlBuild.toString();
    }

    /**
     * Create and open a JDBC connection to the test database.
     * 
     * @return An open connection to the database.
     * @throws ClassNotFoundException
     * @throws IOException 
     */
	public Connection openDbconnection() throws ClassNotFoundException, IOException 
    {
		Connection databaseConnection = null;
		try
        {
            Properties dbProperties = getDbProperties();
            databaseConnection = DriverManager.getConnection(
                    buildConnectionURL(dbProperties), 
                    dbProperties.getProperty(KEY_USER), 
                    dbProperties.getProperty(KEY_PASSWORD));
//			System.out.println("Connected to the database");
		} 
        catch (SQLException err) 
        {
			System.err.println("Error connecting to the database");
			err.printStackTrace(System.err);
			System.exit(0);
		}
		
		return databaseConnection;
	}
}
