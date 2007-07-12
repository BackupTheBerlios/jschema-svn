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
boolean isHighlightMouseover = true;		// SHould we highlight rows when mousing over?

public JTypedSelectTable()
{
	super();
	this.addMouseListener(new MyMouseAdapter());
	this.addMouseMotionListener(new MyMouseMotionAdapter());
}

/** Controls which column in selected row will be returned as the value */
public void setValueColU(String name)
	{ valueColU = getModelU().findColumn(name); }
	
/** Returns last legal value of the widget.  Same as method in JFormattedTextField */
public Object getValue()
{ return val; }

/** Sets the value.  Same as method in JFormattedTextField.  Fires a
 * propertyChangeEvent("value") when calling setValue() changes the value. */
public void setValue(Object o)
{
	if (o == null) {
		getSelectionModel().clearSelection();
		return;
	}
	for (int i=0; i<getModel().getRowCount(); ++i) {
		Object val = getModelU().getValueAt(i, valueColU);
		boolean eq = (val == null ? o == null : o.equals(val));
		if (eq) {
			this.getSelectionModel().setSelectionInterval(i,i);
			return;
		}
	}
	getSelectionModel().clearSelection();
}

public void setHighlightMouseover(boolean b) { isHighlightMouseover = b; }
public boolean getHighlightMouseover(boolean b) { return isHighlightMouseover; }

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
// ================================================================
// Stuff to highlight on mouseover
//Color mouseoverColor = new Color(0,255,0);
//Color defaultColor = new Color(255,255,255);
Color cTextHighlight = UIManager.getDefaults().getColor("textHighlight");
Color cText = UIManager.getDefaults().getColor("text");
public Component prepareRenderer(TableCellRenderer renderer, int row, int col)
{
	Component c = super.prepareRenderer(renderer, row, col);
	if (row == mouseRow) {
		c.setBackground(cTextHighlight);
	} else {
		c.setBackground(cText);
	}
	return c;
}
int mouseRow = -1;		// Row the mouse is currently hovering over.
class MyMouseMotionAdapter extends MouseMotionAdapter {
public void mouseMoved(MouseEvent e) {
	if (!isHighlightMouseover) return;
	
	JTable aTable =  (JTable)e.getSource();
	int oldRow = mouseRow;
	mouseRow = aTable.rowAtPoint(e.getPoint());
//	itsColumn = aTable.columnAtPoint(e.getPoint());
	if (oldRow != mouseRow) aTable.repaint();
}}
class MyMouseAdapter extends MouseAdapter {
public void mouseExited(MouseEvent e) {
	if (!isHighlightMouseover) return;
	
	JTable aTable =  (JTable)e.getSource();
	mouseRow = -1;
	aTable.repaint();
}}

// ================================================================
// ListSelectionListener
//class SharedListSelectionHandler implements ListSelectionListener {
public void valueChanged(ListSelectionEvent e) {
//	ListSelectionModel lsm = (ListSelectionModel)e.getSource();
//
//	int firstIndex = e.getFirstIndex();
//	int lastIndex = e.getLastIndex();
//	boolean isAdjusting = e.getValueIsAdjusting();

	Object oldval = val;
	
	int selRow = this.getSelectedRow();
	if (selRow < 0) val = null;
	else val = getModelU().getValueAt(selRow, valueColU);
//	if (oldval == val) return;		// Try to filter out at least a few spurious events.
	firePropertyChange("value", oldval, val);
}

	
}
