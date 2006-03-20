/*
 * BaseSwingerMap.java
 *
 * Created on March 18, 2006, 8:00 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.swing.typed;

import citibob.sql.*;

/**
 *
 * @author citibob
 */
public class BaseSwingerMap extends SwingerMap
{
public BaseSwingerMap() {

	// SqlNumeric
	this.addMaker(SqlNumeric.class, new SwingerMap.Maker() {
	public SqlSwinger newSwinger(SqlType sqlType) {
		return new SqlNumericSwinger((SqlNumeric)sqlType);
	}});
	
	// SqlEnum
	this.addMaker(SqlEnum.class, new SwingerMap.Maker() {
	public SqlSwinger newSwinger(SqlType sqlType) {
		return new SqlEnumSwinger((SqlEnum)sqlType);
	}});
	
}
	
}
