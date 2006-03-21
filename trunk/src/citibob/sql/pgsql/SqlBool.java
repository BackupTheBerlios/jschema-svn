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

import java.sql.*;

public class SqlBool implements citibob.sql.SqlType
{
	boolean nullable = true;
	public SqlBool(boolean n) { nullable = n; }
	public SqlBool() { this(true); }
	
	/** Java class used to represent this type */
	public Class getObjClass()
		{ return Boolean.class; }

	/** Convert an element of this type to an Sql string for use in a query */
	public String toSql(Object o)
		{ return o == null ? "null" : o.toString(); }
	
	public boolean isInstance(Object o)
		{ return (o instanceof Boolean || (nullable && o == null)); }

// ========================================================
	public static String sql(Boolean ii)
		{ return ii == null ? "null" : sql(ii.booleanValue()); }
	public static String sql(boolean b)
	{
		return (b ? "'true'" : "'false'");
	}

}