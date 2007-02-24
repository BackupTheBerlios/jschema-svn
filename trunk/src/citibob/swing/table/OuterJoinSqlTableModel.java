/*
 * OuterJoinRSTableModel.java
 *
 * Created on February 12, 2007, 8:50 PM
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
public class OuterJoinSqlTableModel extends SqlTableModel
{

//public OuterJoinRSTableModel()
//{
//	super();
//}
	
MainSqlTableModel main;	// The main table
String joinCol;

/** @param main main table we're doing outer join to
 @param main main table we're joining to
 @param joinMap indicates the row that each key value appears on
 @param joinCol name of column in this table to join to
 */
public OuterJoinSqlTableModel(MainSqlTableModel main, String joinCol,
SqlTypeSet tset, String sql)
{
	super(tset, sql);
	this.main = main;
	this.joinCol = joinCol;
}

public void executeQuery(Statement st, String sql) throws SQLException
{
	setRowCount(main.getRowCount());
	super.executeQuery(st, sql);
}

public void addAllRows(ResultSet rs)
throws SQLException
{
	ResultSetMetaData meta = rs.getMetaData();
	int ncol = meta.getColumnCount();
	//int ijoinCol = meta.this.findColumn(joinCol);
	Map<Object,Integer> joinMap = main.getJoinMap();
	while (rs.next()) {
		int row = joinMap.get(rs.getObject(joinCol));
		setRow(row, rs);
	}
}

/** Once we've figured out which row in our table a particular ResultSet
record goes in, integrate it into our data table.  This will often be overridden. */
public void setRow(int row, ResultSet rs) throws SQLException
{
	ResultSetMetaData meta = rs.getMetaData();
	int ncol = meta.getColumnCount();
	for (int i=0; i<ncol; ++i) {
		this.setValueAt(rs.getObject(i), row, i);
	}
}


}
