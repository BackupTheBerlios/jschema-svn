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
 * JDate.java
 *
 * Created on May 14, 2003, 8:52 PM
 */

package citibob.swing.typed;

import citibob.text.KeyedSFormat;
import citibob.text.StringSFormat;
import citibob.types.JType;
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
import citibob.text.*;
import citibob.types.*;

/**
 *
 * @author  citibob
 */
public class JTypedLabel
extends JLabel
implements TextTypedWidget {

/** Our best guess of the class this takes. */
//Class objClass = null;
JType jType;	
SFormat sformat;
Object val;
boolean useToolTips = true;		// Should we set the tooltip to the same as the label text?

public JTypedLabel()
{
	super();
}
public JTypedLabel(String s)
{
	sformat = new StringSFormat();
	setValue(s);
}
	
// --------------------------------------------------------------
//public void setJType(Swinger f)
//{
////System.out.println("JTypedTextField.setJType: " + f + ", " + f.getJType());
//	jType = f.getJType();
//	formatter = f.newFormatterFactory().getDefaultFormatter();
//}
public void setJType(JType jt, SFormat sformat)
{
	this.jType = jt;
	this.sformat = sformat;
}

/** Convenience function.
 @param nullText String to use for null value, or else <null> if this is not nullable. */
public void setJType(citibob.types.KeyedModel kmodel, String nullText)
{
	JEnum tt = new JEnum(kmodel);
	setJType(tt, new KeyedSFormat(kmodel, nullText));
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
	if (val == o && val != null) return;		// This was called multiple times; ignore
	Object oldVal = val;
	val = o;
	if (val == null) setText(sformat.getNullText());
	else {
//if (formatter == null) {
//	System.out.println("hoi formatter is null");
//}
		try {
//System.out.println(getColName());
			String text = sformat.valueToString(val);
			setText(text);
			if (useToolTips) setToolTipText(text);
		} catch(java.text.ParseException e) {
			setText("<parseException>");
		}
	}
	this.firePropertyChange("value", oldVal, val);
}
public Object getValue()
{
	return val;
}
// --------------------------------------------------------------
String colName;
/** Row (if any) in a RowModel we will bind this to at runtime. */
public String getColName() { return colName; }
/** Row (if any) in a RowModel we will bind this to at runtime. */
public void setColName(String col) { colName = col; }
public Object clone() throws CloneNotSupportedException { return super.clone(); }
// ---------------------------------------------------
}
