/*
 * AdhocOJRSTableModel.java
 *
 * Created on February 13, 2007, 11:27 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.sql;

import citibob.swing.table.*;
import java.sql.*;
import javax.swing.table.*;
import javax.swing.event.*;
import citibob.swing.typed.*;
import java.util.*;

/**
 *
 * @author citibob
 */
public abstract class AdhocOJSqlTableModel extends OuterJoinSqlTableModel
{

public AdhocOJSqlTableModel(MainSqlTableModel main, String joinCol,
Col[] tableCols, SqlTypeSet tset, String sql)
{
	super(main, joinCol, tset, sql);
	setColHeaders(tableCols);
//	setColumnCount(tableCols.length);
//System.out.println("Column Count set to " + getColumnCount());
}

public void executeQuery(Statement st, String sql) throws SQLException
{
//	setNumRows(0);
//	setRowCount(0);
//	for (int i=0; i<main.getRowCount(); ++i) {
//		addRow(new Vector(getColumnCount()));
//	}
	setRowCount(main.getRowCount());
	ResultSet rs = null;
	try {
		rs = st.executeQuery(sql);
		addAllRows(rs);
	} finally {
		try { rs.close(); } catch(Exception e) {}
	}
}

/** Debugging */
public Object getValueAt(int row, int col)
{
	Object o = super.getValueAt(row, col);
//System.out.println("AdhocOJSqlTableModel.getValueAt(" + row + ", " + col + ") = " + o + "(row/col count = " + getRowCount() + " / " + getColumnCount());
	return o;
}
public void setValueAt(Object o, int row, int col)
{
//System.out.println("AdhocOJSqlTableModel.setValueAt(" + row + ", " + col + ") = " + o);
	super.setValueAt(o, row, col);
//System.out.print("      "); getValueAt(row,col);
}
///** Once we've figured out which row in our table a particular ResultSet
//record goes in, integrate it into our data table.  This will often be overridden. */
//public void setRow(int row, ResultSet rs) throws SQLException

}
