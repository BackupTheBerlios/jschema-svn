/*
Offstage CRM: Enterprise Database for Arts Organizations
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
/*
 *
 * Created on March 19, 2005, 12:00 AM
 */

package citibob.jschema.gui;

import javax.swing.table.*;
import javax.swing.*;
import javax.swing.event.*;
import java.sql.*;
import citibob.jschema.*;
import citibob.swing.table.*;
import citibob.swing.typed.*;
import citibob.swing.*;

/**
 * High-level table: contains a status field and a bunch of other stuff.
 * @author citibob
 */
public class StatusTable extends CitibobJTable {

/** Creates a new instance of GroupsTable */
public StatusTable() {
	setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
}

//		new String[] {"S", "Type", "Phone"},
//		new String[] {"__status__", "groupid", "phone"});

/** Convenience function, to be used by subclasses:
 * finds the column number based on a column name, not display name. */
public int findUnderlyingCol(String s)
{
	return ((ColPermuteTableModel)getModel()).findUnderlyingCol(s);
}


/** @param schemaBuf Underling data buffer to use
 * @param typeCol Name of type column in the schema
 * @param xColNames Columns (other than type and status) from schema to display
 */
public void initRuntime(SchemaBuf schemaBuf,
		String[] xColNames, String[] xSColMap)
//throws java.sql.SQLException
{
	
	// Prepend 2 columns to column list
	String[] colNames = new String[xColNames.length + 1];
	String[] sColMap = new String[xSColMap.length + 1];
	colNames[0] = "Status";
	sColMap[0] = "__status__";
	for (int i=0; i<xColNames.length; ++i) {
		colNames[i+2] = xColNames[i];
		sColMap[i+2] = xSColMap[i];
	}

	// Set it up
	StatusSchemaBuf ssb = new StatusSchemaBuf(schemaBuf);
	ColPermuteTableModel model = new ColPermuteTableModel(
		ssb, colNames, sColMap);
	setModel(model);
	
	setRenderEdit(findUnderlyingCol("__status__"),
		new citibob.swing.table.StatusRenderEdit());
}

	
}
