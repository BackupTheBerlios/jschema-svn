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
/*
 * JDate.java
 *
 * Created on May 14, 2003, 8:52 PM
 */

package citibob.swing.typed;

import javax.swing.*;
import java.beans.*;
import citibob.sql.*;

/**
 * A boolean checkbox that allows Y/N/null.
 * @author  citibob
 */
public class JBoolButton extends JButton implements TypedWidget {

//PropertyChangeSupport support = new PropertyChangeSupport(this);
Boolean val;

String trueLabel = "Y";
String falseLabel = "N";
JType jType;

/** Returns last legal value of the widget.  Same as method in JFormattedTextField */
public Object getValue()
{
	return val;
}

/** Sets the value.  Same as method in JFormattedTextField.  Fires a
 * propertyChangeEvent("value") when calling setValue() changes the value. */
public void setValue(Object d)
{
	if (d != null && d.getClass() != Boolean.class)
		throw new ClassCastException("Expected Boolean");
	Object oldVal = val;
	val = (Boolean)d;
	setText(val == null ? "-" : val.booleanValue() ? trueLabel : falseLabel);
	this.firePropertyChange("value", oldVal, val);
}

/** Is this object an instance of the class available for this widget?
 * If so, then setValue() will work.  See JType.. */
public boolean isInstance(Object o)
{
	if (o == null) return jType.isInstance(null);
	return (o instanceof Boolean);
}

/** Set up widget to edit a specific JType.  Note that this widget does not
 have to be able to edit ALL JTypes... it can throw a ClassCastException
 if asked to edit a JType it doesn't like. */
public void setJType(citibob.swing.typed.JTypeSwinger f) throws ClassCastException
{
	jType = f.getJType();
	Class klass = f.getJType().getObjClass();
	if (!(Boolean.class.isInstance(klass)))
		throw new ClassCastException("Expected Boolean type, got " + klass);
}

// ---------------------------------------------------
String colName;
/** Row (if any) in a RowModel we will bind this to at runtime. */
public String getColName() { return colName; }
/** Row (if any) in a RowModel we will bind this to at runtime. */
public void setColName(String col) { colName = col; }
public boolean stopEditing() { return true; }
public Object clone() throws CloneNotSupportedException { return super.clone(); }
// ---------------------------------------------------

public JBoolButton()
{
	super();
	setText("-");
	setMargin(new java.awt.Insets(2, 2, 2, 2));
	addActionListener(new java.awt.event.ActionListener() {
	public void actionPerformed(java.awt.event.ActionEvent evt) {
		if (val == null) setValue(Boolean.TRUE);
		else if (!val.booleanValue()) setValue((jType.isInstance(null) ? null : Boolean.TRUE));
		else setValue(Boolean.FALSE);
	}});
}
public void setValue(boolean b)
	{ setValue(b ? Boolean.TRUE : Boolean.FALSE); }

}
