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
package citibob.swing.html;

import java.net.*;

public abstract class ObjHtmlPanelMVC extends org.xamjwg.html.gui.HtmlPanel
{
public static interface Listener {
    public void linkSelected(URL href, String target);
}
// ======================================================
public static class Adapter implements ObjHtmlPanelMVC.Listener {
    public void linkSelected(URL href, String target) {}
}
// ======================================================
java.util.LinkedList listeners = new java.util.LinkedList();
public void addListener(ObjHtmlPanelMVC.Listener l)
	{ listeners.add(l); }
public void removeListener(ObjHtmlPanelMVC.Listener l)
	{ listeners.remove(l); }

// ======================================================
public void fireLinkSelected(URL href, String target)
{
	for (java.util.Iterator ii=listeners.iterator(); ii.hasNext(); ) {
		ObjHtmlPanelMVC.Listener l = (ObjHtmlPanelMVC.Listener)ii.next();
		l.linkSelected(href, target);
	}
}
}
