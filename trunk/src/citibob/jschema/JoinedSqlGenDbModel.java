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
import citibob.sql.ConsSqlQuery;
import javax.swing.event.*;
import citibob.sql.*;
import static citibob.jschema.RowStatusConst.*;
import java.util.*;

public abstract class JoinedSqlGenDbModel
implements DbModel
{

///** If a row is inserted to the buffer but not edited, should it be inserted to the DB? */
//boolean[] insertBlankRow;
DbChangeModel dbChange;

//SchemaBuf[] gens;

/** Name of table to which each SqlGen is bound */
//protected String[] tables;		// Table name used to reference each sub-table
//String[] sqlTables;		// Table (with all joins, etc) as it's to be added in the SQL query

TableSpec[] specs;
//String[] tableNames;
//String[] asNames;
/** This is modified by MultiSqlGenDbModel */
public static class TableSpec
{
	String tableName;
	String asName;
	String joinLogic;
	SqlGen gen;
	boolean insertBlankRow;

	public TableSpec(String tableName, String asName, String joinLogic,
	boolean insertBlankRow, SchemaBuf gen)
	{
		this.tableName = tableName;
		this.asName = asName;
		this.joinLogic = joinLogic;
		this.insertBlankRow = insertBlankRow;
		this.gen = gen;
	}
	public TableSpec(SchemaBuf gen)
	{
		this.gen = gen;
	}
	public TableSpec(Schema schema)
		{ this(new SchemaBuf(schema)); }
	public TableSpec(String tableName,
	boolean insertBlankRow, SchemaBuf gen)
	{
		this.tableName = tableName;
		this.insertBlankRow = insertBlankRow;
		this.gen = gen;		
	}
}
///** The listener used to push updates to the database instantly (a la Access) */
//TableModelListener instantUpdateListener = null;
// -----------------------------------------------------------
//public void setInsertBlankRow(int tab, boolean b) { insertBlankRow[tab] = b; }
///** If a row is inserted to the buffer but not edited, should it be inserted to the DB? */
//public boolean getInsertBlankRow(int tab) { return insertBlankRow[tab]; }

// -----------------------------------------------------------
public JoinedSqlGenDbModel(DbChangeModel dbChange, TableSpec[] specs)
{
	// Process table names, filling in missing names
	Set asNamesSet = new TreeSet();
	for (int i=0; i<specs.length; ++i) {
		TableSpec spc = specs[i];
		if (spc.tableName == null) {
			spc.tableName = spc.gen.getDefaultTable();
		}

		// Find first unique as-name
		String aname = (spc.asName != null ? spc.asName : spc.tableName);
		String aname2 = aname;
		int nn=0;
		while (asNamesSet.contains(aname2)) aname2 = aname + (nn++);
		spc.asName = aname2;
		asNamesSet.add(aname2);
		
		spc.gen.setColPrefix(spc.asName);
	}
	this.specs = specs;
	this.dbChange = dbChange;
}
public SqlGen getSqlGen(int i)
	{ return specs[i].gen; }

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
/** Set the where clause for the select statement, based on current key... 
 Meant to be overridden. */
public void setSelectWhere(ConsSqlQuery q) {}
// -----------------------------------------------------------
/** Adds extra fields to an insert query that must be provided
before a row can be inserted into the database.  Typically, this
will involve setting the key fields (same as setSelectWhere()),
which are usually the same for all the same for all records
in the SqlGenDbModel.  This method is called AFTER the rest of
the insert query has been constructed. */
public void setInsertKeys(int tab, int row, ConsSqlQuery sql) {}
// -----------------------------------------------------------
/** Get Sql query to re-select current records
* from database.  When combined with an actual
* database and the SqlDisplay.setSqlValue(), this
* has the result of refreshing the current display. */
public void doSelect(Statement st) throws java.sql.SQLException
{
	ConsSqlQuery q = new ConsSqlQuery(ConsSqlQuery.SELECT);
	for (int i=0; i<specs.length; ++i) {
		q.addTable(specs[i].tableName, specs[i].asName, specs[i].joinLogic);
		specs[i].gen.getSelectCols(q, specs[i].asName);
	}
	setSelectWhere(q);	// Non-join where clauses (can include inner join as well)
	ResultSet rs = st.executeQuery(q.toString());
	int firstRow = specs[0].gen.getRowCount();
	int n=0;
	while (rs.next()) {
		for (int i=0; i<specs.length; ++i) {
			specs[i].gen.addRowNoFire(rs, specs[i].gen.getRowCount());
		}
		++n;
	}
	rs.close();
	int lastRow = specs[0].gen.getRowCount() -1;
	if (lastRow >= firstRow) {
		for (int i=0; i<specs.length; ++i) {
			specs[i].gen.fireTableRowsInserted(firstRow, lastRow);
		}
	}
	
}

// -----------------------------------------------------------
/** Get Sql query to insert record into database,
* assuming it isn't already there. */
public void doInsert(Statement st) throws java.sql.SQLException
{
//	for (int row = 0; row < specs[0].gen.getRowCount(); ++row) {
////System.out.println("doSimpleInsert on row " + row + " of " + gen.getRowCount());
//		doSimpleInsert(row, st);
//	}
}
// -----------------------------------------------------------
/** Have any of the values here changed and not stored in the DB? */
public boolean valueChanged()
{
	for (int i=0; i<specs.length; ++i) {
		for (int row = 0; row < specs[i].gen.getRowCount(); ++row) {
			if (specs[i].gen.valueChanged(row)) return true;
		}
	}
	return false;
}
// -----------------------------------------------------------
int getRowStatus(int row)
{
	int status = 0;
	for (int i=0; i<specs.length; ++i) status = status | specs[i].gen.getStatus(row);
	return status;
}
// -----------------------------------------------------------
/** Get Sql query to flush updates to database.
* Only updates records that have changed; returns null
* if nothing has changed. */
public void doUpdate(Statement st, int row) throws java.sql.SQLException
{
	int status = getRowStatus(row);
	for (int i=0; i<specs.length; ++i) {
				doSimpleUpdate(i, row, st);
//	//System.out.println("doUpdate.status(" + row + ") = " + gen.getStatus(row));
//		switch(status) {
//			// case DELETED | INSERTED :
//				// Do nothing; we inserted then deleted record.
//			// break;
//			case DELETED :
//			case DELETED | CHANGED :
//				doSimpleDelete(specs[i].gen, row, st);
//			break;
//			case INSERTED :
//				if (insertBlankRow) doSimpleInsert(specs[i].gen, row, st);
//				else specs[i].gen.removeRow(row);
//			break;
//			case INSERTED | CHANGED :
//				doSimpleInsert(specs[i].gen, row, st);
//			break;
//			case 0 :
//			case CHANGED :	// No status bits, just a normal record
//				doSimpleUpdate(specs[i].gen, row, st);
//			break;
//		}
	}
	
	for (int i=0; i<specs.length; ++i) {
		if (dbChange != null) dbChange.fireTableChanged(st, specs[i].tableName);
	}
}
// -----------------------------------------------------------
/** Get Sql query to flush updates to database.
* Only updates records that have changed; returns null
* if nothing has changed. */
public void doUpdate(Statement st) throws java.sql.SQLException
{
	for (int row = 0; row < specs[0].gen.getRowCount(); ++row) doUpdate(st, row);
}
// -----------------------------------------------------------
/** Get Sql query to delete current record. */
public void doDelete(Statement st) throws java.sql.SQLException
{
//	for (int row = 0; row < specs[0].gen.getRowCount(); ++row) {
//		int rowStatus = getRowStatus(row);
//		// Only delete if this is a real record in the DB.
//		if ((gen.getStatus(row) & INSERTED) == 0) {
//			doSimpleDelete(row, st);
//		}
//	}
}
// -----------------------------------------------------------
// =============================================================
// Private helper methods
// -----------------------------------------------------------
/** Get Sql query to flush updates to database.
* Only updates records that have changed; returns null
* if nothing has changed. */
private void doSimpleUpdate(int tab, int row, Statement st) throws java.sql.SQLException
{
	SqlGen gen = specs[tab].gen;
	if (gen.valueChanged(row)) {
		ConsSqlQuery q = new ConsSqlQuery(ConsSqlQuery.UPDATE);
		gen.getUpdateCols(row, q, false);
		q.setMainTable(specs[tab].tableName);

		// Add the where clause, and error-check.
		int beforeWhere = q.numWhereClauses();
		specs[tab].gen.getWhereKey(row, q, specs[tab].tableName);
		int afterWhere = q.numWhereClauses();
		System.out.println(q.toString());
		if (beforeWhere == afterWhere) {
			throw new SQLException("Update statement missing key fields in WHERE clause\n"
				+ q.toString());
		}
	String sql = q.toString();
System.out.println("doSimpleUpdate: " + sql);
//		st.executeUpdate(sql);
	}
	specs[tab].gen.setStatus(row, 0);

}
// -----------------------------------------------------------

/** Get Sql query to delete current record. */
private void doSimpleDelete(int tab, int row, Statement st) throws java.sql.SQLException
{
	ConsSqlQuery q = new ConsSqlQuery(ConsSqlQuery.DELETE);
	q.setMainTable(specs[tab].tableName);
	specs[tab].gen.getWhereKey(row, q, specs[tab].tableName);
System.out.println(q.toString());
	if (q.numWhereClauses() == 0) {
		throw new SQLException("Delete statement missing WHERE clause\n" +
			q.toString());
	}
	String sql = q.toString();
System.out.println("doSimpleDelete: " + sql);
	st.executeUpdate(sql);
	specs[tab].gen.removeRow(row);
}
// -----------------------------------------------------------
/** Get Sql query to insert record into database,
* assuming it isn't already there. */
protected ConsSqlQuery doSimpleInsert(int tab, int row, Statement st) throws java.sql.SQLException
{
	ConsSqlQuery q = new ConsSqlQuery(ConsSqlQuery.INSERT);
	q.setMainTable(specs[tab].tableName);
System.out.println("doSimpleInsert: ");
	specs[tab].gen.getInsertCols(row, q, false);
	setInsertKeys(tab, row, q);
	String sql = q.toString();
System.out.println("   sql = " + sql);
	st.executeUpdate(sql);
	specs[tab].gen.setStatus(row, 0);
	return q;
}
// -----------------------------------------------------------

	
}
