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
package citibob.swing.table;

import java.util.*;
import javax.swing.table.*;
import javax.swing.event.*;
import citibob.sql.*;
import citibob.types.JType;

public interface JTypeTableModel extends CitibobTableModel
{
//	/** Return SqlType for an entire column --- or null, if this column does not have a single SqlType. */
//	public JType getColumnJType(int col);

	/** Return SqlType for a cell.  If type depends only on col, ignores the row argument. */
	public JType getJType(int row, int col);

	/** Convenience function */
	public JType getJType(int row, String col);

//	/** Return a value we can sort on --- usually the same as getValueAt().  But, eg,
//	if a column is an integer key with a KeyedModel, then it would be sorted according
//	to the KeyedModel sort order. */
//	public Object getSortValueAt(int row, int col);


// --------------------------------------------------------

}
