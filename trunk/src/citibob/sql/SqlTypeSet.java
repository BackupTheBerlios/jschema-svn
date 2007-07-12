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
