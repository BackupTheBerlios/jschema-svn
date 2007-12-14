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
 * JavaSwingerMap.java
 *
 * Created on March 18, 2006, 8:00 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.swingers;

import citibob.sql.*;
import citibob.swingers.SqlNumericSwinger;
import citibob.swing.typed.*;
import citibob.swing.typed.Swinger;
import citibob.swing.typed.SwingerMap;
import java.text.DecimalFormat;
import citibob.types.*;
import citibob.text.*;
import java.util.*;
import java.text.*;

/**
 *
 * @author citibob
 */
public class JavaSwingerMap extends DefaultSwingerMap
{



public JavaSwingerMap(final TimeZone tz)
{
Maker maker;

	// =========== Standard Java classes
	this.addMaker(String.class, new DefaultSwingerMap.Maker() {
	public Swinger newSwinger(JType jType) {
		return new JStringSwinger();
	}});

	// Integer
	maker = new DefaultSwingerMap.Maker() {
	public Swinger newSwinger(JType jType) {
		return new JIntegerSwinger();
	}};
	this.addMaker(Integer.class, maker);
	this.addMaker(int.class, maker);
	
	// Double
	maker = new DefaultSwingerMap.Maker() {
	public Swinger newSwinger(JType jType) {
//		DecimalFormat fmt = new DecimalFormat("#");
//		fmt.setPositiveSuffix("m");
//		fmt.setNegativeSuffix("m");
		return new TypedTextSwinger(new JavaJType(Double.class), new DivDoubleSFormat());
//		return new JDoubleSwinger(true, fmt);
	}};
	this.addMaker(Double.class, maker);
	this.addMaker(double.class, maker);

	// Boolean
	maker = new DefaultSwingerMap.Maker() {
	public Swinger newSwinger(JType jType) {
		return new BoolSwinger();
	}};
	this.addMaker(Boolean.class, maker);
	this.addMaker(boolean.class, maker);

	// ================== Other Java Classes
	// TimeZone
	this.addMaker(java.util.TimeZone.class, new DefaultSwingerMap.Maker() {
	public Swinger newSwinger(JType jType) {
		return new TypedTextSwinger(new JavaJType(TimeZone.class), new TimeZoneSFormat());
	}});

	
	// SqlDate --- stored in native TimeZone, render in same TimeZone as is stored.
	this.addMaker(JDate.class, new DefaultSwingerMap.Maker() {
	public Swinger newSwinger(JType jType) {
		JDateType jt = (JDateType)jType;
		return new JDateSwinger(jt,
			new String[] {"MM/dd/yyyy", "yyyy-MM-dd", "MM/dd/yy", "MMddyy", "MMddyyyy"},
			"", tz,
			citibob.swing.calendar.JCalendarDateOnly.class);
	}});

	// =========== JTypes
	this.addMaker(JEnum.class, new DefaultSwingerMap.Maker() {
	public Swinger newSwinger(JType jType) {
		return new JEnumSwinger((JEnum)jType);
	}});
	
	// =========== SQL Types
	// SqlNumeric
	this.addMaker(SqlNumeric.class, new DefaultSwingerMap.Maker() {
	public Swinger newSwinger(JType jType) {
		return new SqlNumericSwinger((SqlNumeric)jType);
	}});
	
	// SqlEnum
	this.addMaker(SqlEnum.class, new DefaultSwingerMap.Maker() {
	public Swinger newSwinger(JType jType) {
		return new SqlEnumSwinger((SqlEnum)jType);
	}});
	
}
	
}
