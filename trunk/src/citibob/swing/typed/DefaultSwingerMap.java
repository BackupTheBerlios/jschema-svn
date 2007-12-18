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
/*
 * SqlTypeMap.java
 *
 * Created on March 15, 2006, 9:22 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.swing.typed;

import citibob.sql.*;
import citibob.types.JType;
import citibob.types.JavaJType;
import java.util.*;
import citibob.text.*;
import citibob.swing.table.*;

/**
 * Maps SqlType objects to various formatters, etc. required by graphical parts
 * of system.  Used to automatically construct GUIs appropriate for a schema.
 * @author citibob
 */
public class DefaultSwingerMap extends citibob.swing.typed.SwingerMap
implements citibob.text.SFormatMap
{

//HashMap constMap = new HashMap();
HashMap<Object,Maker> makerMap = new HashMap();
	
// ===========================================================
protected static interface Maker
{
//	/** Gets a new swinger for a cell of a certain type, depending on whether or not it is editable. */
//	Swinger newSwinger(JType sqlType, boolean editable);
	/** Gets a new swinger for an editable cell. */
	Swinger newSwinger(JType sqlType);
}
// ===========================================================
//protected void addConst(SqlSwinger swing)
//{
//	constMap.put(swing.getSqlType().getClass(), swing);
//}

/** Stores a maker by JType subclass. */
protected void addMaker(Class klass, Maker maker)
{
	makerMap.put(klass, maker);
}
/** Stores a maker by column name */
protected void addMaker(String colName, Maker maker)
{
	makerMap.put(colName, maker);
}


/** Gets a new swinger for a cell of a certain type, depending on whether or not it is editable. */
public Swinger newSwinger(JType t, String colName)
//public Swinger newSwinger(JType t, boolean editable)
{
	Maker m = null;
	
	// Try by name
	if (colName != null) {
		m = makerMap.get(colName);
		if (m != null) return m.newSwinger(t);
	}
	
	// Index on general class of the JType,
	// or on its underlying Java Class (for JavaJType)
	Class klass = t.getClass();
	if (klass == JavaJType.class) klass = ((JavaJType) t).getObjClass();
System.err.println("newSwinger: " + klass);

	for (;;) {
System.err.println("     trying: " + klass);
		m = (Maker)makerMap.get(klass);
		if (m != null) break;
		klass = klass.getSuperclass();
		if (klass == null || klass == Object.class) break;
	}
	if (m != null) return m.newSwinger(t);

	// No swinger found, punt...
System.err.println("Failed to find a swinger");
	return null;
}

/** Create Swinger for an entire set of columns */
public Swinger[] newSwingers(JTypeTableModel model)
{
	int n = model.getColumnCount();
	Swinger[] sfmt = new Swinger[n];
	for (int i=0; i<n; ++i) sfmt[i] = newSwinger(model.getJType(0, i), model.getColumnName(i));
	return sfmt;
}

/** Create Swinger for an entire set of columns
@param scol names of columns for exceptions.
@param sfmt The exceptions for those columns. */
public Swinger[] newSwingers(JTypeTableModel model,
String[] scol, Swinger[] swingers)
{
	int n = model.getColumnCount();
	Swinger[] sfmt2 = new Swinger[n];
	
	// Set up specialized formatters
	if (scol != null)
	for (int i=0; i<scol.length; ++i) {
		int col = model.findColumn(scol[i]);
		sfmt2[col] = swingers[i];
	}
	
	// Fill in defaults
	for (int i=0; i<n; ++i) if (sfmt2[i] == null) {
		sfmt2[i] = newSwinger(model.getJType(0, i), model.getColumnName(i));
	}

	return sfmt2;
}


// ==================================================================
// SFormatMap
public SFormat newSFormat(JType t, String colName)
	{ return newSwinger(t, colName).getSFormat(); }


/** Create SFormat for an entire set of columns */
public SFormat[] newSFormats(JTypeTableModel model)
{
	int n = model.getColumnCount();
	SFormat[] sfmt = new SFormat[n];
	for (int i=0; i<n; ++i) sfmt[i] = newSFormat(model.getJType(0, i), model.getColumnName(i));
	return sfmt;
}

public SFormat[] newSFormats(JTypeTableModel model,
String[] scol, SFormat[] sfmt)
{
	int n = model.getColumnCount();
	SFormat[] sfmt2 = new SFormat[n];
	
	// Set up specialized formatters
	if (scol != null)
	for (int i=0; i<scol.length; ++i) {
		int col = model.findColumn(scol[i]);
		sfmt2[col] = sfmt[i];
	}
	
	// Fill in defaults
	for (int i=0; i<n; ++i) if (sfmt2[i] == null) {
		sfmt2[i] = newSFormat(model.getJType(0, i), model.getColumnName(i));
	}

	return sfmt2;
}



}
