/*
 * AbstractDatabaseManager.java
 */
package edu.cecs.fpo.database.management;

import java.util.logging.Logger;

import edu.cecs.fpo.common.SystemLogger;

/**
 * A tool used to manage database information and send queries to the database.
 * 
 * @author Nick Roberts
 *
 */
public class AbstractDatabaseManager {
	
	private final static Logger logger = SystemLogger.createLogger(AbstractDatabaseManager.class);

	/**
	 * Constructor - Creates a new abstract database manager
	 */
	public AbstractDatabaseManager(){
		initialize();
	}
	
	private void initialize(){
		logger.log(SystemLogger.INFO, "Initializing databases");
		
	}
	
	/**
	 * Main - Used purely for testing purposes, currently
	 * 
	 * @param args
	 */
	public static void main(String[] args){
		logger.log(SystemLogger.INFO, "Starting abstract database manager");
	}
}
