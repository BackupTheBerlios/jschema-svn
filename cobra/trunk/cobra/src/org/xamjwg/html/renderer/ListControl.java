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
 * Created on Feb 12, 2006
 */
package org.xamjwg.html.renderer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.ArrayList;

import org.w3c.dom.Node;
import org.w3c.dom.html2.HTMLLIElement;
import org.w3c.dom.html2.HTMLOListElement;
import org.xamjwg.html.domimpl.*;
import java.util.*;

public class ListControl extends BaseControl {
	private final HTMLElementImpl rootElement;
	private final int nesting;
	LayoutArgs layoutArgs;
	
	public ListControl(HTMLElementImpl listElement, int nesting, LayoutArgs layoutArgs) {
		super(listElement);
		this.rootElement = listElement;
		this.nesting = nesting;
		this.layoutArgs = layoutArgs;
	}

	private int preferredHeight = -1;
	private int preferredWidth = -1;
	private int lastAvailHeight = -1;
	private int lastAvailWidth = -1;
	
	/* (non-Javadoc)
	 * @see org.xamjwg.html.renderer.BaseControl#getPreferredHeightImpl(int, int)
	 */
	protected int getPreferredHeightImpl(int availWidth, int availHeight) {
		this.doLayoutImpl(availWidth, availHeight);
		return this.preferredHeight;
	}
	
	/* (non-Javadoc)
	 * @see org.xamjwg.html.renderer.BaseControl#getPreferredWidthImpl(int, int)
	 */
	protected int getPreferredWidthImpl(int availWidth, int availHeight) {
		this.doLayoutImpl(availWidth, availHeight);
		return this.preferredWidth;
	}
	
	private static final int INDENT = 36;
	private static final int ITEM_SPACING = 4;
	
	private void doLayoutImpl(int availWidth, int availHeight) {
		if(availHeight != this.lastAvailHeight && availWidth != this.lastAvailWidth) {
			this.lastAvailHeight = availHeight;
			this.lastAvailWidth = availWidth;
			Insets insets = this.getInsets();
			int extraWidth = insets.right + insets.left;
			int blockWidth = availWidth - INDENT - extraWidth;
			int maxBlockWidth = blockWidth;
			ArrayList liDescendents = this.rootElement.getDescendents(new LiFilter());
			int size = liDescendents.size();
			this.removeAll();
			int y = insets.top;
			int newNesting = this.nesting + 1;
			for(int i = 0; i < size; i++) {
				HTMLElementImpl liElement = (HTMLElementImpl) liDescendents.get(i);
				HtmlBlock block = new HtmlBlock(newNesting, layoutArgs);
				this.add(block);
				block.setRootNode(liElement);
				Dimension renderSize = block.layoutFor(blockWidth, 0);
				int actualBlockWidth;
				if(renderSize.width > blockWidth) {
					actualBlockWidth = renderSize.width;
				}
				else {
					actualBlockWidth = blockWidth; 
				}
				int blockHeight = renderSize.height;				
				block.setBounds(insets.left + INDENT, y, actualBlockWidth, blockHeight);
				y += blockHeight + ITEM_SPACING;
				if(actualBlockWidth > maxBlockWidth) {
					maxBlockWidth = actualBlockWidth;
				}
			}
			this.preferredHeight = y - ITEM_SPACING + insets.bottom;
			this.preferredWidth = maxBlockWidth + INDENT + insets.left + insets.right;
		}
	}
	
	private static final int BULLET_SPACING = 8; 
	private static final int BULLET_BOTTOM_PADDING = 4; 
	private static final int BULLET_WIDTH = 5;
	private static final int BULLET_HEIGHT = 5;
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	protected void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		HTMLElementImpl rootElement = this.rootElement;
		String type = rootElement.getAttribute("type");
		int bulletType = 0;
		boolean numbered = rootElement instanceof HTMLOListElement; 
		FontMetrics fm = null;
		int bulletNumber = 0;
		if(numbered) {
			HTMLOListElementImpl oList = (HTMLOListElementImpl) rootElement;
			bulletNumber = oList.getStart();
			Font f = g.getFont();
			fm = this.getFontMetrics(f);			
		}
		else {
			if("disc".equalsIgnoreCase(type)) {
				bulletType = 0;
			} else if("circle".equalsIgnoreCase(type)) {
				bulletType = 1;
			}
			else if ("square".equals(type)) {
				bulletType = 2;
			}
			else {
				bulletType = this.nesting;
			}			
		}
		int ncomponents = this.getComponentCount();
		for(int i = 0; i < ncomponents; i++) {
			Object c = this.getComponent(i);
			if(c instanceof HtmlBlock) {
				HtmlBlock hp = (HtmlBlock) c;
				Rectangle bounds = hp.getBounds();
				int lineHeight = hp.getFirstLineHeight();
				int bulletRight = bounds.x - BULLET_SPACING;
				int bulletBottom = bounds.y + lineHeight;
				if(numbered) {
					// TODO: value attribute from LI element
					// TODO: type attribute from LI element
					String numberText = bulletNumber + "."; 				
					int bulletLeft = bulletRight - fm.stringWidth(numberText);
					int bulletY = bulletBottom - fm.getDescent();
					g.drawString(numberText, bulletLeft, bulletY);
				}
				else {
					bulletBottom -= BULLET_BOTTOM_PADDING;
					int bulletTop = bulletBottom - BULLET_HEIGHT;
					int bulletLeft = bulletRight - BULLET_WIDTH;
					if(bulletType == 0) {
						g.fillOval(bulletLeft, bulletTop, BULLET_WIDTH, BULLET_HEIGHT);
					}
					else if(bulletType == 1) {
						g.drawOval(bulletLeft, bulletTop, BULLET_WIDTH, BULLET_HEIGHT);
					}
					else {
						g.fillRect(bulletLeft, bulletTop, BULLET_WIDTH, BULLET_HEIGHT);
					}
				}
				bulletNumber++;
			}
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#doLayout()
	 */
	public void doLayout() {
		// nop
	}
	
	private static class LiFilter implements NodeFilter
	{
		/* (non-Javadoc)
		 * @see org.xamjwg.html.domimpl.NodeFilter#accept(org.w3c.dom.Node)
		 */
		public boolean accept(Node node) {
			return node instanceof HTMLLIElement;
		}
	}
}
