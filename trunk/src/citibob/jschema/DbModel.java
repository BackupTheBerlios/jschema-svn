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

/** Implements smart update and delete operations... */
public interface DbModel
{

// This method is like a constructor; will always differ.
// in every implementation.
// void setKey()

/** Initialize component to receive data.  Might be needed if some kind of database lookup is needed. */
void doInit(Statement st) throws java.sql.SQLException;

/** Have any of the values here changed and not stored in the DB?  I.e. would calling doUpdate() cause any changes to the database? */
boolean valueChanged();

/** Get Sql query to re-select current record
* from database.  When combined with an actual
* database and the SqlDisplay.setSqlValue(), this
* has the result of refreshing the current display. */
void doSelect(Statement st) throws java.sql.SQLException;

/** Get Sql query to flush updates to database.
* Only updates records that have changed; returns null
* if nothing has changed. */
void doUpdate(Statement st) throws java.sql.SQLException;

/** Get Sql query to insert record into database,
* assuming it isn't already there. */
void doInsert(Statement st) throws java.sql.SQLException;

/** Get Sql query to delete current record. */
void doDelete(Statement st) throws java.sql.SQLException;

/** Clear all buffered data from this component.  Then there
is no current record. */
void doClear();

}
