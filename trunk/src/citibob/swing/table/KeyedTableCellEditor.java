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

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.*;
import java.util.*;
import citibob.jschema.swing.*;
//import citibob.swing.calendar.JDateChooser;
import citibob.swing.typed.*;
import citibob.types.KeyedModel;

public class KeyedTableCellEditor 
extends AbstractCellEditor implements TableCellEditor {
	
	// This is the component that will handle the editing of the cell value
    JKeyedComboBox combo; // = new JDateChooser();
	
	public KeyedTableCellEditor(KeyedModel kmodel)
	{
		combo = new JKeyedComboBox(kmodel);
	}
	

    // This method is called when a cell value is edited by the user.
    public Component getTableCellEditorComponent(JTable table, Object value,
	boolean isSelected, int rowIndex, int vColIndex) {
System.out.println("KeyedTableCellEditor.getTableCellEditorComponent");
		combo.setValue(value);
        return combo;
    }
    
    // This method is called when editing is completed.
    // It must return the new value to be stored in the cell.
    public Object getCellEditorValue() {
		return combo.getValue();
    }
}