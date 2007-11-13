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
package citibob.swing;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import citibob.text.*;
import citibob.swing.table.*;
import citibob.swing.typed.*;
import citibob.swingers.*;
import citibob.types.*;
//import de.chka.swing.components.*;

public class CitibobJTable extends JTable
implements MouseListener, MouseMotionListener
{

private boolean isHighlightMouseover = false;		// SHould we highlight rows when mousing over?

public void setHighlightMouseover(boolean b)
{
	if (isHighlightMouseover == b) return;

	isHighlightMouseover = b;
	if (b) {
		this.addMouseListener(this); //new MyMouseAdapter());
		this.addMouseMotionListener(this); //new MyMouseMotionAdapter());
	} else {
		this.removeMouseListener(this); //new MyMouseAdapter());
		this.removeMouseMotionListener(this); //new MyMouseMotionAdapter());		
	}
}
public boolean getHighlightMouseover(boolean b) { return isHighlightMouseover; }

//RowHeightUpdater rhu;

public CitibobJTable()
{
	// See: http://bugs.sun.com/bugdatabase/view_bug.do;:YfiG?bug_id=4709394
	// Unfortunately, this "fix" breaks the JDateChooser date editor.
	// As soon as user selects a date, the focus is lost from the table,
	// BEFORE the JDateChooser has had a chance to update itself...
	// TODO: For now, I won't use it, but once JDateChooser is fixed, I'll turn
	// it back on.
	this.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

	setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

//javax.swing.plaf.basic.BasicComboBoxUI
}

public void setModel(TableModel model)
{
	super.setModel(model);
	if (model instanceof CitibobTableModel) {
		CitibobTableModel ctm = (CitibobTableModel)model;
//		rhu = new RowHeightUpdater(this, ctm.getPrototypes());
//		rhu.setEnabled(true);
	}
}

public CitibobTableModel getCBModel()
{
	TableModel m = super.getModel();
	return (CitibobTableModel)m;
}

//public void setRowHeightUpdaterEnabled(boolean b)
//{
//	rhu.setEnabled(b);
//}
//
//public boolean isRowHeightUpdaterEnabled()
//	{ return rhu.isEnabled(); }

public void setRenderEdit(int colNo, Swinger swinger)
{
	if (swinger == null) return;
	Swinger.RenderEdit re = swinger.newRenderEdit();
	setRenderEdit(colNo, re);
}

public void setRenderEdit(int colNo, KeyedModel kmodel)
{
	setRenderEdit(colNo,
		new KeyedRenderEdit(kmodel));
}

/** Sets a renderer and editor pair at once, for a column. */
public void setRenderEdit(int colNo, Swinger.RenderEdit re)
{
	if (re == null) return;		// Don't change, if we don't know what to set it TO.
	
	TableColumn col = getColumnModel().getColumn(colNo);
	TableCellRenderer rr = re.getRenderer(getModel().isCellEditable(0, colNo));
		if (rr != null) col.setCellRenderer(rr);
	TableCellEditor ee = re.getEditor();
		if (ee != null) col.setCellEditor(ee);
}

//public void setRenderEdit(int colNo, TypedWidget tw)
//{
//	TableColumn col = getColumnModel().getColumn(colNo);
//	col.setCellRenderer(new SFormatRenderer(sformat));
//	JTypedTextField tw = new JTypedTextField();
//	tw.setJType((JType)null, sformat);		// We don't really know about JTypes at CitibobJTable anyway
//	col.setCellEditor(new TypedWidgetEditor(tw));
//}

/** Sets a text-based renderer and editor pair at once, for a column,
without going through Swingers or anything.  Works for simpler text-based
renderers and editors ONLY. */
public void setRenderEdit(int colNo, SFormat sformat)
{
	TableColumn col = getColumnModel().getColumn(colNo);
	col.setCellRenderer(new SFormatRenderer(sformat));
	JTypedTextField tw = new JTypedTextField();
	tw.setJType((JType)null, sformat);		// We don't really know about JTypes at CitibobJTable anyway
	col.setCellEditor(new TypedWidgetEditor(tw));
}

public void setRenderEdit(int colNo, java.text.Format fmt)
	{ setRenderEdit(colNo, new FormatSFormat(fmt)); }

/** Sets up a renderer and editor based on a format string.  Works for a small
number of well-known types, this is NOT general. */
public void setRenderEdit(int colNo, String fmtString)
{
	Class klass = getModel().getColumnClass(colNo);
	setRenderEdit(colNo, FormatUtils.newFormat(klass, fmtString));
}

/** Sets a renderer and editor pair at once, for a column. */
public void setRenderer(int colNo, TableCellRenderer re)
{
	TableColumn col = getColumnModel().getColumn(colNo);
	col.setCellRenderer(re);
}

//public void setSFormat(int col, SFormat sfmt)
//	{ setRenderer(col, new citibob.swingers.SFormatRenderer(sfmt)); }
//public void setSFormat(String scol, SFormat sfmt)
//	{ setSFormat(getCBModel().findColumn(scol), sfmt); }
//public void setFormat(int col, Format fmt)
//	{ setRenderer(col, new citibob.swingers.FormatRenderer(fmt)); }
//public void setFormat(String scol, Format fmt)
//	{ setFormat(getCBModel().findColumn(scol), fmt); }



///** Sets a renderer and editor pair at once, for a column. */
//public void setDefaultRenderEdit(Class klass, RenderEdit re)
//{
//	setDefaultRenderer(klass, re.getRenderer());
//	setDefaultEditor(klass, re.getEditor());
//}

///** Sets RenderEdit on a column according to the column's declared class. */
//public void setRenderEdit(int col, RenderEditSet res)
//{
//	Class klass = getModel().getColumnClass(col);
//	setRenderEdit(col, res.getRenderEdit(klass));
//}
// ================================================================
// Stuff to highlight on mouseover
// See: http://forum.java.sun.com/thread.jspa?threadID=280692&messageID=1091824
// TODO: Actually, we need to use different colors (and fonts) if this is being
// used in a popup.  We should subclass for that...  But it's OK for now.
Color cTextHighlightBg = UIManager.getDefaults().getColor("List.selectionBackground");
Color cTextBg = UIManager.getDefaults().getColor("List.background");
Color cTextHighlightFg = UIManager.getDefaults().getColor("List.selectionForeground");
Color cTextFg = UIManager.getDefaults().getColor("List.foreground");
public Component prepareRenderer(TableCellRenderer renderer, int row, int col)
{
	
	Component c = super.prepareRenderer(renderer, row, col);

	// Tooltips
	if (c instanceof JComponent) {
		JComponent jc = (JComponent)c;
		String ttip = getTooltip(row, col);
//	System.out.println(ttip);
		jc.setToolTipText(ttip);
//		jc.setToolTipText("<html>This is the first line<br>This is the second line</html>");
	}

	// Highlight row the mouse is over
	if (row == mouseRow) {
		c.setBackground(cTextHighlightBg);
		c.setForeground(cTextHighlightFg);
	} else {
		c.setBackground(cTextBg);
		c.setForeground(cTextFg);
	}
	return c;
}
int mouseRow = -1;		// Row the mouse is currently hovering over.

/** Override this to do tooltips in custom manner.  For now, we return the "tooltip column" */
public String getTooltip(int row, int col) { return null; }
// =====================================================================
// MouseMotionListener
/**
 * Invoked when a mouse button is pressed on a component and then 
 * dragged.  <code>MOUSE_DRAGGED</code> events will continue to be 
 * delivered to the component where the drag originated until the 
 * mouse button is released (regardless of whether the mouse position 
 * is within the bounds of the component).
 * <p> 
 * Due to platform-dependent Drag&Drop implementations, 
 * <code>MOUSE_DRAGGED</code> events may not be delivered during a native 
 * Drag&Drop operation.  
 */
public void mouseDragged(MouseEvent e) {}

/**
 * Invoked when the mouse cursor has been moved onto a component
 * but no buttons have been pushed.
 */
public void mouseMoved(MouseEvent e) {
	if (!isHighlightMouseover) return;
	
	JTable aTable =  (JTable)e.getSource();
	int oldRow = mouseRow;
	mouseRow = aTable.rowAtPoint(e.getPoint());
//	itsColumn = aTable.columnAtPoint(e.getPoint());
	if (oldRow != mouseRow) aTable.repaint();
}

// =====================================================================
// MouseListener
/**
 * Invoked when the mouse button has been clicked (pressed
 * and released) on a component.
 */
public void mouseClicked(MouseEvent e) {}

/**
 * Invoked when a mouse button has been pressed on a component.
 */
public void mousePressed(MouseEvent e) {}

/**
 * Invoked when a mouse button has been released on a component.
 */
public void mouseReleased(MouseEvent e) {}

/**
 * Invoked when the mouse enters a component.
 */
public void mouseEntered(MouseEvent e) {}

/**
 * Invoked when the mouse exits a component.
 */
public void mouseExited(MouseEvent e) {
	if (!isHighlightMouseover) return;

	JTable aTable =  (JTable)e.getSource();
	mouseRow = -1;
	aTable.repaint();
}
// ================================================================

}
