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

/** Gives a single-row view of a multiple-row model. */
public interface MultiRowModel extends RowModel
{


//public static interface Listener
//{
//	void curRowChanged();
//}
//public static class Adapter implements Listener
//{
//	public void curRowChanged();
//}


/** curRow == NOROW if this is there is no currently selected row. */
public static int NOROW = -1;

public int getRowCount();
public int getCurRow();

/** Results in the listeners being called, if the row changes. */
public void setCurRow(int row);
// =======================================

}
