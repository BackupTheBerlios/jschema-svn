/*
JSchema: library for GUI-based database applications
This file Copyright (c) 2006-2007 by Robert Fischer

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
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
