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

import citibob.swing.table.CitibobTableModel;
import de.chka.swing.components.*;

public class CitibobJTable extends JTable
{

RowHeightUpdater rhu;

public void setModel(TableModel model)
{
	super.setModel(model);
	if (model instanceof CitibobTableModel) {
		CitibobTableModel ctm = (CitibobTableModel)model;
		rhu = new RowHeightUpdater(this, ctm.getPrototypes());
		rhu.setEnabled(true);
	}
}

public void setRowHeightUpdaterEnabled(boolean b)
{
	rhu.setEnabled(b);
}

public boolean isRowHeightUpdaterEnabled()
	{ return rhu.isEnabled(); }

}
