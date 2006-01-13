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

public interface RowModel
{


// ================================================================
 /** Listener provided by single-column widgets listening to the
current row.  This listener will be called when the value changes or
the row being watched changes; the single-column widget doesn't care,
its job is just to display and edit the value it's assigned to. */
public static interface ColListener
{
	void valueChanged(int col);
	void curRowChanged(int col);		// Used by MultiRowModel
}
public static class ColAdapter implements ColListener
{
	public void valueChanged(int col) {}
	public void curRowChanged(int col) {}		// Used by MultiRowModel
}

/** Only one listener per column needed. */
public void addColListener(int colIndex, ColListener l);
public void removeColListener(int colIndex, ColListener l);

// ===========================================================
// We also need a way for single-column widgets to get and set
// individual values in the current row.
public void set(int col, Object val);
public Object get(int col);

/** Returns number of columns */
public int getColCount();

}
