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
import java.beans.*;

/**
 * TODO: This class need sthe once-over!
 * @author  citibob
 * Doesn't hold NULL values.
 */
public class JTypedDateChooser extends JDateChooser implements TypedWidget {

SqlDateType jType;
//PropertyChangeSupport support = new PropertyChangeSupport(this);

/** Returns last legal value of the widget.  Same as method in JFormattedTextField */
public Object getValue()
{
//System.out.println("JTDC.getValue = " + getModel().getTime());
	return getModel().getTime();
}

/** Sets the value.  Same as method in JFormattedTextField.  Fires a
 * propertyChangeEvent("value") when calling setValue() changes the value. */
public void setValue(Object d)
{
	if (!isInstance(d)) throw new ClassCastException("Bad type " + d);
	java.util.Date dt = (d == null ? null :  jType.truncate((java.util.Date)d));
	
System.out.println("JTDC: Setting date to " + dt );
	java.util.Date oldDt = getModel().getTime();
	getModel().setTime(dt);
	//support.firePropertyChange("value", oldDt, dt);
	// calChanged() will be called below...
}

/** Overrides from JDateChooser to fire propertychangedevent... */
public void calChanged()
{
//System.out.println("calChanged to: " + getValue());
	super.calChanged();
//System.out.println("Widget firing propertyChange: " + this);
	firePropertyChange("value", null, getValue());
}

//static java.util.Date oldDt = new java.util.Date();

/** Overrides from JDateChooser to fire propertychangedevent... */
public void nullChanged() {
	super.nullChanged();
	firePropertyChange("value", null, getValue());
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
System.out.println("stopEditing: value = " + getValue());
//	getModel().useTmpDay();
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
///** Implemented in java.awt.Component --- property will be "value" */
//public void addPropertyChangeListener(String property, java.beans.PropertyChangeListener listener)
//{
//	support.addPropertyChangeListener(listener);
//}
///** Implemented in java.awt.Component --- property will be "value"  */
//public void removePropertyChangeListener(String property, java.beans.PropertyChangeListener listener)
//{
//	support.removePropertyChangeListener(listener);
//}

}
