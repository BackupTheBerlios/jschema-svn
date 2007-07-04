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
public class JTypedLabel
extends JLabel
implements TypedWidget {

/** Our best guess of the class this takes. */
//Class objClass = null;
JType jType;	
JFormattedTextField.AbstractFormatter formatter;
Object val;
String nullText = "";
boolean useToolTips = true;		// Should we set the tooltip to the same as the label text?

public JTypedLabel()
{
	super();
}
public JTypedLabel(Swinger f)
{
	this();
	setJType(f);
}
public void setNullText(String s)
	{ nullText = s; }
public String getNullText() { return nullText; }
// --------------------------------------------------------------
public void setJType(Swinger f)
{
//System.out.println("JTypedTextField.setJType: " + f + ", " + f.getJType());
	jType = f.getJType();
	formatter = f.newFormatterFactory().getDefaultFormatter();
}
public void setJType(JType jt, JFormattedTextField.AbstractFormatter formatter)
{
	jType = jt;
	this.formatter = formatter;
}
/** Convenience function.
 @param nullText String to use for null value, or else <null> if this is not nullable. */
public void setJType(citibob.util.KeyedModel kmodel, String nullText)
{
	setJType(new SqlEnumSwinger(new SqlEnum(kmodel, nullText)));
}


// --------------------------------------------------------------

public boolean isInstance(Object o)
{
	return jType.isInstance(o);
}
public boolean stopEditing()
{  return true; }
// --------------------------------------------------------------
public void setValue(Object o)
{
	if (val == o) return;		// This was called multiple times; ignore
	val = o;
	if (val == null) setText(nullText);
	else {
		try {
			String text = formatter.valueToString(val);
			setText(text);
			if (useToolTips) setToolTipText(text);
		} catch(java.text.ParseException e) {
			setText("<parseException>");
		}
	}
}
public Object getValue()
{ return val; }
// --------------------------------------------------------------
String colName;
/** Row (if any) in a RowModel we will bind this to at runtime. */
public String getColName() { return colName; }
/** Row (if any) in a RowModel we will bind this to at runtime. */
public void setColName(String col) { colName = col; }
public Object clone() throws CloneNotSupportedException { return super.clone(); }
// ---------------------------------------------------
}
