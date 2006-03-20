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
package citibob.swing;

import javax.swing.*;
import javax.swing.table.*;

import citibob.swing.table.*;
//import de.chka.swing.components.*;

public class CitibobJTable extends JTable
{

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

/** Sets a renderer and editor pair at once, for a column. */
public void setRenderEdit(int colNo, RenderEdit re)
{
	if (re == null) return;		// Don't change, if we don't know what to set it TO.
	
	TableColumn col = getColumnModel().getColumn(colNo);
	if (re.getRenderer() != null) col.setCellRenderer(re.getRenderer());
	if (re.getEditor() != null) col.setCellEditor(re.getEditor());
}

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
}
