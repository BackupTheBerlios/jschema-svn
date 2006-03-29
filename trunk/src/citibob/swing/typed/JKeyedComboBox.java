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
//import java.text.DateFormat;
//import java.util.Date;
import javax.swing.*;
import java.util.*;
import java.sql.*;
import citibob.sql.KeyedModel;
import java.awt.*;
import citibob.sql.*;

/**
 *
 * @author  citibob
Used to make a combo box that returns one of a fixed set of integer values.  The ComboBox displays a list of describtive strings, one per integer value to be returned.
 */
public class JKeyedComboBox extends JComboBox implements TypedWidget {
KeyedModel kmodel;
KeyedFormatter kformatter;
//SqlType sqlType;
// ------------------------------------------------------
public JKeyedComboBox()
{
	setRenderer(new MyRenderer());
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
	DefaultComboBoxModel cmodel = new DefaultComboBoxModel(kmodel.getKeyList());
	super.setModel(cmodel);
}
public void setSqlType(SqlSwinger f)
{
	SqlType sqlType = f.getSqlType();
	if (!(sqlType instanceof SqlEnum)) 
		throw new ClassCastException("Expected Enum type, got " + sqlType);
	SqlEnum etype = (SqlEnum)sqlType;
	setKeyedModel(etype.getKeyedModel());
}
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
System.out.println("JKeyedComboBox.setValue: " + d);
	setSelectedItem(d);
}
public void setValue(int i)
{
	Integer ii = new Integer(i);
	setValue(ii);
}
	
public Object getValue()
	{ return getSelectedItem(); }

// ==============================================================
class MyRenderer 
extends DefaultListCellRenderer {

    public Component getListCellRendererComponent(
	JList list, Object value, int index,
	boolean isSelected, boolean cellHasFocus) {
		if (kformatter != null) value = kformatter.valueToString(value);
		return super.getListCellRendererComponent(
			list, value, index,isSelected,cellHasFocus);
    }
}

}
