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
 * OuterJoinRSTableModel.java
 *
 * Created on February 12, 2007, 8:50 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.sql;

import citibob.sql.*;
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
