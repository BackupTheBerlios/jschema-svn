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
import javax.swing.event.*;
import citibob.multithread.*;
import citibob.sql.*;
import java.util.*;
import citibob.swing.table.*;

public class JoinedSchemaBufDbModel extends JoinedSqlGenDbModel
{
String whereClause;
String orderClause;

boolean updateBufOnUpdate = false;	// Should we update sequence columns on insert?
//Statement st;
//JTypeTableModel xtra;
MultiJTypeTableModel model;

// -------------------------------------------------------------
//public MultiSchemaBufDbModel(String[] tables, SchemaBuf[] bufs)
//{
//	super(tables, bufs);
//}
/** Uses the default table for the Schema in buf.
 @param xtra Report columns not derived from the database in a standard way. */
public JoinedSchemaBufDbModel(DbChangeModel dbChange, TableSpec[] specs)
{
	super(dbChange, specs);
//	this.xtra = xtra;
	
	JTypeTableModel[] models = new JTypeTableModel[specs.length];
	int i;
	for (i=0; i<specs.length; ++i) models[i] = (JTypeTableModel)specs[i].gen;
	MultiJTypeTableModel model = new MultiJTypeTableModel(models);
}

/** Returns a JTypeTableModel that can be displayed in a JTable */
public JTypeTableModel getTableModel()
{
	return model;
}

//public void setUpdateBufOnUpdate(boolean b) { updateBufOnUpdate = b; }

public void setWhereClause(String whereClause)
{
	this.whereClause = whereClause;
}
public void setOrderClause(String orderClause)
	{ this.orderClause = orderClause; }
// -------------------------------------------------------------
public void doSelect(Statement st) throws java.sql.SQLException
{
//	for (int i=0; i<specs.length; ++i)
//		getSchemaBuf(i).clear();
	doClear();
	super.doSelect(st);
}

public SchemaBuf getSchemaBuf(int i)
	{ return (SchemaBuf)getSqlGen(i); }

public Schema getSchema(int i)
	{ return getSchemaBuf(i).getSchema(); }

public void doClear()
{
	for (int i=0; i<specs.length; ++i) {
		getSchemaBuf(i).clear();
//		if (xtra != null) xtra.clear();
	}
}

// -----------------------------------------------------------
public void setSelectWhere(ConsSqlQuery q)
{
	q.addWhereClause(whereClause);
	q.addOrderClause(orderClause);
}

/** @see SqlGenDbModel */
public void setInsertKeys(int row, ConsSqlQuery q) {}


// ===========================================================
//InstantUpdateListener instantUpdateListener;
//
///** This should NOT be used by subclasses.  In general, instant update is a property
//assigned by enclosing objects --- panels that USE this DbModel.
//TODO: Make instant updates delete instantly when user hits "delete". */
//public void setInstantUpdate(ActionRunner runner, boolean instantUpdate)
//{
//	if (instantUpdate) {
//		if (instantUpdateListener == null) {
//			instantUpdateListener = new InstantUpdateListener(this, runner);
//			this.getSchemaBuf().addTableModelListener(instantUpdateListener);
//		}
//	} else {
//		if (instantUpdateListener != null) {
//			this.getSchemaBuf().removeTableModelListener(instantUpdateListener);
//			instantUpdateListener = null;
//		}
//	}
//}
//public boolean isInstantUpdate()
//{
//	return (instantUpdateListener != null);
//}

protected ConsSqlQuery doSimpleInsert(int tab, int row, Statement st) throws java.sql.SQLException
{
	
	ConsSqlQuery q = super.doSimpleInsert(tab, row, st);

	/** Figure out which sequence columns were not inserted, and find their keys */
	SchemaBuf sb = getSchemaBuf(tab); //(SchemaBuf)gens[tab];
	Schema schema = sb.getSchema();
	String[] sinserted = q.getColumnNames();
	TreeSet inserted = new TreeSet();
	for (int i=0; i<sinserted.length; ++i) inserted.add(sinserted[i]);

	if (!updateBufOnUpdate) return q;
	for (int i=0; i<schema.getColCount(); ++i) {
		Column col = schema.getCol(i);
		if ((col.type instanceof SqlSequence) && !inserted.contains(col.name)) {
			// Update this in the SchemaBuf if it wasn't inserted...
			SqlSequence seq = (SqlSequence)col.type;
			int val = seq.getCurVal(st);
			sb.setValueAt(new Integer(val), row, i);
		}
	}
	
	return q;
}
// -----------------------------------------------------------


//// ==============================================
//private static class InstantUpdateListener implements TableModelListener {
////	Statement st;
//	ActionRunner runner;
//	SqlGenDbModel dbModel;
//	public InstantUpdateListener(SqlGenDbModel dbModel, ActionRunner runner)
//	{
//		this.runner = runner;
//		this.dbModel = dbModel;
//	}
//	public void tableChanged(final TableModelEvent e) {
//System.out.println("InstantUpdateListener.tableChanged()");
//		runner.doRun(new StRunnable() {
//		public void run(Statement st) throws SQLException {
//			switch(e.getType()) {
//				// TODO: Update only rows that have changed, don't waste
//				// your time on all the other rows!
//				case TableModelEvent.UPDATE :
//					for (int r=e.getFirstRow(); r <= e.getLastRow(); ++r) {
//System.out.println("InstantUpdateListener.doUpdate row = " + r);
//						dbModel.doUpdate(st, r);
//					}
//				break;
//			}
//		}});
// 	}
//}


}
