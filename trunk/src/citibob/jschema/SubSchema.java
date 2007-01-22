/*
 * SubSchema.java
 *
 * Created on January 19, 2007, 9:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.jschema;

/**
 *
 * @author citibob
 */
public class SubSchema implements Schema
{

int[] cols;	// Map from our column #s to schema's column #s
int[] icols;	// Map from schema's col # to our col #
Schema schema;

/** Creates a new instance of SubSchema */
public SubSchema(Schema schema, String[] scols)
{
	this.schema = schema;
	cols = new int[scols.length];
	icols = new int[schema.getColCount()];
	for (int i=0; i<scols.length; ++i) {
		cols[i] = schema.findCol(scols[i]);
		icols[cols[i]] = i;
	}
}

/** The table with which this schema is MOST COMMONLY used. */
public String getDefaultTable() { return schema.getDefaultTable(); }

/** Number of cols in this Schema */
public int getColCount() { return cols.length; }

/** Retrieve info on a column by number. */
public Column getCol(int colNo) { return schema.getCol(cols[colNo]); }
public Column getCol(String name)
	{ return schema.getCol(name); }

/** Retrieve a column's index by name. */
public int findCol(String name)
	{ return icols[schema.findCol(name)]; }

}
