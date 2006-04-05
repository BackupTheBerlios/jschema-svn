/*
 * FormattedTableCellRenderer.java
 *
 * Created on March 18, 2006, 1:58 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.swing.typed;


import java.awt.*;
import javax.swing.table.*;
//import citibob.swing.typed.*;
import java.sql.*;
import citibob.util.KeyedModel;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import citibob.sql.*;

/**
 *
 * @author citibob
 */
public class FormattedTableCellRenderer extends DefaultTableCellRenderer
{
	DefaultFormatterFactory ffactory;
	public FormattedTableCellRenderer(DefaultFormatterFactory ffactory)
	{
		this.ffactory = ffactory;
	}
	JFormattedTextField.AbstractFormatter getFormatter(Object val)
	{
		JFormattedTextField.AbstractFormatter fmt;
		if (val == null) fmt = ffactory.getNullFormatter();
		else fmt = ffactory.getDisplayFormatter();
		if (fmt == null) fmt = ffactory.getDefaultFormatter();
		return fmt;
	}

	public void setValue(Object o) {
		JFormattedTextField.AbstractFormatter fmt = getFormatter(o);
		try {
			setText(fmt.valueToString(o));
		} catch(java.text.ParseException e) {
			setText(e.toString());		// Should not happen
		}
	}
}
