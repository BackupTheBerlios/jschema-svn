/*
 * TimeFormat.java
 *
 * Created on June 9, 2007, 11:03 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.text;

import java.text.*;
import java.util.*;

/**
 * Format for just the time portions of a Date/Time.
 Assumes the long value of the java.util.Date is in milliseconds
 into the day.  i.e. it "assumes" GMT.  To make a Time of 14:00,
 do new java.util.Date(14*3600*1000L);
 * @author citibob
 */
public class SimpleTimeFormat extends SimpleDateFormat
{
	
public SimpleTimeFormat()
{
	super();
	setTimeZone(TimeZone.getTimeZone("GMT"));
}
public SimpleTimeFormat(String pattern)
{
	super(pattern);
	setTimeZone(TimeZone.getTimeZone("GMT"));
}
SimpleTimeFormat(String pattern, DateFormatSymbols formatSymbols)
{
	super(pattern, formatSymbols);
	setTimeZone(TimeZone.getTimeZone("GMT"));
}
SimpleTimeFormat(String pattern, Locale locale)
{
	super(pattern, locale);
	setTimeZone(TimeZone.getTimeZone("GMT"));
}
}
