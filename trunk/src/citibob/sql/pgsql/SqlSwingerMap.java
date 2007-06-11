/*
 * SqlSwingerMap.java
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
import citibob.swing.pgsql.SqlBoolSwinger;
import citibob.swing.typed.JDateSwinger;
import citibob.swing.pgsql.SqlIntegerSwinger;
import citibob.swing.pgsql.SqlStringSwinger;
import citibob.swing.pgsql.SqlTimeSwinger;
import citibob.swing.pgsql.SqlTimestampSwinger;
import citibob.sql.pgsql.*;
import citibob.swing.typed.JType;
import java.util.*;


/**
 *
 * @author citibob
 */
public class SqlSwingerMap extends JavaSwingerMap
{
	
/**
 * Creates a new instance of SqlSwingerMap 
 */
public SqlSwingerMap(final TimeZone tz) {
	super();
	
	// SqlBool
	this.addMaker(SqlBool.class, new SwingerMap.Maker() {
	public Swinger newSwinger(JType sqlType) {
		return new SqlBoolSwinger((SqlBool)sqlType);
	}});
	
	// SqlDate
	this.addMaker(SqlDate.class, new SwingerMap.Maker() {
	public Swinger newSwinger(JType sqlType) {
		return new JDateSwinger((SqlDate)sqlType, tz, "MM-dd-yyyy");
	}});

	// SqlInteger
	this.addMaker(SqlInteger.class, new SwingerMap.Maker() {
	public Swinger newSwinger(JType sqlType) {
		return new SqlIntegerSwinger((SqlInteger)sqlType);
	}});

	// SqlString
	this.addMaker(SqlString.class, new SwingerMap.Maker() {
	public Swinger newSwinger(JType sqlType) {
		return new SqlStringSwinger((SqlString)sqlType);
	}});

	// SqlTime
	this.addMaker(SqlTime.class, new SwingerMap.Maker() {
	public Swinger newSwinger(JType sqlType) {
		return new SqlTimeSwinger((SqlTime)sqlType, "HH:mm:ss");
	}});

	// SqlTimestamp
	this.addMaker(SqlTimestamp.class, new SwingerMap.Maker() {
	public Swinger newSwinger(JType sqlType) {
		return new SqlTimestampSwinger((SqlTimestamp)sqlType, tz, "MM-dd-yyyy HH:mm z");
	}});
}
	
}
