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
package citibob.swing.typed;

import java.text.DateFormat;
import java.util.Date;
import javax.swing.*;

import citibob.swing.typed.*;
import citibob.sql.*;
import citibob.exception.*;
import citibob.jschema.*;
import citibob.swing.*;
import java.awt.*;

/**
 * Binds a TypedWidget to a RowModel, passing events back and forth.
 * @author  citibob
 */
public class TypedWidgetBinder
implements RowModel.ColListener, java.beans.PropertyChangeListener
{

TableRowModel bufRow;
int colNo;
TypedWidget tw;

// --------------------------------------------------------------------
/** Bind widget and set its type. */
public void bind(TypedWidget tw, SchemaBufRowModel bufRow, String colName, SwingerMap map)
{
	// Set the type
	Schema schema = bufRow.getSchema();
	SqlType sqlType = schema.getCol(schema.findCol(colName)).getType();
	SqlSwinger f = map.getSwinger(sqlType);		// Default ways to render & edit
	tw.setSqlType(f);
	
	bind(tw, bufRow, colName);
}

/** Just bind widget, don't much with its type. */
public void bind(TypedWidget tw, TableRowModel bufRow, String colName)
{
	colNo = bufRow.findColumn(colName);
	
	// Bind as a listener to the RowModel (which fronts a SchemaBuf)...
	this.bufRow = bufRow;
	bufRow.addColListener(colNo, this);

	// Bind as listener to the TypedWidget
	Component c = (Component)tw;
	c.addPropertyChangeListener("value", this);

	/* Now, set the initial value. */
	valueChanged(colNo);
}
// --------------------------------------------------------------------
public void unBind()
{
	Component c = (Component)tw;
	c.removePropertyChangeListener("value", this);
	bufRow.removeColListener(colNo, this);
	
	// Allow for garbage collection
	tw = null;
	bufRow = null;
}
// --------------------------------------------------------------------
boolean valsEqual(Object a, Object b)
{
	if (a == b) return true;
	if (a != null && a.equals(b)) return true;
	return false;
}

// ===============================================================
// Implementation of RowModel.Listener
/** Propagate change in underlying RowModel to widget value. */
public void valueChanged(int col)
{
//System.out.println("valueChanged(" + col + ") = " + bufRow.get(col));
	tw.setValue(bufRow.get(col));
}
public void curRowChanged(int col)
{
	int row = bufRow.getCurRow();
	Component c = (Component)tw;
	c.setEnabled(row != MultiRowModel.NOROW);
	tw.setValue(row == MultiRowModel.NOROW ? null : bufRow.get(col));
}
// ===============================================================
// Implementation of PropertyChangeListener
public void propertyChange(java.beans.PropertyChangeEvent evt)
{
	// Stop infinite loop of property change events between the two objects.
	if (valsEqual(evt.getNewValue(), evt.getOldValue())) return;
	// OK, propagate it down to the row.
	bufRow.set(colNo, evt.getNewValue());
}
// ===============================================================
}
