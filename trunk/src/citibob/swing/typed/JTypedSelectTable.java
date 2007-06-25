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
import javax.swing.table.*;
import javax.swing.*;

/**
 * Allows user to select rows in a table, returns one column as the value of this widget.
 * @author citibob
 */
public class JTypedSelectTable extends JTypeColTable
implements TypedWidget
{

int valueColU = 0;		// This column in the selected row will be returned as the value

/** Controls which column in selected row will be returned as the value */
public void setValueColU(String name)
	{ valueColU = getModelU().findColumn(name); }
	
/** Returns last legal value of the widget.  Same as method in JFormattedTextField */
public Object getValue()
{
	int selRow = this.getSelectedRow();
	if (selRow < 0) return null;
	return getModelU().getValueAt(selRow, valueColU);
}

/** Sets the value.  Same as method in JFormattedTextField.  Fires a
 * propertyChangeEvent("value") when calling setValue() changes the value. */
public void setValue(Object o)
{
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

	
}
