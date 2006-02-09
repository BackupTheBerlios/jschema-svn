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
package citibob.jschema.pgsql;

import java.sql.Timestamp;
import java.sql.*;

import citibob.textconverter.*;

public class SqlTimestamp implements citibob.jschema.SqlType
{

public TextConverter getTextConverter()
	{ return new TimestampDateTextConverter(); }


	/** Java class used to represent this type */
	public Class getObjClass()
		{ return java.sql.Timestamp.class; }

	/** Name of type in the database */
	// public String getDBType()
	// 	{ return ""; }

	/** Convert an element of this type to an Sql string for use in a query */
	public String toSql(Object o)
		{ return SqlTimestamp.sql((Timestamp)o); }

	/** Read an element of this type from a ResultSet */
	public Object get(ResultSet rs, int colno) throws SQLException
		{ return rs.getTimestamp(colno); }

	/** Read an element of this type from a ResultSet */
	public Object get(ResultSet rs, String colname) throws SQLException
		{ return rs.getTimestamp(colname); }

	public Object getPrototype()
		{ return new Timestamp(System.currentTimeMillis()); }
	public boolean compareTo(Object o)
		{ return (o instanceof SqlTimestamp); }

	private static java.text.DateFormat sqlFmt;
	static {
		try {
			sqlFmt = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public static String sql(Timestamp ts)
	{
		return ts == null ? "null" :
			("TIMESTAMP '" + sqlFmt.format(ts) + '\'');
	}
}
