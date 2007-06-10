/*
 * BaseSqlTypeFactory.java
 *
 * Created on January 28, 2007, 9:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.sql;

import java.sql.*;

/**
 *
 * @author citibob
 */
public abstract class BaseSqlTypeSet implements SqlTypeSet
{

public SqlType getSqlType(ResultSet rs, int col) throws SQLException
{
	return getSqlType(rs.getMetaData(), col);
}
public SqlType getSqlType(ResultSetMetaData md, int col) throws SQLException
{
	boolean nullable = (md.isNullable(col) != ResultSetMetaData.columnNoNulls);
//System.out.println("col = " + col);
	return getSqlType(md.getColumnType(col), md.getPrecision(col), md.getScale(col), nullable);
}
	
}
