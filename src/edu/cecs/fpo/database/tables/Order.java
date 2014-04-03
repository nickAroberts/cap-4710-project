/*
 * Orders.java
 */
package edu.cecs.fpo.database.tables;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The table representation of orders within the database.
 * 
 * @author Nick
 */
public class Order extends AbstractTableEntry {
	
	private static final String TABLE_NAME = "ORDERS";	
	private static final String[] COLUMN_NAMES = {
		"orderId",
		"userId",
		"orderRequestDate",
		"purchaseDate",
		"approvalDate",
		"receiveDate",
		"accountNumber",
		"urgent",
		"computerPurchase",
		"vendor",
		"itemDesc",
		"preOrderNotes",
		"attachment",		
		"requestor",
		"requestorEmail",
		"amount",
		"accountCode",
		"PONumber",
		"postOrderNotes"
	};
	
	public static String ORDER_ID = COLUMN_NAMES[0];
	public static String USER_ID = COLUMN_NAMES[1];
	public static String ORDER_REQUEST_DATE = COLUMN_NAMES[2];
	public static String PURCHASE_DATE = COLUMN_NAMES[3];
	public static String APPROVAL_DATE = COLUMN_NAMES[4];
	public static String RECEIVE_DATE = COLUMN_NAMES[5];
	public static String ACCOUNT_NUMBER = COLUMN_NAMES[6];
	public static String URGENT = COLUMN_NAMES[7];
	public static String COMPUTER_PURCHASE = COLUMN_NAMES[8];
	public static String VENDOR = COLUMN_NAMES[9];
	public static String ITEM_DESC = COLUMN_NAMES[10];
	public static String PRE_ORDER_NOTES = COLUMN_NAMES[11];
	public static String ATTACHMENT = COLUMN_NAMES[12];
	public static String REQUESTOR = COLUMN_NAMES[13];
	public static String REQUESTOR_EMAIL = COLUMN_NAMES[14];
	public static String AMOUNT = COLUMN_NAMES[15];
	public static String ACCOUNT = COLUMN_NAMES[16];
	public static String PO_NUMBER = COLUMN_NAMES[17];
	public static String POST_ORDER_NOTES = COLUMN_NAMES[18];
	
	private static final String PRIMARY_KEY_NAME = COLUMN_NAMES[0];
	
	//---[INSTANCE FIELDS]---

	/** 
	 * PRIMARY KEY - The order's identification number
	 * */
	private int orderId;
	
	private int userId;
	
	private Date orderRequestDate;
	
	private Date purchaseDate;
	
	private Date approvalDate;
	
	private Date receiveDate;
	
	private String accountNumber;
	
	private boolean urgent;
	
	private String computerPurchase;
	
	private String vendor;
	
	private String itemDesc;
	
	private String preOrderNotes;
	
	private String attachment;		
	
	private String requestor;
	
	private String requestorEmail;
	
	private Float amount;
	
	private String accountCode;
	
	private String PONumber;
	
	private String postOrderNotes;
	
	
	//---[METHODS]---
	
	/**
	 * Creates a new order with no specified information.
	 */
	public Order(){
		super(TABLE_NAME, COLUMN_NAMES, PRIMARY_KEY_NAME);
	}
	
	/**
	 * Creates a new order.
	 * 
	 * @param orderId
	 */
	public Order(
			int orderId,
			int userId,
			Date orderRequestDate,
			Date purchaseDate,
			Date approvalDate,
			Date receiveDate,
			String accountNumber,
			boolean urgent,
			String computerPurchase,
			String vendor,
			String itemDesc,
			String preOrderNotes,
			String attachment,		
			String requestor,
			String requestorEmail,
			Float amount,
			String accountCode,
			String PONumber,
			String postOrderNotes
		){
		
		super(TABLE_NAME, COLUMN_NAMES, PRIMARY_KEY_NAME);
		
		this.orderId = orderId;
		this.userId = userId;
		this.orderRequestDate = orderRequestDate;
		this.purchaseDate = purchaseDate;
		this.approvalDate = approvalDate;
		this.receiveDate = receiveDate;
		this.accountNumber = accountNumber;
		this.urgent = urgent;
		this.computerPurchase = computerPurchase;
		this.vendor = vendor;
		this.itemDesc = itemDesc;
		this.preOrderNotes = preOrderNotes;
		this.attachment = attachment;	
		this.requestor = requestor;
		this.requestorEmail = requestorEmail;
		this.amount = amount;
		this.accountCode = accountCode;
		this.PONumber = PONumber;
		this.postOrderNotes = postOrderNotes;
		
		
	}

	@Override
	public int getId() {
		return this.orderId;
	}
	
	public int getOrderId (){
		return this.orderId;
	}
	
	public int getUserId(){
		return this.userId;
	}
	public Date getOrderRequestDate(){
		return this.orderRequestDate;
	}
	
	public Date getPurchaseDate(){
		return this.purchaseDate;
	}
	
	public Date getApprovalDate(){
		return this.approvalDate;
	}
	
	public Date getReceiveDate(){
		return this.receiveDate;
	}
	
	public String getAccountNumber(){
		return this.accountNumber;
	}
	
	public boolean getUrgent(){
		return this.urgent;
	}
	
	public String getComputerPurchase(){
		return this.computerPurchase;
	}
	
	public String getVendor(){
		return this.vendor;
	}
	
	public String getItemDesc(){
		return this.itemDesc;
	}
	
	public String getPreOrderNotes(){
		return this.preOrderNotes;
	}
	
	public String getAttachment(){
		return this.attachment;	
	}
	
	public String getRequestor(){
		return this.requestor;
	}
	
	public String getRequestorEmail(){
		return this.requestorEmail;
	}
	
	public float getAmount(){
		return this.amount;
	}
	
	public String getAccountCode(){
		return this.accountCode;
	}
	
	public String getPONumber(){
		return this.PONumber;
	}
	
	public String getPostOrderNotes(){
		return this.postOrderNotes;
	}
	
	@Override
	public void setId(int id) {
		this.orderId = id;
	}
	
	public void setOrderId (int orderId){
		this.orderId = orderId;
	}
	
	public void setUserId(int userId){
		this.userId = userId;
	}
	public void setOrderRequestDate(Date orderRequestDate){
		this.orderRequestDate = orderRequestDate;
	}
	
	public void setPurchaseDate(Date purchaseDate){
		this.purchaseDate = purchaseDate;
	}
	
	public void setApprovalDate(Date approvalDate){
		this.approvalDate = approvalDate;
	}
	
	public void setReceiveDate(Date receiveDate){
		this.receiveDate = receiveDate;
	}
	
	public void setAccountNumber(String accountNumber){
		this.accountNumber = accountNumber;
	}
	
	public void setUrgent(boolean urgent){
		this.urgent = urgent;
	}
	
	public void setComputerPurchase(String computerPurchase){
		this.computerPurchase = computerPurchase;
	}
	
	public void setVendor(String vendor){
		this.vendor = vendor;
	}
	
	public void setItemDesc(String itemDesc){
		this.itemDesc = itemDesc;
	}
	
	public void setPreOrderNotes(String preOrderNotes){
		this.preOrderNotes = preOrderNotes;
	}
	
	public void setAttachment(String attachment){
		this.attachment = attachment;	
	}
	
	public void setRequestor(String requestor){
		this.requestor = requestor;
	}
	
	public void setRequestorEmail(String requestorEmail){
		this.requestorEmail = requestorEmail;
	}
	
	public void setAmount(float amount){
		this.amount = amount;
	}
	
	public void setAccountCode(String accountCode){
		this.accountCode = accountCode;
	}
	
	public void setPONumber(String PONumber){
		this.PONumber = PONumber;
	}
	
	public void setPostOrderNotes(String postOrderNotes){
		this.postOrderNotes = postOrderNotes;
	}

	@Override
	public AbstractTableEntry populateFromResultSet(ResultSet set) throws SQLException {
		
		//populate the values for this order with the values in the database
		setOrderId(set.getInt(COLUMN_NAMES[0]));
		setUserId(set.getInt(COLUMN_NAMES[1]));
		setOrderRequestDate(set.getDate(COLUMN_NAMES[2]));
		setPurchaseDate(set.getDate(COLUMN_NAMES[3]));
		setApprovalDate(set.getDate(COLUMN_NAMES[4]));
		setReceiveDate(set.getDate(COLUMN_NAMES[5]));
		setAccountNumber(set.getString(COLUMN_NAMES[6]));
		setUrgent(set.getBoolean(COLUMN_NAMES[7]));
		setComputerPurchase(set.getString(COLUMN_NAMES[8]));
		setVendor(set.getString(COLUMN_NAMES[9]));
		setItemDesc(set.getString(COLUMN_NAMES[10]));
		setPreOrderNotes(set.getString(COLUMN_NAMES[11]));
		setAttachment(set.getString(COLUMN_NAMES[12]));
		setRequestor(set.getString(COLUMN_NAMES[13]));
		setRequestorEmail(set.getString(COLUMN_NAMES[14]));
		setAmount(set.getFloat(COLUMN_NAMES[15]));
		setAccountCode(set.getString(COLUMN_NAMES[16]));
		setPONumber(set.getString(COLUMN_NAMES[17]));
		setPostOrderNotes(set.getString(COLUMN_NAMES[18]));
		
		return this;
	}

	@Override
	public String toSQLRepresentation() {
		return 
			"(" +
				"" + orderId + ", " +
				"" + userId + ", " +
				"\"" + orderRequestDate + "\", " +
				"\"" + purchaseDate + "\", " +
				"\"" + approvalDate + "\", " +
				"\"" + receiveDate + "\", " +
				"\"" + accountNumber + "\", " +
				"" + (urgent ? 1 : 0)  + ", " +	//SQL needs a value of 0 or 1 for booleans
				"\"" + computerPurchase + "\", " +
				"\"" + vendor + "\", " +
				"\"" + itemDesc + "\", " +
				"\"" + preOrderNotes + "\", " +
				"\"" + attachment + "\", " +		
				"\"" + requestor + "\", " +
				"\"" + requestorEmail + "\", " +
				"" + amount + ", " +
				"\"" + accountCode + "\", " +
				"\"" + PONumber + "\", " +
				"\"" + postOrderNotes + "\"" +
			")";

	}

	@Override
	public List<Object> getValues() {
		
		List<Object> values = new ArrayList<Object>();
		
		values.add(getOrderId());
		values.add(getUserId());
		values.add(getOrderRequestDate());
		values.add(getPurchaseDate());
		values.add(getApprovalDate());
		values.add(getReceiveDate());
		values.add(getAccountNumber());
		values.add(getUrgent());
		values.add(getComputerPurchase());
		values.add(getVendor());
		values.add(getItemDesc());
		values.add(getPreOrderNotes());
		values.add(getAttachment());
		values.add(getRequestor());
		values.add(getRequestorEmail());
		values.add(getAmount());
		values.add(getAccountCode());
		values.add(getPONumber());
		values.add(getPostOrderNotes());
		
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
