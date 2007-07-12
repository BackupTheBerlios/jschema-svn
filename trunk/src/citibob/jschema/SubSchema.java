/*
JSchema: library for GUI-based database applications
This file Copyright (c) 2006-2007 by Robert Fischer

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
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
