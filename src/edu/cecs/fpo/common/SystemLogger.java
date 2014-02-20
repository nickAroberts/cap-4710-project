/*
 * SystemLogger.java
 */
package edu.cecs.fpo.common;

import java.util.Date;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An extension of the standard Java logger used to manage logging resources.
 * 
 * @author Nick Roberts
 */
public class SystemLogger extends Logger{
	
	/**
	 * Logging levels used by the system.
	 * 
	 * DEBUG specifies that only log statements with a level of WARN should be printed. Use this to print case-specific information for finding data.
	 * WARN specifies that only log statements with a level of WARN or DEBUG should be printed
	 */
	public final static Level WARN = Level.WARNING;
	public final static Level INFO = Level.INFO;
	public final static Level DEBUG = Level.SEVERE;
	public final static Level ALL = Level.ALL;
	
	/** 
	 * A map of each package to its respective logging level.
	 *
	 * Nick: I've defaulted all logging to Level.WARNING to catch exceptions and system errors but nothing else.
	 * To change the logging level of a package, simply change the corresponding mapping below.
	 */
	private final static HashMap<String, Level> packageToLogLevel = new HashMap<>();	
	static{
		packageToLogLevel.put("edu.cecs.fpo.common", WARN);
		packageToLogLevel.put("edu.cecs.fpo.database.management", WARN);
		packageToLogLevel.put("edu.cecs.fpo.database.tables", WARN);
	}
	
	/**
	 * Constructor - Exists only because it is required by the Logger class. Do not use this to make new loggers. Use 'createLogger' instead.
	 * 
	 * @param name
	 * @param resourceBundleName
	 */
	protected SystemLogger(String name, String resourceBundleName) {
		super(name, resourceBundleName);
	}
	
	/**
	 * Creates and initializes a logger for the specified class.
	 * 
	 * @param clazz The class for which this logger is being made
	 * @return A new logger for the specified class
	 */
	public static Logger createLogger(Class<?> clazz){
		
		//create the logger and set its logging level
		Logger logger = SystemLogger.getLogger(clazz.getName());
		logger.setLevel(packageToLogLevel.get(clazz.getPackage().getName()));
		
		//make the logger print to an output file named <package name>_<week day>_ <month>_<day>_<hour-minute-seccond>.log.html
		Handler handler;
		try {
			Date date = new Date();
			handler = new FileHandler(("output/logger/" + clazz.getPackage().getName().replace("edu.cecs.fpo.", "") + "_" + date.toString() + ".log.html").replace(" ", "_").replace(":", "-"));
			logger.addHandler(handler);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.log(SystemLogger.INFO, "Logger initialized for class " + clazz.getName());
		
		return logger;		
	}
}
