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
