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
public class DivDoubleSFormat extends FormatSFormat
{

double div = 1;

/** @param div Divide by this amount when displaying the number. */
public DivDoubleSFormat(NumberFormat fmt, String nullText, double div)
{
	super(fmt, nullText);
	this.div = div;
}
public DivDoubleSFormat(String fmtString, double div)
	{ this(new DecimalFormat(fmtString), "", div); }

public DivDoubleSFormat(NumberFormat fmt, String nullText)
	{ this(fmt, nullText, 1.0); }
public DivDoubleSFormat(String fmtString)
	{ this(fmtString, 1.0); }

public DivDoubleSFormat()
	{ this("#.0000", 1.0); }

public Object stringToValue(String text)  throws java.text.ParseException
{
	if (nullText.equals(text)) return null;
	Number n = (Number)fmt.parseObject(text);
	return new Double(n.doubleValue() * div);
}
public String valueToString(Object value) throws java.text.ParseException
{
	if (value == null) return nullText;
	double val = ((Number)value).doubleValue();
	return fmt.format(val / div);
}

}
