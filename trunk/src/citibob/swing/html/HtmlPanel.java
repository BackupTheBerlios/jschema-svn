/*
 * HtmlPanel.java
 *
 * Created on October 8, 2006, 5:37 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.swing.html;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

import org.w3c.dom.Document;
import org.xamjwg.html.*;
import org.xamjwg.html.parser.*;
import org.xamjwg.html.gui.*;
import org.xml.sax.InputSource;
import org.xamjwg.html.renderer.*;

import javax.swing.*;
import java.util.*;
import java.net.URL;
import citibob.swing.typed.*;

/**
 *
 * @author citibob
 */
public class HtmlPanel extends org.xamjwg.html.gui.HtmlPanel
{
	Map widgetMap = new HashMap();
	public Map getMap() { return widgetMap; }
	
//	public HtmlPanel(Reader htmlIn, Map widgetMap)
//	throws org.xml.sax.SAXException, java.io.IOException
//	{
//		super();
//		setDocument(htmlIn, widgetMap, null);
//	}
//	
//	public HtmlPanel(Reader htmlIn, Map widgetMap, String uri)
//	throws org.xml.sax.SAXException, java.io.IOException
//	{
//		super();
//		setDocument(htmlIn, widgetMap, uri);
//	}
	
	public JTypedTextField addTextField(String name, Swinger swinger)
	{
		JTypedTextField widget = new JTypedTextField(swinger);
		widgetMap.put(name, widget);

		// Set text field size to 150 pixels wide x default height
		java.awt.Dimension d = widget.getPreferredSize();
		d.width = 150;
		widget.setSize(d);
		return widget;
	}

	public JButton addJButton(String name, String text)
	{
		JButton widget = new JButton(text);
		addWidget(name, widget);
		return widget;
	}

	public JComponent addWidget(String name, JComponent widget)
	{
		widgetMap.put(name, widget);
		widget.setSize(widget.getPreferredSize());
		return widget;		
	}
	
	/** Simple convenience method... only works on a brand new HtmlPanel instance. */
	public void setDocument(Reader htmlIn, String uri)
	throws org.xml.sax.SAXException, java.io.IOException
	{
		if (uri == null) uri = "http://localhost";
		
		// InputSourceImpl constructor with URI recommended
		// so the renderer can resolve page component URLs.
		InputSource is = new InputSourceImpl(htmlIn, uri);
		
		HtmlParserContext parserContext = new NullHtmlParserContext();
		HtmlPanel htmlPanel = this;
		HtmlRendererContext rendererContext = new NullHtmlRendererContext(htmlPanel);
		DocumentBuilderImpl builder = new DocumentBuilderImpl(parserContext, rendererContext);
		Document document = builder.parse(is);
		JFrame frame = new JFrame();
		frame.getContentPane().add(htmlPanel);
		LayoutArgs layoutArgs = new LayoutArgs(widgetMap);
		layoutArgs.getWidgetMap().put("obj", new JButton("My Object"));
		htmlPanel.setLayoutArgs(layoutArgs);
		htmlPanel.setDocument(document, rendererContext);		
	}

	public Object getValue(String name)
	{
		Object o = widgetMap.get(name);
		if (o == null) return null;
		if (!(o instanceof TypedWidget)) return null;
		TypedWidget w = (TypedWidget)o;
		return w.getValue();
	}

	/** Gets all the TypedWidget values from the form. */
	public void getAllValues(Map map)
	{
		for (Iterator ii=getMap().entrySet().iterator(); ii.hasNext(); ) {
			Map.Entry e = (Map.Entry)ii.next();
			if (!(e.getValue() instanceof TypedWidget)) continue;
			TypedWidget tw = (TypedWidget)e.getValue();
			map.put(e.getKey(), tw.getValue());
		}
	}
	
}
