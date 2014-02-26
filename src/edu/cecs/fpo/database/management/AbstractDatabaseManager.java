/*
 * AbstractDatabaseManager.java
 */
package edu.cecs.fpo.database.management;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.logging.Logger;

import edu.cecs.fpo.common.LoggerManager;

/**
 * A tool used to manage database information and send queries to the database.
 * 
 * @author Nick Roberts
 *
 */
public class AbstractDatabaseManager {
	
	//---[STATIC FIELDS]---
	
	/** Logger used in debugging and detecting exceptional behavior */
	private final static Logger logger = LoggerManager.createLogger(AbstractDatabaseManager.class);
	
	/** Class name used to dynamically load the JDBC driver */
	private final static String JDBC_DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
	
	/** Host URL used to establish the server connection */
	private final static String HOST_URL = "jdbc:mysql://localhost/";
	
	/** The name of the MySQL database*/
	private final static String DATABASE_NAME = "facultypurshaseorders";
	
	/** The user name used to connect to the MySQL host */
	private final static String USER = "root";
	
	/** The password used to connect to the MySQL host */
	private final static String PASSWORD = "cap-4710-project-spring-2014";

	
	//---[METHODS]---
	
	/**
	 * Constructor - Creates a new abstract database manager
	 */
	public AbstractDatabaseManager(){
		//nothing to do
	}
	
	/**
	 * Creates a connection to the MySQL database
	 * 
	 * @return the connection to the database, if such a connection could be established
	 * @throws ConnectException if a connection to the database could not be established
	 */
	public static Connection establishConnection() throws ConnectException{
		
		 logger.log(LoggerManager.INFO, "Connecting to the database...");
		 
		 Connection connection;
		
		 try{
			 //load the MySQL driver for JDBC
			 Class.forName(JDBC_DRIVER_CLASS_NAME);
		     
		    try{
				//try to connect to the database
				connection = DriverManager.getConnection(
			    		 HOST_URL + DATABASE_NAME,
			             USER,
			             PASSWORD
			     );
				
			}catch(Exception e){			
				if(e.getMessage().contains("Unknown database")){			
					
					//if the database does not exist, open a connection to the host and create the database
					connection = DriverManager.getConnection(
					    	HOST_URL,
					    	USER,
				            PASSWORD
				    );
					
				    createDatabase(connection);	
				    
				}else{
					throw e;
				}
				
			}
		     
		 }catch(Exception e){
			 logger.log(LoggerManager.WARN, "An error occurred while establishing a connection to the database.", e);
			 throw new ConnectException();
		 }
		 
		 logger.log(LoggerManager.INFO, "Connection successfully established to the database.");
		 return connection;
	}
	
	/**
	 * Creates the database and redirects the specified connection to it
	 * 
	 * @param connection
	 */
	private static void createDatabase(Connection connection){
		
		logger.log(LoggerManager.INFO, "Creating the database...");
		
		try {					
			//create the database
			Statement statement = connection.createStatement();
			statement.executeUpdate("create database if not exists " + DATABASE_NAME +";");
			
			logger.log(LoggerManager.INFO, "Database created successfully. Creating tables...");

			//open a connection to the newly created database
		    connection = DriverManager.getConnection(
		    		 HOST_URL + DATABASE_NAME,
		             USER,
		             PASSWORD
		     );
		    
		    statement = connection.createStatement();
			
		    //create USERS table
			statement.executeUpdate(
				"create table if not exists USERS(userID int not null auto_increment,"+ 
						"username varchar(25) NOT NULL," + 
						"pass varchar(50) NOT NULL," + 
						"firstName varchar(50)," + 
						"lastName varchar(50)," +
						"emailAddress varchar(75)," +
						"role varchar (25)," +
						"primary key (userID)" +
				");"
			);
			
			//create ORDERS table
			statement.executeUpdate(
				"create table if not exists ORDERS(orderID int not null auto_increment," + 
						"userId int," +
						"orderRequestDate date," +
						"purchaseDate date," +
						"approvalDate date," +
						"receiveDate date," +
						"accountNumber bigint," +
						"urgent varchar(5),"+
						"computerPurchase varchar(50),"+
						"vendor varchar(50),"+
						"itemDesc varchar(250)," +
						"preOrderNotes varchar(250)," +
						"attachment varchar(100)," +		
						"requestor varchar(50)," +
						"requestorEmail varchar(75)," +
						"amount float," +
						"accountCode varchar(50)," +
						"PONumber bigint," +
						"postOrderNotes varchar(250)," +
						"primary key(orderID)" +
				");"
			);
			
			logger.log(LoggerManager.INFO, "Tables created successfully.");
			
			statement.close();
			
		} catch (Exception e) {
			logger.log(LoggerManager.WARN, "An error occurred while creating the database", e);
		}
	}
	
	/**
	 * Terminates the specified connection and frees the JDBC resources associated with it.
	 * 
	 * @param connection the connection to terminate
	 * @return whether or not the connection could be terminated
	 */
	private static boolean terminateConnection(Connection connection){
		try {
			connection.close();
			return true;
			
		} catch (Exception e) {
			logger.log(LoggerManager.WARN, "An error occurred while closing the connection.", e);
		}
		
		return false;
	}
	
	/**
	 * Main - Used purely for testing purposes, currently
	 * 
	 * @param args
	 */
	public static void main(String[] args){
		try {
			Connection c = establishConnection();
			terminateConnection(c);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
