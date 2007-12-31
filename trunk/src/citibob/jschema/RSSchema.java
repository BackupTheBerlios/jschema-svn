/*
 * RSSchema.java
 *
 * Created on December 30, 2007, 6:36 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.jschema;

import citibob.sql.SqlTypeSet;
import java.sql.*;

/**
 *
 * @author citibob
 */
public class RSSchema extends ConstSchema
{

/** Creates a new instance of RSSchema */
public RSSchema(ResultSet rs, Schema[] typeSchemas, String[] keyFields, SqlTypeSet tset)
throws SQLException
{
	ResultSetMetaData md = rs.getMetaData();
	table = null;
	cols = new Column[md.getColumnCount()];
	for (int i=0; i<md.getColumnCount(); ++i) {
		int i1 = i+1;
		cols[i] = new Column(tset.getSqlType(md, i1),
			md.getColumnName(i1), md.getColumnLabel(i1), false);
	}
	
	// Set up data types (and key fields)
	if (typeSchemas != null)
	for (int i=0; i<typeSchemas.length; ++i) {
		Schema schema = typeSchemas[i];
		setJTypes(schema);
	}

	// Set up key fields
	if (keyFields != null)
	for (int i=0; i<keyFields.length; ++i) {
		Column col = cols[findCol(keyFields[i])];
		col.key = true;
	}
	
}

/** Looks for corresponding names, and sets all column types the same
 as same-named columns in schema. */
public void setJTypes(Schema schema)
{
	for (int i=0; i<schema.getColCount(); ++i) {
		Column scol = schema.getCol(i);
		int j = findCol(scol.getName());
		if (j < 0) continue;
		Column tcol = this.getCol(j);
		tcol.jType = scol.getType();
		tcol.key = scol.isKey();
		tcol.label = scol.getLabel();
	}
}
}
