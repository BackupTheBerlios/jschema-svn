/*
 * SuperSchemaBuf.java
 *
 * Created on January 28, 2007, 10:12 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.jschema;

import javax.swing.table.*;
import citibob.swing.table.*;
import citibob.swing.typed.*;
import java.sql.*;
import citibob.sql.*;
import javax.swing.event.*;
import java.util.*;

/**
 * Represents a SchemaBuf, plus a bunch of other ad-hoc columns.  This is used
 for generating reports in which changes to the columns covered by the SchemaBuf
 can be saved back to the database.
 * @author citibob
 */
public class SuperSchemaBuf extends AbstractTableModel
implements JTypeTableModel
{

public static class ColInfo
{
	JType jtype;
	String name;
	boolean editable;
}
	
SchemaBuf sbuf;		// The base schemaBuf
ColInfo[] xcols;
ArrayList<Object[]> xdata;	// The extra data (row-major format)
static String prefix = "x.";		// All the extra columns start with the prefix

/** Creates a new instance of SuperSchemaBuf */
public SuperSchemaBuf(SchemaBuf sbuf)
{
}

/** Return SqlType for a cell.  If type depends only on col, ignores the row argument. */
public JType getJType(int row, int col)
{
	if (col < sbuf.getColumnCount()) return sbuf.getJType(row, col);
	return xcols[col - sbuf.getColumnCount()].jtype;
}
/** Return SqlType for a cell.  If type depends only on col, ignores the row argument. */
public String getColumnName(int col)
{
	if (col < sbuf.getColumnCount()) return sbuf.getColumnName(col);
	return prefix + xcols[col - sbuf.getColumnCount()].name;
}
public boolean isCellEditable(int row, int col)
{
	if (col < sbuf.getColumnCount()) return sbuf.isCellEditable(row, col);
	return xcols[col - sbuf.getColumnCount()].editable;	
}
public void setValueAt(Object val, int row, int col)
{
	if (col < sbuf.getColumnCount()) sbuf.setValueAt(val, row, col);
	else {
		xdata.get(row)[col - sbuf.getColumnCount()] = val;
		fireTableCellUpdated(row, col);
	}
}
public Object getValueAt(int row, int col)
{
	if (col < sbuf.getColumnCount()) return sbuf.getValueAt(row, col);
	return xdata.get(row)[col - sbuf.getColumnCount()];
}
public int findColumn(String columnName)
{
	if (columnName.startsWith(prefix)) {
		String name = columnName.substring(prefix.length());
		for (int i=0; i<xcols.length; ++i) {
			if (name.equals(xcols[i].name)) return i + sbuf.getColumnCount();
		}
		return -1;
	} else {
		return sbuf.findColumn(columnName);
	}
}

public int getColumnCount()
{
	return sbuf.getColumnCount() + xcols.length;
}

public int getRowCount()
{
	return sbuf.getRowCount();
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
//		if (col_u == TableModelEvent.ALL_COLUMNS) {
//			this.fireTableRowsUpdated(e_u.getFirstRow(), e_u.getLastRow());
//		} else {
//			// Re-map the columns.
////System.out.println("col_u = " + col_u);
////for (int i = 0; i < colMap.length; ++i) System.out.println(colMap[i] + " " + model_u.getColumnName(colMap[i]));
//			int col_t = iColMap[col_u];
////System.out.println("ColPermuteTableModel: col_u = " + col_u + ", col_t = " + col_t + " " + this);
////System.out.println("rows = " +e_u.getFirstRow() + ", " + e_u.getLastRow());
//			if (col_t == -1) return;
//			for (int i=e_u.getFirstRow(); i <= e_u.getLastRow(); ++i)
//				this.fireTableCellUpdated(i, col_t);
////			this.fireTableChanged(new TableModelEvent(
////				this, col_t, e_u.getFirstRow(), e_u.getLastRow(), type));
////			this.fireTableRowsUpdated(e_u.getFirstRow(), e_u.getLastRow());
//		}
	break;
	case TableModelEvent.INSERT : {
		// Not yet tested
		int ncol = this.getColumnCount();
		int ninsert = e_u.getLastRow() - e_u.getFirstRow() + 1;
		xdata.ensureCapacity(xdata.size() + ninsert);
		for (int i=0; i<ninsert; ++i) xdata.add(null);
		for (int i=xdata.size() - 1; i >= e_u.getFirstRow(); --i)
			xdata.set(i+ninsert, xdata.get(i));
		for (int i=e_u.getFirstRow(); i<=e_u.getLastRow(); ++i)
			xdata.set(i, new Object[ncol]);
//		this.fireTableRowsInserted(e_u.getFirstRow(), e_u.getLastRow());
	}
	case TableModelEvent.DELETE :
		// Not yet tested
		int ndelete = e_u.getLastRow() - e_u.getFirstRow() + 1;
		for (int i = e_u.getLastRow() + 1; i < xdata.size(); ++i)
			xdata.set(i-ndelete, xdata.get(i));
		for (int i=0; i<ndelete; ++i) xdata.remove(xdata.size()-1);
//		this.fireTableRowsDeleted(e_u.getFirstRow(), e_u.getLastRow());
	break;
	}
	
	fireTableChanged(e_u);
}



//	int type = e_u.getType();
//	int col_u = e_u.getColumn();
//	switch(type) {
//	case TableModelEvent.UPDATE :
//		if (col_u == TableModelEvent.ALL_COLUMNS) {
//			this.fireTableRowsUpdated(e_u.getFirstRow(), e_u.getLastRow());
//		} else {
//			// Re-map the columns.
////System.out.println("col_u = " + col_u);
////for (int i = 0; i < colMap.length; ++i) System.out.println(colMap[i] + " " + model_u.getColumnName(colMap[i]));
//			int col_t = iColMap[col_u];
////System.out.println("ColPermuteTableModel: col_u = " + col_u + ", col_t = " + col_t + " " + this);
////System.out.println("rows = " +e_u.getFirstRow() + ", " + e_u.getLastRow());
//			if (col_t == -1) return;
//			for (int i=e_u.getFirstRow(); i <= e_u.getLastRow(); ++i)
//				this.fireTableCellUpdated(i, col_t);
////			this.fireTableChanged(new TableModelEvent(
////				this, col_t, e_u.getFirstRow(), e_u.getLastRow(), type));
////			this.fireTableRowsUpdated(e_u.getFirstRow(), e_u.getLastRow());
//		}
//	break;
//	case TableModelEvent.INSERT :
//		this.fireTableRowsInserted(e_u.getFirstRow(), e_u.getLastRow());
//	case TableModelEvent.DELETE :
//		this.fireTableRowsDeleted(e_u.getFirstRow(), e_u.getLastRow());
//	break;
//	}


}
