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
package citibob.jschema;

import java.util.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.*;
import citibob.swing.table.*;
import citibob.sql.*;

import java.io.*;
import citibob.types.JType;

public class StatusSchemaBuf extends AbstractJTypeTableModel
implements TableModelListener, SchemaBuf.Listener
{

SchemaBuf sb;
// ---------------------------------------------------------
/** Constructor
 @param Column i in this table maps to column colMap[i] in underlying table. */
public StatusSchemaBuf(SchemaBuf sb)
{
	this.sb = sb;
	sb.addTableModelListener(this);
	sb.addSchemaBufListener(this);
}
// -------------------------------------------------------------------
//public Object getValueAt(int row, String col)
//	{ return getValueAt(row, findColumn(col)); }
//public void setValueAt(Object val, int row, String col)
//	{ setValueAt(val, row, findColumn(col)); }
//public JType getJType(int row, String col)
//	{ return getJType(row, findColumn(col)); }
// --------------------------------------------------------
public int findColumn(String colName)
{
	if ("__status__".equals(colName)) return 0;
	return sb.findColumn(colName) + 1;
}
public Class getColumnClass(int colIndex)
{
	if (colIndex == 0) return Integer.class;
	return sb.getColumnClass(colIndex-1);
}
public JType getJType(int row, int col)
{
	if (col == 0) return null;
	return sb.getJType(row, col-1);
}
//public JType getColumnJType(int colIndex)
//{
//	if (colIndex == 0) return null;
//	return sb.getColumnJType(colIndex-1);
//}
//public JType getJType(int row, int col)
//	{ return getColumnJType(col); }
public String getColumnName(int colIndex)
{
	if (colIndex == 0) return "__status__";
	return sb.getColumnName(colIndex-1);
}
/** Allow editing of all non-key fields. */
public boolean isCellEditable(int rowIndex, int columnIndex)
{
	if (columnIndex == 0) return false;
	return sb.isCellEditable(rowIndex, columnIndex-1);
}

public int getColumnCount()
{
	return sb.getColumnCount() + 1;
}
public Object getValueAt(int rowIndex, int colIndex)
{
//System.out.println("Returning status value: " + sb.getStatus(rowIndex));
//if (colIndex == 0) return new Integer(1);
	Object val;
	if (colIndex == 0) val = new Integer(sb.getStatus(rowIndex));
	else val = sb.getValueAt(rowIndex, colIndex-1);
//	System.out.println("getValueAt(" + rowIndex + ", " + colIndex + " = " + val);
	return val;
}
public void setValueAt(Object val, int rowIndex, int colIndex)
{
	if (colIndex == 0) return;
	sb.setValueAt(val, rowIndex, colIndex-1);
}

public int getRowCount()
	{ return sb.getRowCount(); }

//public List getPrototypes()
//{
//	List l = sb.getPrototypes();
//	l.add(0, new Integer(0));
//	return l;
//}

// =========================================================
public void statusChanged(int row)
{
	// Pass along as a column-0 event
	this.fireTableCellUpdated(row, 0);
}
public void tableChanged(TableModelEvent e_u) 
{
	TableModelEvent e_t;

	int type = e_u.getType();
	int col_u = e_u.getColumn();
	switch(type) {
	case TableModelEvent.UPDATE :
		if (col_u == TableModelEvent.ALL_COLUMNS) {
			this.fireTableRowsUpdated(e_u.getFirstRow(), e_u.getLastRow());
		} else {
			// Column in this TableModel is 1 greater than in underlying model
			int col_t = col_u + 1;
System.out.println("StatusSchemaBuf.tableChanged: underlying " + col_u +
	" --> " + col_t + "(" + sb.getColumnName(col_u));
//			this.fireTableChanged(new TableModelEvent(
//				this, col_t, e_u.getFirstRow(), e_u.getLastRow(), type));
			for (int i = e_u.getFirstRow(); i <= e_u.getLastRow(); ++i) {
				this.fireTableCellUpdated(i, col_t);
			}
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
