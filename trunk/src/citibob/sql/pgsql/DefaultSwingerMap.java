/*
 * DefaultSwingerMap.java
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
import citibob.swing.pgsql.SqlDateSwinger;
import citibob.swing.pgsql.SqlIntegerSwinger;
import citibob.swing.pgsql.SqlStringSwinger;
import citibob.swing.pgsql.SqlTimeSwinger;
import citibob.swing.pgsql.SqlTimestampSwinger;
import citibob.sql.pgsql.*;


/**
 *
 * @author citibob
 */
public class DefaultSwingerMap extends BaseSwingerMap
{
	
/** Creates a new instance of DefaultSwingerMap */
public DefaultSwingerMap() {
	super();
	
	// SqlBool
	this.addMaker(SqlBool.class, new SwingerMap.Maker() {
	public SqlSwinger newSwinger(SqlType sqlType) {
		return new SqlBoolSwinger((SqlBool)sqlType);
	}});
	
	// SqlDate
	this.addMaker(SqlDate.class, new SwingerMap.Maker() {
	public SqlSwinger newSwinger(SqlType sqlType) {
		return new SqlDateSwinger((SqlDate)sqlType, null, "MM-dd-yyyy");
	}});

	// SqlInteger
	this.addMaker(SqlInteger.class, new SwingerMap.Maker() {
	public SqlSwinger newSwinger(SqlType sqlType) {
		return new SqlIntegerSwinger((SqlInteger)sqlType);
	}});

	// SqlString
	this.addMaker(SqlString.class, new SwingerMap.Maker() {
	public SqlSwinger newSwinger(SqlType sqlType) {
		return new SqlStringSwinger((SqlString)sqlType);
	}});

	// SqlTime
	this.addMaker(SqlTime.class, new SwingerMap.Maker() {
	public SqlSwinger newSwinger(SqlType sqlType) {
		return new SqlTimeSwinger((SqlTime)sqlType, null, "HH:mm:ss");
	}});

	// SqlTimestamp
	this.addMaker(SqlTimestamp.class, new SwingerMap.Maker() {
	public SqlSwinger newSwinger(SqlType sqlType) {
		return new SqlTimestampSwinger((SqlTimestamp)sqlType, null, "MM-dd-yyyy HH:mm");
	}});
}
	
}
