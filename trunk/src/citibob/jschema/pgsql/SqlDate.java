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

import java.sql.Date;
import java.sql.*;

import citibob.textconverter.DateTextConverter;
import citibob.textconverter.TextConverter;

public class SqlDate implements citibob.jschema.SqlType
{

public TextConverter getTextConverter()
	{ return new DateTextConverter(); }


	/** Java class used to represent this type */
	public Class getObjClass()
		{ return java.sql.Date.class; }

	/** Name of type in the database */
	// public String getDBType()
	// 	{ return ""; }

	/** Convert an element of this type to an Sql string for use in a query */
	public String toSql(Object o)
		{ return SqlDate.sql((Date)o); }

	/** Read an element of this type from a ResultSet */
	public Object get(ResultSet rs, int colno) throws SQLException
		{ return rs.getDate(colno); }

	/** Read an element of this type from a ResultSet */
	public Object get(ResultSet rs, String colname) throws SQLException
		{ return rs.getDate(colname); }

	public Object getPrototype()
		{ return new Date(System.currentTimeMillis()); }


	public static String sql(Date ts)
	{
		return ts == null ? "null" : '\'' + ts.toString() + '\'';
	}
	public boolean compareTo(Object o)
		{ return (o instanceof SqlDate); }

}
