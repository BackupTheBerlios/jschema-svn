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
import citibob.sql.pgsql.*;
import java.sql.*;
import citibob.sql.*;
//import java.util.*;

/** Description of a set of queries we would like to do and load into a PersonsBuf. */
public class IntKeyedDbModel extends SchemaBufDbModel
{

/** Key fields to control who gets displayed. */
int idValue;
String keyField;
int keyCol;

/** Should we add the key field to the SQL statement when we insert records?  Generally,
this will be false for main tables (because they have auto-insert), and
true for subsidiary tables. Defaults to true. */
boolean doInsertKeys;

public void setKey(int idValue)
{
	this.idValue = idValue;
}
public int getKey() { return idValue; }
///** Gets the key column of a row from the underlying SchemaBuf */
//public int getKeyValueAt(int row)
//{
//	Integer I = (Integer)getSchemaBuf().getValueAt(row, keyCol);
//	return (I == null ? -1 : I.intValue());
//}
// --------------------------------------------------------------
public IntKeyedDbModel(SchemaBuf buf, String keyField, boolean doInsertKeys)
{ this(buf, keyField, doInsertKeys, null); }

public IntKeyedDbModel(SchemaBuf buf, String keyField, boolean doInsertKeys, DbChangeModel dbChange)
{
	super(buf, dbChange);
	this.keyField = keyField;
	this.keyCol = buf.findColumn(keyField);
	this.doInsertKeys = doInsertKeys;	
}
public IntKeyedDbModel(Schema schema, String keyField, boolean doInsertKeys)
{ this(schema, keyField, doInsertKeys, null); }
public IntKeyedDbModel(Schema schema, String keyField, boolean doInsertKeys, DbChangeModel dbChange)
{
	this(new SchemaBuf(schema), keyField, doInsertKeys, dbChange);
}

public IntKeyedDbModel(Schema schema, String keyField, DbChangeModel dbChange)
	{ this(schema, keyField, true, dbChange); }
public IntKeyedDbModel(Schema schema, String keyField)
	{ this(schema, keyField, null); }
// --------------------------------------------------------------

public void setSelectWhere(ConsSqlQuery q)
{
	super.setSelectWhere(q);
	q.addWhereClause(keyField + " = " + idValue);
}
public void setInsertKeys(int row, ConsSqlQuery q)
{
	super.setInsertKeys(row, q);
	if (doInsertKeys) q.addColumn(keyField, SqlInteger.sql(idValue));
//	q.addColumn("lastupdated", "now()");
}
// -----------------------------------------------------------

}
