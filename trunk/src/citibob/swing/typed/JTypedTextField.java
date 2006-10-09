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

import java.text.DateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import citibob.exception.*;
import citibob.sql.*;
import citibob.swing.typed.SwingerMap;
import java.beans.*;
//import citibob.sql.JType;

/**
 *
 * @author  citibob
 */
public class JTypedTextField
extends JFormattedTextField
implements TypedWidget, KeyListener {

/** Our best guess of the class this takes. */
//Class objClass = null;
JType jType;	

public JTypedTextField()
{
	super();
	addKeyListener(this);
}
public JTypedTextField(Swinger f)
{
	this();
	setJType(f);
}

// --------------------------------------------------------------
public void setJType(Swinger f)
{
//System.out.println("JTypedTextField.setJType: " + f + ", " + f.getJType());
	jType = f.getJType();
	super.setFormatterFactory(f.newFormatterFactory());
}
public void setJType(JType jt, AbstractFormatterFactory ffactory)
{
	jType = jt;
	super.setFormatterFactory(ffactory);	
}
public void setJType(Class klass, AbstractFormatterFactory ffactory)
{
	jType = new JavaJType(klass);
	super.setFormatterFactory(ffactory);	
}
public void setJType(JType jt, JFormattedTextField.AbstractFormatter defaultFormatter)
{
	setJType(jt, new DefaultFormatterFactory(defaultFormatter));
}
public void setJType(Class klass, JFormattedTextField.AbstractFormatter defaultFormatter)
{
	setJType(klass, new DefaultFormatterFactory(defaultFormatter));
}
// --------------------------------------------------------------

public boolean isInstance(Object o)
{
	return jType.isInstance(o);
}
public boolean stopEditing()
{
//System.out.println("stopEditing: value = " + super.getText() + " --> " + super.getValue());
	try {
		super.commitEdit();
		return true;
	} catch(java.text.ParseException e) {
		return false;
	}
}
// --------------------------------------------------------------
public Object getValue()
{
	String text = super.getText();
	Object o = super.getValue();
//	Class oclass = (o == null ? null : o.getClass());
//	System.out.println("JTypedTextField returning value: " + text + " --> " + o + "(" + oclass + ")");
	return o;
}
// --------------------------------------------------------------
//public Class getObjClass()
//	{ return objClass; }
//private void resetValue()
//{
//	setValue(getValue());	// Sets text in accordance with last good value
//}
// JFormatterTextField already calls PropertyChangeEvent
//public void setValue(Object val)
//{
//	super.setValue(val);
//}
// ---------------------------------------------------
String colName;
/** Row (if any) in a RowModel we will bind this to at runtime. */
public String getColName() { return colName; }
/** Row (if any) in a RowModel we will bind this to at runtime. */
public void setColName(String col) { colName = col; }
public Object clone() throws CloneNotSupportedException { return super.clone(); }
// ---------------------------------------------------
// ===================== KeyListener =====================
public void keyTyped(KeyEvent e) {
	if (e.getKeyChar() == '\033') setValue(getValue());		// revert
//	if (e.getKeyChar() == '\r') setLatestValue();	// Submit current value.
}
public void keyReleased(KeyEvent e) {
	if (e.getKeyCode() == KeyEvent.VK_ESCAPE) setValue(getValue());
}
public void keyPressed(KeyEvent e) {}

}
