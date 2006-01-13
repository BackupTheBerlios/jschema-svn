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
package citibob.jschema;

import java.sql.*;

import citibob.textconverter.TextConverter;

public interface SqlType
{
	/** Java class used to represent this type */
	Class getObjClass();

	/** Name of type in the database.  The format varies too much to bother describing it in more detail here.  However, this field can be used (for example) for JTypedTextField to discover length limits. */
//	String getDbType();

	/** Convert an element of this type to an Sql string for use in a query */
	String toSql(Object o);

	/** Read an element of this type from a ResultSet */
	Object get(ResultSet rs, int colno) throws SQLException;

	/** Read an element of this type from a ResultSet */
	Object get(ResultSet rs, String colname) throws SQLException;

	/** Return an example object of this type. */
	Object getPrototype();

	/** Returns capabilities to convert between this data type
	and user input Strings.  This cannot just return nulL!  It will
	crash when you try to use it. */
	TextConverter getTextConverter();
}
