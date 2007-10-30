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
 * Created on Apr 17, 2005
 */
package org.xamjwg.html.renderer;

import java.awt.Color;
import java.awt.Rectangle;

import org.xamjwg.html.domimpl.ContainingBlockContext;
import org.xamjwg.html.domimpl.RenderableContext;

/**
 * @author J. H. S.
 */
public class RUIControl extends BaseBoundableRenderable implements ContainingBlockContext {
	public final UIControl widget;
	
	/**
	 * 
	 */
	public RUIControl(RenderableContext me, UIControl widget) {
		super(me);
		this.widget = widget;
	}
	
	public final void invalidate() {
		this.widget.invalidate();
	}

	public void onMouseClick(java.awt.event.MouseEvent event, int x, int y) {
		RenderableContext me = this.renderableContext;
		if(me != null) {
			me.onMouseClick(event, x, y, true);
		}
	}

	public void onMousePressed(java.awt.event.MouseEvent event, int x, int y) {
		RenderableContext me = this.renderableContext;
		if(me != null) {
			me.onMousePressed(event, x, y, true);
		}
	}

	public void onMouseReleased(java.awt.event.MouseEvent event, int x, int y) {
		RenderableContext me = this.renderableContext;
		if(me != null) {
			me.onMouseReleased(event, x, y, true);
		}
	}
	public void onMouseDisarmed(java.awt.event.MouseEvent event) {
		RenderableContext me = this.renderableContext;
		if(me != null) {
			me.onMouseDisarmed(event, true);
		}
	}

	/* (non-Javadoc)
	 * @see org.xamjwg.html.renderer.BoundableRenderable#invalidateState(org.xamjwg.html.renderer.RenderableContext)
	 */
	public void invalidateState(RenderableContext context) {
		//nop
	}

	/* (non-Javadoc)
	 * @see org.xamjwg.html.domimpl.ContainingBlockContext#repaint(org.xamjwg.html.renderer.RenderableContext)
	 */
	public void repaint(RenderableContext renderableContext) {
		Object widget = this.widget;
		if(widget instanceof ContainingBlockContext) {
			((ContainingBlockContext) widget).repaint(renderableContext);
		}
		else {
			this.repaint();
		}
	}
	
	public void updateWidgetBounds(Rectangle parentBounds) {
		Rectangle b = this.bounds;
		this.widget.setBounds(parentBounds.x + b.x, parentBounds.y + b.y, b.width, b.height);
	}
	
	public Color getPaneColor() {
		return this.widget.getBackgroundColor();
	}

}
