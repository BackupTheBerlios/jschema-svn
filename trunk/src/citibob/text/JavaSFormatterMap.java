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
 * JavaSFormatterMap.java
 *
 * Created on March 18, 2006, 8:00 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.text;

import citibob.sql.*;
import citibob.swing.typed.*;
import java.text.*;

/**
 *
 * @author citibob
 */
public class JavaSFormatterMap extends SFormatterMap
{
public JavaSFormatterMap() {

	// =========== Standard Java classes
	this.addMaker(String.class, new SFormatterMap.Maker() {
	public SFormatter newSFormatter(JType sqlType) {
		return new ToStringSFormatter();
	}});
	
	this.addMaker(Integer.class, new SFormatterMap.Maker() {
	public SFormatter newSFormatter(JType sqlType) {
		return new ToStringSFormatter();
	}});

	// =========== JTypes
	this.addMaker(JEnum.class, new SFormatterMap.Maker() {
	public SFormatter newSFormatter(JType jType) {
		return new citibob.swing.typed.KeyedFormatter(((JEnum)jType).getKeyedModel());
	}});
	
	// =========== SQL Types
	// SqlNumeric
	this.addMaker(SqlNumeric.class, new SFormatterMap.Maker() {
	public SFormatter newSFormatter(JType sqlType) {
		return new LooseNumberFormatter((SqlNumeric)sqlType);
	}});
	
	// SqlEnum
	this.addMaker(SqlEnum.class, new SFormatterMap.Maker() {
	public SFormatter newSFormatter(JType sqlType) {
		return new citibob.swing.typed.KeyedFormatter(((SqlEnum)sqlType).getKeyedModel());
	}});
	
}
	
}
