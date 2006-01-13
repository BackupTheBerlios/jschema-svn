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

import citibob.textconverter.IntegerTextConverter;
import citibob.textconverter.TextConverter;

public class SqlInteger implements citibob.jschema.SqlType
{

public final static TextConverter textConverter = new IntegerTextConverter();
public TextConverter getTextConverter()
	{ return textConverter; }

	/** Java class used to represent this type */
	public Class getObjClass()
		{ return Integer.class; }

	/** Name of type in the database */
	// public String getDBType()
	//	{ return "int"; }

	/** Convert an element of this type to an Sql string for use in a query */
	public String toSql(Object o)
		{ return (o == null ? "null" : o.toString()); }

	/** Read an element of this type from a ResultSet */
	public Object get(ResultSet rs, int colno) throws SQLException
		{ return new Integer(rs.getInt(colno)); }
	/** Read an element of this type from a ResultSet */
	public Object get(ResultSet rs, String colname) throws SQLException
		{ return new Integer(rs.getInt(colname)); }

	public Object getPrototype()
		{ return new Integer(17); }


	public static String sql(Integer ii)
		{ return ii == null ? "null" : ii.toString(); }
	public static String sql(int i)
		{ return ""+i; }
	public boolean compareTo(Object o)
		{ return (o instanceof SqlInteger); }

}
