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
package citibob.sql;

import java.sql.*;

public class SqlDouble implements SqlType
{
	boolean nullable = true;
	
	public SqlDouble(boolean nullable) {
		this.nullable = nullable;
	}
	public SqlDouble()
	{ this(true); }
	
	/** Java class used to represent this type */
	public Class getObjClass()
		{ return Double.class; }

	/** Convert an element of this type to an Sql string for use in a query */
	public String toSql(Object o)
		{ return (o == null ? "null" : o.toString()); }

	public boolean isInstance(Object o)
	{
		if (o == null) return nullable;
		return (o instanceof Double);
	}
	public Object get(java.sql.ResultSet rs, int col) throws SQLException
		{ return rs.getObject(col); }
	public Object get(java.sql.ResultSet rs, String col) throws SQLException
		{ return rs.getObject(col); }
// ================================================
	public static String sql(Double ii)
		{ return ii == null ? "null" : ii.toString(); }
	public static String sql(int i)
		{ return ""+i; }

}
