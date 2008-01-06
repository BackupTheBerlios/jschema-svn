/*
 * SqlBufDbModel.java
 *
 * Created on December 31, 2007, 1:06 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.jschema;

import citibob.sql.DbChangeModel;
import citibob.sql.SqlRunner;
import citibob.sql.SqlTypeSet;
import citibob.sql.UpdRunnable;
import citibob.app.App;

/**
 *
 * @author citibob
 */
public abstract class SqlBufDbModel extends SchemaBufDbModel
{

/** @param proto True if we just want the columns. */
public abstract String getSelectSql(boolean proto);


/** Creates a new instance of SqlBufDbModel
@param typeSchemas Schemas used to determine type of each column (beyond that from the SQL query)
@param keyFields Which columns are key fields (beyond that as determined by typeSchemas).
@param sinfos Schemas/Tables used to send updates back to database */
protected void init(SqlRunner str, final App app,
Schema[] typeSchemas, String[] keyFields, final SchemaInfo[] updateSchemas)
{
	final SchemaBuf sb = new SchemaBuf(str, getSelectSql(true), typeSchemas, keyFields, app.getSqlTypeSet());
	str.execUpdate(new UpdRunnable() {
	public void run(SqlRunner str) {
		init(sb, null, updateSchemas, app.getDbChange());
	}});
}

/** Creates a new instance of SqlBufDbModel
@param typeSchemas Schemas used to determine type of each column (beyond that from the SQL query)
@param keyFields Which columns are key fields (beyond that as determined by typeSchemas).
@param sinfos Schemas/Tables used to send updates back to database */
public SqlBufDbModel(SqlRunner str, final App app,
Schema[] typeSchemas, String[] keyFields, final SchemaInfo[] updateSchemas)
	{ init(str,app,typeSchemas,keyFields,updateSchemas); }


/** Convenience method.  Takes schemas as strings, looks them up in App */
public SqlBufDbModel(SqlRunner str, App app,
String[] sTypeSchemas, String[] keyFields, String[] sUpdateSchemas)
{
	Schema[] typeSchemas = null;
	SchemaInfo[] updateSchemas = null;

	if (sTypeSchemas != null) {
		typeSchemas = new Schema[sTypeSchemas.length];
		for (int i=0; i<typeSchemas.length; ++i)
			typeSchemas[i] = app.getSchema(sTypeSchemas[i]);
	}
	if (sUpdateSchemas != null) {
		updateSchemas = new SchemaInfo[sUpdateSchemas.length];
		for (int i=0; i<updateSchemas.length; ++i)
			updateSchemas[i] = new SchemaInfo(app.getSchema(sUpdateSchemas[i]));
	}

	init(str, app, typeSchemas, keyFields, updateSchemas);
}



/** Get Sql query to re-select current records
* from database.  When combined with an actual
* database and the SqlDisplay.setSqlValue(), this
* has the result of refreshing the current display. */
public void doSelect(SqlRunner str)
{
	sbuf.setRows(str, getSelectSql(false));
}


}
