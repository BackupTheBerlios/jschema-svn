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

import javax.swing.table.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * A cell renderer and editor that go together for a table column.
 * This is a convenience class.
 * @author citibob
 */
public abstract class RenderEdit {

protected TableCellRenderer renderer;
protected TableCellEditor editor;

public TableCellRenderer getRenderer() { return renderer; }
public TableCellEditor getEditor() { return editor; }
	
}
