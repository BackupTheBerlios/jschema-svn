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

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import gnu.xml.dom.ls.WriterOutputStream;
import foundry.io.DocumentWriter;
import javax.swing.event.*;
import java.io.*;
import citibob.swing.text.CircularPlainDocument;
import citibob.io.LineBufferWriter;

public class JLogScrollPane extends JScrollPane
{
	
	final JTextArea textArea;
	Writer logWriter;
	boolean autoScroll;
	
//public JTextArea getTextArea() { return textArea; }
	
	/** Creates a new instance of JLogScrollPanel */
	public JLogScrollPane()
	{
		super();
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		
		setViewportView(textArea);
	}
	
	public Writer getWriter()
	{ return logWriter; }
	
	public void setAutoScroll(boolean as)
	{
		if (!autoScroll && as) scrollToEnd();
		autoScroll = as;
	}
	
	public void setDocument(Document doc)
	{
		textArea.setDocument(doc);
		textArea.setCaretPosition(textArea.getDocument().getLength());
//System.out.println("textArea text is (this = " + this + "):" + textArea.getText());
//	if (autoScroll) {
//		doc.addDocumentListener(new DocumentListener() {
//			public void changedUpdate(DocumentEvent e) {}
//			public void removeUpdate(DocumentEvent e) {}
//			public void insertUpdate(DocumentEvent e) {
//				scrollToEnd();
//			}
//		});
//	}
	}
	
	public void scrollToEnd()
	{
//		this.textArea.setCaretPosition(this.textArea.getDocument().getLength());
	
		// Scroll to end
		final JScrollBar sb = getVerticalScrollBar();
		if (sb.getValue() != sb.getMaximum())
		{
			java.awt.EventQueue.invokeLater(new Runnable()
			{
				public void run()
				{
					sb.setValue(sb.getMaximum());
				}
			});
		}
	}
	
	public void newDocument(int nlines)
	{
		Document doc = new CircularPlainDocument(nlines);
		doc.addDocumentListener(new DocumentListener()
		{
			public void changedUpdate(DocumentEvent e)
			{
				if (autoScroll) scrollToEnd();
			}
			public void insertUpdate(DocumentEvent e)
			{
				if (autoScroll) scrollToEnd();
			}
			public void removeUpdate(DocumentEvent e)
			{
				//if (autoScroll) scrollToEnd();
			}
		});
		logWriter = new LineBufferWriter(new DocumentWriter(doc));
		setDocument(doc);
//try {
//logWriter.write("Hello World\n");
//System.err.println("Text is: " + doc.getText(0,doc.getLength()));
//} catch(Exception e) {
//	e.printStackTrace(System.out);
//}
		
	}
	
	public void redirectStdout(int nlines, boolean autoScroll)
	{
		this.autoScroll = autoScroll;
		newDocument(nlines);
		OutputStream screenOut = new WriterOutputStream(logWriter);
		System.setOut(new PrintStream(screenOut));
	}
	
	
	public JTextArea getTextArea()
	{ return textArea; }
	
// =====================================================================
//	public class ScrolledLineBufferWriter extends LineBufferWriter
//	{
//		public ScrolledLineBufferWriter(Writer out)
//		{ super(out); }
//		
//		public void processLine() throws IOException
//		{
//			super.processLine();
//			if (autoScroll)
//			{
//				System.err.println("invoking the scrollToEnd() method.");
//				scrollToEnd();
//			}
//		}
//	}
// =====================================================================
	public static void main(String[] args)
	throws Exception
	{
		JFrame f = new JFrame();
		f.setSize(200,200);
		JLogScrollPane sp = new JLogScrollPane();
		sp.getTextArea().setText("Hoi 3");
		f.getContentPane().add(sp);
		sp.getTextArea().setText("Hoi 4");
		f.setVisible(true);
		
		try { Thread.sleep(4000); } catch (InterruptedException ie) { }
		
		sp.redirectStdout(1000, true);
		
		System.out.println("Hello World Redirect");
		System.out.println("I like you too");
		
		for (int i=0; i<20; i++)
		{
			System.out.println(i+"\tHello World Redirect");
			System.out.println(i+"\tI like you too");
			System.out.println();
			try { Thread.sleep(250); } catch (InterruptedException ie) { }
		}
		
		
	}
	
	
	
}


