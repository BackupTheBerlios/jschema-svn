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
 * MultiTableCellEditor.java
 *
 * Created on June 30, 2005, 11:41 PM
 */

package citibob.swing.table;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.util.*;
import java.awt.*;
import citibob.swing.*;

/**
 *
 * @author citibob
 */
public class MultiTableCellEditor extends MultiCellEditor implements TableCellEditor {

public Component getTableCellEditorComponent(JTable table, Object value,
	boolean isSelected, int row, int column)
{
	TableCellEditor tcur = (TableCellEditor)getCur();
	return tcur.getTableCellEditorComponent(table, value, isSelected, row, column);
}

}
