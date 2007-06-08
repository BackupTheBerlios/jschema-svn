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

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
//import org.xamjwg.html.gui.*;
import java.util.*;
import citibob.swing.typed.*;
import citibob.swing.html.ObjHtmlPanel;
import java.net.URL;

/**
 * Meant to be subclassed to produce Wizards, etc...
 * @author citibob
 */
public class HtmlDialog extends JDialog implements ObjHtmlPanel.Listener
{

protected ObjHtmlPanel html;
String submitName;
JButton submitButton;

public String getSubmitName() { return submitName; }
public JButton getSubmitButton() { return submitButton; }

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

public JTypedTextField addTextField(String name, Swinger swinger)
	{ return html.addTextField(name, swinger); }
//public JComponent getWidget(String name)
//{ return (JComponent)html.getMap().get(name); }
public JComponent addWidget(String name, JComponent widget)
	{ return html.addWidget(name, widget); }


public JButton addSubmitButton(String name, String text)
{
	return setSubmit(name, html.addJButton(name, text));
}

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
