/*
JSchema: library for GUI-based database applications
This file Copyright (c) 2006-2007 by Robert Fischer

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package citibob.sql;

import java.sql.*;
import citibob.jschema.*;
import citibob.util.KeyedModel;
import citibob.swing.typed.*;

/** For enumerate types...  NOTE: does NOT extend JEnum*/
public class SqlEnum
extends JEnum
implements SqlType
{
//	KeyedModel kmodel;
	boolean nullable = true;
	
	/** nullText = string to use for null value, or else <null> if this is not nullable. */
	public SqlEnum(KeyedModel kmodel, String nullText) {
		super(kmodel);
		this.nullable = (nullText != null);
		if (nullable) {
			if (kmodel.get(null) == null) kmodel.addItem(null, nullText);
		}
	}
	
	public SqlEnum(KeyedModel kmodel, boolean nullable) {
		this(kmodel, nullable ? "" : null);
	}
	
	/** Java class used to represent this type */
	public Class getObjClass()
		{ return Integer.class; }

	/** Convert an element of this type to an Sql string for use in a query */
	public String toSql(Object o)
		{ return (o == null ? "null" : o.toString()); }

	public boolean isInstance(Object o)
	{
		if (o == null) return nullable;
		if (!(o instanceof Integer)) return false;
		return (kmodel.get(o) != null);
	}
	public Object get(java.sql.ResultSet rs, int col) throws SQLException
		{ return rs.getObject(col); }
	public Object get(java.sql.ResultSet rs, String col) throws SQLException
		{ return rs.getObject(col); }
// ================================================
	public static String sql(Integer ii)
		{ return ii == null ? "null" : ii.toString(); }
	public static String sql(int i)
		{ return ""+i; }

}
