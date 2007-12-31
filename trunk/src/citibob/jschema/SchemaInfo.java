/*
 * SchemaInfo.java
 *
 * Created on December 30, 2007, 10:15 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.jschema;

/** Stuff going with a Schema in a SchemaBuf2Writer */
public class SchemaInfo {
	public Schema schema;	// Schemas used for SQL update generation against sbuf
	public String table;	// Tables to generate SQL for each...
	public int[] schemaMap;	// Column i in schema = column colmap[i] in sbuf.getSchema().  User need not set.
	public SchemaInfo(Schema schema, String table) {
		this.schema = schema;
		if (table == null) table = schema.getDefaultTable();
		this.table = table;
	}
	public SchemaInfo(Schema schema) {
		this(schema, schema.getDefaultTable());
	}
}
