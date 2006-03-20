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
 * KeyedButtonGroup.java
 *
 * Created on May 9, 2005, 10:46 PM
 */

package citibob.swing.typed;

import citibob.jschema.*;
import java.util.*;
import javax.swing.*;
import java.beans.*;

/**
 *
 * @author citibob
 */
public class KeyedButtonGroup
extends ButtonGroup implements TypedWidget {

PropertyChangeSupport support = new PropertyChangeSupport(this);

protected Map map;		// key -> AbstractButton
Map imap;		// ButtonModel -> key
Class objClass = null;
Object val = null;

/** Returns last legal value of the widget.  Same as method in JFormattedTextField */
public Object getValue()
{ return val; }

/** Sets the value.  Same as method in JFormattedTextField.  Fires a
 * propertyChangeEvent("value") when calling setValue() changes the value. */
public void setValue(Object o)
{
	Object oldVal = val;
	val = o;
	AbstractButton b = (AbstractButton)map.get(o);
	setSelected(b.getModel(), true);
	support.firePropertyChange("value", oldVal, val);
}

/** Is this object an instance of the class available for this widget?
 * If so, then setValue() will work.  See SqlType.. */
public boolean isInstance(Object o)
{
	return map.containsKey(o);
}

/** Set up widget to edit a specific SqlType.  Note that this widget does not
 have to be able to edit ALL SqlTypes... it can throw a ClassCastException
 if asked to edit a SqlType it doesn't like. */
public void setSqlType(citibob.swing.typed.SqlSwinger f) throws ClassCastException
{
	// Could be anything...
//	SqlType sqlType = f.getSqlType();
//	if (!(sqlType instanceof SqlEnum)) 
//		throw new ClassCastException("Expected Enum type, got " + sqlType);
//	SqlEnum etype = (SqlEnum)sqlType;
//	setKeyedModel(etype.getKeyedModel());
}

// ---------------------------------------------------
String colName;
/** Row (if any) in a RowModel we will bind this to at runtime. */
public String getColName() { return colName; }
/** Row (if any) in a RowModel we will bind this to at runtime. */
public void setColName(String col) { colName = col; }
public boolean stopEditing() { return true; }

// ---------------------------------------------------
/** Creates a new instance of KeyedButtonGroup */
public KeyedButtonGroup()
{
	map = new HashMap();
	imap = new HashMap();
}
// -------------------------------------------------------------
public void add(Object key, AbstractButton b)
{
	super.add(b);
	ButtonModel bm = b.getModel();
	map.put(key, b);
	imap.put(bm, key);
	
	// Check the type of the item we just added.
	if (key != null) {
		Class c = key.getClass();
		if (objClass == null) {
			objClass = c;
		} else {
			if (objClass != c) throw new ClassCastException(
				"KeyedButtonGroup.add() received object of class "
				+ c + ", expected class " + objClass);
		}
	}
}

public AbstractButton remove(Object key)
{
	Object o = map.remove(key);
	if (o == null) return null;
	AbstractButton b = (AbstractButton)o;
	imap.remove(b.getModel());
	super.remove(b);
	return b;
}
// -------------------------------------------------------------

// =====================================================
// TypedWidget

protected Object getValue(AbstractButton b)
{
	ButtonModel m = b.getModel();
	Object ret = imap.get(m);
//System.out.println("getValue: " + b + " returns\n '" + ret + "'");
	return ret;
}

protected void setButton(AbstractButton b)
{
	setSelected(b.getModel(), true);
}

}

