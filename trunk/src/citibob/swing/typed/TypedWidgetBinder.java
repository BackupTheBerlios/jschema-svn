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
package citibob.swing.typed;

import java.text.DateFormat;
import java.util.Date;
import javax.swing.*;

import citibob.swing.typed.*;
import citibob.sql.*;
import citibob.exception.*;
import citibob.jschema.*;
import citibob.swing.*;
import java.awt.*;
import java.sql.*;

/**
 * Binds a TypedWidget to a RowModel, passing events back and forth.
 * @author  citibob
 */
public class TypedWidgetBinder
implements RowModel.ColListener, java.beans.PropertyChangeListener
{

boolean inValueChanged = false;
boolean inCurRowChanged = false;
boolean inPropertyChange = false;

TableRowModel bufRow;
int colNo;
TypedWidget tw;

// --------------------------------------------------------------------
public void bind(TypedWidget tw, SchemaRowModel bufRow, SwingerMap map)
	{ bind(tw, bufRow, null, map); }
public void bind(TypedWidget tw, TableRowModel bufRow)
	{ bind(tw, bufRow, null); }

// -------------------------------------------------------------------------------
/** Set the type of a widget */
public static void setJType(TypedWidget tw, SchemaRowModel bufRow, String colName, SwingerMap map)
{
	// Set the type
	if (map != null) {
		Schema schema = bufRow.getSchema();
		int col = schema.findCol(colName);
		if (col < 0) return;		// This widget was not meant for us
//		if (col < 0) System.out.println("TypedWidgetBinder: Cannot find column nanmed " + colName);
		JType sqlType = schema.getCol(col).getType();
		Swinger f = map.newSwinger(sqlType);		// Default ways to render & edit
	//System.out.println("colName = " + colName);
		tw.setJType(f);
	}
}
// -------------------------------------------------------------------------------
/** Bind widget and set its type. */
public void bind(TypedWidget tw, SchemaRowModel bufRow, String colName, SwingerMap map)
{
//if (colName.equals("dob")) {
//	System.out.println("dob column reached!!!");
//}
	setJType(tw, bufRow, colName, map);
	bind(tw, (TableRowModel)bufRow, colName);
}

/** Just bind widget, don't mess with its type. */
public void bind(TypedWidget tw, TableRowModel bufRow, String colName)
{
	if (colName == null) colName = tw.getColName();
	colNo = bufRow.findColumn(colName);
	if (colNo < 0) return;		// This column is not for us

	bindRowModel(bufRow, colNo);
	bindWidget(tw);
	
	/* Now, set the initial value. */
	valueChanged(colNo);
}

// --------------------------------------------------------------------
public void bindRowModel(TableRowModel bufRow, int colNo)
{
	// Bind as a listener to the RowModel (which fronts a SchemaBuf)...
	this.bufRow = bufRow;
	bufRow.addColListener(colNo, this);	
}
public void bindWidget(TypedWidget tw)
{
	// Bind as listener to the TypedWidget
//System.out.println("tw.class = " + tw.getClass());
//	Component c = (Component)tw;
//System.out.println("registering to receive property change from: " + tw);
//if ("mailprefid".equals(tw.getColName())) {
//	System.out.println("hoi");
//}
	tw.addPropertyChangeListener("value", this);
	this.tw = tw;
}
// --------------------------------------------------------------------
public void unBind()
{
	//Component c = (Component)tw;
	tw.removePropertyChangeListener("value", this);
	bufRow.removeColListener(colNo, this);
	
	// Allow for garbage collection
	tw = null;
	bufRow = null;
}
// --------------------------------------------------------------------
boolean valsEqual(Object a, Object b)
{
	if (a == b) return true;
	if (a != null && a.equals(b)) return true;
	return false;
}

// ===============================================================
// Implementation of RowModel.Listener
/** Propagate change in underlying RowModel to widget value. */
public void valueChanged(int col)
{
//if ("mailprefid".equals(tw.getColName())) {
//	System.out.println("hoi");
//	System.out.println("valueChanged(" + col + ") = " + bufRow.get(col));
//	System.out.println("tw = " + tw + ", bufRow = " + bufRow);
//}
	if (inValueChanged) return;
	inValueChanged = true;

	tw.setValue(bufRow.get(col));

	inValueChanged = false;
}
public void curRowChanged(int col)
{
//if ("mailprefid".equals(tw.getColName())) {
//	System.out.println("hoi");
//}
	if (inCurRowChanged) return;
	inCurRowChanged = true;

	int row = bufRow.getCurRow();
	//Component c = (Component)tw;
	tw.setEnabled(row != MultiRowModel.NOROW);
	tw.setValue(row == MultiRowModel.NOROW ? null : bufRow.get(col));

	inCurRowChanged = false;
}
// ===============================================================
// Implementation of PropertyChangeListener
public void propertyChange(java.beans.PropertyChangeEvent evt)
{
//if ("mailprefid".equals(tw.getColName())) {
//	System.out.println("hoi");
//}
	if (inPropertyChange) return;
	inPropertyChange = true;

//System.out.println("Property change received from widget: " + evt.getSource());
	//if (!"value".equals(evt.getPropertyName())) return;
	
//	// Stop infinite loop of property change events between the two objects.
//	if (!valsEqual(evt.getNewProperty(), evt.getOldProperty())) return;
//	// OK, propagate it down to the row.
	bufRow.set(colNo, evt.getNewValue());

	inPropertyChange = false;
}
// ===============================================================
/** Binds all components in a widget tree to a (Schema, RowModel), if they implement SchemaRowBinder. */
public static void bindRecursive(Component c, SchemaRowModel bufRow, SwingerMap map)
{
	// Take care of yourself
	if (c instanceof TypedWidget) {
		TypedWidget tw = (TypedWidget)c;
		if (tw.getColName() != null) {
			new TypedWidgetBinder().bind(tw, bufRow, tw.getColName(), map);
		}
	}

	// Take care of your children
	if (c instanceof Container) {
	    Component[] child = ((Container)c).getComponents();
	    for (int i = 0; i < child.length; ++i) {
			bindRecursive(child[i], bufRow, map);
		}
	}
}

// --------------------------------------------------------------
/** Set the type of a widget
 @param colName column in ResultSet to bind --- if null, use from tw. */
static void setValue(TypedWidget tw, java.sql.ResultSet rs,
SwingerMap map, citibob.sql.SqlTypeSet typeset) throws java.sql.SQLException
{
	String colName = tw.getColName();
	int col = 0;
	try {
		col = rs.findColumn(colName);
	} catch(java.sql.SQLException e) {
		return;		// Col not found
	}
	if (col <= 0) return;
	SqlType sqlType = typeset.getSqlType(rs, col);
	tw.setJType(map.newSwinger(sqlType));
	tw.setValue(sqlType.get(rs, col));
}


/** Sets the JType and value of all widgets in a tree, but does not bind them. */
public static void setValueRecursive(Component c, java.sql.ResultSet rs, SwingerMap map, SqlTypeSet tset)
throws SQLException
{
	// Take care of yourself
	if (c instanceof TypedWidget) {
		TypedWidget tw = (TypedWidget)c;
		if (tw.getColName() != null) {
			setValue(tw, rs, map, tset);
		}
	}

	// Take care of your children
	if (c instanceof Container) {
	    Component[] child = ((Container)c).getComponents();
	    for (int i = 0; i < child.length; ++i) {
			setValueRecursive(child[i], rs, map, tset);
		}
	}
}

}
