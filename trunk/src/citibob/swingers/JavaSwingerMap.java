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
import citibob.types.JEnum;
import citibob.types.JType;

/**
 *
 * @author citibob
 */
public class JavaSwingerMap extends DefaultSwingerMap
{



public JavaSwingerMap() {

	// =========== Standard Java classes
	this.addMaker(String.class, new DefaultSwingerMap.Maker() {
	public Swinger newSwinger(JType sqlType) {
		return new JStringSwinger();
	}});
	
	this.addMaker(Integer.class, new DefaultSwingerMap.Maker() {
	public Swinger newSwinger(JType sqlType) {
		return new JIntegerSwinger();
	}});

	// =========== JTypes
	this.addMaker(JEnum.class, new DefaultSwingerMap.Maker() {
	public Swinger newSwinger(JType jType) {
		return new JEnumSwinger((JEnum)jType);
	}});
	
	// =========== SQL Types
	// SqlNumeric
	this.addMaker(SqlNumeric.class, new DefaultSwingerMap.Maker() {
	public Swinger newSwinger(JType sqlType) {
		return new SqlNumericSwinger((SqlNumeric)sqlType);
	}});
	
	// SqlEnum
	this.addMaker(SqlEnum.class, new DefaultSwingerMap.Maker() {
	public Swinger newSwinger(JType sqlType) {
		return new SqlEnumSwinger((SqlEnum)sqlType);
	}});
	
}
	
}
