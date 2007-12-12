/*
 * NullNumberFormatter.java
 *
 * Created on December 10, 2007, 9:31 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.swing.text;

import java.text.NumberFormat;

/**
 *
 * @author citibob
 */
public class NullNumberFormatter extends javax.swing.text.NumberFormatter
{
	String nullText;
	boolean nullable;
	
	public NullNumberFormatter(NumberFormat fmt, String nullText)
	{
		super(fmt);
		nullable = true;
		this.nullText = nullText;
	}
	public NullNumberFormatter(NumberFormat fmt)
	{
		super(fmt);
		nullable = false;
	}

	public String valueToString(Object val) throws java.text.ParseException
	{
		if (val == null && nullable) return nullText;
		return super.valueToString(val);
	}
	public Object stringToValue(String text) throws java.text.ParseException
	{
		if (nullText.equals(text)) {
			if (nullable) return null;
			return super.stringToValue("0");
		}
		return super.stringToValue(text);
	}
	
}
