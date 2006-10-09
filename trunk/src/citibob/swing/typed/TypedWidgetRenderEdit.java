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
 * TypedWidgetItemCellRenderer.java
 *
 * Created on March 20, 2005, 6:46 PM
 */

package citibob.swing.typed;

import javax.swing.table.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import java.text.*;
import citibob.swing.calendar.*;
import citibob.swing.table.*;
import java.awt.*;

/** Provides renderer and editor appropriate for an java.util.TypedWidget
 * (day only, no time).  Works only for TypedWidgets that extend Component. */
public class TypedWidgetRenderEdit extends RenderEdit
{

public TypedWidgetRenderEdit(TypedWidget widget1, TypedWidget widget2)
{
	renderer = new Renderer(widget1);
	editor = new Editor(widget2);
//	try {
//		editor = new Editor((TypedWidget)(widget.clone()));
//	} catch(Exception e) {
//		e.printStackTrace(System.out);
//		System.exit(-1);
//	}
}
public TypedWidgetRenderEdit(Swinger f)
{
	// Get formatter info for this SqlType
	//SqlTypeMap.Factory f = map.getFactory(type);
	
	// Set the editor
	TypedWidget widget = f.newTypedWidget();
	editor = new Editor(widget);

	// Set the renderer --- use either the widget, or a formatted label
	if (!f.renderWithWidget()) {
		DefaultFormatterFactory ffactory = f.newFormatterFactory();
		if (ffactory != null) renderer = new FormattedTableCellRenderer(ffactory);
	}
	if (renderer == null) renderer = new Renderer(f.newTypedWidget());
}
// ==================================================================
public static class Renderer implements TableCellRenderer
{
	TypedWidget tw;
	public Renderer(TypedWidget tw) { this.tw = tw;}
	public Component getTableCellRendererComponent(
            JTable table, Object value ,
            boolean isSelected, boolean hasFocus,
            int row, int column) {

//if (tw instanceof JDateChooser) {
//	((JDateChooser)tw).setButtonsEnabled(false);
//}
		
		
		tw.setValue(value);
//System.out.println("TypedWidgetRenderer returning: " + tw.getClass());
		return (Component)tw;
	}
}
// ==================================================================
public static class Editor extends AbstractCellEditor  implements TableCellEditor
{
	TypedWidget tw;
	public Editor(TypedWidget tw) { this.tw = tw;}

// This method is called when a cell value is edited by the user.
public Component getTableCellEditorComponent(JTable table, Object value,
		boolean isSelected, int rowIndex, int vColIndex)
{
//if (tw instanceof JDateChooser) {
//	((JDateChooser)tw).getModel().setTime(new java.util.Date());
//}
	tw.setValue(value);

	return (Component)tw;
}

// This method is called when editing is completed.
// It must return the new value to be stored in the cell.
public Object getCellEditorValue()
{
	
	tw.stopEditing();
//	if (tw instanceof JFormattedTextField) {
//		JFormattedTextField tf = (JFormattedTextField)tw;
//		try {
//			tf.commitEdit();
//		} catch(ParseException e) {}
//	}
	
	Object o = tw.getValue();
	System.out.println("TypedWidgetRenderEdit.getCellEditorValue: " + o);
	return o;
}
}
// ==================================================================
}
