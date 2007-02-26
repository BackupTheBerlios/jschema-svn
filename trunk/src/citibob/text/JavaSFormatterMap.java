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
