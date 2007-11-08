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
/*
 * HtmlDialog.java
 *
 * Created on October 8, 2006, 5:25 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.swing.html;

import citibob.swing.typed.Swinger;
import citibob.swing.typed.SwingerMap;
import citibob.types.JType;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
//import org.xamjwg.html.gui.*;
import java.util.*;
import citibob.swing.typed.*;
import citibob.swing.html.ObjHtmlPanel;
import java.net.URL;
import citibob.jschema.*;
import citibob.swing.typed.*;

/**
 * Meant to be subclassed to produce Wizards, etc...
 * @author citibob
 */
public class HtmlDialog extends JDialog implements ObjHtmlPanel.Listener
{

SwingerMap swingers;
protected ObjHtmlPanel html;
String submitName;
JButton submitButton;

public String getSubmitName() { return submitName; }
public JButton getSubmitButton() { return submitButton; }

public HtmlDialog(Frame owner, String title, SwingerMap swingers, boolean modal)
{
	this(owner, title, modal);
	this.swingers = swingers;
}
/**
 * Creates a new instance of HtmlDialog 
 */
public HtmlDialog(Frame owner, String title, boolean modal)
{
	super(owner, title, modal);
//	super(title);
	html = new ObjHtmlPanel();
	getContentPane().add(html);
//	this.setSize(600, 400);
	html.addListener(this);
	
    // Add a listener for the close event
    addWindowListener(new java.awt.event.WindowAdapter() {
	public void windowClosing(WindowEvent evt) {
		submitName = "cancel";
		submitButton = null;
		setVisible(false);
    }});
}

// ------------------------------------------------------------------
public JTypedTextField addTextField(String name, Swinger swinger)
	{ return html.addTextField(name, swinger); }

/** Add a text field with the correct Swinger already created... */
public JTypedTextField addTextField(String name, Schema schema)
	{ return addTextField(name, swingers.newSwinger(schema.getCol(name).getType())); }
// ------------------------------------------------------------------
public JComponent getWidget(String name)
{ return (JComponent)html.getMap().get(name); }
public JComponent addWidget(String name, JComponent widget)
	{ return html.addWidget(name, widget); }


public TypedWidget addWidget(String instanceName, String colName, Schema schema)
{
	JType jt = schema.getCol(colName).getType();
	Swinger sw = swingers.newSwinger(jt);
	TypedWidget w = sw.newWidget();
	addWidget(instanceName, (JComponent)w);
	return w;
}

public void addWidgetRecursive(Component c)
{
	// Take care of yourself
	if (c instanceof TypedWidget) {
		TypedWidget tw = (TypedWidget)c;
		if (tw.getColName() != null) {
			addWidget(tw.getColName(), (JComponent)c);
		}
	}

	// Take care of your children
	if (c instanceof Container) {
	    Component[] child = ((Container)c).getComponents();
	    for (int i = 0; i < child.length; ++i) {
			addWidgetRecursive(child[i]);
		}
	}
	
	// Take care of explicit invisible children
	if (c instanceof BindContainer) {
	    Component[] child = ((BindContainer)c).getBindComponents();
	    for (int i = 0; i < child.length; ++i) {
			addWidgetRecursive(child[i]);
		}
	}
}

/** Adds a field based on its type in a schema, and the SwingerMap */
public TypedWidget addWidget(String name, Schema schema)
{ return addWidget(name, name, schema); }
// ------------------------------------------------------------------

public JButton addSubmitButton(String name, String text)
{
	return setSubmit(name, html.addJButton(name, text));
}
// ------------------------------------------------------------------

protected JButton setSubmit(final String name, final JButton button)
{
	if (button == null) return null;
	button.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
		submitName = name;
		submitButton = button;
		setVisible(false);
	}});
	return button;
}
protected JButton setSubmit(final String name)
{
	Object o = html.getMap().get(name);
	if (!(o instanceof JButton)) return null;
	final JButton button = (JButton)o;
	return setSubmit(name, button);
}

protected void loadHtml()
throws org.xml.sax.SAXException, java.io.IOException
{
	html.loadHtml(getClass());
}
protected void loadHtmlResource(String resourceName)
throws org.xml.sax.SAXException, java.io.IOException
{
	html.loadHtmlResource(resourceName);
}


/** Gets all the TypedWidget values from the form. */
public void getAllValues(Map map)
{
	html.getAllValues(map);
	map.put("submit", getSubmitName());
}
// ==========================================================
// ObjHtmlPanel.Listener
public void linkSelected(URL href, String target)
{
	String url = href.toExternalForm();
	int slash = url.lastIndexOf('/');
	if (slash > 0) url = url.substring(slash+1);
	submitName = url;
	submitButton = null;
	setVisible(false);
}

//// ====================================================================
//class MyRendererContext extends org.xamjwg.html.gui.NullHtmlRendererContext
//{
//	public MyRendererContext() { super(null); }
//	public void navigate(URL href, String target) {
//System.out.println("MyRenderer.navigate(): " + href + ", " + target);
//		String url = href.toExternalForm();
//		int slash = url.lastIndexOf('/');
//		if (slash > 0) url = url.substring(slash+1);
//		submitName = url;
//		submitButton = null;
//		setVisible(false);
//		
//	}
//}
}
