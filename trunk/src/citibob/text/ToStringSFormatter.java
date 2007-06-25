/*
 * FormatSFormatter.java
 *
 * Created on February 26, 2007, 12:52 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.text;

import java.text.*;

/**
 *
 * @author citibob
 */
public class ToStringSFormatter implements SFormatter
{
//	Format fmt;
	String nullval = "";
	
	public String  valueToString(Object value) throws java.text.ParseException
	{
		return (value == null ? nullval : value.toString());
//		return value.toString();
	}
	public Object  stringToValue(String text)  throws java.text.ParseException
	{
		throw new ParseException("ToStringSFormatter doesn't know how to parse!", 0);
	}
	
}
