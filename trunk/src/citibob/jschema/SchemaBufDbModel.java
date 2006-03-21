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

public class SchemaBufDbModel extends SqlGenDbModel
{

//Statement st;
	
public SchemaBufDbModel(String table, SchemaBuf buf)
{
	super(table, buf);
}

/** Uses the default table for the Schema in buf. */
public SchemaBufDbModel(SchemaBuf buf)
{
	super(buf.getSchema().getDefaultTable(), buf);
}

public void doSelect(Statement st) throws java.sql.SQLException
{
	getSchemaBuf().clear();
	super.doSelect(st);
}

public SchemaBuf getSchemaBuf()
	{ return (SchemaBuf)getSqlGen(); }

public Schema getSchema()
	{ return getSchemaBuf().getSchema(); }

public void doClear()
	{ ((SchemaBuf)getSqlGen()).clear(); }

// -----------------------------------------------------------
// ===========================================================
/** This should NOT be used by subclasses.  In general, instant update is a property
assigned by enclosing objects --- panels that USE this DbModel.
TODO: Make instant updates delete instantly when user hits "delete". */
public void setInstantUpdate(ActionRunner runner, boolean instantUpdate)
{
	if (instantUpdate) {
		if (instantUpdateListener == null) {
			instantUpdateListener = new InstantUpdateListener(runner);
			getSchemaBuf().addTableModelListener(instantUpdateListener);
		}
	} else {
		if (instantUpdateListener != null) {
			getSchemaBuf().removeTableModelListener(instantUpdateListener);
			instantUpdateListener = null;
		}
	}
}
public boolean isInstantUpdate()
{
	return (instantUpdateListener != null);
}
// ==============================================
private class InstantUpdateListener implements TableModelListener {
//	Statement st;
	ActionRunner runner;
	public InstantUpdateListener(ActionRunner runner)
	{
		this.runner = runner;
	}
	public void tableChanged(final TableModelEvent e) {
System.out.println("InstantUpdateListener.tableChanged()");
		runner.doRun(new StRunnable() {
		public void run(Statement st) throws SQLException {
			switch(e.getType()) {
				// TODO: Update only rows that have changed, don't waste
				// your time on all the other rows!
				case TableModelEvent.UPDATE :
					for (int r=e.getFirstRow(); r <= e.getLastRow(); ++r) {
System.out.println("InstantUpdateListener.doUpdate row = " + r);
						doUpdate(st, r);
					}
				break;
			}
		}});
 	}
}

}
