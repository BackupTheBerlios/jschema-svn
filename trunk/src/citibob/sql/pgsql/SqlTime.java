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
import java.sql.*;
import java.text.*;

public class SqlTime extends citibob.swing.typed.JDate
implements citibob.sql.SqlDateType
{

private static TimeZone tz;
static java.text.DateFormat sqlFmt, sqlParse;
public static DateFormat hhmmss;
static {
	tz = TimeZone.getTimeZone("GMT");
	sqlFmt = new SimpleDateFormat("HH:mm:ss.SSS");
	sqlFmt.setTimeZone(tz);
	sqlParse = new SimpleDateFormat("HH:mm:ss.SSSSSS");
	sqlParse.setTimeZone(tz);
	hhmmss = new SimpleDateFormat("HH:mm:ss");
	hhmmss.setTimeZone(tz);
}
public SqlTime(boolean nullable) {
	super(tz, nullable);
}
public SqlTime()
	{ this(true); }
// -----------------------------------------------------
/** Convert an element of this type to an Sql string for use in a query */
public String toSql(Object o)
{
System.out.println("o.class = " + o.getClass());
	return SqlTime.sql((java.util.Date)o);
}
// ==================================================	
/** Reads the date with the appropriate timezone. */
public java.util.Date get(java.sql.ResultSet rs, int col) throws SQLException
{
	try {
		String s = rs.getString(col);
		if (s == null) return null;
		return sqlParse.parse(s);
	} catch(java.text.ParseException e) {
		throw new SQLException(e.getMessage());
	}
}
/** Reads the date with the appropriate timezone. */
public java.util.Date get(java.sql.ResultSet rs, String col) throws SQLException
{
	return get(rs, rs.findColumn(col));
}
public java.util.Date truncate(java.util.Date dt)
{ return dt; }
// ===========================================================

public static String sql(java.util.Date time)
	{ return '\'' + sqlFmt.format(time) + '\''; }


}
