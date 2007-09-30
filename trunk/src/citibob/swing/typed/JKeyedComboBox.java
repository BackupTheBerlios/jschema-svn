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
/* This works if there's a null in kmodel!! */

package citibob.swing.typed;
//import java.text.DateFormat;
//import java.util.Date;
import javax.swing.*;
import java.util.*;
import java.sql.*;
import citibob.util.KeyedModel;
import java.awt.*;
import java.awt.event.*;
import citibob.sql.*;

/**
 *
 * @author  citibob
Used to make a combo box that returns one of a fixed set of integer values.  The ComboBox displays a list of describtive strings, one per integer value to be returned.
 */
public class JKeyedComboBox extends JComboBox implements TypedWidget {
KeyedModel kmodel;
KeyedFormatter kformatter;
Object value;
//JType jType;

static final Object NULL = new Object();
// ------------------------------------------------------
public JKeyedComboBox()
{
	setRenderer(new MyRenderer());
	
	// Called when user changes selection
	addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent evt) {
		Object o = getSelectedItem();
		setValue(o == NULL ? null : o);
//		// No need for setValue(), since value === getSelectedItem()
//		this.firePropertyChange("value", null, getValue());
	}});
}
public JKeyedComboBox(KeyedModel kmodel)
{
	this();
	setKeyedModel(kmodel);
}
// --------------------------------------------------------------
public void setKeyedModel(KeyedModel kmodel)
{
	this.kmodel = kmodel;
	kformatter = new KeyedFormatter(kmodel);
	Vector keyList = kmodel.getKeyList();
	// Handle null specially if it is in our key list.
	if (kmodel.get(null) != null) {
		keyList = (Vector)keyList.clone();
		for (int i=0; i<keyList.size(); ++i)
			if (keyList.get(i) == null) keyList.set(i, NULL);
	}
	DefaultComboBoxModel cmodel = new DefaultComboBoxModel(keyList);
	super.setModel(cmodel);
	if (keyList.size() > 0) this.setSelectedIndex(0);	// Make sure getValue() returns something
}
public KeyedModel getKeyedModel()
{ return kmodel; }
//public void setJType(Swinger f)
//{
//	JType jType = f.getJType();
//	if (!(jType instanceof JEnum)) 
//		throw new ClassCastException("Expected Enum type, got " + jType);
//	JEnum etype = (JEnum)jType;
//	setKeyedModel(etype.getKeyedModel());
//}
// --------------------------------------------------------------
public boolean isInstance(Object o)
{
	return kmodel.getItemMap().containsKey(o);
}
// --------------------------------------------------------------
String colName;
/** Row (if any) in a RowModel we will bind this to at runtime. */
public String getColName() { return colName; }
/** Row (if any) in a RowModel we will bind this to at runtime. */
public void setColName(String col) { colName = col; }
public boolean stopEditing() { return true; }
public Object clone() throws CloneNotSupportedException { return super.clone(); }

// ---------------------------------------------------


//// -------------------------------------------------------
//public void setSelectedItem(Object o)
//{
//	System.out.println("setSelectedItem("+ o + ")");
//	super.setSelectedItem(o);
////	System.out.println("getSelectedItem = " + getSelectedItem());
//}
//public Object getSelectedItem()
//{
//	Object ret = super.getSelectedItem();
////	System.out.println("getSelectedItem = " + ret);
//	return ret;
//}
// ============================================================
// TypedWidget stuff
public void setValue(Object d)
{
//System.out.println("JKeyedComboBox.setValue: " + d);
//	// HACK: Handle it if 
//	if (d instanceof Short) d = new Integer(((Short)d).intValue());
	Object oldVal = value;
	
//	// No event if we haven't changed... (stop infinite recursion too)
//	if (oldVal == d) return;
//	if (oldVal != null && d != null && d.equals(oldVal)) return;
	value = d;
	
	setSelectedItem(d == null ? NULL : d);
	this.firePropertyChange("value", oldVal, d);
}
//public void setValue(int i)
//{
//	Integer ii = new Integer(i);
//	setValue(ii);
//}
	
public Object getValue()
{
	return value;
//	Object o = getSelectedItem();
//	return o;
}

// ==============================================================
class MyRenderer 
extends DefaultListCellRenderer {

    public Component getListCellRendererComponent(
	JList list, Object value, int index,
	boolean isSelected, boolean cellHasFocus) {
//if (value == null || value == NULL) {
//	System.out.println("hoi");
//}
		if (kformatter != null) value = kformatter.valueToString(
			value == NULL ? null : value);
		return super.getListCellRendererComponent(
			list, value == NULL ? null : value, index,isSelected,cellHasFocus);
    }
}

}
