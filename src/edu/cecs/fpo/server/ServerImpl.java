package edu.cecs.fpo.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Date;
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
import edu.cecs.fpo.database.tables.Order;
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
	private static String ACCOUNTANT_EMAIL_ADDRESS;
	private static String COMPUTER_PURCHASER_EMAIL_ADDRESS;
	private static String PURCHASER_EMAIL_ADDRESS;
	private static Properties EMAIL_SESSION_PROPERTIES;
	
	static {	
		
		Properties prop  = new Properties();
		InputStream input = null;
		
		try{			
			logger.log(LoggerManager.INFO, "Getting messenger configuration settings from " + MESSENGER_CONFIG_FILE_NAME + ".");
			
			//get the messenger configuration settings from the designated file
			input = ServerImpl.class.getClassLoader().getResourceAsStream(MESSENGER_CONFIG_FILE_NAME);			
			prop.load(input);
					
			//get the messenger's email propeties from the messenger configuration settings
			SENDER_EMAIL_ADDRESS = prop.getProperty("senderEmailAddress");
			SENDER_EMAIL_PASSWORD = prop.getProperty("senderEmailPassword");
			ACCOUNTANT_EMAIL_ADDRESS = prop.getProperty("accountantEmailAddress");
			COMPUTER_PURCHASER_EMAIL_ADDRESS = prop.getProperty("computerPurchaserEmailAddress");
			PURCHASER_EMAIL_ADDRESS = prop.getProperty("purchaserEmailAddress");
			prop.remove("senderEmailAddress");
			prop.remove("senderEmailPassword");
			prop.remove("accountantEmailAddress");
			prop.remove("computerPurchaserEmailAddress");
			prop.remove("purchaserEmailAddress");
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
	
	public static void createAndVerifyPurchaseOrder(String requestorName, String requestorEmailAddress, String accountNumber, boolean isUrgent, boolean isComputer, String vendor, String description, float amount, String attachedFiles) throws Exception{
		
		String[] firstNameLastName = requestorName.split(" ");
		
		HashMap<String, Object> columnNamesToValues = new HashMap<String, Object>();
		columnNamesToValues.put(User.FIRST_NAME, firstNameLastName[0]);
		if(firstNameLastName.length > 1){
			columnNamesToValues.put(User.LAST_NAME, firstNameLastName[1]);
		}
		columnNamesToValues.put(User.EMAIL_ADDRESS, requestorEmailAddress);
		
		List<AbstractTableEntry> userList = AbstractDatabaseManager.selectRowsByColumns(columnNamesToValues, User.class);
		User requestorUser = null;
		
		if(userList == null){
			
			//if the list of users returned is null, an error occurred
			throw new Exception("An error occurred while searching for requestor information in the database.");
		}
		
		else if(!userList.isEmpty()
				&& userList.get(0) instanceof User){
			
			//otherwise, if a user with the specified information is found, use that user as the requestor
			requestorUser = (User) userList.get(0);
		}
		
		if(requestorUser == null){
			throw new Exception("The requestor's information did not match that of any user in the database.");
		}
				
		Order purchaseOrder = new Order(0, requestorUser.getId(), new Date(new java.util.Date().getTime()), new Date(0), new Date(0), new Date(0), accountNumber, isUrgent, isComputer, vendor, description, "", "", requestorName, requestorEmailAddress, amount, "", "", "");
		
		String[] emailRecipients = {requestorEmailAddress};
		
		String fileNames = attachedFiles;
		
		sendEmail(
			emailRecipients,
			"Order Request Sent",
			"Your order request has been sent and is awaiting approval. Your order information is listed below:\n\n"
			+ "\tOrder ID: " + purchaseOrder.getOrderId() +"\n"
			+ "\tRequestor Name: " + requestorName + "\n"
			+ "\tRequestor Email Address: " + requestorEmailAddress + "\n"
			+ "\tUrgent: " + (isUrgent ? "Yes" : "No") + "\n"
			+ "\tComputer: " + (isComputer ? "Yes" : "No") + "\n"
			+ "\tVendor: " + vendor + "\n"
			+ "\tItem Description: " + description + "\n"
			+ "\tTotal Purchase Amount: " + amount + "\n"
			+ "\tAttachments : " + fileNames + "\n");
	}
	
	/**
	 * Main method - Used for testing
	 * 
	 * @param args Function arguments (unused)
	 */
	public static void main(String[] args){
		
		User user = new User(3, "nickar", "testpass", "Nick", "Roberts", "nickar0b3rts@gmail.com", "Faculty");
		
		AbstractDatabaseManager.insertRow(user);
		
		try {
			createAndVerifyPurchaseOrder("Nick Roberts", "nickar0b3rts@gmail.com", "6", true, false, "Best Buy", "Insert Description Here", (float) 10.00, "test.txt");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
