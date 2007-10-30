package org.xamjwg.html.test;

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

import org.xamjwg.html.domimpl.*;

/**
 * Minimal rendering example: google.com.
 * @author J. H. S.
 */
public class Test2Entry {
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
//		String uri = "http://google.com";
//		URL url = new URL(uri);
//		URLConnection connection = url.openConnection();
//		InputStream in = connection.getInputStream();
//		// A Reader should be created with the correct charset.
//		Reader reader = new InputStreamReader(in);
		Reader reader = new StringReader(
			"<h1>Test</h1>How does this look today?" +
			"<form><input type='submit' value='hoi'/></form>" +
//			"<input name='obj' type='obj' width='50' height='30'/>");
			"<object name='obj' width='100' height='25'/>");
		String uri = "http://localhost";
		
		// InputSourceImpl constructor with URI recommended
		// so the renderer can resolve page component URLs.
		InputSource is = new InputSourceImpl(reader, uri);
		
		HtmlParserContext parserContext = new LocalHtmlParserContext();
		HtmlPanel htmlPanel = new HtmlPanel();
		HtmlRendererContext rendererContext = new LocalHtmlRendererContext(htmlPanel);
		DocumentBuilderImpl builder = new DocumentBuilderImpl(parserContext, rendererContext);
		HTMLDocumentImpl document = builder.parse(is);
		JFrame frame = new JFrame();
		frame.getContentPane().add(htmlPanel);
		LayoutArgs layoutArgs = new LayoutArgs();
		layoutArgs.getWidgetMap().put("obj", new JButton("My Object"));
		htmlPanel.setLayoutArgs(layoutArgs);
		htmlPanel.setDocument(document, rendererContext);//, new LayoutArgs());
		// Set the size of the JFrame when the root
		// component does not have a preferred size.
		frame.setSize(600, 400);
		frame.setVisible(true);
	}

	private static class LocalHtmlParserContext extends SimpleHtmlParserContext {
		// Override methods here to implement browser functionality
	}
	
	private static class LocalHtmlRendererContext extends SimpleHtmlRendererContext {
		// Override methods here to implement browser functionality
		
		public LocalHtmlRendererContext(HtmlPanel contextComponent) {
			super(contextComponent);
		}
	}
}
