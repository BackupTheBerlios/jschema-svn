/*
 * TypedWidgetRenderer.java
 *
 * Created on November 7, 2007, 8:41 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.swingers;

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
public class TypedWidgetRenderer implements TableCellRenderer
{
	protected TypedWidget tw;
	public TypedWidgetRenderer(TypedWidget tw) { this.tw = tw;}
	public Component getTableCellRendererComponent(
	JTable table, Object value , boolean isSelected, boolean hasFocus,
	int row, int column) {
		tw.setValue(value);
		return (Component)tw;
	}

}
