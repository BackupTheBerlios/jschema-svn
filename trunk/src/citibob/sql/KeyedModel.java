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
 * KeyedModel.java
 *
 * Created on March 20, 2005, 5:51 PM
 * TODO: Should this be in citibob.jschema package?
 */

package citibob.sql;

import java.sql.*;
import java.util.*;
//import
/**
 *
 * @author citibob
 */
public class KeyedModel {

Map itemMap = new HashMap();	// HashMap instead of TreeMap doesn't require comparision method
Vector keyList = new Vector();
//String nullString = "<none>";

/** An item to be added to the combo box in JIntComboBox. */
public class Item {
	public Object key;
	public Object obj;
	public String toString() {
		if (obj == null) return null;
		return obj.toString();
	}
	public Item(Object key, Object obj) {
		this.key = key;
		this.obj = obj;
	}
	/** Pointer equality requires model object be used */
	public boolean equals(Object o)
	{
		if (o instanceof Item) {
			Item it = (Item)o;
			return (it == this);
		} else return false;
	}
}

//public void setNullString(String s)
//	{ this.nullString = s; }
//public String getNullString()
//	{ return nullString; }
public Vector getKeyList()
	{ return keyList; }
public Map getItemMap()
	{ return itemMap; }
public Object get(Object key)
	{ return itemMap.get(key); }


void clear()
{
	itemMap.clear();
	keyList.clear();
}
private void addItem(KeyedModel.Item oi)
{
	itemMap.put(oi.key, oi);
	keyList.add(oi.key);
}

/** Adds another item to the dropdown list. */
public void addItem(Object key, Object item)
{
	KeyedModel.Item oi = new KeyedModel.Item(key, item);
	addItem(oi);
}

public void addAllItems(ResultSet rs, int keyCol, int itemCol) throws SQLException
{
//System.out.println("addAllItems: this = " + this);
	while (rs.next()) addItem(rs.getObject(keyCol), rs.getObject(itemCol));
}

public void addAllItems(ResultSet rs, String keyCol, String itemCol)
throws SQLException
{
//System.out.println("addAllItems: this = " + this);
	//ResultSetMetaData md = rs.getMetaData();
	while (rs.next()) {
		addItem(rs.getObject(keyCol),
			rs.getObject(itemCol));
	}
}

/** Converts key value to a string, if the key exists in the model;
 *otherwise return null. */
public String toString(Object key)
{
	//if (key == null) return null;
	KeyedModel.Item oi = (Item)itemMap.get(key);
	if (oi == null) return null;
	return oi.toString();
}
// -------------------------------------------------------------------
public void KeyedModel(ResultSet rs, int keyCol, int itemCol) throws SQLException
{
	addAllItems(rs,keyCol,itemCol);
}
public void KeyedModel(ResultSet rs, String keyCol, String itemCol) throws SQLException
{
	addAllItems(rs,keyCol,itemCol);
}
public KeyedModel() {}

// -------------------------------------------------------------------

}
