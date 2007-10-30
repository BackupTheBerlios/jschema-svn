/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The XAMJ Project

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: info@xamjwg.org
*/
/*
 * Created on Nov 19, 2005
 */
package org.xamjwg.html.gui;

import java.awt.Color;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.*;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.xamjwg.html.HtmlRendererContext;
import org.xamjwg.html.domimpl.*;
import org.xamjwg.html.renderer.FrameSetPanel;
import org.xamjwg.html.renderer.NodeRenderer;
import org.xamjwg.html.renderer.ScrollableHtmlBlock;
import org.xamjwg.util.WrapperLayout;
import org.xamjwg.html.renderer.LayoutArgs;
import java.io.*;

/**
 * The <code>HtmlPanel</code> class is a Swing
 * component that can render an HTML DOM. 
 * @author J. H. S.
 */
public class HtmlPanel extends JComponent {
	
	/**
	 * Constructs an <code>HtmlPanel</code>.
	 */
	public HtmlPanel() {
		super();
		this.setLayout(WrapperLayout.getInstance());
//		this.setUpBodyScrollable();
//		this.layoutArgs = new LayoutArgs();
	}


	
	
	LayoutArgs layoutArgs;
	
	private boolean isFrameSet = false;
	private NodeRenderer nodeRenderer = null;
	private NodeImpl rootNode;

	public void setLayoutArgs(LayoutArgs layoutArgs)
	{
		this.layoutArgs = layoutArgs;
		this.setUpBodyScrollable();
	}
	
	private void setUpBodyScrollable() {
		ScrollableHtmlBlock shp = new ScrollableHtmlBlock(Color.WHITE, true, layoutArgs);
		shp.setDefaultPaddingInsets(new Insets(8, 8, 8, 8));
		JScrollPane pane = new JScrollPane(shp);
		this.removeAll();
		this.add(pane);
		this.nodeRenderer = shp;
	}

	/**
	 * Scrolls the document such that x and y coordinates
	 * are placed in the upper-left corner of the panel.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 */
	public void scroll(int x, int y) {
		try {
			Object root = this.getComponent(0);
			if(root instanceof JScrollPane) {
				((JScrollPane) root).getViewport().scrollRectToVisible(new Rectangle(x, y, 1, 1));
			}
		} catch(IndexOutOfBoundsException iob) {
			// ignore
		}
	}

//	public void setDocument(Document node, HtmlRendererContext rcontext)
//	{
//		setDocument(node, rcontext, new LayoutArgs());
//	}
	
	/**
	 * Sets an HTML DOM node to be rendered. 
	 * @param node This should
	 * normally be a Document instance obtained with
	 * {@link org.xamjwg.html.parser.DocumentBuilderImpl}.
	 * @param rcontext A renderer context.
	 */
//	public synchronized void setDocument(Document node, HtmlRendererContext rcontext, LayoutArgs layoutArgs) {
//		if(!(node instanceof HTMLDocumentImpl)) {
//			throw new IllegalArgumentException("Only nodes of type HTMLDocumentImpl are currently supported. Use DocumentBuilderImpl.");
//		}
	public synchronized void setDocument(HTMLDocumentImpl nodeImpl,
	HtmlRendererContext rcontext) {
		nodeImpl.setHtmlRendererContext(rcontext);
		this.rootNode = nodeImpl;
		NodeImpl fsrn = this.getFrameSetRootNode(nodeImpl);
		boolean newIfs = fsrn != null;
		if(newIfs != this.isFrameSet) {
			this.isFrameSet = newIfs;
			if(newIfs) {
				FrameSetPanel fsp = new FrameSetPanel();
				this.removeAll();
				this.add(fsp);
				this.nodeRenderer = fsp;
				fsp.setRootNode(fsrn);
			}
			else {
				this.setUpBodyScrollable();
			}		
			this.repaint();
		}		
		NodeRenderer nr = this.nodeRenderer;
		if(nr != null) {
			if(newIfs) {
				nr.setRootNode(fsrn);
			}
			else {
				nr.setRootNode(nodeImpl);
			}
		}
	}

	/**
	 * Gets the HTML DOM node currently rendered if any.
	 */
	public synchronized NodeImpl getRootNode() {
		return this.rootNode;
	}
	
	private NodeImpl getFrameSetRootNode(NodeImpl node) {
		if(node instanceof Document) {
			ElementImpl element = (ElementImpl) ((Document) node).getDocumentElement();
			if(element != null && "HTML".equalsIgnoreCase(element.getTagName())) {
				return this.getFrameSet(element);
			}
			else {
				return this.getFrameSet(node);
			}
		}
		else {
			return null;
		}
	}
	
	private NodeImpl getFrameSet(NodeImpl node) {
		NodeImpl[] children = node.getChildrenArray();
		int length = children.length;
		NodeImpl frameSet = null;
		for(int i = 0; i < length; i++) {
			NodeImpl child = children[i];
			if(child instanceof Text) {
				String textContent = ((Text) child).getTextContent();
				if(textContent != null && !"".equals(textContent.trim())) {
					return null;
				}
			}
			else if(child instanceof ElementImpl) {
				String tagName = child.getNodeName();
				if("FRAMESET".equalsIgnoreCase(tagName)) {
					frameSet = child;
				}
				else {
					if(this.hasSomeHtml((ElementImpl) child)) {
						return null;
					}
				}
			}
		}
		return frameSet;
	}
	
	private boolean hasSomeHtml(ElementImpl element) {
		String tagName = element.getTagName();
		if("HEAD".equalsIgnoreCase(tagName) || "TITLE".equalsIgnoreCase(tagName)) {
			return false;
		}
		NodeImpl[] children = element.getChildrenArray();
		if(children != null) {
			int length = children.length;
			for(int i = 0; i < length; i++) {
				NodeImpl child = children[i];
				if(child instanceof Text) {
					String textContent = ((Text) child).getTextContent();
					if(textContent != null && !"".equals(textContent.trim())) {
						return false;
					}
				}
				else if(child instanceof ElementImpl) {
					if(this.hasSomeHtml((ElementImpl) child)) {
						return false;
					}
				}
			}
		}
		return true;		
	}


}
