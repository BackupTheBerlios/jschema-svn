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
