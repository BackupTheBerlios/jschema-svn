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

public class SchemaBufDbModel extends SqlGenDbModel implements TableDbModel
{
String whereClause;
String orderClause;
QueryLogger logger;

boolean updateBufOnUpdate = true;	// Should we update sequence columns on insert?
//SqlRunner str;

// -------------------------------------------------------------
public SchemaBufDbModel(String table, SchemaBuf buf)
{
	super(table, buf);
}
/** Uses the default table for the Schema in buf. */
public SchemaBufDbModel(SchemaBuf buf, DbChangeModel dbChange)
{
	super(buf.getSchema().getDefaultTable(), buf, dbChange);
}
/** Uses the default table for the Schema in buf. */
public SchemaBufDbModel(Schema schema, DbChangeModel dbChange)
{
	super(schema.getDefaultTable(), new SchemaBuf(schema), dbChange);
}

/** Uses the default table for the Schema in buf. */
public SchemaBufDbModel(SchemaBuf buf)
{
	super(buf.getSchema().getDefaultTable(), buf);
}
/** Uses the default table for the Schema in buf. */
public SchemaBufDbModel(Schema schema)
{
	super(schema.getDefaultTable(), new SchemaBuf(schema));
}

public void setLogger(QueryLogger logger) { this.logger = logger; }

public void setUpdateBufOnUpdate(boolean b) { updateBufOnUpdate = b; }

/** This will sometimes be overridden. */
public void setKey(Object[] key)
{
	if (key == null || key.length == 0) return;
	
	SchemaBuf buf = (SchemaBuf)gen;
	Schema schema = buf.getSchema();
	StringBuffer sb = new StringBuffer("1=1");
	int j = 0;
	for (int i=0; i<schema.getColCount(); ++i) {
		Column c = schema.getCol(i);
		if (!c.isKey()) continue;
		sb.append(" and " + buf.getDefaultTable() + "." + c.getName() +
			"=" + c.getType().toSql(key[j]));
		++j;
	}
	setWhereClause(sb.toString());
}

public void setWhereClause(String whereClause)
{
	this.whereClause = whereClause;
}
public void setOrderClause(String orderClause)
	{ this.orderClause = orderClause; }
// -------------------------------------------------------------
public void doSelect(SqlRunner str)
{
//	gen.fireRefreshStart();
	str.execUpdate(new UpdRunnable() {
	public void run(SqlRunner str) throws Exception {
		getSchemaBuf().clear();		
	}});
	super.doSelect(str);
//	gen.fireRefreshFinish();
}

public SchemaBuf getSchemaBuf()
	{ return (SchemaBuf)getSqlGen(); }
public citibob.swing.table.JTypeTableModel getTableModel() { return getSchemaBuf(); }

public Schema getSchema()
	{ return getSchemaBuf().getSchema(); }

public void doClear()
	{ ((SchemaBuf)getSqlGen()).clear(); }

// -----------------------------------------------------------
public void setSelectWhere(ConsSqlQuery q)
{
	q.addWhereClause(whereClause);
	q.addOrderClause(orderClause);
}

/** @see SqlGenDbModel */
public void setInsertKeys(int row, ConsSqlQuery q) {}


// ===========================================================
InstantUpdateListener instantUpdateListener;

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

protected ConsSqlQuery doSimpleInsert(final int row, SqlRunner str)
{
	//ConsSqlQuery q = 
	ConsSqlQuery q = super.doSimpleInsert(row, str);
	
//	SqlQuery q = new SqlQuery(SqlQuery.INSERT);
//	q.setMainTable(table);
//System.out.println("doSimpleInsert: ");
//	gen.getInsertCols(row, q, false);
//	setInsertKeys(row, q);
//	String sql = q.toString();
//System.out.println("   sql = " + sql);
//	st.executeUpdate(sql);
//	gen.setStatus(row, 0);
	
	/** Figure out which sequence columns were not inserted, and find their keys */
	final SchemaBuf sb = (SchemaBuf)gen;
	Schema schema = sb.getSchema();
	
	TreeMap<String,ConsSqlQuery.NVPair> inserted = new TreeMap();
	for (ConsSqlQuery.NVPair nv : q.getColumns()) inserted.put(nv.name, nv);
	
	if (updateBufOnUpdate) {
		for (int i=0; i<schema.getColCount(); ++i) {
			Column col = schema.getCol(i);
			if ((col.jType instanceof SqlSequence) && inserted.get(col.name)==null) {
				// Update this in the SchemaBuf if it wasn't inserted...
				final SqlSequence seq = (SqlSequence)col.jType;
//				int val = seq.getCurVal(st);
				final int ii = i;
				seq.getCurVal(str);
				str.execUpdate(new UpdRunnable() {
				public void run(SqlRunner str) throws Exception {
					sb.setValueAt(seq.retrieve(str), row, ii);
				}});
			}
		}
	}

//	// =============== Figure out logging stuff
//	System.out.println("Insert Log");
//	for (int i=0; i<schema.getColCount(); ++i) {
//		// Retrieve info on column + data from Query and SchemaBuf (if present)
//		Column col = schema.getCol(i);		
//		ConsSqlQuery.NVPair nv = inserted.get(col.name);
//		
//		// Determine the value we ended up setting
//		Object sqlval = null;		// The value we ended up setting
//		boolean haveval = false;
//		if (nv != null) {
//			// Take the value we actually inserted, if it exists.
//			sqlval = nv.value;
//			haveval = true;
//		} else if (sb != null) {
//			// Take value in the SchemaBuf, which was set after the insert
//			// due to a SqlSerial data type.  Ignore if null
//			Object setval = sb.getValueAt(row,i);
//			if (setval != null) {
//				// Convert setval to a Sql string
//				sqlval = col.getType().toSql(setval);
//				haveval = true;
//			}
//		}
//
//		
//		if (col.isKey()) {
//			System.out.println("     Key field: " + col.getName() + " = " + sqlval);
//		} else if (haveval) {
//			System.out.println("     inserted field: " + col.getName() + " = " + sqlval);
//		}
//	}
	if (logger != null) logger.log(new QueryLogRec(q, schema, sb, row));
	return q;
}
// -----------------------------------------------------------
protected ConsSqlQuery doSimpleUpdate(int row, SqlRunner str)
{
	SchemaBuf sb = (SchemaBuf)gen;
	Schema schema = sb.getSchema();
	ConsSqlQuery q = super.doSimpleUpdate(row, str);
	if (q != null && logger != null) logger.log(new QueryLogRec(q, schema, sb, row));
	return q;
}
/** Get Sql query to delete current record. */
protected ConsSqlQuery doSimpleDelete(int row, SqlRunner str)
{
	SchemaBuf sb = (SchemaBuf)gen;
	Schema schema = sb.getSchema();
	ConsSqlQuery q = doSimpleDeleteNoRemoveRow(row, str);
	if (logger != null) logger.log(new QueryLogRec(q, schema, sb, row));
	gen.removeRow(row);
	return q;
}
// ==============================================
private static class InstantUpdateListener implements TableModelListener {
//	SqlRunner str;
	ActionRunner runner;
	SqlGenDbModel dbModel;
	public InstantUpdateListener(SqlGenDbModel dbModel, ActionRunner runner)
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
						dbModel.doUpdate(str, r);
					}
				break;
			}
		}});
 	}
}


}
