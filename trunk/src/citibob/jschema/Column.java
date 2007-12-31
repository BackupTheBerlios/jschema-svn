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
package citibob.jschema;

import citibob.sql.SqlType;
import citibob.types.JType;
import citibob.types.JDateType;
//import citibob.sql.SqlType;

/** Represents one column in a Schema. */
public class Column
{

SqlType jType;
String name;
String label;		// Suggested label for display
boolean key;

public Column(SqlType type, String name, boolean key)
	{ this(type, name, name, key); }
public Column(SqlType type, String name)
	{ this(type, name, name, false); }
public Column(SqlType type, String name, String label, boolean key)
{
	this.jType = type;
	this.name = name;
	this.label = label;
	this.key = key;
}
// --------------------------------------------------------------------
/** Type of this column */
public SqlType getType()
	{ return jType; }

/** Name of column in Sql */
public String getName()
	{ return name; }

public String getLabel()
	{ return label; }

/** Is this a key column? */
public boolean isKey()
	{ return key; }

/** Default value for column when inserting new row in buffers. 
 This method will be overridden. */
public Object getDefault()
	{ return null; }

// ====================================================================
// Convenience Functions
/** Convenience function */
public java.util.TimeZone getTimeZone() { return ((JDateType)getType()).getTimeZone(); }
/** Convenience function */
public JDateType getJDateType() { return ((JDateType)getType()); }
/** Convenience function */
public java.util.Date newDate() { return getJDateType().truncate(new java.util.Date()); }
/** Convenience function */
public String toSql(Object o) { return ((SqlType)jType).toSql(o); }


}
