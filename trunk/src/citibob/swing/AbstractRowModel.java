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

public abstract class AbstractRowModel implements RowModel
{

RowModel.ColListener[] colListeners;		// One listener per column.  We don't allow multiple listeners
public void addColListener(int colIndex, ColListener l)
	{ colListeners[colIndex] = l; }

public void removeColListener(int colIndex, ColListener l)
	{ colListeners[colIndex] = null; }

void fireValueChanged(int colIndex)
{
System.out.println("AbstractRowModel: value " + colIndex + " changed to " + this.get(colIndex));
	ColListener l = colListeners[colIndex];
	if (l != null) l.valueChanged(colIndex);
}
void fireAllValuesChanged()
{
//for (int i = 0; i < listeners.length; ++i) System.out.println("    fireAllValuesChanged: " + get(i));
	for (int i = 0; i < colListeners.length; ++i) {
		if (colListeners[i] != null) fireValueChanged(i);
	}
}
void fireCurRowChanged()
{
	for (int i = 0; i < colListeners.length; ++i) {
		if (colListeners[i] != null) colListeners[i].curRowChanged(i);
	}
}

}
