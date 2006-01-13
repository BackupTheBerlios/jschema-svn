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

import java.sql.*;

import citibob.textconverter.*;

public class SqlString implements citibob.jschema.SqlType
{

	/* Limit on string length */
	int limit;

	public SqlString(int limit)
	{
		this.limit = limit;
	}
	public SqlString()
	{ this.limit = 0; }
	
private static TextConverter tc = new StringTextConverter();
public TextConverter getTextConverter()
	{ return tc; }

	/** Java class used to represent this type */
	public Class getObjClass()
		{ return String.class; }

	/** Name of type in the database */
	// public String getDbType()
	// 	{ return ""; }

	/** Convert an element of this type to an Sql string for use in a query */
	public String toSql(Object o)
		{ return SqlString.sql((String)o); }

//	public static String toSql(Object o)
//		{ return SqlString.sql((String)o); }



	/** Read an element of this type from a ResultSet */
	public Object get(ResultSet rs, int colno) throws SQLException
		{ return rs.getString(colno); }

	/** Read an element of this type from a ResultSet */
	public Object get(ResultSet rs, String colname) throws SQLException
		{ return rs.getString(colname); }



/** Converts a Java String to a form appropriate for inclusion in an
Sql query.  This is done by single-quoting the input and repeating any
single qoutes found in it (Sql convention for quoting a quote).  If
the input is null, the string "null" is returned. */
	public static String sql(String s, boolean quotes)
	{
		if (s == null) return "null";
		StringBuffer str = new StringBuffer();
		if (quotes) str.append('\'');
		int len = s.length();
		for (int i = 0; i < len; ++i) {
			char ch = s.charAt(i);
			switch(ch) {
				case '\'' : str.append("''"); break;
				default: str.append(ch); break;
			}
		}
		if (quotes) str.append('\'');
		return str.toString();
	}
	public static String sql(String s)
		{ return sql(s, true); }

	public Object getPrototype()
		{ return "Seventeen"; }

	public boolean compareTo(Object o)
		{ return (o instanceof SqlString); }
}
