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
package citibob.swing.calendar;

import java.util.*;

public abstract class CalModelMVC
{
public static interface Listener {
    /**  Value has changed. */
    public void calChanged();


    /**  The "final" value has been changed. */
/*    finalCalChanged();*/

    /**  User clicked on a day selection button --- will cause the popup to disappear. */
    public void dayButtonSelected();
}
// ======================================================
public static class Adapter implements CalModelMVC.Listener {
    /**  Value has changed. */
    public void calChanged() {}


    /**  The "final" value has been changed. */
/*    finalCalChanged();*/

    /**  User clicked on a day selection button --- will cause the popup to disappear. */
    public void dayButtonSelected() {}
}
// ======================================================
java.util.LinkedList listeners = new java.util.LinkedList();
public void addListener(CalModelMVC.Listener l)
	{ listeners.add(l); }
public void removeListener(CalModelMVC.Listener l)
	{ listeners.remove(l); }

// ======================================================
public void fireCalChanged()
{
	for (java.util.Iterator ii=listeners.iterator(); ii.hasNext(); ) {
		CalModelMVC.Listener l = (CalModelMVC.Listener)ii.next();
		l.calChanged();
	}
}
public void fireDayButtonSelected()
{
	for (java.util.Iterator ii=listeners.iterator(); ii.hasNext(); ) {
		CalModelMVC.Listener l = (CalModelMVC.Listener)ii.next();
		l.dayButtonSelected();
	}
}
}
