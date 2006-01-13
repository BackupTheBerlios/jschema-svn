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

import citibob.jschema.*;
import citibob.jschema.pgsql.*;
import java.sql.*;
import citibob.sql.SqlQuery;
//import java.util.*;

/** Description of a set of queries we would like to do and load into a PersonsBuf. */
public class IntKeyedDbModel extends SchemaBufDbModel
{

/** Key fields to control who gets displayed. */
int idValue;
String keyField;

/** Should we add the key field to the list when we insert records?  Generally,
this will be false for main tables, and true for subsidiary tables. Defaults to true. */
boolean doInsertKeys;

public void setKey(int idValue)
{
	this.idValue = idValue;
}

public IntKeyedDbModel(Schema schema, String keyField, boolean doInsertKeys)
{
	super(new SchemaBuf(schema));
	this.keyField = keyField;
	this.doInsertKeys = doInsertKeys;
}

public IntKeyedDbModel(Schema schema, String keyField)
	{ this(schema, keyField, true); }

public void setSelectWhere(SqlQuery q)
{
	q.addWhereClause(keyField + " = " + idValue);
}
public void setInsertKeys(int row, SqlQuery q)
{
	if (doInsertKeys) q.addColumn(keyField, SqlInteger.sql(idValue));
}

}
