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

import java.sql.*;
import javax.swing.table.*;
import javax.swing.event.*;

import citibob.swing.table.*;

import java.util.*;
import citibob.sql.ConsSqlQuery;
import citibob.types.JType;
import static citibob.jschema.RowStatusConst.*;

public class SchemaBuf extends AbstractJTypeTableModel
implements SqlBuf //, JTypeTableModel
{
/** Data model we'll use for our columns. */
Schema schema;

/** Prefex all reported column names in the JTable with this string. */
String colPrefix = null;		// null = don't use any

/** Should the first column of this TableModel display the status? */
//boolean statusCol = true;

// =====================================================
// Our data storage
ArrayList rows = new ArrayList();			// ArrayList<SqlRow>
private SqlRow newRow()
{
	int n = getColumnCount();
	SqlRow row = new SqlRow(n);
	
	// Put in default values
	for (int i=0; i<n; ++i) {
		row.data[i] = schema.getCol(i).getDefault();
//System.out.println("default["+i+"] = "+row.data[i]);
	}
	
	return row;
}
///** User should override this; provides default values for rows
// newly inserted into this schema buf */
//public Object getDefault(int col)
//{
//	return null;
//}
// =====================================================
public interface Listener
{
	void statusChanged(int row);
}
public abstract class Adapter implements Listener
{
	public void statusChanged(int row) {}
}
// ==============================================================
// Unique to SchemaBuf

	// --------------------------------------------------
	public SchemaBuf(Schema schema)
	{
		this.schema = schema;
	}
	// --------------------------------------------------
	/** The schema describing the columns */
	public Schema getSchema()
		{ return schema; }
	// --------------------------------------------------

//public boolean getStatusCol()
//	{ return statusCol; }
//public void setStatusCol(boolean b)
//	{ statusCol = b; }
// ===============================================================
// Implementation of SqlGen
public String getDefaultTable() { return schema.getDefaultTable(); }
// --------------------------------------------------
// (shared with TableModel)
// public int getRowCount()
//	{ return rows.size(); }
// --------------------------------------------------
public boolean valueChanged(SqlRow r, int col)
{
	//SqlRow r = (SqlRow)rows.get(row);

	Object origData = r.origData[col];
	Object curData = r.data[col];
//String name = schema.getCol(col).getName();
//System.out.println(name + ": " + origData + " -> " + curData);
//if ("isorg".equals(name)) {
//	System.out.println("hoi");
//}
	return !(curData == null ? origData == null : curData.equals(origData));
}
public boolean valueChanged(int row, int col)
{
	SqlRow r = (SqlRow)rows.get(row);
	return valueChanged(r, col);
}
public boolean valueChanged(int row)
{
	SqlRow r = (SqlRow)rows.get(row);
	for (int col = 0; col < schema.getColCount(); ++col) {
//		Object origData = r.origData[col];
//		Object curData = r.data[col];
		if (valueChanged(r, col)) return true;
	}
	return false;
}
// --------------------------------------------------
public Object getOrigValueAt(int rowIndex, int colIndex)
{
	SqlRow r = (SqlRow)rows.get(rowIndex);
 	return r.origData[colIndex];
}
public void resetValueAt(int rowIndex, int colIndex)
{
	setValueAt(getOrigValueAt(rowIndex, colIndex), rowIndex, colIndex);
}
// --------------------------------------------------

public int getStatus(int row)
{
//System.out.println("rows.len = " + rows.size() + ", row = " + row);
	SqlRow r = (SqlRow)rows.get(row);
	return r.status;
}
// --------------------------------------------------
// ===============================================================
// Implementation of SqlGen: Read rows from the database
public int addRowNoFire(ResultSet rs, int rowIndex) throws SQLException
{
	String pre = (colPrefix == null ? "" : colPrefix + "_");
//System.out.println("SchemaBuf.addRow: " + rowIndex);
//	String asNameDot = (asName == null || "".equals(asName) ? "" : asName + ".");
	SqlRow row = newRow();
	for (int i = 0; i < getColumnCount(); ++i) {
		// Should we be using column numbers here instead of names?  After all,
		// we know col numbers because of the schema (or do we)?
		Column col = schema.getCol(i);
//if (col.getName().equals("date")) {
//	System.out.println("hoi");
//}
		row.data[i] = col.getType().get(rs, pre + col.getName()); //xyzqqq
//		row.data[i] = rs.getObject(pre + col.getName()); //xyzqqq
		row.origData[i] = row.data[i];
//System.out.println("     col[" + i + "] = " + row.data[i] + " name = " + col.getName());
//if (row.data[i] == null) System.out.println("             " + rs.getString(col.getName()));
	}
	rows.add(rowIndex, row);
	return rowIndex;
}

/** Appends a row in the data */
public void addRow(ResultSet rs) throws SQLException
{
	int rowIndex = addRowNoFire(rs, rows.size());
	fireTableRowsInserted(rowIndex, rowIndex);
//	fireTableDataChanged();
}
// --------------------------------------------------
/** Convenience function (sort of)... */
public void addAllRows(ResultSet rs) throws SQLException
{
	int firstRow = rows.size();
	int n = 0;
	while (rs.next()) {
		addRowNoFire(rs, rows.size());
		++n;
//if (n % 1000 == 0) System.out.println("SchemaBuf.addAllRows: " + n);
	}
//System.err.println("addAllRows: " + n);

	rs.close();
	int lastRow = rows.size()-1;
//System.out.println("SchemaBuf Firing event...");
	if (lastRow >= firstRow) fireTableRowsInserted(firstRow, lastRow);
//System.out.println("SchemaBuf Done firing event!");
	rs.close();
//System.err.println("addAllRows: count = " + getRowCount());
}
// --------------------------------------------------
// /** Convenience functions for single-row SchemaBufs */
// public void setOneRow(ResultSet rs)
// {
// 	clear();
// 	rs.next();
// 	addRow(rs);
// 	rs.close();
// }
// --------------------------------------------------
// ===============================================================
// Implementation of SqlGen: Write rows to the database
// --------------------------------------------------
/** Makes update query update column(s) represented by this object.
 @param updateUnchanged If true, update even columns that haven't been edited. */
public void getUpdateCols(int row, ConsSqlQuery q, boolean updateUnchanged)
{
	SqlRow r = (SqlRow)rows.get(row);
	for (int col = 0; col < schema.getColCount(); ++col) {
		Object origData = r.origData[col];
		Object curData = r.data[col];
		Column c = schema.getCol(col);
//if ("firstname".equals(c.getName())) {
//	System.out.println("hoi");
//}
		boolean unchanged = (curData == null ? origData == null : curData.equals(origData));
//		boolean changed = valueChanged(r, col);
		if (updateUnchanged || !unchanged) {
//		if (updateUnchanged || !curData.equals(origData)) {
			q.addColumn(c.getName(), c.getType().toSql(curData));
		}
	}
}
// --------------------------------------------------
public void getInsertCols(int row, ConsSqlQuery q, boolean insertUnchanged)
{
	SqlRow r = (SqlRow)rows.get(row);
	for (int col = 0; col < schema.getColCount(); ++col) {
		Object origData = r.origData[col];
		Object curData = r.data[col];
		Column c = schema.getCol(col);
		boolean unchanged = (curData == null ? origData == null : curData.equals(origData));
System.out.println("   getInsertCols(" + row + ", " + col + "): " + !unchanged + ", " + origData + " -> " + curData);
		if (insertUnchanged || !unchanged) {
			q.addColumn(c.getName(), c.getType().toSql(curData));
		}
	}

}
// --------------------------------------------------
public void getWhereKey(int row, ConsSqlQuery q, String table)
{
	SqlRow r = (SqlRow)rows.get(row);
	SchemaHelper.getWhereKey(schema, q, table, r.data);
}
// --------------------------------------------------
public void setColPrefix(String colPrefix) { this.colPrefix = colPrefix; }
public void getSelectCols(ConsSqlQuery q, String asName)
{
	SchemaHelper.getSelectCols(schema, q, asName, colPrefix);
}
// --------------------------------------------------
// ===============================================================
// Implementation of SqlBuf

// --------------------------------------------------
/** Clear all rows from RecSet, sets nRows() == 0 */
public void clear()
{
	int oldSize = rows.size();
	rows.clear();
	if (oldSize > 0) fireTableRowsDeleted(0, oldSize - 1);
}
// --------------------------------------------------
public void setStatus(int row, int status)
{
	SqlRow r = (SqlRow)rows.get(row);
	r.status = status;
	fireStatusChanged(row);
}
// ---------------------------------------------------
/** Mark a row for deletion. */
public void deleteRow(int rowIndex)
{
	if (rowIndex < 0) return;
	SqlRow r = (SqlRow)rows.get(rowIndex);
	if ((r.status & DELETED) != 0) return;		// Already deleted
	if ((r.status & INSERTED) != 0) {
//		// Delete from buffer rows that have been inserted but not in the DB.
//		rows.remove(rowIndex);
//		fireTableRowsDeleted(rowIndex, rowIndex);
		removeRow(rowIndex);
	} else {
		// Mark row as deleted, so we can remove it from DB
		r.status |= DELETED;
		fireStatusChanged(rowIndex);
//		fireTableCellUpdated(rowIndex, rowIndex);
	}
}
/** Mark all rows for deletion. */
public void deleteAllRows()
{
	for (int i=0; i<getRowCount(); ++i) deleteRow(i);
}
public void undeleteRow(int rowIndex)
{
	if (rowIndex < 0) return;
	SqlRow r = (SqlRow)rows.get(rowIndex);
	if ((r.status & DELETED) == 0) return;		// Already not deleted
	
	// Mark row as not deleted, so we can remove it from DB
	r.status &= ~DELETED;
	fireStatusChanged(rowIndex);
}
/** Mark all rows for deletion. */
public void undeleteAllRows()
{
	for (int i=0; i<getRowCount(); ++i) undeleteRow(i);
}
// ---------------------------------------------------
/** Physically remove a row from this buffer. */
public void removeRow(int row)
{
	rows.remove(row);
	fireTableRowsDeleted(row, row);
}
// ---------------------------------------------------
/** For making space for a new row of data to be later inserted into DB.
If rowIndex == -1, it gets appended on end. */
public int insertRow(int rowIndex)
{
	if (rowIndex == -1) rowIndex = rows.size();
	SqlRow row = newRow();
	row.status = INSERTED;
	rows.add(rowIndex, row);
//System.out.println("fireTalbeRowsInserted...");
//TableModelListener[] l=getTableModelListeners();
//for (int i=0; i < l.length; ++i) System.out.println("   " + l[i]);
	fireTableRowsInserted(rowIndex, rowIndex);
	return rowIndex;
}
// --------------------------------------------------
/** Insert a row and initialize it with data.  colNames[] and
vals[] must have the same length.
 @param vals initial values for the columns
 @param colNames columns for which we want initial values */
public int insertRow(int rowIndex, String[] colNames, Object[] vals)
throws KeyViolationException
{

for (int i = 0; i < colNames.length; ++i) System.out.println("    insertRow " + i + ": " + colNames[i] + " " + vals[i]);
	int rowInserted = 0;		// Row # of the row we inserted (or found)
	int[] coli = new int[colNames.length];
	int numKey = 0;
	for (int i=0; i<colNames.length; ++i) {
		coli[i] = findColumn(colNames[i]);
		if (coli[i] < 0) throw new KeyViolationException("Column named " + colNames[i] + " not found!");
		if (isKey(coli[i])) ++numKey;
	}

	// Count # of keys in Schema.  If we haven't inserted into all of them
	// assume we're using an auto-increment or default key field, and thus
	// no key violation.
	int fullKeyCount = 0;
	for (int i=0; i<this.getColumnCount(); ++i) if (isKey(i)) ++fullKeyCount;

	if (numKey == 0) {
		// No keys: just insert the row and be done with it.
		rowInserted = insertRow(rowIndex);
	} else {
		// Check the key fields against other items in the table
		for (int r=0; ; ++r) {
			if (r == getRowCount()) {
				// Didn't find a match: insert a new row.
				rowInserted = insertRow(rowIndex);
				break;
			}

			// See if this row matches
			boolean beq = true;
			for (int i=0; i<colNames.length; ++i) {
//System.out.println("r = " + r + " col " + i + " coli = " + coli[i] + " valueAt = " + getValueAt(r,coli[i]) + " val = " + vals[i]);
				if (isKey(coli[i]) && !getValueAt(r,coli[i]).equals(vals[i])) {
					beq = false;
					break;
				}
			}

			// It does match!  Let's use it!
			if (beq) {
				// We equal a pre-existing key...
				if ((getStatus(r) & DELETED) != 0) {
					// If it's been marked deleted, just un-mark it.
//System.out.println("r = " + r);
					setStatus(r, getStatus(r) & ~DELETED);
					rowInserted = r;
					break;
				} else if (numKey == fullKeyCount) {
					// An actual duplicate; throw exception
					throw new KeyViolationException("Key violation");	// Keep msg simple for now
				}
			}
		}
	}
	// NOW: the correct row has been inserted.

	// Set any values necessary
	for (int i=0; i<colNames.length; ++i) {
		setValueAt(vals[i], rowInserted, findColumn(colNames[i]));
	}
	return rowInserted;
}
public int insertRow(int rowIndex, String colName, Object val)
throws KeyViolationException
{
	return insertRow(rowIndex, new String[] {colName}, new Object[] {val});
}
///** Undoes any edits... */
// Not really needed... undo is done by re-querying DB through DBModel
//public void resetRow(int row)
//{
//	for (int i=0; i<colNames.length; ++i) {
//		setValueAt(getOrigValueAt(row,i), row,i);
//	}
//}
// ===============================================================
// Implementation of TableModel

// --------------------------------------------------
public int findColumn(String colName)
	{ return schema.findCol(colName); }
public Class getColumnClass(int colIndex)
	{ return schema.getCol(colIndex).getType().getObjClass(); }
public JType getJType(int row, int col)
	{ return schema.getCol(col).getType(); }
//public JType getColumnJType(int colIndex)
//	{ return schema.getCol(colIndex).getType(); }
//public JType getJType(int row, int col)
//	{ return getColumnJType(col); }
public String getColumnName(int colIndex)
	{ return (colPrefix == null ? "" : colPrefix + "_") +
		  schema.getCol(colIndex).getName(); }
// --------------------------------------------------
public boolean isKey(int columnIndex)
	{ return schema.getCol(columnIndex).isKey(); }
/** Allow editing of all non-key fields. */
public boolean isCellEditable(int rowIndex, int columnIndex)
	{ return !isKey(columnIndex); }
// --------------------------------------------------
public void setValueAt(Object val, int row, int col)
{
	
//System.out.println("SchemaBuf.setValueAt(" + val + ", " + row + ", " + col + ")");
//if (col == 0 && getColumnName(col).equals("entityid")) {
//	System.out.println("hoi");
//}
	// Figure out whether our new value is same or different from original
	SqlRow srow = (SqlRow)rows.get(row);

	boolean ochanged = valueChanged(srow, col);
	srow.data[col] = val;		// Make the change

	// Figure out status, etc...
	boolean nchanged = valueChanged(srow, col);
	if (ochanged == nchanged) return;
	int status = getStatus(row);
	int nstatus = (nchanged ? status | CHANGED : status & ~CHANGED);
	if (status != nstatus) this.setStatus(row, nstatus);

	// Update listeners
	fireTableCellUpdated(row, col);
}
///** Convenience function, select column by name! */
//public void setValueAt(Object val, int row, String col)
//{
//	setValueAt(val, row, findColumn(col));
//}
// --------------------------------------------------
public int getColumnCount()
	{ return schema.getColCount(); }
public int getRowCount()
	{ return rows.size(); }
public Object getValueAt(int rowIndex, int colIndex)
{
	SqlRow r = (SqlRow)rows.get(rowIndex);
	return r.data[colIndex];
}
///** Convenience function, select column by name! */
//public Object getValueAt(int row, String col)
//{
//	return getValueAt(row, findColumn(col));
//}
// --------------------------------------------------
//// ===============================================================
//// Implementation of CitibobTableModel (prototype stuff)
//public List getPrototypes()
//{
//	Schema s = getSchema();
//	if (s == null) return null;
//	return s.getPrototypes();
//}
// ================================================================
// Deprecated stuff

// --------------------------------------------------
// public Object getOrigValueAt(int rowIndex, int colIndex)
// {
// 	SqlRow r = (SqlRow)rows.get(rowIndex);
// 	return r.origData[colIndex];
// }
// --------------------------------------------------

// --------------------------------------------------
/** Tells if the value has changed since the last setOrigData() or row insertion. */
// public boolean valueChanged(int row, int col)
// {
// 	SqlRow r = (SqlRow)rows.get(row);
// 	Object origData = r.origData[col];
// 	Object curData = r.data[col];
// 	return !curData.equals(origData);
// }
// =========================================
// boiler Plate listener code

LinkedList listeners = new LinkedList();
public void addSchemaBufListener(Listener l)
	{ listeners.add(l); }
public void removeSchemaBufListener(Listener l)
	{ listeners.remove(l); }
public void fireStatusChanged(int row)
{
	for (Iterator ii=listeners.iterator(); ii.hasNext();) {
		Listener l = (Listener)ii.next();
		l.statusChanged(row);
	}
}


}
