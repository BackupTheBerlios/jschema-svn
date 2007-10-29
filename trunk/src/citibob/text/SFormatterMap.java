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
package citibob.text;

import citibob.sql.*;
import citibob.swing.typed.*;
import citibob.swing.table.JTypeTableModel;
import java.util.*;
import java.text.*;


/**
 * Maps SqlType objects to various formatters, etc. for String output.
 * This is a lot like SwingerMap, but much simpler.
 * @author citibob
 */
public class SFormatterMap
{

//HashMap constMap = new HashMap();
HashMap makerMap = new HashMap();
	
// ===========================================================
protected static interface Maker
{
	/** Gets a new swinger for an editable cell. */
	SFormatter newSFormatter(JType jType);
}
// ===========================================================
//protected void addConst(SqlSwinger swing)
//{
//	constMap.put(swing.getSqlType().getClass(), swing);
//}
protected void addMaker(Class klass, Maker maker)
{
	makerMap.put(klass, maker);
}
//public Swinger newSwinger(JType t)
//{ return newSwinger(t, true); }
/** Gets a new swinger for a cell of a certain type, depending on whether or not it is editable. */

public SFormatter newSFormatter(JType t)
//public Swinger newSwinger(JType t, boolean editable)
{
	// Index on general class of the JType, or on its underlying
	// Java Class (for JavaJType)
	Class klass = t.getClass();
	if (klass == JavaJType.class) klass = ((JavaJType) t).getObjClass();

	Maker m = null;
	for (;;) {
		m = (Maker)makerMap.get(klass);
		if (m != null) break;
		klass = klass.getSuperclass();
		if (klass == Object.class) break;
	}
	if (m != null) return m.newSFormatter(t);
	
	return null;
}

/** Create SFormatter for an entire set of columns */
public SFormatter[] newSFormatters(JTypeTableModel model)
{
	int n = model.getColumnCount();
	SFormatter[] sfmt = new SFormatter[n];
	for (int i=0; i<n; ++i) sfmt[i] = newSFormatter(model.getJType(0, i));
	return sfmt;
}

public SFormatter[] newSFormatters(JTypeTableModel model,
String[] scol, SFormatter[] sfmt)
{
	int n = model.getColumnCount();
	SFormatter[] sfmt2 = new SFormatter[n];
	
	// Set up specialized formatters
	if (scol != null)
	for (int i=0; i<scol.length; ++i) {
		int col = model.findColumn(scol[i]);
		sfmt2[col] = sfmt[i];
	}
	
	// Fill in defaults
	for (int i=0; i<n; ++i) if (sfmt2[i] == null) {
		sfmt2[i] = newSFormatter(model.getJType(0, i));
	}

	return sfmt2;
}

}
