package edu.cecs.fpo.server;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import edu.cecs.fpo.common.LoggerManager;
import edu.cecs.fpo.database.management.AbstractDatabaseManager;
import edu.cecs.fpo.database.tables.AbstractTableEntry;
import edu.cecs.fpo.database.tables.User;

/**
 * An implementation of server methods used to provide services requested from the web page by clients.
 * 
 * @author Nick Roberts
 */
public class ServerImpl {
	
	//---[STATIC FIELDS]---
	
	/** Logger used in debugging and detecting exceptional behavior */
	private final static Logger logger = LoggerManager.createLogger(AbstractDatabaseManager.class);
	
	/** File name used to locate and open the messenger configuration settings file */
	private static String MESSENGER_CONFIG_FILE_NAME = "config/server/messenger.properties";
	
	/** Properties used by the messenger to send emails */
	private static String SENDER_EMAIL_ADDRESS;
	private static String SENDER_EMAIL_PASSWORD;
	private static Properties EMAIL_SESSION_PROPERTIES;
	
	static {	
		
		Properties prop  = new Properties();
		InputStream input = null;
		
		try{			
			logger.log(LoggerManager.INFO, "Getting messenger configuration settings from " + MESSENGER_CONFIG_FILE_NAME + ".");
			
			//get the messenger configuration settings from the designated file
			input = new FileInputStream(MESSENGER_CONFIG_FILE_NAME);			
			prop.load(input);
					
			//get the messanger's email address, email password, and email propeties from the server configuration settings
			SENDER_EMAIL_ADDRESS = prop.getProperty("senderEmailAddress");
			SENDER_EMAIL_PASSWORD = prop.getProperty("senderEmailPassword");
			prop.remove("senderEmailAddress");
			prop.remove("senderEmailPassword");
			EMAIL_SESSION_PROPERTIES = prop;
			
		} catch(Exception e){
			logger.log(LoggerManager.WARN, "An error occured while getting the messenger configuration in "+ MESSENGER_CONFIG_FILE_NAME +".", e);
		
		} finally {
			if(input != null){
				try{
					input.close();
				} catch(Exception e){
					logger.log(LoggerManager.WARN, "An error occured while closing "+ MESSENGER_CONFIG_FILE_NAME +".", e);
				}
			}
		}
	}
	
	//---[STATIC SUBCLASSES]---
	
	/**
	 * An extension of javax.mail.Authenticator used to send emails from the account with the specified address and password.
	 * 
	 * @author Nick Roberts
	 */
	private static class MailAuthenticator extends Authenticator{
		String address;
		String password;
		
		public MailAuthenticator(String username, String password){
			super();
			
			this.address = username;
			this.password = password;
		}
		
		public PasswordAuthentication getPasswordAuthentication(){
			return new PasswordAuthentication(address, password);
		}
	}
	
	//---[METHODS]---
	
	/**
	 * Gets the user with the specified username and password
	 * 
	 * @param username The user's username
	 * @param password The user's password
	 * @return The user with the specified username and password, or null if no such user exists.
	 */
	public static User login(String username, String password) throws Exception{
		
		logger.log(LoggerManager.INFO, "Logging in user with username = " + username + " and password = " + password + "." );
		
		//map the username and password passed in to their respective columns in the USERS table
		HashMap<String, Object> columnNamesToValues = new HashMap<String, Object>();
		columnNamesToValues.put(User.USER_NAME, username);
		columnNamesToValues.put(User.PASSWORD, password);
		
		//have the database manager query for the username and password in the USERS table
		List<AbstractTableEntry> userList = AbstractDatabaseManager.selectRowsByColumns(columnNamesToValues, User.class);
		
		if(userList == null){
			
			//if the list of users returned is null, an error occurred
			throw new Exception("An error occurred while searching for login information in the database.");
		}
		
		else if(!userList.isEmpty()
				&& userList.get(0) instanceof User){
			
			logger.log(LoggerManager.INFO, "Successfully logged in user.");
			
			//otherwise, if a user with the specified username and password is found, return the user
			return (User) userList.get(0);
		}
		
		logger.log(LoggerManager.INFO, "Provided credentials do not match any user in the database.");
		return null;
	}
	
	/**
	 * Sends an email to the specified email addresses with the specified subject and message content.
	 * 
	 * @param recipientAddresses The email addresses to send the email to
	 * @param subject The subject of the email
	 * @param message The message content of the email
	 * @throws Exception If an error occurs while sending the email
	 */
	public static void sendEmail(String[] recipientAddresses, String subject, String message) throws Exception{
		
		logger.log(LoggerManager.INFO, "Sending an email to " + recipientAddresses + ".");

		Session session = Session.getDefaultInstance(
				EMAIL_SESSION_PROPERTIES, 
				new MailAuthenticator(SENDER_EMAIL_ADDRESS, SENDER_EMAIL_PASSWORD)
		);
		
		try{
			Message msg = new MimeMessage(session);
			
			msg.setFrom(new InternetAddress(SENDER_EMAIL_ADDRESS, "[PURCHASE ORDER MESSENGER]"));
			for(int i=0; i < recipientAddresses.length; i++){
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientAddresses[i]));
			}
			
			msg.setSubject(subject);
			msg.setText(message);
			Transport.send(msg);
			
		}catch(Exception e){
			throw new Exception("An error occurred while emailing a message.", e);
		}
	}
	
	/**
	 * Main method - Used for testing
	 * 
	 * @param args Function arguments (unused)
	 */
	public static void main(String[] args){
		
		//this is commented mainly because I don't want my inbox getting full of these.
		
		/*try {
			String addressees[] = {"nickar0b3rts@gmail.com"};
			sendEmail(addressees, "Testing Time", "Testing. 1... 2.... 3.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

}
