/*
 * IntegerSFormat.java
 *
 * Created on November 21, 2007, 11:57 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.text;

import java.text.*;

/**
 *
 * @author fiscrob
 */
public class IntegerSFormat extends FormatSFormat
{

public IntegerSFormat(NumberFormat fmt, String nullText)
	{ super(fmt, nullText); }
public IntegerSFormat(NumberFormat fmt)
	{ this(fmt, ""); }
public IntegerSFormat()
	{ this(NumberFormat.getIntegerInstance(), ""); }
public IntegerSFormat(String fmtString, String nullText)
	{ this(new DecimalFormat(fmtString), nullText); }


public Object stringToValue(String text)  throws java.text.ParseException
{
	if (nullText.equals(text)) return null;
	Number n = (Number)fmt.parseObject(text);
	return new Integer(n.intValue());
}

}
