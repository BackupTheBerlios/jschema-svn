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
	
public class SqlChar implements citibob.sql.SqlType
{

	boolean nullable = true;

	public SqlChar(boolean nullable)
	{
		this.nullable = nullable;
	}
	public SqlChar()
	{
		this(true);
	}
	
	/** Java class used to represent this type */
	public Class getObjClass()
		{ return Character.class; }

	/** Convert an element of this type to an Sql string for use in a query */
	public String toSql(Object o)
	{
		if (o instanceof Character) return SqlChar.sql((Character)o);
		return SqlChar.sql(((String)o).charAt(0));
	}


/** Converts a Java String to a form appropriate for inclusion in an
Sql query.  This is done by single-quoting the input and repeating any
single qoutes found in it (Sql convention for quoting a quote).  If
the input is null, the string "null" is returned. */
	public static String sql(Character c, boolean quotes)
	{
		if (c == null) return "null";
		char ch = c.charValue();
		return sql(ch, quotes);
	}
	public static String sql(Character s)
		{ return sql(s, true); }

	public static String sql(char ch, boolean quotes)
	{
		StringBuffer str = new StringBuffer();
		if (quotes) str.append('\'');
		switch(ch) {
			case '\'' : str.append("''"); break;
			default: str.append(ch); break;
		}
		if (quotes) str.append('\'');
		return str.toString();
	}
	public boolean isInstance(Object o)
	{
		if (nullable && o == null) return true;
		return (o instanceof Character);
}

}
