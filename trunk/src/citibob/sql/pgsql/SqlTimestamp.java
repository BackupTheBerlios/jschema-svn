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

import java.util.*;

public class SqlTimestamp  implements citibob.sql.SqlDateType //swing.typed.JDateType
{

Calendar cal;
boolean nullable = true;

// -----------------------------------------------------
public SqlTimestamp(Calendar cal, boolean nullable)
{
	this.cal = cal;
	this.nullable = nullable;
}
public SqlTimestamp(TimeZone tz, boolean nullable)
{
	this(Calendar.getInstance(tz), nullable);
}
public SqlTimestamp(boolean nullable)
	{ this(Calendar.getInstance(), nullable); }
public SqlTimestamp()
	{ this(Calendar.getInstance(), true); }
// -----------------------------------------------------
/** Java class used to represent this type */
public Class getObjClass()
	{ return java.sql.Timestamp.class; }

/** Convert an element of this type to an Sql string for use in a query */
public String toSql(Object o)
{
	System.out.println("o.class = " + o.getClass());
	return SqlTimestamp.sql((java.util.Date)o);
}

public boolean isInstance(Object o)
	{ return (o instanceof java.util.Date); }
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
{ return dt; }
// ===========================================================
	private static java.text.DateFormat sqlFmt;
	static {
		try {
			sqlFmt = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public static String sql(java.util.Date ts)
	{
		return ts == null ? "null" :
			("TIMESTAMP '" + sqlFmt.format(ts) + '\'');
	}
}
