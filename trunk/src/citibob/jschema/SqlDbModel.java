/*
 * SqlDbModel.java
 *
 * Created on February 12, 2007, 10:06 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.jschema;


import java.sql.*;
import citibob.sql.*;

/**
 *
 * @author citibob
 */
public class SqlDbModel extends ReadonlyDbModel implements TableDbModel
{
SqlTableModel model;

/** Creates a new instance of SqlDbModel */
public SqlDbModel(SqlTableModel model)
{
	this.model = model;
}


public SqlTableModel getTableModel() { return model; }
// ========================================================
// DbModel
/** Initialize component to receive data.  Might be needed if some kind of database lookup is needed. */
public void doInit(Statement st) throws java.sql.SQLException {}

/** Get Sql query to re-select current record
* from database.  When combined with an actual
* database and the SqlDisplay.setSqlValue(), this
* has the result of refreshing the current display. */
public void doSelect(Statement st) throws java.sql.SQLException
{
//	ResultSet rs = st.executeQuery(sql);
	model.executeQuery(st);
}

/** Clear all buffered data from this component.  Then there
is no current record. */
public void doClear()
{
	model.setRowCount(0);
}

}
