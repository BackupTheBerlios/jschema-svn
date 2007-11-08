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
import citibob.types.JType;
import java.util.*;

/**
 *
 * @author citibob
 */
public class SqlSFormatterMap extends JavaSFormatterMap
{


/**
 * Creates a new instance of SqlSFormatterMap 
 */
public SqlSFormatterMap() { this(null); }

public SqlSFormatterMap(final TimeZone tz) {
	super();
	
	// SqlBool
	this.addMaker(SqlBool.class, new SFormatterMap.Maker() {
	public SFormatter newSFormatter(JType sqlType) {
		return new BoolFormatter();
	}});
	
	// SqlDate
	this.addMaker(SqlDate.class, new SFormatterMap.Maker() {
	public SFormatter newSFormatter(JType sqlType) {
		return new JDateSFormatter("MM/dd/yyyy", tz);
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

	// SqlString
	this.addMaker(SqlChar.class, new SFormatterMap.Maker() {
	public SFormatter newSFormatter(JType sqlType) {
		return new ToStringSFormatter();
	}});

	// SqlTime
	this.addMaker(SqlTime.class, new SFormatterMap.Maker() {
	public SFormatter newSFormatter(JType sqlType) {
		return new FormatSFormatter(new SimpleTimeFormat("HH:mm:ss"));
//			JDateSFormatter(java.util.TimeZone.getTimeZone("GMT"), "HH:mm:ss");
	}});

	// SqlTimestamp
	this.addMaker(SqlTimestamp.class, new SFormatterMap.Maker() {
	public SFormatter newSFormatter(JType sqlType) {
		return new JDateSFormatter("MM-dd-yyyy HH:mm", tz);
	}});
}
	
}
