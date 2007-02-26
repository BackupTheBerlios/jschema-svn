/*
 * MainSqlTableModel.java
 *
 * Created on February 14, 2007, 12:04 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.sql;

import java.sql.*;
import citibob.swing.typed.*;
import java.util.*;

/**
 *
 * @author citibob
 */
public class MainSqlTableModel extends SqlTableModel
{

String joinCol;
HashMap<Object,Integer> joinMap;

/** @param joinCol Name of column (in TableModel) used to join on. */
public MainSqlTableModel(SqlTypeSet tset, String joinCol, String sql)
{
	super(tset, sql);
	this.joinCol = joinCol;
	joinMap = new HashMap();
}

public Map<Object,Integer> getJoinMap() { return joinMap; }

public void executeQuery(Statement st, String sql) throws SQLException
{
System.out.println("MainSqlTableModel.executeQuery: " + sql);
	super.executeQuery(st, sql);

	// Re-set the joinMap
	int iJoinCol = findColumn(joinCol);
	joinMap.clear();
	for (int i=0; i<this.getRowCount(); ++i) {
		joinMap.put(getValueAt(i, iJoinCol), i);
	}
}

}
