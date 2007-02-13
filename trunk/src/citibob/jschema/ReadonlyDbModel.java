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
