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
 * SqlLogger.java
 *
 * Created on June 8, 2007, 1:08 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.jschema.log;

import static citibob.sql.ConsSqlQuery.*;
import java.sql.*;
import citibob.sql.*;
import java.util.*;
import citibob.jschema.*;

public class QueryLogRec
{
public int type;		// INSERT, UPDATE, DELETE
public String table;	// Table affected
public List<ColUpdate> keyCols = new ArrayList();	// Value of columns identifying this record
public List<ColUpdate> valCols = new ArrayList();	// Non-key columns --- value updated or inserted
	
public static class ColUpdate {
	public String name;
	public String value;
	public String oldval;
	public ColUpdate(String name, String oldval, String value)
		{ this.name = name; this.oldval = oldval; this.value = value;}

}

/** For INSERT queries only --- if we can read the WHERE clause, we can use
 this for UPDATE and DELETE queries as well. */
public QueryLogRec(ConsSqlQuery q, Schema schema)
	{ this(q, schema, null, -1); }

/** For INSERT, UPDATE and DELETE queries.
 @param sb SchemaBuf used to generate this query; null if query was
 generated by hand in a Wizard (for INSERT queries only; UPDATE and DELETE
 queries require sb because we're not smart enough to read the where clause)
 @param row Row in the SchemaBuf used to generate query; -1 if sb == null.
 @param schema Schema must match first table in query
 @param query the query we're logging
 */
public QueryLogRec(ConsSqlQuery query, Schema schema, SchemaBuf sb, int row)
{
	this.type = query.getType();
	this.table = query.getMainTable();
	
	TreeMap<String,NVPair> inserted = new TreeMap();
	for (NVPair nv : query.getColumns()) inserted.put(nv.name, nv);
	
	for (int i=0; i<schema.getColCount(); ++i) {
		// Retrieve info on column + data from Query and SchemaBuf (if present)
		Column col = schema.getCol(i);		
		NVPair nv = inserted.get(col.getName());
		
		// Determine old value
		String oldsqlval = "null";
		if (sb != null) {
			Object oldval = sb.getOrigValueAt(row,i);
			if (oldval != null) oldsqlval = col.getType().toSql(oldval);			
		}
		
		// Determine the value we ended up setting
		String sqlval = null;		// The value we ended up setting
		boolean haveval = false;
		if (nv != null) {
			// Take the value we actually inserted/updated, if it exists.
			sqlval = nv.value;
			haveval = true;
		} else if (sb != null && col.isKey()) {
			// Take value in the SchemaBuf, which was set after the insert
			// due to a SqlSerial data type -- or had the key all along
			// in the case of an update.  Ignore if null
			Object setval = sb.getValueAt(row,i);
			if (setval != null) {
				// Convert setval to a Sql string
				sqlval = col.getType().toSql(setval);
				haveval = true;
			}
		}

		
		
		if (col.isKey()) {
			keyCols.add(new ColUpdate(col.getName(), oldsqlval, sqlval));
//			System.out.println("     Key field: " + col.getName() + " = " + sqlval);
		} else if (type != DELETE && haveval) {
			valCols.add(new ColUpdate(col.getName(), oldsqlval, sqlval));
//			System.out.println("     inserted field: " + col.getName() + " = " + sqlval);
		}
	}
	System.out.println("Done");	

}
}


