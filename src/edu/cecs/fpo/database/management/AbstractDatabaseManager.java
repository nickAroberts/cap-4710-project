/*
 * AbstractDatabaseManager.java
 */
package edu.cecs.fpo.database.management;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import edu.cecs.fpo.common.LoggerManager;
import edu.cecs.fpo.database.tables.AbstractTableEntry;
import edu.cecs.fpo.database.tables.User;

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
	
	/** File name used to locate and open the server configuration settings file */
	private final static String SERVER_CONFIG_FILE_NAME = "config/server.properties";
	
	/** The name of the MySQL database*/
	private final static String DATABASE_NAME = "facultypurshaseorders";
	
	/** Host URL used to establish the MySQL server connection */
	private static String HOST_URL;
	
	/** The user name used to connect to the MySQL host */
	private static String USER;
	
	/** The password used to connect to the MySQL host */
	private static String PASSWORD;

	static {	
		logger.log(LoggerManager.INFO, "Getting the server configuration settings from " + SERVER_CONFIG_FILE_NAME + ".");
		
		Properties prop  = new Properties();
		InputStream input = null;
		
		try{			
			//get the server configuration settings from the designated file
			input = new FileInputStream(SERVER_CONFIG_FILE_NAME);			
			prop.load(input);
					
			//get the host url, user name, and password from the server configuration settings
			HOST_URL = "jdbc:mysql://" + prop.getProperty("ipAddress") + "/";
			USER = prop.getProperty("username");
			PASSWORD = prop.getProperty("password");
			
		} catch(Exception e){
			logger.log(LoggerManager.WARN, "An error occured while getting the server configuration in "+ SERVER_CONFIG_FILE_NAME +".", e);
		
		} finally {
			if(input != null){
				try{
					input.close();
				} catch(Exception e){
					logger.log(LoggerManager.WARN, "An error occured while closing "+ SERVER_CONFIG_FILE_NAME +".", e);
				}
			}
		}
	}
	
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
				    
				    connection = DriverManager.getConnection(
				    		 HOST_URL + DATABASE_NAME,
				             USER,
				             PASSWORD
				     );
				    
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
	 * Inserts or updates a row corresponding to the specified table entry into the table corresponding to that entry.
	 * 
	 * @param entry The table entry for which a row is being inserted
	 * @return Whether or not the insert or update is successful
	 */
	public static boolean insertRow(AbstractTableEntry entry){
		
		logger.log(LoggerManager.INFO, "Inserting row with " + entry.getPrimaryKeyName() + ": " + entry.getId() + " into " + entry.getTableName() + " table.");
		
		Connection connection = null;
		
		try{
			connection = establishConnection();
			Statement statement = connection.createStatement();
			
			StringBuilder sqlCommand = new StringBuilder();
			
			if(selectRowByPrimaryKey(entry.getId(), entry.getClass()) == null){
				//if the entry to insert does not exist in the database, create an insert statement
				
				sqlCommand.append(
					"insert into " + entry.getTableName() + " ("				
				);
				
				for(int i = 0; i < entry.getColumnNames().length; i++){
					
					if(i == entry.getColumnNames().length - 1){
						sqlCommand.append(entry.getColumnNames()[i]);
					
					} else {
						sqlCommand.append(entry.getColumnNames()[i] + ", ");
					}
				}
				
				sqlCommand.append(") values " + entry.toSQLRepresentation() + ";");
			
			}else{
				//otherwise, create an update statement
			
				sqlCommand.append(
					"update " + entry.getTableName() +
					" set "
				);
				
				for(int i = 0; i < entry.getColumnNames().length; i++){
					
					String value;
					if(entry.getValue(i) instanceof String){
						value = "\"" + entry.getValue(i) + "\"";
						
					}else{
						value = entry.getValue(i).toString();
					}
					
					if(i == entry.getColumnNames().length - 1){
						sqlCommand.append(entry.getColumnNames()[i] + "=" + value);
					
					}else{
						sqlCommand.append(entry.getColumnNames()[i] + "=" + value + ", ");
					}
				}
				
				sqlCommand.append(" where " + entry.getPrimaryKeyName() + "=" + entry.getId() + ";");
			}
			
			logger.log(LoggerManager.INFO, "Executing command \"" + sqlCommand.toString() + "\".");
			
			statement.executeUpdate(sqlCommand.toString());
			
			statement.close();
			
			logger.log(LoggerManager.INFO, "Successfully inserted row to the database!");
			
			return true;
			
		} catch(Exception e){
			logger.log(LoggerManager.WARN, "An error occurred while inserting a row into " + entry.getTableName() + " table.", e);
			
		} finally {
			terminateConnection(connection);
		}
		
		return false;
	}
	

	/**
	 * Deletes a row corresponding to the specified table entry from the table corresponding to that entry.
	 * 
	 * @param entry The table entry for which a row is being deleted
	 * @return Whether or not the delete is successful
	 */
	public static boolean deleteRow(AbstractTableEntry entry){
		
		logger.log(LoggerManager.INFO, "Deleting row with " + entry.getPrimaryKeyName() + ": " + entry.getId() + " from " + entry.getTableName() + " table.");
		
		Connection connection = null;
		
		try{
			connection = establishConnection();
			Statement statement = connection.createStatement();
			
			StringBuilder sqlCommand = new StringBuilder();
			
			sqlCommand.append(
				"delete from " + entry.getTableName() + 
				" where " + entry.getPrimaryKeyName() + "=" + entry.getId()
			);
			
			logger.log(LoggerManager.INFO, "Executing command \"" + sqlCommand.toString() + "\".");
			
			statement.executeUpdate(sqlCommand.toString());
			
			statement.close();
			
			logger.log(LoggerManager.INFO, "Successfully deleted row from the database!");
			
			return true;
			
		} catch(Exception e){
			logger.log(LoggerManager.INFO, "An error occurred while deleting a row from " + entry.getTableName() + " table.", e);
			
		} finally{
			terminateConnection(connection);
		}
		
		return false;
	}
	
	/**
	 * Gets a table entry corresponding to a row with the specified primary key value in the table associated with the specified table entry class 
	 * 
	 * @param primaryKeyId The primary key value of the table entry to be returned
	 * @param entryClass The class extending AbstractTableEntry that is associated with the table containing the table entry to be be returned
	 * @return The table entry with the specified primary key value in the table associated with the specified table entry class 
	 */
	public static AbstractTableEntry selectRowByPrimaryKey(int primaryKeyId, Class<? extends AbstractTableEntry> entryClass){	
		
		logger.log(LoggerManager.INFO, "Selecting row with primary key " + primaryKeyId + " from the table associated with the table entry class " + entryClass.getName() + " .");
		
		Connection connection = null;
		
		try{
			AbstractTableEntry classInstance = entryClass.newInstance();

			connection = establishConnection();
			Statement statement = connection.createStatement();
			
			StringBuilder sqlCommand = new StringBuilder();
			
			sqlCommand.append(
				"select * " +
				"from " + classInstance.getTableName() + 
				" where " + classInstance.getPrimaryKeyName() + "=" + primaryKeyId
			);
			
			logger.log(LoggerManager.INFO, "Executing command \"" + sqlCommand.toString() + "\".");
			
			ResultSet entrySet = statement.executeQuery(sqlCommand.toString());
			
			if(entrySet.next()){
				
				classInstance.populateFromResultSet(entrySet);
				
				statement.close();
				
				logger.log(LoggerManager.INFO, "Successfully selected row from the database!");
				
				return classInstance;
				
			}else{
				
				statement.close();
				
				logger.log(LoggerManager.INFO, "Could not find a row in the database.");
				
				return null;
			}

		} catch(ExceptionInInitializerError e){
			logger.log(LoggerManager.WARN, "An error occurred while initializing an instance of the class " + entryClass.getName() + ".", e);
			
		} catch(InstantiationException e){
			logger.log(LoggerManager.WARN, "The class " + entryClass.getName() + " is missing a default, no argument constructor.", e);
			
		} catch(IllegalAccessException e){
			logger.log(LoggerManager.WARN, "The default, no argument constructor of " + entryClass.getName() + " could not be accessed.", e);
			
		} catch(Exception e){
			logger.log(LoggerManager.INFO, "An error occurred while selecting the entry with a primary key of " + primaryKeyId + " from"
					+ " the table associated with the table entry class " + entryClass.getName() + " .");
			
		} finally{
			terminateConnection(connection);
		}
		
		return null;
	}
	
	/**
	 * Terminates the specified connection and frees the JDBC resources associated with it.
	 * 
	 * @param connection the connection to terminate
	 * @return whether or not the connection could be terminated
	 */
	private static boolean terminateConnection(Connection connection){
		
		if(connection != null){
			try {
				connection.close();
				return true;
				
			} catch (Exception e) {
				logger.log(LoggerManager.WARN, "An error occurred while closing the connection.", e);
			}
		}
		
		return false;
	}
	
	/**
	 * Prints the contents of the database to output/dbContents.csv.
	 */
	private static void printDatabaseContents(){
		
		logger.log(LoggerManager.INFO, "Printing database contents to output/dbContents.csv.");
		
		PrintStream stream = null;
		Connection connection = null;
		
		try {
			//open output/dbContents.csv
			FileOutputStream outputStream = new FileOutputStream("output/dbContents.csv");
			stream = new PrintStream(outputStream);
			
			//establish the connection to the database
			connection = establishConnection();			
			
			//get the names of all the tables in the database	
			List<String> tableNames = new ArrayList<String>();
			DatabaseMetaData metadata = connection.getMetaData();
					
			ResultSet tableSet = metadata.getTables(null, null, "%", null);
			while (tableSet.next()) {								
			  tableNames.add(tableSet.getString(3)); 
			  //column 3 is designated as the table's name. See http://docs.oracle.com/javase/6/docs/api/java/sql/DatabaseMetaData.html	  
			}
			
			//print out the contents of each table in the database
			Statement statement = connection.createStatement();			
			
			for(String tableName : tableNames){
				logger.log(LoggerManager.INFO, "Printing contents of table: " + tableName + ".");
				
				stream.print(tableName + ":\n");
				
				//get the set of all entries in the current table
				ResultSet entrySet = statement.executeQuery(
					"select * " +
					"from " + tableName
				);			
				
				//print the column headers
				logger.log(LoggerManager.INFO, "Getting headers for table: " + tableName + ".");
				List<String> columnNames = new ArrayList<String>();
				
				for(int i = 1; i < entrySet.getMetaData().getColumnCount(); i++){
					String columnName = entrySet.getMetaData().getColumnName(i);
					
					stream.print("," + columnName);
					columnNames.add(columnName);
				}
				
				stream.print("\n");
				
				//print the values of each entry
				logger.log(LoggerManager.INFO, "Printing Entries for table: " + tableName + ".");
				while(entrySet.next()){
					
					stream.print("Row: " + entrySet.getRow() + ",");
					
					for(String columnName : columnNames){
						if(columnNames.indexOf(columnName) == columnNames.size()-1){
							stream.print(entrySet.getString(columnName));
						} else{
							stream.print(entrySet.getString(columnName) + ",");
						}						
					}
					
					stream.print("\n");
				}

				stream.print("\n");
				
				logger.log(LoggerManager.INFO, "Finished printing table: " + tableName + ".");
			}
			
			statement.close();		
			
			logger.log(LoggerManager.INFO, "Successfully printed the contents of the database!");
			
		} catch (Exception e) {
			logger.log(LoggerManager.WARN, "An error occurred while printing the contents of the database.", e);
		
		} finally {
			stream.close();
			terminateConnection(connection);
		}
	}
	
	/**
	 * Main - Used purely for testing purposes, currently
	 * 
	 * @param args
	 */
	public static void main(String[] args){
		try {
			
			insertRow(new User(1, "A", "B", "C", "D", "E", "F"));
			
			insertRow(new User(2, "G", "H", "I", "J", "K", "L"));		
			
			deleteRow(new User(2, "Only", "Primary", "Key", "ID", "Matters", "Here"));
			
			User u1 = (User) selectRowByPrimaryKey(1, User.class);
			if(u1 != null){
				System.out.print("User:" + u1.toSQLRepresentation());
			}else{
				System.out.print("No user with the specified ID exists in the database.");
			}
			
			printDatabaseContents();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
