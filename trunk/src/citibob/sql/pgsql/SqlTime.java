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
package citibob.sql.pgsql;

import java.text.*;
import java.util.*;

public class SqlTime implements citibob.sql.SqlDateType //citibob.swing.typed.JDateType, citibob.swing.typed.SqlType
{
boolean nullable = true;

// -----------------------------------------------------
public SqlTime(boolean nullable)
{
//	this.cal = cal;
	this.nullable = nullable;
}
public SqlTime()
	{ this(true); }
// -----------------------------------------------------
/** Java class used to represent this type */
public Class getObjClass()
	{ return java.sql.Date.class; }

/** Convert an element of this type to an Sql string for use in a query */
public String toSql(Object o)
{
	System.out.println("o.class = " + o.getClass());
	return SqlTime.sql((java.util.Date)o);
}

public boolean isInstance(Object o)
{
	if (o == null) return nullable;
	if (!(o instanceof java.util.Date)) return false;
	java.util.Date dt = (java.util.Date)o;
	return true;
//	cal.setTime(dt);
//	return (
//		cal.get(Calendar.YEAR) == 0 &&
//		cal.get(Calendar.MONTH) == 0 &&
//		cal.get(Calendar.DAY_OF_MONTH) == 0);
}
// ==================================================	
public Calendar getCalendar() { return null; }
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
//	cal.setTime(dt);
//	cal.set(Calendar.YEAR, 0);
//	cal.set(Calendar.MONTH, 0);
//	cal.set(Calendar.DAY_OF_MONTH, 0);
//	return cal.getTime();
	return dt;
}
// ==================================================	
	private static DateFormat sqlFmt;
	static {
		try {
			sqlFmt = new SimpleDateFormat("HH:mm:ss.SSS");
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	public static String sql(java.util.Date ts)
	{
		return ts == null ? "null" :
			('\'' + sqlFmt.format(ts) + '\'');
	}
}


