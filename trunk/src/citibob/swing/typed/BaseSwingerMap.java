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

	// =========== Standard Java classes
	this.addMaker(String.class, new SwingerMap.Maker() {
	public JTypeSwinger newSwinger(JType sqlType) {
		return new JStringSwinger();
	}});
	
	this.addMaker(Integer.class, new SwingerMap.Maker() {
	public JTypeSwinger newSwinger(JType sqlType) {
		return new JIntegerSwinger();
	}});

	// =========== JTypes
	this.addMaker(JEnum.class, new SwingerMap.Maker() {
	public JTypeSwinger newSwinger(JType jType) {
		return new JEnumSwinger((JEnum)jType);
	}});
	
	// =========== SQL Types
	// SqlNumeric
	this.addMaker(SqlNumeric.class, new SwingerMap.Maker() {
	public JTypeSwinger newSwinger(JType sqlType) {
		return new SqlNumericSwinger((SqlNumeric)sqlType);
	}});
	
	// SqlEnum
	this.addMaker(SqlEnum.class, new SwingerMap.Maker() {
	public JTypeSwinger newSwinger(JType sqlType) {
		return new SqlEnumSwinger((SqlEnum)sqlType);
	}});
	
}
	
}
