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
package citibob.jschema;

import citibob.swing.table.AbstractJTypeTableModel;

public abstract class SchemaBufMVC extends AbstractJTypeTableModel
{
public static interface Listener {
    public void statusChanged(int row);
}
// ======================================================
public static class Adapter implements SchemaBufMVC.Listener {
    public void statusChanged(int row) {}
}
// ======================================================
java.util.LinkedList listeners = new java.util.LinkedList();
public void addListener(SchemaBufMVC.Listener l)
	{ listeners.add(l); }
public void removeListener(SchemaBufMVC.Listener l)
	{ listeners.remove(l); }

// ======================================================
public void fireStatusChanged(int row)
{
	for (java.util.Iterator ii=listeners.iterator(); ii.hasNext(); ) {
		SchemaBufMVC.Listener l = (SchemaBufMVC.Listener)ii.next();
		l.statusChanged(row);
	}
}
}
