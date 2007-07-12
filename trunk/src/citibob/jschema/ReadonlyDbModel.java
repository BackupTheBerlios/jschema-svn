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
 * ReadOnlyDbModel.java
 *
 * Created on February 12, 2007, 9:57 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.jschema;

import java.sql.*;

/**
 * A DbModel that controls read-only data --- good for use with ad-hoc RSTableModel
 * @author citibob
 */
public abstract class ReadonlyDbModel implements DbModel
{
	
///** Initialize component to receive data.  Might be needed if some kind of database lookup is needed. */
//void doInit(Statement st) throws java.sql.SQLException;
///** Get Sql query to re-select current record
//* from database.  When combined with an actual
//* database and the SqlDisplay.setSqlValue(), this
//* has the result of refreshing the current display. */
//void doSelect(Statement st) throws java.sql.SQLException;
///** Clear all buffered data from this component.  Then there
//is no current record. */
//void doClear();

/** Have any of the values here changed and not stored in the DB?  I.e. would calling doUpdate() cause any changes to the database? */
public boolean valueChanged() { return false; }


/** Get Sql query to flush updates to database.
* Only updates records that have changed; returns null
* if nothing has changed. */
public void doUpdate(Statement st) throws java.sql.SQLException {}

/** Get Sql query to insert record into database,
* assuming it isn't already there. */
public void doInsert(Statement st) throws java.sql.SQLException {}

/** Get Sql query to delete current record. */
public void doDelete(Statement st) throws java.sql.SQLException {}

	
}
