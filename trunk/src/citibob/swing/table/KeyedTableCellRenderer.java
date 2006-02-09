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
 * KeyedItemCellRenderer.java
 *
 * Created on March 20, 2005, 6:46 PM
 */

package citibob.swing.table;

import javax.swing.table.*;
import citibob.swing.typed.*;
import java.sql.*;
import citibob.jschema.KeyedModel;
import javax.swing.*;
import javax.swing.event.*;

public class KeyedTableCellRenderer extends DefaultTableCellRenderer {

KeyedModel model;
String nullString = "";

/** Creates a new instance of KeyedItemCellRenderer */
public KeyedTableCellRenderer() {
	model = new KeyedModel();
}
public KeyedTableCellRenderer(KeyedModel model)
{
	this.model = model;
}
// ------------------------------------------------------

public void setModel(KeyedModel model)
{
	this.model = model;
}
public void setNullString(String s)
	{ nullString = s; }

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

public void setValue(Object o) {
//System.out.println(model);
//System.out.println(model.getItemMap());
//System.out.println("KeyedRender.setValue(" + o + ")");
	Object keyedO = null;
	if (o != null) {
		keyedO = model.getItemMap().get(o);
		if (keyedO != null) setText(keyedO.toString());
		else setText("x" + o.toString());
	} else setText(nullString);
}
}
