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

/**
 *
 * @author citibob
 */
public class KeyedButtonGroup
extends ButtonGroup implements TypedWidget {

protected Map map;		// key -> AbstractButton
Map imap;		// ButtonModel -> key
Class objClass = null;
	
ObjModel model;
public ObjModel getObjModel() { return model; }
public void setObjModel(ObjModel m) { model = m; }

/** Creates a new instance of KeyedButtonGroup */
public KeyedButtonGroup()
{
	model = new DefaultObjModel();
	map = new HashMap();
	imap = new HashMap();
}

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

// =====================================================
// TypedWidget

protected Object getValue(AbstractButton b)
{
	ButtonModel m = b.getModel();
	Object ret = imap.get(m);
//System.out.println("getValue: " + b + " returns\n '" + ret + "'");
	return ret;
}

/** Returns current value in the widget. */
public Object getValue()
{
//	ButtonModel m = getSelection();
//	return map.get(m);
	return model.getValue();
}
/** Sets the value.  Returns a ClassCastException */
public void setValue(Object o)
{
	model.setValue(o);
	AbstractButton b = (AbstractButton)map.get(o);
	setSelected(b.getModel(), true);
}

protected void setButton(AbstractButton b)
{
	setSelected(b.getModel(), true);
}
/** Returns type of object this widget edits. */
public Class getObjClass()
	{ return objClass; }
public void setObjClass(Class c)
	{ objClass = c; }
public void resetValue() {}
public void setLatestValue() {}
public boolean isValueValid() { return true; }
}

