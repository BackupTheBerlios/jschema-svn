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
import javax.swing.event.*;
import citibob.multithread.*;
import citibob.sql.*;
import java.util.*;
import citibob.jschema.log.*;

public class SchemaBufDbModel implements TableDbModel, RowStatusConst
{
QueryLogger logger;

boolean updateBufOnUpdate = true;	// Should we update sequence columns on insert?
//SqlRunner str;

boolean insertBlankRow = false;
DbChangeModel dbChange;

SchemaBuf sbuf;		// The buffer -- has its own schema, to be used for SQL selects in subclass
String selectTable = null;		// Table to use for select queries

SchemaInfo[] sinfos;
/** The listener used to push updates to the database instantly (a la Access) */
TableModelListener instantUpdateListener = null;
//InstantUpdateListener instantUpdateListener;

String whereClause;
String orderClause;

// -------------------------------------------------------------
protected void init(SchemaBuf sbuf, String selectTable, SchemaInfo[] sinfos, DbChangeModel dbChange)
{
	this.sbuf = sbuf;
	this.selectTable = selectTable;
	this.dbChange = dbChange;
	this.sinfos = sinfos;
	for (int i=0; i<sinfos.length; ++i) {
		sinfos[i].schemaMap = SchemaHelper.newSchemaMap(sinfos[i].schema, sbuf.getSchema());
	}
}
protected SchemaBufDbModel() {}

/** Uses the default table for the Schema in buf. */
public SchemaBufDbModel(SchemaBuf sbuf, SchemaInfo[] sinfos, DbChangeModel dbChange)
	{ init(sbuf, sbuf.getDefaultTable(), sinfos, dbChange); }
public SchemaBufDbModel(SchemaBuf sbuf, DbChangeModel dbChange)
	{ this(sbuf, sbuf.getDefaultTable(), dbChange); }
public SchemaBufDbModel(Schema schema, DbChangeModel dbChange)
	{ this(new SchemaBuf(schema), dbChange); }
public SchemaBufDbModel(SchemaBuf sbuf)
	{ this(sbuf, sbuf.getDefaultTable(), null); }
public SchemaBufDbModel(SchemaBuf sbuf, String table, DbChangeModel dbChange)
{
	if (table == null) table = sbuf.getDefaultTable();
	init(sbuf, table,
		new SchemaInfo[] {new SchemaInfo(sbuf.getSchema(), table)},
		dbChange);
}
public SchemaBufDbModel(Schema schema, String table, DbChangeModel dbChange)
{
	if (table == null) table = schema.getDefaultTable();
	init(new SchemaBuf(schema), table,
		new SchemaInfo[] {new SchemaInfo(schema, table)},
		dbChange);
}

// -------------------------------------------------------------
public SchemaBuf getSchemaBuf() { return sbuf; }
public citibob.swing.table.JTypeTableModel getTableModel() { return getSchemaBuf(); }
public void setLogger(QueryLogger logger) { this.logger = logger; }

public void setUpdateBufOnUpdate(boolean b) { updateBufOnUpdate = b; }

// -----------------------------------------------------------
/** This will sometimes be overridden. */
public void setKey(Object[] key)
{
	if (key == null || key.length == 0) return;
	
	Schema schema = sbuf.getSchema();
	StringBuffer sb = new StringBuffer("1=1");
	int j = 0;
	for (int i=0; i<schema.getColCount(); ++i) {
		Column c = schema.getCol(i);
		if (!c.isKey()) continue;
		sb.append(" and " + selectTable + "." + c.getName() +
			"=" + c.getType().toSql(key[j]));
		++j;
	}
	setWhereClause(sb.toString());
}

/** Common convenience function, to override. */
public void setKey(int key) {}
/** Common convenience function, to override. */
public void setKey(String key) {}

public void setWhereClause(String whereClause)
{
	this.whereClause = whereClause;
}
public void setOrderClause(String orderClause)
	{ this.orderClause = orderClause; }

// -----------------------------------------------------------
/** Adds extra fields to an insert query that must be provided
before a row can be inserted into the database.  Typically, this
will involve setting the key fields (same as setSelectWhere()),
which are usually the same for all the same for all records
in the SqlGenDbModel.  This method is called AFTER the rest of
the insert query has been constructed. */
public void setInsertKeys(int row, ConsSqlQuery sql) {}

/** Set the where clause for the select statement, based on current key... */
public void setSelectWhere(ConsSqlQuery q)
{
	q.addWhereClause(whereClause);
	q.addOrderClause(orderClause);
}
// ===========================================================

/** This should NOT be used by subclasses.  In general, instant update is a property
assigned by enclosing objects --- panels that USE this DbModel.
TODO: Make instant updates delete instantly when user hits "delete". */
public void setInstantUpdate(ActionRunner runner, boolean instantUpdate)
{
	if (instantUpdate) {
		if (instantUpdateListener == null) {
			instantUpdateListener = new InstantUpdateListener(this, runner);
			this.getSchemaBuf().addTableModelListener(instantUpdateListener);
		}
	} else {
		if (instantUpdateListener != null) {
			this.getSchemaBuf().removeTableModelListener(instantUpdateListener);
			instantUpdateListener = null;
		}
	}
}
public boolean isInstantUpdate()
{
	return (instantUpdateListener != null);
}

// -----------------------------------------------------------
protected ConsSqlQuery doSimpleInsert(final int row, SqlRunner str, SchemaInfo qs)
{
	// Put together the insert for this row
	ConsSqlQuery q = new ConsSqlQuery(ConsSqlQuery.INSERT);
	q.setMainTable(qs.table);
	sbuf.getInsertCols(row, q, false, qs);
	setInsertKeys(row, q);
	String sql = q.getSql();
		
	// Dispatch it
	str.execSql(sql, new UpdRunnable() {
	public void run(SqlRunner str) {
		sbuf.setStatus(row, 0);
	}});
	
	/** Figure out which sequence columns in qc.schema were not inserted, and find their keys */
	Schema schema = qs.schema;
	
	TreeMap<String,ConsSqlQuery.NVPair> inserted = new TreeMap();
	for (ConsSqlQuery.NVPair nv : q.getColumns()) inserted.put(nv.name, nv);
	
	if (updateBufOnUpdate) {
		for (int i=0; i<schema.getColCount(); ++i) {
			Column col = schema.getCol(i);
			if ((col.jType instanceof SqlSequence) && inserted.get(col.name)==null) {
				// Update this in the SchemaBuf if it wasn't inserted...
				final SqlSequence seq = (SqlSequence)col.jType;
//				int val = seq.getCurVal(st);
				final int qcol = qs.schemaMap[i];
				seq.getCurVal(str);
				str.execUpdate(new UpdRunnable() {
				public void run(SqlRunner str) throws Exception {
					sbuf.setValueAt(seq.retrieve(str), row, qcol);
				}});
			}
		}
	}

	if (logger != null) logger.log(new QueryLogRec(q, qs, sbuf, row));
	return q;
}
// -----------------------------------------------------------
protected ConsSqlQuery doSimpleUpdate(int row, SqlRunner str, SchemaInfo qs)
{
	SchemaBuf sb = (SchemaBuf)sbuf;
	Schema schema = qs.schema;
	
	if (sbuf.valueChanged(row, qs.schemaMap)) {
		ConsSqlQuery q = new ConsSqlQuery(ConsSqlQuery.UPDATE);
		sbuf.getUpdateCols(row, q, false, qs);
		q.setMainTable(qs.table);

		// Add the where clause, and error-check.
		int beforeWhere = q.numWhereClauses();
		sbuf.getWhereKey(row, q, qs);
		int afterWhere = q.numWhereClauses();
		System.out.println(q.getSql());
		if (beforeWhere == afterWhere) {
			throw new IllegalArgumentException("Update statement missing key fields in WHERE clause\n"
				+ q.getSql());
		}
	String sql = q.getSql();
System.out.println("doSimpleUpdate: " + sql);
		str.execSql(sql);
			sbuf.setStatus(row, 0);
		if (logger != null) logger.log(new QueryLogRec(q, qs, sb, row));
		return q;
	} else {
		sbuf.setStatus(row, 0);
		return null;
	}
}
/** Get Sql query to delete current record. */
protected ConsSqlQuery doSimpleDeleteNoRemoveRow(int row, SqlRunner str, SchemaInfo qs)
{
	SchemaBuf sb = (SchemaBuf)sbuf;
	Schema schema = sb.getSchema();

	ConsSqlQuery q = new ConsSqlQuery(ConsSqlQuery.DELETE);
	q.setMainTable(qs.table);
	sbuf.getWhereKey(row, q, qs);
	if (q.numWhereClauses() == 0) {
		throw new IllegalArgumentException("Delete statement missing WHERE clause\n" +
			q.getSql());
	}
	String sql = q.getSql();
	str.execSql(sql);
	if (logger != null) logger.log(new QueryLogRec(q, qs, sb, row));
//	sbuf.removeRow(row);
	return q;
}

// -----------------------------------------------------------
public void setInsertBlankRow(boolean b) { insertBlankRow = b; }
/** If a row is inserted to the buffer but not edited, should it be inserted to the DB? */
public boolean getInsertBlankRow() { return insertBlankRow; }

// -----------------------------------------------------------
/** Get Sql query to re-select current records
* from database.  When combined with an actual
* database and the SqlDisplay.setSqlValue(), this
* has the result of refreshing the current display. */
public void doSelect(SqlRunner str)
{
	ConsSqlQuery q = new ConsSqlQuery(ConsSqlQuery.SELECT);
	sbuf.getSelectCols(q, selectTable);
	q.addTable(selectTable);
	setSelectWhere(q);
	sbuf.setRows(str, q.getSql());
}
// -----------------------------------------------------------
/** Get Sql query to insert record into database,
* assuming it isn't already there. */
public void doInsert(SqlRunner str)
{
	for (int row = 0; row < sbuf.getRowCount(); ++row) {
		for (SchemaInfo qs : sinfos) doSimpleInsert(row, str, qs);
	}
}
// -----------------------------------------------------------
/** Have any of the values here changed and not stored in the DB? */
public boolean valueChanged()
{
	for (int row = 0; row < sbuf.getRowCount(); ++row) {
		if (sbuf.getStatus(row) != 0) return true;
//		if (sbuf.valueChanged(row)) return true;
	}
	return false;
}
// -----------------------------------------------------------
/** Get Sql query to flush updates to database.
* Only updates records that have changed.
 @returns true if the row was deleted from the model. */
public boolean doUpdate(SqlRunner str, int row, SchemaInfo qs)
{
	boolean deleted = false;
//System.out.println("doUpdate.status(" + row + ") = " + sbuf.getStatus(row));
	int status = sbuf.getStatus(row); // & ~CHANGED;
	switch(status) {
		// case DELETED || INSERTED :
			// Do nothing; we inserted then deleted record.
		// break;
		case DELETED :
		case DELETED | CHANGED :
			doSimpleDeleteNoRemoveRow(row, str, qs);
			deleted = true;
		break;
		case INSERTED :
			if (insertBlankRow) doSimpleInsert(row, str, qs);
			else sbuf.removeRow(row);
		break;
		case INSERTED | CHANGED :
			doSimpleInsert(row, str, qs);
		break;
		case 0 :
		case CHANGED :	// No status bits, just a normal record
			doSimpleUpdate(row, str, qs);
		break;
	}
	return deleted;
}
// -----------------------------------------------------------
void fireTablesWillChange(SqlRunner str)
{
	for (SchemaInfo qs : sinfos) if (dbChange != null) {
		dbChange.fireTableWillChange(str, qs.table);
	}
}
/** Get Sql query to flush updates to database.
* Only updates records that have changed; returns null
* if nothing has changed. */
public void doUpdate(SqlRunner str)
{
	for (int row = 0; row < sbuf.getRowCount(); ++row) {
		doUpdateNoFireTableWillChange(str, row);
	}
	fireTablesWillChange(str);
}
void doUpdateNoFireTableWillChange(SqlRunner str, int row)
{
	for (SchemaInfo qs : sinfos) {
		if (doUpdate(str, row, qs)) {
			sbuf.removeRow(row);
			--row;		// Row was deleted, adjust our counting
		}
	}
}
// -----------------------------------------------------------
/** Get Sql query to delete current record. */
public void doDelete(SqlRunner str)
{
	for (int row = 0; row < sbuf.getRowCount(); ++row) {
		for (SchemaInfo qs : sinfos) {
			// Only delete if this is a real record in the DB.
			if ((sbuf.getStatus(row) & INSERTED) == 0) {
				doSimpleDeleteNoRemoveRow(row, str, qs);
			}
			sbuf.removeRow(row);
			-- row;
		}
	}
	fireTablesWillChange(str);
}
public void doClear()
	{ sbuf.clear(); }
// -----------------------------------------------------------
// ==============================================
private static class InstantUpdateListener implements TableModelListener {
//	SqlRunner str;
	ActionRunner runner;
	SchemaBufDbModel dbModel;
	public InstantUpdateListener(SchemaBufDbModel dbModel, ActionRunner runner)
	{
		this.runner = runner;
		this.dbModel = dbModel;
	}
	public void tableChanged(final TableModelEvent e) {
System.out.println("InstantUpdateListener.tableChanged()");
		runner.doRun(new BatchRunnable() {
		public void run(SqlRunner str) throws SQLException {
			switch(e.getType()) {
				// TODO: Update only rows that have changed, don't waste
				// your time on all the other rows!
				case TableModelEvent.UPDATE :
					for (int r=e.getFirstRow(); r <= e.getLastRow(); ++r) {
System.out.println("InstantUpdateListener.doUpdate row = " + r);
						dbModel.doUpdateNoFireTableWillChange(str, r);
						dbModel.fireTablesWillChange(str);
					}
				break;
			}
		}});
 	}
}
// =============================================================
// -----------------------------------------------------------

}
