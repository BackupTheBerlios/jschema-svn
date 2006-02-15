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
package citibob.swing.table;

import java.util.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.*;


import java.io.*;

public class ColPermuteTableModel extends AbstractTableModel
implements CitibobTableModel, TableModelListener
{

CitibobTableModel model_u;

//final ArrayList prototypes = new ArrayList(NUMCOLS);

String[] colNames;
int[] colMap;		// Column map: i in this table --> colMap[i] in underlying
int[] iColMap;
boolean[] editable;	// Is each column editable?  (NULL ==> use underlying default)
// ---------------------------------------------------------
/** Clone the bheavior of the underlying TableModel. */
public ColPermuteTableModel(CitibobTableModel model_u)
{
	int ncol = model_u.getColumnCount();
	String[] colNames = new String[ncol];
	int[] colMap = new int[ncol];
	for (int i=0; i<ncol; ++i) {
		colNames[i] = model_u.getColumnName(i);
		colMap[i] = i;
	}
	init(model_u, colNames, colMap);
}
/** Constructor
 @param Column i in this table maps to column colMap[i] in underlying table. */
public ColPermuteTableModel(CitibobTableModel model_u, String[] colNames, int[] colMap)
{
	init(model_u, colNames, colMap);
}

public ColPermuteTableModel(CitibobTableModel model_u,
String[] colNames,
String[] sColMap)
{
System.out.println("ColPermuteTableModel: this = " + this);
	int[] colMap = new int[sColMap.length];
	for (int i = 0; i < colMap.length; ++i) {
System.out.println("sColMap[" + i + "] = " + sColMap[i]);
		for (int j = 0; j < model_u.getColumnCount(); ++j) {
			if (sColMap[i].equals(model_u.getColumnName(j))) {
System.out.println("    " + model_u.getColumnName(j));
				colMap[i] = j;
				break;
			}
			colMap[i] = -1;
		}
	}

	init(model_u, colNames, colMap);
}
public void setEditable(boolean[] editable)
{
	this.editable = editable;
}
private void init(CitibobTableModel model_u, String[] colNames, int[] colMap)
{
	this.model_u = model_u;
	this.colNames = colNames;
	this.colMap = colMap;
	this.editable = null;

	// Set up inverse column map
	iColMap = new int[model_u.getColumnCount()];
	for (int i=0; i < model_u.getColumnCount(); ++i) iColMap[i] = -1;
	for (int i=0; i < colMap.length; ++i) iColMap[colMap[i]] = i;


	model_u.addTableModelListener(this);

//for (int i = 0; i < colMap.length; ++i) System.out.println("colMap["+i+"] = "+colMap[i]);
}
// -------------------------------------------------------------------
public int findColumn(String name)
{
	for (int i = 0; i < colNames.length; ++i) {
		if (colNames[i].equals(name)) return i;
	}
	return -1;
}
/** Finds a column by name in the UNDERLYING table model, then
reports its location in THIS table model.  This allows one to refer
to columns by their UNDERLYING name, not their display name.
 TODO: Maybe add a separate concept for actual name and display name in 
 the table model... */
public int findUnderlyingCol(String s)
{
	int col_u = model_u.findColumn(s);
	int col_t = iColMap[col_u];
	return col_t;
}
public String getColumnName(int col)
	{ return colNames[col]; }
public Class getColumnClass(int col)
	{ return model_u.getColumnClass(colMap[col]); }

public boolean isCellEditable(int rowIndex, int columnIndex)
{
	if (editable == null) {
		return model_u.isCellEditable(rowIndex, colMap[columnIndex]);
	} else {
		return editable[columnIndex];
	}
}

public int getColumnCount()
	{ return colMap.length; }
public int getRowCount()
	{ return model_u.getRowCount(); }

public List getPrototypes()
{
	List pold = model_u.getPrototypes();
	if (pold == null) return null;

	// TODO: This could be inefficient with LinkedList.
	ArrayList pnew = new ArrayList(colMap.length);
	for (int i = 0; i < colMap.length; ++i) pnew.add(pold.get(colMap[i]));
	return pnew;
}

public Object getValueAt(int row, int column)
	{ return model_u.getValueAt(row, colMap[column]); }

public void setValueAt(Object val, int row, int column)
{
	System.out.println("ColpermuteTableModel: setValueAt(" + val + ", " + row + ", " + column);
	model_u.setValueAt(val, row, colMap[column]);
}

// =========================================================
public void tableChanged(TableModelEvent e_u) 
{
	TableModelEvent e_t;
// _u = underlying table, _t = this table
//System.out.println("ColPermute: " + e_u);

	int type = e_u.getType();
	int col_u = e_u.getColumn();
	switch(type) {
	case TableModelEvent.UPDATE :
		if (col_u == TableModelEvent.ALL_COLUMNS) {
			this.fireTableRowsUpdated(e_u.getFirstRow(), e_u.getLastRow());
		} else {
			// Re-map the columns.
System.out.println("col_u = " + col_u);
for (int i = 0; i < colMap.length; ++i) System.out.println(colMap[i] + " " + model_u.getColumnName(colMap[i]));
			int col_t = iColMap[col_u];
System.out.println("ColPermuteTableModel: col_u = " + col_u + ", col_t = " + col_t + " " + this);
//System.out.println("rows = " +e_u.getFirstRow() + ", " + e_u.getLastRow());
			if (col_t == -1) return;
			for (int i=e_u.getFirstRow(); i <= e_u.getLastRow(); ++i)
				this.fireTableCellUpdated(i, col_t);
//			this.fireTableChanged(new TableModelEvent(
//				this, col_t, e_u.getFirstRow(), e_u.getLastRow(), type));
//			this.fireTableRowsUpdated(e_u.getFirstRow(), e_u.getLastRow());
		}
	break;
	case TableModelEvent.INSERT :
		this.fireTableRowsInserted(e_u.getFirstRow(), e_u.getLastRow());
	case TableModelEvent.DELETE :
		this.fireTableRowsDeleted(e_u.getFirstRow(), e_u.getLastRow());
	break;
	}
}



}
