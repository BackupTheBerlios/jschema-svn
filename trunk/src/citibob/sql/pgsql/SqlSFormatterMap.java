/*
 * SqlSFormatterMap.java
 *
 * Created on March 18, 2006, 8:24 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.sql.pgsql;

import citibob.swing.typed.*;
import citibob.sql.*;
import citibob.sql.pgsql.*;
import citibob.text.*;
import citibob.swing.typed.*;

/**
 *
 * @author citibob
 */
public class SqlSFormatterMap extends JavaSFormatterMap
{
	
/**
 * Creates a new instance of SqlSFormatterMap 
 */
public SqlSFormatterMap() {
	super();
	
	// SqlBool
	this.addMaker(SqlBool.class, new SFormatterMap.Maker() {
	public SFormatter newSFormatter(JType sqlType) {
		return new BoolFormatter();
	}});
	
	// SqlDate
	this.addMaker(SqlDate.class, new SFormatterMap.Maker() {
	public SFormatter newSFormatter(JType sqlType) {
		return new JDateSFormatter(null, "MM-dd-yyyy");
	}});

	// SqlInteger
	this.addMaker(SqlInteger.class, new SFormatterMap.Maker() {
	public SFormatter newSFormatter(JType sqlType) {
		return new ToStringSFormatter();
	}});

	// SqlString
	this.addMaker(SqlString.class, new SFormatterMap.Maker() {
	public SFormatter newSFormatter(JType sqlType) {
		return new ToStringSFormatter();
	}});

	// SqlTime
	this.addMaker(SqlTime.class, new SFormatterMap.Maker() {
	public SFormatter newSFormatter(JType sqlType) {
		return new JDateSFormatter(null, "HH:mm:ss");
	}});

	// SqlTimestamp
	this.addMaker(SqlTimestamp.class, new SFormatterMap.Maker() {
	public SFormatter newSFormatter(JType sqlType) {
		return new JDateSFormatter(null, "MM-dd-yyyy HH:mm");
	}});
}
	
}
