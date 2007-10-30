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
 * Created on Oct 23, 2005
 */
package org.xamjwg.html.renderer;

import javax.swing.*;
import org.xamjwg.html.domimpl.*;
import org.xamjwg.util.WrapperLayout;

public class HtmlControl extends BaseControl implements UIControl {
	private final ScrollableHtmlBlock bodyUIControl;

	/**
	 * @param context
	 */
	public HtmlControl(RenderableContext ctx, LayoutArgs layoutArgs) {
		super(ctx);
		this.setLayout(WrapperLayout.getInstance());
		ScrollableHtmlBlock bw = new ScrollableHtmlBlock(layoutArgs);
		JScrollPane sp = new JScrollPane(bw);
		this.add(sp);
		this.bodyUIControl = bw;
	}
	
	public void setRootNode(NodeImpl node) {
		this.bodyUIControl.setRootNode(node);
	}
}
