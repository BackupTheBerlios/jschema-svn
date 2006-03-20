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
import java.awt.*;
import java.awt.event.*;
import citibob.exception.*;
import citibob.sql.*;
import citibob.swing.typed.SwingerMap;

/**
 *
 * @author  citibob
 */
public class JTypedTextField
extends JFormattedTextField
implements TypedWidget, KeyListener {

/** Our best guess of the class this takes. */
//Class objClass = null;
SqlType sqlType;	

public JTypedTextField()
{
	super();
	addKeyListener(this);
}
public JTypedTextField(SqlSwinger f)
{
	this();
	setSqlType(f);
}

// --------------------------------------------------------------
public void setSqlType(SqlSwinger f)
{
	sqlType = f.getSqlType();
	super.setFormatterFactory(f.newFormatterFactory());
}
public boolean isInstance(Object o)
{
	return sqlType.isInstance(o);
}
public boolean stopEditing()
{
	try {
		super.commitEdit();
		return true;
	} catch(java.text.ParseException e) {
		return false;
	}
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
	if (e.getKeyChar() == '\033') setValue(getValue());
//	if (e.getKeyChar() == '\r') setLatestValue();	// Submit current value.
}
public void keyReleased(KeyEvent e) {
	if (e.getKeyCode() == KeyEvent.VK_ESCAPE) setValue(getValue());
}
public void keyPressed(KeyEvent e) {}

}
