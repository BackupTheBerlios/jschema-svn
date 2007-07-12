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

import java.sql.*;
import citibob.sql.ConsSqlQuery;
import javax.swing.event.*;
import citibob.sql.*;

public abstract class SqlGenDbModel implements DbModel, RowStatusConst
{

boolean insertBlankRow = false;
DbChangeModel dbChange;

SqlGen gen;
/** Name of table to which we're bound. */
String table;
/** Key of the (single) record we're processing. */
//Object[] curKey;
/** The listener used to push updates to the database instantly (a la Access) */
TableModelListener instantUpdateListener = null;
// -----------------------------------------------------------
public void setInsertBlankRow(boolean b) { insertBlankRow = b; }
/** If a row is inserted to the buffer but not edited, should it be inserted to the DB? */
public boolean getInsertBlankRow() { return insertBlankRow; }

// -----------------------------------------------------------
public SqlGenDbModel(String table, SqlGen gen, DbChangeModel dbChange)
{
	this.table = table;
	this.gen = gen;
	this.dbChange = dbChange;
}
public SqlGenDbModel(String table, SqlGen gen)
	{ this(table, gen, null); }
public SqlGen getSqlGen()
	{ return gen; }
// -----------------------------------------------------------
// This method is like a constructor; will always differ.
// in every implementation.
// void setKey()
// -----------------------------------------------------------
public void doInit(Statement st) throws java.sql.SQLException
{
//	this.st = st;
}
// -----------------------------------------------------------
/** Set the where clause for the select statement, based on current key... */
public void setSelectWhere(ConsSqlQuery q) {}
// -----------------------------------------------------------
/** Adds extra fields to an insert query that must be provided
before a row can be inserted into the database.  Typically, this
will involve setting the key fields (same as setSelectWhere()),
which are usually the same for all the same for all records
in the SqlGenDbModel.  This method is called AFTER the rest of
the insert query has been constructed. */
public void setInsertKeys(int row, ConsSqlQuery sql) {}
// -----------------------------------------------------------
/** Get Sql query to re-select current records
* from database.  When combined with an actual
* database and the SqlDisplay.setSqlValue(), this
* has the result of refreshing the current display. */
public void doSelect(Statement st) throws java.sql.SQLException
{
	ConsSqlQuery q = new ConsSqlQuery(ConsSqlQuery.SELECT);
	gen.getSelectCols(q, table);
	q.addTable(table);
	setSelectWhere(q);
System.out.println("doSelect: " + q.getSql());
	gen.addAllRows(st.executeQuery(q.getSql()));
}
// -----------------------------------------------------------
/** Get Sql query to insert record into database,
* assuming it isn't already there. */
public void doInsert(Statement st) throws java.sql.SQLException
{
	for (int row = 0; row < gen.getRowCount(); ++row) {
System.out.println("doSimpleInsert on row " + row + " of " + gen.getRowCount());
		doSimpleInsert(row, st);
	}
}
// -----------------------------------------------------------
/** Have any of the values here changed and not stored in the DB? */
public boolean valueChanged()
{
	for (int row = 0; row < gen.getRowCount(); ++row) {
		if (gen.getStatus(row) != 0) return true;
//		if (gen.valueChanged(row)) return true;
	}
	return false;
}
// -----------------------------------------------------------
/** Get Sql query to flush updates to database.
* Only updates records that have changed; returns null
* if nothing has changed. */
public void doUpdate(Statement st, int row) throws java.sql.SQLException
{
//System.out.println("doUpdate.status(" + row + ") = " + gen.getStatus(row));
	int status = gen.getStatus(row); // & ~CHANGED;
	switch(status) {
		// case DELETED || INSERTED :
			// Do nothing; we inserted then deleted record.
		// break;
		case DELETED :
		case DELETED | CHANGED :
			doSimpleDelete(row, st);
		break;
		case INSERTED :
			if (insertBlankRow) doSimpleInsert(row, st);
			else gen.removeRow(row);
		break;
		case INSERTED | CHANGED :
			doSimpleInsert(row, st);
		break;
		case 0 :
		case CHANGED :	// No status bits, just a normal record
			doSimpleUpdate(row, st);
		break;
	}
	if (dbChange != null) dbChange.fireTableChanged(st, table);	
}
// -----------------------------------------------------------
/** Get Sql query to flush updates to database.
* Only updates records that have changed; returns null
* if nothing has changed. */
public void doUpdate(Statement st) throws java.sql.SQLException
{
	for (int row = 0; row < gen.getRowCount(); ++row) doUpdate(st, row);
}
// -----------------------------------------------------------
/** Get Sql query to delete current record. */
public void doDelete(Statement st) throws java.sql.SQLException
{
	for (int row = 0; row < gen.getRowCount(); ++row) {
		// Only delete if this is a real record in the DB.
		if ((gen.getStatus(row) & INSERTED) == 0) {
			doSimpleDelete(row, st);
		}
	}
}
// -----------------------------------------------------------
// =============================================================
// Private helper methods
// -----------------------------------------------------------
/** Get Sql query to flush updates to database.
* Only updates records that have changed; returns null
* if nothing has changed. */
protected ConsSqlQuery doSimpleUpdate(int row, Statement st) throws java.sql.SQLException
{
	if (gen.valueChanged(row)) {
		ConsSqlQuery q = new ConsSqlQuery(ConsSqlQuery.UPDATE);
		gen.getUpdateCols(row, q, false);
		q.setMainTable(table);

		// Add the where clause, and error-check.
		int beforeWhere = q.numWhereClauses();
		gen.getWhereKey(row, q, table);
		int afterWhere = q.numWhereClauses();
		System.out.println(q.getSql());
		if (beforeWhere == afterWhere) {
			throw new SQLException("Update statement missing key fields in WHERE clause\n"
				+ q.getSql());
		}
	String sql = q.getSql();
System.out.println("doSimpleUpdate: " + sql);
		st.executeUpdate(sql);
		gen.setStatus(row, 0);
		return q;
	} else {
		gen.setStatus(row, 0);
		return null;
	}
}
// -----------------------------------------------------------

/** Get Sql query to delete current record. */
protected ConsSqlQuery doSimpleDeleteNoRemoveRow(int row, Statement st) throws java.sql.SQLException
{
	ConsSqlQuery q = new ConsSqlQuery(ConsSqlQuery.DELETE);
	q.setMainTable(table);
	gen.getWhereKey(row, q, table);
System.out.println(q.getSql());
	if (q.numWhereClauses() == 0) {
		throw new SQLException("Delete statement missing WHERE clause\n" +
			q.getSql());
	}
	String sql = q.getSql();
System.out.println("doSimpleDelete: " + sql);
	st.executeUpdate(sql);
	return q;
}
protected ConsSqlQuery doSimpleDelete(int row, Statement st) throws java.sql.SQLException
{
	ConsSqlQuery q = doSimpleDeleteNoRemoveRow(row, st);
	gen.removeRow(row);
	return q;
}
// -----------------------------------------------------------
/** Get Sql query to insert record into database,
* assuming it isn't already there. */
protected ConsSqlQuery doSimpleInsert(int row, Statement st) throws java.sql.SQLException
{
	ConsSqlQuery q = new ConsSqlQuery(ConsSqlQuery.INSERT);
	q.setMainTable(table);
System.out.println("doSimpleInsert: ");
	gen.getInsertCols(row, q, false);
	setInsertKeys(row, q);
	String sql = q.getSql();
System.out.println("   sql = " + sql);
	st.executeUpdate(sql);
	gen.setStatus(row, 0);
	return q;
}
// -----------------------------------------------------------

	
}
