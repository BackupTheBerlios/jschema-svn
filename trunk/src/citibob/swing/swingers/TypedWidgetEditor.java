/*
 * TypedWidgetEditor.java
 *
 * Created on November 7, 2007, 8:36 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.swing.swingers;

import citibob.swing.typed.*;
import javax.swing.table.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import java.text.*;
import citibob.swing.calendar.*;
import citibob.swing.table.*;
import java.awt.*;

/**
 *
 * @author citibob
 */
public class TypedWidgetEditor extends AbstractCellEditor implements TableCellEditor
{

protected TypedWidget tw;
public TypedWidgetEditor(TypedWidget tw) { this.tw = tw;}

/** This method is called when a cell value is edited by the user. */
public Component getTableCellEditorComponent(JTable table, Object value,
boolean isSelected, int rowIndex, int vColIndex)
{
	tw.setValue(value);
	return (Component)tw;
}

/** This method is called when editing is completed.
It must return the new value to be stored in the cell. */
public Object getCellEditorValue()
{
	
	tw.stopEditing();
	
	Object o = tw.getValue();
//	System.out.println("TypedWidgetRenderEdit.getCellEditorValue: " + o);
	return o;
}

}
