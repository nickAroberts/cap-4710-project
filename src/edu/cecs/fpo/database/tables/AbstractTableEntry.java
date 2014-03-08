package edu.cecs.fpo.database.tables;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractTableEntry {

	private final String TABLE_NAME;	
	private final String[] COLUMN_NAMES;	
	private final String PRIMARY_KEY_NAME;
	
	public String[] getColumnNames(){
		return COLUMN_NAMES;
	}
	
	public String getTableName(){
		return TABLE_NAME;
	}
	
	public String getPrimaryKeyName(){
		return PRIMARY_KEY_NAME;
	}
	
	public AbstractTableEntry(String tableName, String[] columnNames, String primaryKeyName){
		this.TABLE_NAME = tableName;
		this.COLUMN_NAMES = columnNames;
		this.PRIMARY_KEY_NAME = primaryKeyName;
	}
	
	public abstract void setId(int id);
	
	public abstract int getId();
	
	public abstract AbstractTableEntry populateFromResultSet(ResultSet set) throws SQLException;
	
	public abstract String toSQLRepresentation();
	
}
