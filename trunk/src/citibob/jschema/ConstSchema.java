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
package citibob.jschema;

import java.util.*;

public class ConstSchema implements Schema
{

protected Column[] cols;
protected String table;

//public List getPrototypes()
//{
//	ArrayList ls = new ArrayList(cols.length);
//	for (int i = 0; i < cols.length; ++i) {
//		ls.add(cols[i].getType().getPrototype());
//	}
//	return ls;
//}

public String getDefaultTable()
	{ return table; }

public int getColCount()
	{ return cols.length; }

public Column getCol(int colNo)
	{ return cols[colNo]; }

public ColIterator colIterator()
	{ return new MyColIterator(); }

public int findCol(String name)
{
	for (int i = 0; i < cols.length; ++i) {
		if (cols[i].getName().equals(name)) return i;
	}
	return -1;
}

/** Used to subclass schemas and append columns to them. */
protected void appendCols(Column[] add)
{
	Column[] newcols = new Column[cols.length + add.length];
	for (int i = 0; i < cols.length; ++i) newcols[i] = cols[i];
	for (int i = 0; i < add.length; ++i) newcols[i + cols.length] = add[i];
	cols = newcols;
}

// ===================================================

// ===============================================
private class MyColIterator implements ColIterator
{
	int i;
	public boolean hasNext()
		{ return (i < cols.length); }
	public Column next()
		{ return cols[i++]; }
}
// --------------------------------------------------------



}
