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
import citibob.util.KeyedModel;

/**
 *
 * @author citibob
 */
public class DbKeyedModel extends KeyedModel
implements DbChangeModel.Listener
{

DbChangeModel change;
String idTableName;
//String idFieldName;
//String nameFieldName;
//String orderFieldName;

String sql;

/** @param change model that will tell us when we need to requery.
 @param idTableName Name of table on which a change should trigger a requery.
 @parm sql Query to generate key/value pairs; ID must be in column 1, Name in column 2. */
public DbKeyedModel(Statement st, DbChangeModel change,
String idTableName, String sql)
throws SQLException
{
	super();
	this.sql = sql;
	this.idTableName = idTableName;
	this.change = change;
	requery(st);
	if (change != null) change.addListener(idTableName, this);
}
public DbKeyedModel(Statement st, DbChangeModel change,
String idTableName, String idFieldName,
String nameFieldName, String orderFieldName)
throws SQLException
{
	this(st, change, idTableName,
		"select " + idFieldName + ", " + nameFieldName + " from " +
			idTableName + " order by " + orderFieldName);
}

/** Re-load keyed model from database... */
public void requery(Statement st) throws SQLException
{
	clear();
//	ResultSet rs = st.executeQuery("select " + idFieldName + ", " + nameFieldName + " from " +
//			idTableName + " order by " + orderFieldName);
	ResultSet rs = st.executeQuery(sql);
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
