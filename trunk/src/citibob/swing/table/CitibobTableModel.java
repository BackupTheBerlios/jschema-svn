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
package citibob.swing.table;

import java.util.*;
import javax.swing.table.*;
import javax.swing.event.*;

public interface CitibobTableModel extends TableModel
{
	/** Model provies "prototype" information, i.e. a sample row of data. */
//	List getPrototypes();

	/** These, you will get for free if you subclass AbstractTableModel. */
	void fireTableChanged(TableModelEvent e);

	/** Finds a column's index by name --- also implemented in AbstractTableModel.
	 Returns -1 if column names doesn't exist or is null. */
	int findColumn(String name);

}
