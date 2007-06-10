/*
 * SqlTypeFactory.java
 *
 * Created on January 28, 2007, 9:23 PM
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
public interface SqlTypeSet
{
	
public SqlType getSqlType(ResultSet rs, int col) throws SQLException;
public SqlType getSqlType(ResultSetMetaData md, int col) throws java.sql.SQLException;

/** @param col the first column is 1, the second is 2, ...
 @returns an SqlType, given one of the basic types in java.sql.Types.  If N/A,
 or not yet implemented as an SqlType, returns null. */
public SqlType getSqlType(int type, int precision, int scale, boolean nullable);

}
