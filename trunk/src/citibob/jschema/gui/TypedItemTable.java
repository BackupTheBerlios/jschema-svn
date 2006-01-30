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
 * High-level table: contains a type field, a status field and a bunch of other stuff.
 * @author citibob
 */
public class TypedItemTable extends CitibobJTable {

/** Creates a new instance of GroupsTable */
public TypedItemTable() {
	setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
}

//		new String[] {"S", "Type", "Phone"},
//		new String[] {"__status__", "groupid", "phone"});

/** @param schemaBuf Underling data buffer to use
 * @param typeCol Name of type column in the schema
 * @param xColNames Columns (other than type and status) from schema to display
 */
public void initRuntime(SchemaBuf schemaBuf,
		String typeCol, KeyedModel typeKeyedModel,
		String[] xColNames, String[] xSColMap)
//throws java.sql.SQLException
{
	
	// Prepend 2 columns to column list
	String[] colNames = new String[xColNames.length + 2];
	String[] sColMap = new String[xSColMap.length + 2];
	colNames[0] = "Status";
	sColMap[0] = "__status__";
	colNames[1] = "Type";
	sColMap[1] = typeCol;
	for (int i=0; i<xColNames.length; ++i) {
		colNames[i+2] = xColNames[i];
		sColMap[i+2] = xSColMap[i];
System.out.println("   column " + colNames[i+2]);
	}

	// Set it up
	StatusSchemaBuf ssb = new StatusSchemaBuf(schemaBuf);
	ColPermuteTableModel model = new ColPermuteTableModel(
		ssb, colNames, sColMap);
System.out.println("model = " + model);
	setModel(model);
	
	setRenderEdit(model.findColUnderlying(typeCol),
		new KeyedRenderEdit(typeKeyedModel));
	setRenderEdit(model.findColUnderlying("__status__"),
		new citibob.jschema.swing.StatusRenderEdit());
}

	
}
