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
 * JKeyedItemLabel.java
 *
 * Created on March 20, 2005, 6:37 PM
 */

package citibob.swing.typed;

import javax.swing.*;
import java.sql.*;
import citibob.jschema.KeyedModel;

/**
 *
 * @author citibob
 */
public class JKeyedLabel extends JLabel
implements TypedWidget {

Object val = null;
KeyedModel model;

/** Creates a new instance of JKeyedItemLabel */
public JKeyedLabel() {
	model = new KeyedModel();
}
// ------------------------------------------------------

public void setModel(KeyedModel model)
{
	this.model = model;
}

public void addAllItems(ResultSet rs, String intCol, String itemCol)
throws SQLException
{
	model.addAllItems(rs, intCol, itemCol);
}

public void addAllItems(ResultSet rs, int intCol, int itemCol)
throws SQLException
{
	model.addAllItems(rs, intCol, itemCol);
}
// ------------------------------------------------------
/** Returns current value in the widget. */
public Object getValue()
	{ return val; }

/** Sets the value.  Returns a ClassCastException */
public void setValue(Object o)
	{ setText(model.getItemMap().get(o).toString()); }

/** Returns type of object this widget edits. */
public Class getObjClass()
	{ return Object.class; }

public void resetValue() {}
public void setLatestValue() {}
public boolean isValueValid() { return true; }

}
