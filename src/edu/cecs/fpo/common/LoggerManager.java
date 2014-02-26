/*
 * LoggerManager.java
 */
package edu.cecs.fpo.common;

import java.util.Date;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

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
	public final static Level DEBUG = Level.SEVERE;
	public final static Level ALL = Level.ALL;
	
	/** 
	 * A map of each package to its respective logging level.
	 *
	 * Nick: I've defaulted all logging to WARN to catch exceptions and system errors but nothing else.
	 * To change the logging level of a package, simply change the corresponding mapping below.
	 */
	private final static HashMap<String, Level> packageToLogLevel = new HashMap<>();	
	static{
		packageToLogLevel.put("edu.cecs.fpo.common", WARN);
		packageToLogLevel.put("edu.cecs.fpo.database.management", WARN);
		packageToLogLevel.put("edu.cecs.fpo.database.tables", WARN);
		packageToLogLevel.put("edu.cecs.fpo.web.client", WARN);
		packageToLogLevel.put("edu.cecs.fpo.web.server", WARN);
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
}
