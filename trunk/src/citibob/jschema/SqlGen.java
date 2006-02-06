/*
JSchema: library for GUI-based database applications
This file Copyright (c) 2006 by Robert Fischer

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/
package citibob.jschema;

import java.sql.*;
import citibob.sql.SqlQuery;

/** NOTE: Implementations of this interface do not NECESSARILY have to be Schema-based; however, it is expected that most will be. */
public interface SqlGen extends RowStatusConst
{
int getRowCount();

/** Has the given row changed value since it was loaded with data? */
boolean valueChanged(int row);

/** Was the given row inserted or deleted since being read from database? */
public int getStatus(int row);

/** After we've updated the DB, use this to clear status bits. */
public void setStatus(int row, int status);

// ===============================================================
// Read rows from the database

/** Add a new row from the current place in a result set */
void addRow(ResultSet rs) throws java.sql.SQLException;

/** Add all the rows from a result set, starting with rs.next(), then close the result set. */
void addAllRows(ResultSet rs) throws java.sql.SQLException;

/** Removes a row from the buffer... */
void removeRow(int row);

/** Undoes any edits... */
//public void resetRow(int row);

///** Convenience functions for single-row SchemaBufs */
//void setOneRow(ResultSet rs) throws java.sql.SQLException;

// ===============================================================
// Write rows to the database

/** Makes update query update column(s) represented by this object. */
void getUpdateCols(int row, SqlQuery q, boolean updateUnchanged);
void getInsertCols(int row, SqlQuery q, boolean insertUnchanged);

/** Adds the where clauses corresponding to the currently stored keyfield values. */
void getWhereKey(int row, SqlQuery q, String table);
//void getInsertKey(int row, SqlQuery q);

/** If schema-based, just a passthrough to the underlying Schema. */
void getSelectCols(SqlQuery q, String table);

}