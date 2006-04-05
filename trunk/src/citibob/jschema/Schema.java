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

import java.util.*;

public interface Schema
{
	/** The table with which this schema is MOST COMMONLY used. */
	String getDefaultTable();

	/** Number of cols in this Schema */
	int getColCount();

	/** Retrieve info on a column by number. */
	Column getCol(int colNo);
	Column getCol(String name);
	
	ColIterator colIterator();

	/** Retrieve a column's index by name. */
	int findCol(String name);

	/** This might be more useful... */
	// ColumnIterator colIterator();

	/** Gets a set of prototype values for the columns, of the given types.  This is used to properly set table display parameters in Swing. */
//	List getPrototypes();

//void getWhereKey(SqlQuery q, String table, Object[] whereKey);
//void getSelectCols(SqlQuery q, String table);

}
