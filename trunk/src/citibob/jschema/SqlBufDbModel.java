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

/**
 *
 * @author citibob
 */
public abstract class SqlBufDbModel extends SchemaBufDbModel
{

/** @param proto True if we just want the columns. */
public abstract String getSelectSql(boolean proto);


/** Creates a new instance of SqlBufDbModel */
public SqlBufDbModel(SqlRunner str, Schema[] typeSchemas, String[] keyFields, SqlTypeSet tset,
final SchemaInfo[] sinfos, final DbChangeModel dbChange)
{
	final SchemaBuf sb = new SchemaBuf(str, getSelectSql(true), typeSchemas, keyFields, tset);
	str.execUpdate(new UpdRunnable() {
	public void run(SqlRunner str) {
		init(sb, null, sinfos, dbChange);
	}});
}
/** Creates a new instance of SqlBufDbModel.
 @updateSchema the table to which updates will be generated. */
public SqlBufDbModel(SqlRunner str, Schema[] typeSchemas, String[] keyFields, SqlTypeSet tset,
Schema updateSchema, String updateTable, DbChangeModel dbChange)
{
	this(str, typeSchemas, keyFields, tset,
		new SchemaInfo[] {new SchemaInfo(updateSchema, updateTable)},
		dbChange);
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
