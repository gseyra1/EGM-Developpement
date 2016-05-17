/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tracfin;

/**
 *
 * @author GSEYRA
 */
public class Column {

    
private String columnName;
private String columnType;
private String columnMaxSize;

public Column(String columnName, String columnType, String columnMaxSize) {
	super();
	this.columnName = columnName;
	this.columnType = columnType;
	this.columnMaxSize = columnMaxSize;
}
public Column() {
	super();
}
public String getColumnName() {
	return columnName;
}
public void setColumnName(String columnName) {
	this.columnName = columnName;
}
public String getColumnType() {
	return columnType;
}
public void setColumnType(String columnType) {
	this.columnType = columnType;
}
public String getColumnMaxSize() {
	return columnMaxSize;
}
public void setColumnMaxSize(String columnMaxSize) {
	this.columnMaxSize = columnMaxSize;
}

}
