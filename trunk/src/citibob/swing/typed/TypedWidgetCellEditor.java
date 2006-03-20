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
 * TypedWidgetCellEditor.java
 *
 * Created on March 20, 2005, 4:57 PM
 */

package citibob.swing.typed;

/**
 *
 * @author citibob
 */
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

/** XXXXXXXXXXXXXXXXXXXXXXXXXXXXX: This class is basically obsolete, used only by DefaultEClauseCellEditor. */
public class TypedWidgetCellEditor
extends AbstractCellEditor
implements TableCellEditor {

Component editorCom;
TypedWidget editorTw;

/** Creates a new instance of TypedWidgetCellEditor.
editor must also implement TypedWidget. */
public TypedWidgetCellEditor(Component editor) {
	// Check types; Cast will tyrow exception on error.
	editorTw = (TypedWidget)editor;
	editorCom = editor;

	// Add listener to JComboBox to finish editing when we select an item
	if (editor instanceof JComboBox) {
		JComboBox cb = (JComboBox)editor;
		cb.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			stopCellEditing();
//			JComboBox cb = (JComboBox)e.getSource();
		}});
	}
/*
	editorCom.addFocusListener(new FocusAdapter() {
	public void focusLost(FocusEvent e) {
		if (stopCellEditing()) fireEditingStopped();
	}});
*/
		this.addCellEditorListener(new CellEditorListener() {
			public void  editingCanceled(ChangeEvent e) {
				System.out.println("Cell Edint cancelled!");
				editorTw.setValue(editorTw.getValue());
			}
		    public void  editingStopped(ChangeEvent e) {
				System.out.println("Cell Editing stopped!");
				editorTw.stopEditing();
				//((JDateChooser)component).getModel().useTmpDay();
			}
		});
}

public TypedWidget getTypedWidget()
	{ return editorTw; }

public Component getTableCellEditorComponent(JTable table, Object value,
boolean isSelected, int row, int column)
{
System.out.println("TypedWidgetCellEditor.getTableCellEditorComponent: " + value);
//System.out.println("editorTw = " + editorTw);
	try {
		editorTw.setValue(value);
	} catch(ClassCastException e) {
		editorTw.setValue(null);
	}
	return editorCom;
}


public Object getCellEditorValue() 
{
	Object ret = editorTw.getValue();
System.out.println("getCellEditorValue: " + ret);
	return ret;
}

//public boolean stopCellEditing()
//{
//	boolean ret = editorTw.stopEditing();
//	if (ret) fireEditingStopped();
//	return ret;
//}
//public void cancelCellEditing()
//{
//	editorTw.setValue(editorTw.getValue());
//	super.cancelCellEditing();
//}

}
