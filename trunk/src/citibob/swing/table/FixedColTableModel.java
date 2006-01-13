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

public class FixedColTableModel extends AbstractTableModel
{

ArrayList rowData = new ArrayList();	// ArrayList<Record>
HashMap dataByIpaqID = new HashMap();	// HashMap<Integer --> Record>

public static final int ROWNUM = 0;
int numCols = 1;
public int getColumnCount() { return numCols; }

public FixedColTableModel(int numCols, Class[] types, String[] colNames)
{
	this.numCols = numCols;
	this.types = types;
	this.colNames = colNames;
}

Class[] types;
String[] colNames;

public String getColumnName(int col)
	{ return colNames[col]; }
public Class getColumnClass(int col)
	{ return types[col]; }
public int getRowCount()
	{ return rowData.size(); }

public Object getValueAt(int row, int column)
{
	if (column >= numCols) return null;
	Object[] r = (Object[])rowData.get(row);
	if (r == null) return null;
	return r[column];
}

public void setValueAt(Object val, int row, int col)
{
	// if (col >= numCols || row >= getColumnCount) return;
	if (!types[col].isAssignableFrom(val.getClass()))
		throw new ClassCastException("Bad class " + val.getClass() + "(expected class " + types[col]);
	Object[] r = (Object[])rowData.get(row);
	if (r == null) return;
	r[col] = val;
	fireTableCellUpdated(row, col);
}

/** Inserts rowData from [firstRow to lastRow], inclusive.  Fires tableRowsInserted. */
public void insertRowsNoFire(int firstRow, int lastRowI)
{
	int lastRow = lastRowI + 1;
	int oldSize = rowData.size();
	int sizeDiff = lastRow - firstRow;
	int numMoved = oldSize - firstRow;
	int newSize = oldSize + sizeDiff;
	rowData.ensureCapacity(newSize);
	for (int i = numMoved-1; i >= 0; --i) {
		int oldPlace = firstRow + i;
		int newPlace = oldPlace + sizeDiff;
		Object[] row = (Object[])rowData.get(oldPlace);
		row[ROWNUM] = new Integer(newPlace);
		rowData.set(newPlace, row);
	}
	for (int i = firstRow; i < lastRow; ++i) {
		Object[] row = new Object[numCols];
		row[ROWNUM] = new Integer(i);
		rowData.set(i, row);
	}
}
public void insertRows(int firstRow, int lastRowI)
{
	insertRowsNoFire(firstRow, lastRowI);
	fireTableRowsInserted(firstRow, lastRowI);
}

/** Returns row # of added row. */
public int appendRow()
{
	int newr = getRowCount();
	insertRows(newr, newr);
	return newr;
}

public int addRowNoFire(Object[] row)
{
	int r = rowData.size();
	row[ROWNUM] = new Integer(r);
	rowData.add(row);
	return r;
}
public int addRow(Object[] row)
{
	int r = addRowNoFire(row);
	fireTableRowsInserted(r, r);
	return r;
}

/** Removes rows from [firstRow to lastRow], inclusive.  Fires tableRowsDeleted. */
public void deleteRowsNoFire(int firstRow, int lastRowI)
{
	int lastRow = lastRowI + 1;
	int sizeDiff = lastRow - firstRow;
	int oldSize = rowData.size();
	for (int i = firstRow; i < oldSize - sizeDiff; ++i) {
		int oldPlace = i + sizeDiff;
		int newPlace = i;
		Object[] row = (Object[])rowData.get(oldPlace);
		row[ROWNUM] = new Integer(newPlace);
		rowData.set(newPlace, row);
	}
}
public void deleteRows(int firstRow, int lastRowI)
{
	deleteRowsNoFire(firstRow, lastRowI);
	fireTableRowsDeleted(firstRow, lastRowI);
}


public Object[] getRow(int r)
	{ return (Object[])rowData.get(r); }

}
