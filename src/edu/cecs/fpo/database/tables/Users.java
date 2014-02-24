/*
 * Users.java
 */
package edu.cecs.fpo.database.tables;

/**
 * The table representation of users within the database.
 * 
 * @author Nick
 */
public class Users {

	/** 
	 * PRIMARY KEY - The user's identification number
	 * */
	int userId;

	/** The user's username */
	String username;

	/** The user's password */
	String password;

	/** The user's first name */
	String firstName;

	/** The user's last name*/
	String lastName;

	/** The user's email address */
	String emailAddress;

	/** The user's role */
	String role;
	
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
		return this.firstName;
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

}
