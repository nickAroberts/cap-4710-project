package edu.cecs.fpo.database.tables;

public abstract class AbstractTableEntry {

	private final String TABLE_NAME;	
	private final String[] COLUMN_NAMES;
	
	public String[] getColumnNames(){
		return COLUMN_NAMES;
	}
	
	public String getTableName(){
		return TABLE_NAME;
	}
	
	public AbstractTableEntry(String tableName, String[] columnNames){
		this.TABLE_NAME = tableName;
		this.COLUMN_NAMES = columnNames;
	}
	
	public abstract String toSQLRepresentation();
}
