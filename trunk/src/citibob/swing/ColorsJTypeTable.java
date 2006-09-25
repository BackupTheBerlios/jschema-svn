/*
 * ColorsJTypeTable.java
 *
 * Created on September 17, 2006, 11:18 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.swing;

import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import citibob.sql.*;
import citibob.swing.table.*;
import citibob.swing.typed.*;
import java.awt.*;

/**
 *
 * @author citibob
 */
public abstract class ColorsJTypeTable extends JTypeTable implements TableCellRenderer
{
	
public abstract Color getBack(boolean isSelected, boolean hasFocus, int row, int col);
public abstract Color getFore(boolean isSelected, boolean hasFocus, int row, int col);

public TableCellRenderer getCellRenderer(int row, int col)
{
	return this;
}


// Implementation of TableCellRenderer interface
public Component getTableCellRendererComponent(JTable table, Object value,
boolean isSelected, boolean hasFocus, int row, int column) {
	TableCellRenderer rend = super.getCellRenderer(row, column);
	Component c = rend.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	c.setBackground(getBack(isSelected, hasFocus, row, column));
	c.setForeground(getFore(isSelected, hasFocus, row, column));
	return c;
}
}
