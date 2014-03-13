/*
 * Users.java
 */
package edu.cecs.fpo.database.tables;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The table representation of users within the database.
 * 
 * @author Nick
 */
public class User extends AbstractTableEntry{
	
	//---[STATIC FIELDS]---
	
	private static final String TABLE_NAME = "USERS";	
	private static final String[] COLUMN_NAMES = {
		"userId",
		"username",
		"pass",
		"firstName",
		"lastName",
		"emailAddress",
		"role"
	};
	private static final String PRIMARY_KEY_NAME = COLUMN_NAMES[0];
	
	
	//---[INSTANCE FIELDS]---

	/** 
	 * PRIMARY KEY - The user's identification number
	 * */
	private int userId;

	/** The user's username */
	private String username;

	/** The user's password */
	private String password;

	/** The user's first name */
	private String firstName;

	/** The user's last name*/
	private String lastName;

	/** The user's email address */
	private String emailAddress;

	/** The user's role */
	private String role;
	
	
	//---[METHODS]---
	
	/**
	 * Creates a new user with no specified information.
	 */
	public User(){
		super(TABLE_NAME, COLUMN_NAMES, PRIMARY_KEY_NAME);
	}
	
	/**
	 * Creates a new user.
	 * 
	 * @param userId The user's ID number
	 * @param username The user's username
	 * @param password The user's password
	 * @param firstName The user's first name
	 * @param lastName The user's last name
	 * @param emailAddress The user's email address
	 * @param role The user's role description
	 */
	public User(int userId, String username, String password, String firstName, String lastName, String emailAddress, String role){
		super(TABLE_NAME, COLUMN_NAMES, PRIMARY_KEY_NAME);
		
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		this.role = role;
	}
	
	public int getUserId(){
		return this.userId;
	}
	
	public void setUserId(int userId){
		this.userId = userId;
	}
	
	public String getUsername(){
		return this.username;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public String getFirstName(){
		return this.firstName;
	}
	
	public void setFirstName(String firstName){
		this.firstName = firstName;
	}
	
	public String getLastName(){
		return this.lastName;
	}
	
	public void setLastName(String lastName){
		this.lastName = lastName;
	}
	
	public String getEmailAddress(){
		return this.emailAddress;
	}
	
	public void setEmailAddress(String emailAddress){
		this.emailAddress = emailAddress;
	}
	
	public String getRole(){
		return this.role;
	}
	
	public void setRole(String role){
		this.role = role;
	}

	@Override
	public int getId() {
		return getUserId();
	}
	
	@Override
	public void setId(int id) {
		setUserId(id);
	}
	
	@Override
	public AbstractTableEntry populateFromResultSet(ResultSet set) throws SQLException {

		//populate the values for this user with the values in the database
		setUserId(set.getInt(COLUMN_NAMES[0]));
		setUsername(set.getString(COLUMN_NAMES[1]));
		setPassword(set.getString(COLUMN_NAMES[2]));
		setFirstName(set.getString(COLUMN_NAMES[3]));
		setLastName(set.getString(COLUMN_NAMES[4]));
		setEmailAddress(set.getString(COLUMN_NAMES[5]));
		setRole(set.getString(COLUMN_NAMES[6]));	
		
		return this;
	}

	@Override
	public String toSQLRepresentation() {
		return "(" + 
			userId + ", " +
			"\"" + username + "\", " +
			"\"" + password + "\", " +
			"\"" + firstName + "\", " +
			"\"" + lastName + "\", " +
			"\"" + emailAddress + "\", " +
			"\"" + role + "\"" +
		")";
	}

	@Override
	public List<Object> getValues() {
		
		List<Object> values = new ArrayList<Object>();
		
		values.add(getUserId());
		values.add(getUsername());
		values.add(getPassword());
		values.add(getFirstName());
		values.add(getLastName());
		values.add(getEmailAddress());
		values.add(getRole());
		
		return Collections.unmodifiableList(values);
	}

	@Override
	public Object getValue(int index) {
		try{
			return getValues().get(index);
		} catch(IndexOutOfBoundsException e){
			return null;
		}
	}
}
