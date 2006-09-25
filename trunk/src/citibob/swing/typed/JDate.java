/*
JSchema: library for GUI-based database applications
This file Copyright (c) 2006 by Robert Fischer

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/
package citibob.swing.typed;

import java.text.*;
import java.util.*;

public class JDate implements citibob.swing.typed.JDateType
{
boolean nullable = true;
Calendar cal;	// Calendar (& TimeZone) used to convert SQL to Java dates
// Assumes SQL dates are stored without a timezone.

// -----------------------------------------------------
public JDate(Calendar cal, boolean nullable)
{
	this.cal = cal;

	this.nullable = nullable;
}
public JDate(boolean nullable)
	{ this(Calendar.getInstance(), nullable); }

public JDate(TimeZone tz, boolean nullable)
	{ this(Calendar.getInstance(tz), nullable); }
public JDate()
	{ this(Calendar.getInstance(), true); }
// -----------------------------------------------------
/** Java class used to represent this type */
public Class getObjClass()
	{ return java.util.Date.class; }

public boolean isInstance(Object o)
{
	if (o == null) return nullable;
	if (!(o instanceof java.util.Date)) return false;
	return true;
////
////	return true;
//// TODO: This is too strict, it just results in class
//	java.util.Date dt = (java.util.Date)o;
//	cal.setTime(dt);
//	return (
//		cal.get(Calendar.HOUR_OF_DAY) == 0 &&
//		cal.get(Calendar.MINUTE) == 0 &&
//		cal.get(Calendar.SECOND) == 0 &&
//		cal.get(Calendar.MILLISECOND) == 0);
}
// ==================================================	
public Calendar getCalendar() { return cal; }
/** Reads the date with the appropriate timezone. */
public java.util.Date get(java.sql.ResultSet rs, int col)
{
	throw new NullPointerException("Not yet implemented!");
}
/** Reads the date with the appropriate timezone. */
public java.util.Date get(java.sql.ResultSet rs, String col)
{
	throw new NullPointerException("Not yet implemented!");
}
public java.util.Date truncate(java.util.Date dt)
{
	cal.setTime(dt);
	cal.set(Calendar.HOUR_OF_DAY, 0);
	cal.set(Calendar.MINUTE, 0);
	cal.set(Calendar.SECOND, 0);
	cal.set(Calendar.MILLISECOND, 0);
	return cal.getTime();
}
// ==================================================	
public static List makeDateList(Date first, Date last, long periodMS)
{
	ArrayList ret = new ArrayList();
	Date dt = (Date)first.clone();
	while (dt.getTime() <= last.getTime()) {
		ret.add(dt);
		dt = new Date(dt.getTime() + periodMS);
	}
	return ret;
}


public static List makeDateList(Calendar cal, int firstHr, int firstMin, int lastHr, int lastMin, long periodMS)
{
	if (cal == null) cal = Calendar.getInstance(); //new GregorianCalendar();
	cal.setTimeInMillis(0);
	
	cal.set(Calendar.HOUR_OF_DAY, firstHr);
	cal.set(Calendar.MINUTE, firstMin);
	java.util.Date first = new java.util.Date(cal.getTimeInMillis());
	
	cal.set(Calendar.HOUR_OF_DAY, lastHr);
	cal.set(Calendar.MINUTE, lastMin);
	java.util.Date last = new java.util.Date(cal.getTimeInMillis());

	return makeDateList(first, last, periodMS);
	
}

}

	

