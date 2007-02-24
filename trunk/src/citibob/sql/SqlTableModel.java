package citibob.sql;
/*
 * SqlTableModel.java
 *
 * Created on February 13, 2007, 11:59 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

import citibob.swing.table.*;
import java.sql.*;
import citibob.swing.typed.*;

/**
 * An RSTableModel that carries around its own SQL query, so it can always execute its own query.
 * @author citibob
 */
public class SqlTableModel extends RSTableModel
{
String sql;

public void setSql(String sql) {this.sql = sql; }
public String getSql() { return sql; }
public SqlTableModel(SqlTypeSet tset)
{
	super(tset);
}

public SqlTableModel(SqlTypeSet tset, String sql)
{
	super(tset);
	this.sql = sql;
}

public void executeQuery(Statement st) throws SQLException
{
	executeQuery(st, sql);
}

}
