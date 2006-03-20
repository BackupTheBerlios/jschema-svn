/*
 * DbKeyedModel.java
 *
 * Created on March 19, 2006, 5:20 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.sql;

import java.sql.*;
import java.util.*;

/**
 *
 * @author citibob
 */
public class DbKeyedModel extends KeyedModel
implements DbChangeModel.Listener
{

DbChangeModel change;
String idTableName;
String idFieldName;
String nameFieldName;
String orderFieldName;

public DbKeyedModel(Statement st, DbChangeModel change,
String idTableName, String idFieldName,
String nameFieldName, String orderFieldName)
throws SQLException
{
	super();
	this.idTableName = idTableName;
	this.idFieldName = idFieldName;
	this.nameFieldName = nameFieldName;
	this.orderFieldName = orderFieldName;
	this.change = change;
	requery(st);
	change.addListener(idTableName, this);
}

/** Re-load keyed model from database... */
public void requery(Statement st) throws SQLException
{
	clear();
	ResultSet rs = st.executeQuery("select " + idFieldName + ", " + nameFieldName + " from " +
			idTableName + " order by " + orderFieldName);
	addAllItems(rs, 1, 2);
	rs.close();
}

/** Called when the data potentially changes in the database. */
public void tableChanged(Statement st, String table)
throws SQLException
{
	requery(st);
}

}
