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
import java.awt.Graphics;
import java.awt.Rectangle;

import org.xamjwg.html.domimpl.RenderableContext;

/**
 * @author J. H. S.
 */
public abstract class BaseBoundableRenderable implements BoundableRenderable {
	protected final Rectangle bounds = new Rectangle();
	protected final RenderableContext renderableContext;
	/**
	 * 
	 */
	public BaseBoundableRenderable(RenderableContext me) {
		super();
		this.renderableContext = me;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.xamj.domimpl.markup.Renderable#getBounds()
	 */
	public Rectangle getBounds() {
		return this.bounds;
	}

	public void setBounds(int x, int y, int width, int height) {
		bounds.x = x;
		bounds.y = y;
		bounds.width = width;
		bounds.height = height;
	}
	
	public void setX(int x) {
		this.bounds.x = x;
	}
	
	public void setY(int y) {
		this.bounds.y = y;
	}

	public void setHeight(int height) {
		bounds.height = height;
	}
	
	/* (non-Javadoc)
	 * @see net.sourceforge.xamj.domimpl.markup.Renderable#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g) {
	}
	
	public void invalidate() {
	}
	
	private Renderable parent;
	
	public void setParent(Renderable parent) {
		this.parent = parent;
	}
	
	public void repaint(int x, int y, int width, int height) {
		Renderable parent = this.parent;
		if(parent instanceof BaseBoundableRenderable) {
			Rectangle b = this.bounds;
			((BaseBoundableRenderable) parent).repaint(x + b.x, y + b.y, width, height);
		}
	}
	
	public void repaint() {
		Rectangle bounds = this.bounds;
		this.repaint(bounds.x, bounds.y, bounds.width, bounds.height);
	}
	
	public Color getPaneColor() {
		Renderable parent = this.parent;
		if(parent instanceof BaseBoundableRenderable) {
			return ((BaseBoundableRenderable) parent).getPaneColor();
		}
		else {
			return Color.WHITE;
		}
	}
}
