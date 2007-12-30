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
/*
 * DbKeyedModel.java
 *
 * Created on March 19, 2006, 5:20 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.sql;

import java.sql.*;
import java.util.*;
import citibob.types.KeyedModel;

/**
 *
 * @author citibob
 */
public class DbKeyedModel extends KeyedModel
implements DbChangeModel.Listener
{

DbChangeModel change;
String idTableName;
//String idFieldName;
//String nameFieldName;
//String orderFieldName;

String sql;

/** @param change model that will tell us when we need to requery.
 @param idTableName Name of table on which a change should trigger a requery.
 @parm sql Query to generate key/value pairs; ID must be in column 1, Name in column 2. */
public DbKeyedModel(SqlRunner str, DbChangeModel change,
String idTableName, String sql)
//throws SQLException
{
	super();
	this.sql = sql;
	this.idTableName = idTableName;
	this.change = change;
	requery(str);
	if (change != null) change.addListener(idTableName, this);
}
public DbKeyedModel(SqlRunner str, DbChangeModel change,
String idTableName, String idFieldName,
String nameFieldName, String orderFieldName)
throws SQLException
{
	this(str, change, idTableName,
		"select " + idFieldName + ", " + nameFieldName + " from " +
			idTableName + " order by " + orderFieldName);
}

/** Re-load keyed model from database... */
public void requery(SqlRunner str)
{
//	clear();
	addAllItems(str, sql, 1, 2);
	str.execUpdate(new UpdRunnable() {
	public void run(SqlRunner str) {
		fireKeyedModelChanged();
	}});
}

/** Called when the data potentially changes in the database. */
public void tableWillChange(SqlRunner str, String table)
//throws SQLException
{
	if (!idTableName.equals(table)) return;
//System.out.println("tableWillChange: " + table);
	requery(str);
}

}
