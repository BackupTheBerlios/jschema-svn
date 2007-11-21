/*
 * DateSFormat.java
 *
 * Created on November 7, 2007, 10:16 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.text;

import java.util.*;
import java.text.*;

/**
 * Formats seconds into the day.  eg: 1400*36 (integer) = 14:00
 * @author citibob
 */
public class STimeSFormat extends DateSFormat
{

public STimeSFormat()
	{ this("HH:mm"); }
public STimeSFormat(String displayFmt)
	{ this(displayFmt, new String[] {"hh:mma", "HH:mm"}); }
public STimeSFormat(String displayFmt, String parseFmt[])
	{ super(new DateFlexiFormat(displayFmt, parseFmt, TimeZone.getTimeZone("GMT")), ""); }

public Object stringToValue(String text)  throws java.text.ParseException
{
	if (nullText.equals(text)) return null;
	Date dt = (Date)fmt.parseObject(text);
	return (int)(dt.getTime() / 1000L);
}
public String valueToString(Object value) throws java.text.ParseException
{
	if (value == null) return nullText;
	int ival = ((Integer)value).intValue();
	Date dt = new Date(ival * 1000L);
	return fmt.format(dt);
}

}
