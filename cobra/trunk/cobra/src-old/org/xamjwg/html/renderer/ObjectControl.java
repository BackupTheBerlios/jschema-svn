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
 * Created on Jan 15, 2006
 */
package org.xamjwg.html.renderer;

import org.xamjwg.html.domimpl.InputContext;
import org.xamjwg.html.domimpl.RenderableContext;
import javax.swing.*;
import org.xamjwg.util.*;

public class ObjectControl extends BaseControl {

	private final JComponent widget; 
	protected int width = -1;
	protected int height = -1;
	
	public ObjectControl(RenderableContext renderableContext, JComponent widget) {
		super(renderableContext);
		this.setLayout(WrapperLayout.getInstance());
		this.setOpaque(false);
		this.widget = widget;
		this.add(widget);
		
		String sizeText = renderableContext.getAttribute("width");
		if(sizeText != null) {
			try {
				this.width = Integer.parseInt(sizeText);
			} catch(NumberFormatException nfe) {
				// ignore
			}
		}
		sizeText = renderableContext.getAttribute("height");
		if(sizeText != null) {
			try {
				this.height = Integer.parseInt(sizeText);
			} catch(NumberFormatException nfe) {
				// ignore
			}
		}
	}
	
	protected int getPreferredWidthImpl(int availWidth, int availHeight) {
		int width = this.width;
		if(width == -1) {
			return widget.getSize().width;
//			java.awt.Dimension ps = this.getPreferredSize();
//			return ps == null ? 1 : ps.width;
		} else {
			return width;
		}
	}
	
	protected int getPreferredHeightImpl(int availWidth, int availHeight) {
		int height = this.height;
		if(height == -1) {
			return widget.getSize().height;
//			java.awt.Dimension ps = this.getPreferredSize();
//			return ps == null ? 1 : ps.height;
		}
		else {
			return height;
		}
	}
	
}
