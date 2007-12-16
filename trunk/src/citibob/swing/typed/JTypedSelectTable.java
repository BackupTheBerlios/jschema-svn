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
 * JTypedSelectTable.java
 *
 * Created on June 8, 2007, 8:45 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.swing.typed;

import citibob.swing.*;
import citibob.swing.table.*;
import citibob.types.JType;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * Allows user to select rows in a table, returns one column as the value of this widget.
 * @author citibob
 */
public class JTypedSelectTable extends JTypeColTable
implements TypedWidget, ListSelectionListener
{

int valueColU = 0;		// This column in the selected row will be returned as the value
Object val = null;
protected boolean inSelect;		// Are we in the middle of having the user change the value?

/** Are we in the middle of having the user change the value? */
public boolean isInSelect() { return inSelect; }

public JTypedSelectTable()
{
	super();
	setHighlightMouseover(true);
}

/** Controls which column in selected row will be returned as the value */
public void setValueColU(String name)
	{ valueColU = getModelU().findColumn(name); }
	
/** Returns last legal value of the widget.  Same as method in JFormattedTextField */
public Object getValue()
{ return val; }

///** Returns the row a value is found on (or -1 if no such row) */
//protected int rowOfValue(Object o)
//{
//	for (int i=0; i<getModel().getRowCount(); ++i) {
//		Object val = getModelU().getValueAt(i, valueColU);
//		boolean eq = (val == null ? o == null : o.equals(val));
//		if (eq) return i;
//	}
//	return -1;
//}

/** Sets the value.  Same as method in JFormattedTextField.  Fires a
 * propertyChangeEvent("value") when calling setValue() changes the value. */
public void setValue(Object val)
{
	super.setSelectedRowU(val, valueColU);
//	if (o == null) {
//		getSelectionModel().clearSelection();
//		return;
//	}
//	int row = rowOfValue(o);
//	if (row >= 0) {
//		this.getSelectionModel().setSelectionInterval(row,row);
//		return;
//	} else {
//		getSelectionModel().clearSelection();
//	}
}




/** From TableCellEditor (in case this is being used in a TableCellEditor):
 * Tells the editor to stop editing and accept any partially edited value
 * as the value of the editor. The editor returns false if editing was not
 * stopped; this is useful for editors that validate and can not accept
 * invalid entries. */
public boolean stopEditing() {return true;}

/** Is this object an instance of the class available for this widget?
 * If so, then setValue() will work.  See SqlType.. */
public boolean isInstance(Object o)
{
	JType type = ((JTypeTableModel)getModelU()).getJType(0,valueColU);
	return type.isInstance(o);
}

/** Set up widget to edit a specific SqlType.  Note that this widget does not
 have to be able to edit ALL SqlTypes... it can throw a ClassCastException
 if asked to edit a SqlType it doesn't like. */
public void setJType(citibob.swing.typed.Swinger f) throws ClassCastException {}

// ---------------------------------------------------
String colName;
/** Row (if any) in a RowModel we will bind this to at runtime. */
public String getColName() { return colName; }
/** Row (if any) in a RowModel we will bind this to at runtime. */
public void setColName(String col) { colName = col; }
//public Object clone() throws CloneNotSupportedException { return super.clone(); }
// ---------------------------------------------------

/** Non-standard way to access any column of the selected row. */
public Object getValue(int colU)
{
	int selRow = this.getSelectedRow();
	if (selRow < 0) return null;
	return getModelU().getValueAt(selRow, colU);
}
public Object getValue(String colName)
{
	int colU = getModelU().findColumn(colName);
	return getValue(colU);
}
// ================================================================
// ListSelectionListener
/** JTable implements ListSelectionListener.  This method overrides that implementation. */
public void valueChanged(ListSelectionEvent e) {
	super.valueChanged(e);
	inSelect = true;
	Object oldval = val;
	
	int selRow = this.getSelectedRow();
	if (selRow < 0) val = null;
	else val = getModelU().getValueAt(selRow, valueColU);
	firePropertyChange("value", oldval, val);
	inSelect = false;
}

	
}
