/*
 * LoggerManager.java
 */
package edu.cecs.fpo.common;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import edu.cecs.fpo.database.management.AbstractDatabaseManager;

/**
 * A tool used to manage logging resources.
 * 
 * @author Nick Roberts
 */
public class LoggerManager{
	
	//---[STATIC FIELDS]---
	
	/**
	 * Logging levels used by the packages of the system.
	 */
	public final static Level WARN = Level.WARNING;
	public final static Level INFO = Level.INFO;
	public final static Level SEVERE = Level.SEVERE;
	public final static Level ALL = Level.ALL;
	
	/** File name used to locate and open the loggeing configuration settings file */
	private final static String LOGGING_CONFIG_FILE_NAME = "config/logger/logging.properties";
	
	/** 
	 * A map of each package to its respective logging level.
	 */
	private final static HashMap<String, Level> packageToLogLevel = new HashMap<>();
	
	static{
		
		Properties prop  = new Properties();
		InputStream input = null;
		
		try{			
			//get the logging configuration settings from the designated file
			input = new FileInputStream(LOGGING_CONFIG_FILE_NAME);			
			prop.load(input);
					
			//get the package logging levels
			packageToLogLevel.put("edu.cecs.fpo.common", toLogLevel(prop.getProperty("edu.cecs.fpo.common")));
			packageToLogLevel.put("edu.cecs.fpo.database.management", toLogLevel(prop.getProperty("edu.cecs.fpo.database.management")));
			packageToLogLevel.put("edu.cecs.fpo.database.tables", toLogLevel(prop.getProperty("edu.cecs.fpo.database.tables")));
			packageToLogLevel.put("edu.cecs.fpo.server", toLogLevel(prop.getProperty("edu.cecs.fpo.server")));
			packageToLogLevel.put("edu.cecs.fpo.servlet", toLogLevel(prop.getProperty("edu.cecs.fpo.servlet")));
						
		} catch(Exception e){
		
		} finally {
			if(input != null){
				try{
					input.close();
				} catch(Exception e){
					
				}
			}
		}
	}
	
	
	//---[METHODS]---
	
	/**
	 * Constructor - Creates a new logger manager.
	 */
	protected LoggerManager() {
		//nothing to do yet
	}
	
	/**
	 * Creates and initializes a logger for the specified class.
	 * 
	 * @param clazz The class for which this logger is being made
	 * @return A new logger for the specified class
	 */
	public static Logger createLogger(Class<?> clazz){
		
		//create the logger and set its logging level. If a log level has not been specified, defaults to WARN.
		Logger logger = Logger.getLogger(clazz.getName());
		logger.setLevel(packageToLogLevel.containsKey(clazz.getPackage().getName()) ? packageToLogLevel.get(clazz.getPackage().getName()) : WARN);
		
		//make the logger print to an output file named <package name>_<week day>_ <month>_<day>_<hour-minute-seccond>.log.html
		Handler handler;
		try {
			Date date = new Date();
			handler = new FileHandler(("output/logger/" + clazz.getPackage().getName().replace("edu.cecs.fpo.", "") + "_" + date.toString() + ".log.html").replace(" ", "_").replace(":", "-"));
			handler.setFormatter(new SimpleFormatter());
			logger.addHandler(handler);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.log(LoggerManager.INFO, "Logger initialized for class " + clazz.getName());
		
		return logger;		
	}
	
	public static Level toLogLevel(String levelName){
		switch(levelName){
			case "WARN": return WARN;
			case "INFO": return INFO;
			case "SEVERE": return SEVERE;
			case "ALL": return ALL;
			default: return WARN;
		}
	}
}
