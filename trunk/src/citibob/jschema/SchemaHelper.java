/*
JSchema: library for GUI-based database applications
This file Copyright (c) 2006 by Robert Fischer

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/
package citibob.jschema;

import citibob.sql.ConsSqlQuery;

/** Extra methods for Schema, but not included in the interface. */
public class SchemaHelper
{

public static void getSelectCols(Schema schema, ConsSqlQuery q, String table, String colPrefix)
{
	for (int col = 0; col < schema.getColCount(); ++col) {
		Column c = schema.getCol(col);
		q.addColumn(table + "." + c.getName() +
			(colPrefix == null ? "" : " as " + colPrefix + "_" + c.getName()));
	}
}
// --------------------------------------------------------
/** Adds key fields to where clause; for delete and select queries.
 * For columns, this will just check the isKey() field and add itself
 * or not.  For records, will call getWhereKey on children
 * (unless the record has special knowledge about itself.) */
public static void getWhereKey(Schema schema, ConsSqlQuery q, String table, Object[] whereKey)
{
	for (int col = 0; col < schema.getColCount(); ++col) {
		Column c = schema.getCol(col);
		if (c.isKey()) {
			q.addWhereClause(table + "." + c.getName() + " = " +
				 c.getType().toSql(whereKey[col]));
		}
	}
}

}
