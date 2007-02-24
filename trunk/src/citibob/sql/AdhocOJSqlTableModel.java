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
}

public void executeQuery(Statement st, String sql) throws SQLException
{
	setNumRows(0);
	ResultSet rs = null;
	try {
		rs = st.executeQuery(sql);
		addAllRows(rs);
	} finally {
		try { rs.close(); } catch(Exception e) {}
	}
}

///** Once we've figured out which row in our table a particular ResultSet
//record goes in, integrate it into our data table.  This will often be overridden. */
//public void setRow(int row, ResultSet rs) throws SQLException

}
