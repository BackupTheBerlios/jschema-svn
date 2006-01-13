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
import citibob.jschema.KeyedModel;
import java.awt.*;
//import java.awt.event.*;

/**
 *
 * @author  citibob
Used to make a combo box that returns one of a fixed set of integer values.  The ComboBox displays a list of describtive strings, one per integer value to be returned.
 */
public class JKeyedComboBox extends JComboBox implements TypedWidget {
	KeyedModel kmodel;

// ------------------------------------------------------
public JKeyedComboBox()
{
//	addActionListener(this);
	setRenderer(new MyRenderer());
	kmodel = new KeyedModel();
}
public JKeyedComboBox(KeyedModel model)
{
//	addActionListener(this);
	setRenderer(new MyRenderer());
	setModel(model);
}
// ------------------------------------------------------

public void setModel(KeyedModel kmodel)
{
	this.kmodel = kmodel;
	DefaultComboBoxModel cmodel = new DefaultComboBoxModel(kmodel.getKeyList());
	super.setModel(cmodel);
}
public void setModel(ResultSet rs, int keyCol, int itemCol) throws SQLException
{
	KeyedModel kmodel = new KeyedModel();
	kmodel.addAllItems(rs,keyCol,itemCol);
	setModel(kmodel);
}
public void setModel(ResultSet rs, String keyCol, String itemCol) throws SQLException
{
	KeyedModel kmodel = new KeyedModel();
	kmodel.addAllItems(rs,keyCol,itemCol);
	setModel(kmodel);
}
// -------------------------------------------------------
public void setSelectedItem(Object o)
{
	System.out.println("setSelectedItem("+ o + ")");
	super.setSelectedItem(o);
//	System.out.println("getSelectedItem = " + getSelectedItem());
}
public Object getSelectedItem()
{
	Object ret = super.getSelectedItem();
//	System.out.println("getSelectedItem = " + ret);
	return ret;
}
// ============================================================

	public void setValue(Object d)
		{ setSelectedItem(d); }
	public void setValue(int i)
		{ setValue(new Integer(i)); }
	
	public Class getObjClass()
		{ return Integer.class; }

	public Object getValue()
		{ return getSelectedItem(); }
	public void resetValue() {}
	public void setLatestValue() {}
	public boolean isValueValid() { return true; }

// ==============================================================
class MyRenderer 
extends DefaultListCellRenderer {

    public Component getListCellRendererComponent(
	JList list, Object value, int index,
	boolean isSelected, boolean cellHasFocus) {

        //Set the icon and text.  If icon was null, say so.
//		Object o = getSelectedItem();
		Object displayObj = null;
		Object o = value;
		if (o == null) displayObj = "<null>";
		else if (o instanceof String) {
			displayObj = o;
		} else if (o instanceof Integer) {
			Map map = kmodel.getItemMap();
			Integer ii = (Integer)o;
			KeyedModel.Item it = (KeyedModel.Item)map.get(ii);
			if (it == null) displayObj = ii.toString();
			else displayObj = it;
		} else {
			// displayObj = o.getClass().toString();//"<Not Integer>";
			displayObj = "<Not Integer>";
		}
		return super.getListCellRendererComponent(
			list,displayObj,index,isSelected,cellHasFocus);
    }
}

/*
    public Component getListCellRendererComponent(
	JList list, Object value, int index,
	boolean isSelected, boolean cellHasFocus) {
        //Get the selected index. (The index param isn't
        //always valid, so just use the value.)
//        int selectedIndex = ((Integer)value).intValue();

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        //Set the icon and text.  If icon was null, say so.
//		Object o = getSelectedItem();
Object o = value;
		if (o == null) setText("<null>");
		else if (o instanceof Integer) {
			Map map = kmodel.getItemMap();
			Integer ii = (Integer)o;
			KeyedModel.Item it = (KeyedModel.Item)map.get(ii);
			if (it == null) setText(ii.toString());
			else setText(it.toString());
		} else {
			setText("<Not Integer>");
		}

        return this;
    }
}
*/
}
