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
package org.xamjwg.html.renderer;

import java.awt.*;
import org.xamjwg.html.domimpl.*;

public class TableControl extends BaseControl {
	private final HTMLTableElementImpl tableElement;
	private final TableMatrix tableMatrix;
	
	public TableControl(HTMLTableElementImpl renderableContext, LayoutArgs layoutArgs) {
		super(renderableContext);
		this.tableElement = renderableContext;
		this.tableMatrix = new TableMatrix(renderableContext, this, layoutArgs);
		this.setOpaque(false);
	}
	
	public void paintComponent(Graphics g) {
		//long time1 = System.currentTimeMillis();
		try {
			Dimension size = this.getSize();
			Insets insets = this.getInsets();
			TableMatrix tm = this.tableMatrix;
			tm.paintDecorations(g, size, insets);
		} finally {
			//long time2 = System.currentTimeMillis();
			//System.out.println("TableControl.paintComponent(): children=" + this.getComponentCount() + ",time=" + (time2 - time1) + " ms");
			super.paintComponent(g);
		}
	}
	
	private volatile int lastAvailWidth = -1;
	private volatile int lastAvailHeight = -1;
	
	private void doLayoutImpl(int availWidth, int availHeight) {
		TableMatrix tm = this.tableMatrix;
		if(availWidth != this.lastAvailWidth || availHeight != this.lastAvailHeight) {
			this.lastAvailHeight = availHeight;
			this.lastAvailWidth = availWidth;
			tm.build(availWidth, availHeight);
			// Note: doLayout invalidates, but tableMatrix is set right after.
			tm.doLayout(this);
			//System.out.println("doLayoutImpl(): Built table matrix: tm.width=" + tm.getTableWidth() + ",tm.height=" + tm.getTableHeight() + ",element=" + this.tableElement + ",hc=" + this.tableElement.hashCode());
		}
	}
	
	public void doLayout() {
		// No need to do anything here.
	}
	
	protected int getPreferredWidthImpl(int availWidth, int availHeight) {
		HtmlLength width = this.renderableContext.getWidthLength();
		if(width != null) {
			return width.getLength(availWidth);
		}
		this.doLayoutImpl(availWidth, availHeight);
		TableMatrix tm = this.tableMatrix;
		return tm.getTableWidth();
	}
	
	protected int getPreferredHeightImpl(int availWidth, int availHeight) {
		HtmlLength height = this.renderableContext.getHeightLength();
		if(height != null) {
			return height.getLength(availHeight);
		}
		this.doLayoutImpl(availWidth, availHeight);
		TableMatrix tm = this.tableMatrix;
		return tm.getTableHeight();
	}
}
