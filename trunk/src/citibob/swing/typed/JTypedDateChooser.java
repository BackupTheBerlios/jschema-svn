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
import citibob.swing.calendar.*;
import citibob.sql.*;

/**
 * TODO: This class need sthe once-over!
 * @author  citibob
 * Doesn't hold NULL values.
 */
public class JTypedDateChooser extends JDateChooser implements TypedWidget {

SqlDateType jType;
	
/** Returns last legal value of the widget.  Same as method in JFormattedTextField */
public Object getValue()
{
System.out.println("JTDC.getValue = " + getModel().getTime());
	return getModel().getTime();
}

/** Sets the value.  Same as method in JFormattedTextField.  Fires a
 * propertyChangeEvent("value") when calling setValue() changes the value. */
public void setValue(Object d)
{
	java.util.Date dt;
	if (d == null) {
		// Set default value to current time
		setValue(new java.util.Date());
		dt = null;
	} else {
		// Truncate the incoming date to fit this kind of date.
		if (!(d instanceof java.util.Date)) throw new ClassCastException("Bad type " + d);
		dt = jType.truncate((java.util.Date)d);
	}
	
	if (!isInstance(dt)) throw new ClassCastException("Bad type, but cast right.");
System.out.println("JTDC: Setting date to " + dt );
	// TODO: Bug in JDateChooser doesn't work well with null dates.
	// It gets confused when embedded in a table.
//		if (val == null) val = new Date();
	getModel().setTime(dt);
}

/** Is this object an instance of the class available for this widget?
 * If so, then setValue() will work.  See JType.. */
public boolean isInstance(Object o)
{
	return jType.isInstance(o);
}

/** Set up widget to edit a specific JType.  Note that this widget does not
 have to be able to edit ALL JTypes... it can throw a ClassCastException
 if asked to edit a JType it doesn't like. */
public void setJType(citibob.swing.typed.JTypeSwinger f) throws ClassCastException
{
	jType = (SqlDateType)f.getJType();

	// Set up the type properly
	Class klass = jType.getObjClass();
	if (!(java.util.Date.class.isAssignableFrom(klass)))
		throw new ClassCastException("Expected Date type, got " + klass);
	CalModel mcal = new CalModel(jType.isInstance(null));
	super.setModel(mcal);
	//super.setNullable(jType.isInstance(null));
}
public boolean stopEditing()
{
	getModel().useTmpDay();
	return true;
}
// ---------------------------------------------------
String colName;
/** Row (if any) in a RowModel we will bind this to at runtime. */
public String getColName() { return colName; }
/** Row (if any) in a RowModel we will bind this to at runtime. */
public void setColName(String col) { colName = col; }
public Object clone() throws CloneNotSupportedException { return super.clone(); }
// ---------------------------------------------------

}
