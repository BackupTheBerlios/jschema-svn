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
 * Created on Apr 16, 2005
 */
package org.xamjwg.html.renderer;

import java.awt.*;
import java.util.*;

import org.xamjwg.html.domimpl.RenderableContext;

/**
 * @author J. H. S.
 */
public class RLine extends BaseBoundableRenderable {
	private final ArrayList renderables = new ArrayList(8);
	private final RenderState startRenderState;
	private final int availHeight;
	private final Container container;
	private RenderState currentRenderState;
	private int baseLineOffset;
	private int offset = 0;
	private int actualWidth = 0;
	
	public RLine(RenderableContext me, Container container, RenderState lastRenderState, int availHeight, int x, int y, int width, int height) {
		super(me);
		Rectangle b = this.bounds;
		b.x = x;
		b.y = y;
		b.width = width;
		b.height = height;
		this.startRenderState = lastRenderState;
		this.currentRenderState = lastRenderState;
		this.availHeight = availHeight;
		this.container = container;
	}
	
	public final void shiftContentsX(int shiftBy) {
		ArrayList renderables = this.renderables;
		int numRenderables = renderables.size();
		for(int i = 0; i < numRenderables; i++) {
			Object r = renderables.get(i);
			if(r instanceof BoundableRenderable) {
				BoundableRenderable br = (BoundableRenderable) r;
				Rectangle oldBounds = br.getBounds();
				((BoundableRenderable) r).setX(oldBounds.x + shiftBy);
				if(r instanceof RUIControl) {
					((RUIControl) r).updateWidgetBounds(this.bounds);
				}
			}
		}
	}
	
	public void repaint(int x, int y, int width, int height) {
		Rectangle b = this.bounds;
		this.container.repaint(b.x, b.y, b.width, b.height);		
	}
	/* (non-Javadoc)
	 * @see net.sourceforge.xamj.domimpl.markup.Renderable#getBounds()
	 */
	public Rectangle getBounds() {
		return this.bounds;
	}
	public int getExactWidth() {
		return this.actualWidth;
	}	
	/* (non-Javadoc)
	 * @see net.sourceforge.xamj.domimpl.markup.Renderable#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g) {
		Iterator i = this.renderables.iterator();
		while(i.hasNext()) {
			Object r = i.next();
			if(r instanceof BoundableRenderable) {
				BoundableRenderable br = (BoundableRenderable) r;
				Rectangle bounds = br.getBounds();
				Graphics newG = g.create(bounds.x, bounds.y, bounds.width, bounds.height);
				br.paint(newG);
			}
			else {
				((Renderable) r).paint(g);
			}
		}
	}
	public final void addRenderState(RenderState rs) {
		this.renderables.add(new RStyleChanger(rs));
		this.currentRenderState = rs;
	}
	public final void addStyleChanger(RStyleChanger sc) {
		this.renderables.add(sc);
		this.currentRenderState = sc.getRenderState();
	}
	
//	public boolean couldAdd(int width) {
//		int offset = this.offset;
//		if(offset == 0) {
//			return true;
//		}
//		return offset + width <= this.bounds.width;
//	}
//	
	public RWord getRWord(RenderableContext me, String word) {
		RenderState rs = this.currentRenderState;
		return new RWord(me, word, rs);
	}
	
	public RUIControl getRUIControl(RenderableContext me, UIControl widget) {
		return new RUIControl(me, widget);
	}
	
	public final void add(Renderable renderable) throws OverflowException {
		if(renderable instanceof RWord) {
			this.addWord((RWord) renderable);
		}
		else if(renderable instanceof RUIControl) {
			this.addUIControl((RUIControl) renderable);
		}
		else if(renderable instanceof RStyleChanger) {
			this.addStyleChanger((RStyleChanger) renderable);
		}
		else if(renderable instanceof RBlank) {
			this.addBlank((RBlank) renderable);
		}
		else {
			throw new IllegalArgumentException("Can't add " + renderable);
		}
	}
	
	public final void addWord(RWord wordInfo) throws OverflowException {
		// Check if it fits horzizontally
		int offset = this.offset;
		int wiwidth = wordInfo.width;
		if(offset != 0 && offset + wiwidth > this.bounds.width) {
			ArrayList renderables = this.renderables;
			ArrayList overflow = null;
			boolean cancel = false;
			for(int i = renderables.size(); --i >= 0;) {
				Renderable renderable = (Renderable) renderables.get(i);
				if(renderable instanceof RWord || !(renderable instanceof BoundableRenderable)) {
					if(overflow == null) {
						overflow = new ArrayList();
					}
					if(renderable != wordInfo && renderable instanceof RWord && ((RWord) renderable).getBounds().x == 0) {
						// Can't overflow words starting at offset zero.
						// Note that all or none should be overflown.
						cancel = true;
						// No need to set offset - set later.
						break;
					}
					if(renderable instanceof RWord) {
						int newOffset = ((RWord) renderable).getBounds().x;
						this.offset = newOffset;
						this.actualWidth = newOffset;
					}
					overflow.add(0, renderable);
					renderables.remove(i);
				}
				else {
					break;
				}
			}
			if(!cancel) {
				if(overflow == null) {
					throw new OverflowException(Collections.singleton(wordInfo));
				}
				else {
					overflow.add(wordInfo);
					throw new OverflowException(overflow);
				}
			}
		}

		// Add it
		this.renderables.add(wordInfo);
		wordInfo.setParent(this);
		
		int extraHeight = 0;
		int maxDescent = this.bounds.height - this.baseLineOffset;
		if(wordInfo.descent > maxDescent) {
			extraHeight += (wordInfo.descent - maxDescent);
		}
		int maxAscentPlusLeading = this.baseLineOffset;
		if(wordInfo.ascentPlusLeading > maxAscentPlusLeading) {
			extraHeight += (wordInfo.ascentPlusLeading - maxAscentPlusLeading);
		}
		if(extraHeight > 0) {
			this.adjustHeight(this.bounds.height + extraHeight, 0.0f);
		}
		else {
			int x = offset;
			offset += wiwidth;
			if(offset > this.bounds.width) {
				this.bounds.width = offset;
			}
			this.offset = offset;
			this.actualWidth = offset;
			wordInfo.setBounds(x, this.baseLineOffset - wordInfo.ascentPlusLeading, wiwidth, wordInfo.height);
		}
	}

	public final void addBlank(RenderableContext me) {
		//NOTE: Blanks may be added without concern for wrapping (?)
		int x = this.offset;
		if(x > 0) {
			RenderState rs = this.currentRenderState;
			FontMetrics fm = rs.getFontMetrics();
			int width = fm.charWidth(' ');
			RBlank rblank = new RBlank(me, rs, fm, width);
			rblank.setBounds(x, this.baseLineOffset - rblank.ascentPlusLeading, width, rblank.height);
			this.renderables.add(rblank);
			rblank.setParent(this);
			this.offset = x + width;
			// No update of actualWidth
		}
	}

	public final void addBlank(RBlank rblank) {
		//NOTE: Blanks may be added without concern for wrapping (?)
		int x = this.offset;
		RenderState rs = this.currentRenderState;
		FontMetrics fm = rs.getFontMetrics();
		int width = fm.charWidth(' ');
		rblank.setBounds(x, this.baseLineOffset - rblank.ascentPlusLeading, width, rblank.height);
		this.renderables.add(rblank);
		rblank.setParent(this);
		this.offset = x + width;
	}

	private final void layoutUIControl(RUIControl rwidget, int x) {
		UIControl widget = rwidget.widget;
		int boundsw = this.bounds.width;
		int componentHeight = this.availHeight;
		int pw = widget.getPreferredWidth(boundsw, componentHeight);
		if(pw == -1) {
			pw = boundsw - x;
		}
		int ph = widget.getPreferredHeight(boundsw, componentHeight);
		if(ph == -1) {
			Dimension ps = widget.getPreferredSize();
			ph = ps.height;
			int charHeight = this.currentRenderState.getFontMetrics().getHeight();
			if(ph < charHeight) {
				ph = charHeight;
			}
		}
		this.layoutUIControl(rwidget, x, pw, ph);		
	}

	private final void layoutUIControl(RUIControl rwidget, int x, int width, int height) {
		UIControl widget = rwidget.widget;
		int yoffset;
		if(height >= this.bounds.height) {
			yoffset = 0;
		}
		else {
			yoffset = (int) ((this.bounds.height - height) * widget.getAlignmentY());
		}
		rwidget.setBounds(x, yoffset, width, height);
		// Is this wrong for nested?
		widget.setBounds(this.bounds.x + x, this.bounds.y + yoffset, width, height);
	}
	
	public final void addUIControl(RUIControl rwidget) throws OverflowException {
		// Check if it fits horizontally
		UIControl widget = rwidget.widget;
		int boundsw = this.bounds.width;
		int componentHeight = this.availHeight;
		int pw = widget.getPreferredWidth(boundsw, componentHeight);
		int offset = this.offset;
		if(pw == -1) {
			pw = boundsw - offset;
		}
		if(offset != 0 && offset + pw > boundsw) {
			throw new OverflowException(Collections.singleton(rwidget));
		}
		//Note: Renderable for widget doesn't paint the widget, but
		//it's needed for height readjustment.
		this.renderables.add(rwidget);
		rwidget.setParent(this);
		
		int boundsh = this.bounds.height;
		int ph = widget.getPreferredHeight(boundsw, componentHeight);
		if(ph == -1) {
			Dimension ps = widget.getPreferredSize();
			ph = ps.height;
			int charHeight = this.currentRenderState.getFontMetrics().getHeight(); 
			if(ph < charHeight) {
				ph = charHeight;
			}
		}
		if(ph > boundsh) {
			this.adjustHeight(ph, widget.getAlignmentY());
		}
		else {
			int prevOffset = this.offset;
			this.layoutUIControl(rwidget, prevOffset, pw, ph);
			int newX = prevOffset + rwidget.getBounds().width;
			this.offset = newX;
			this.actualWidth = newX;
			if(newX > boundsw) {
				this.bounds.width = newX;
			}
		}
	}
	
	private void adjustHeight(int newHeight, float alignmentY) {
		// Set new line height
		this.bounds.height = newHeight;
		Renderable[] rarray = (Renderable[]) this.renderables.toArray(Renderable.EMPTY_ARRAY);
		int rlength = rarray.length;
		
		// Find max baseline 
		int maxDescent = 0;
		for(int i = 0; i < rlength; i++) {
			Renderable r = rarray[i];
			if(r instanceof RWord) {
				RWord rword = (RWord) r;
				int descent = rword.descent;
				if(descent > maxDescent) {
					maxDescent = descent;
				}
			}
		}
		
		// Find max ascent 
		int maxAscentPlusLeading = 0;
		for(int i = 0; i < rlength; i++) {
			Renderable r = rarray[i];
			if(r instanceof RWord) {
				RWord rword = (RWord) r;
				int ascentPlusLeading = rword.ascentPlusLeading;
				if(ascentPlusLeading > maxAscentPlusLeading) {
					maxAscentPlusLeading = ascentPlusLeading;
				}
			}
		}
		
		int maxBaseline = newHeight - maxDescent;
		int minBaseline = maxAscentPlusLeading;
		
		//TODO What if the descent is huge and the ascent tiny?
		int baseline = (int) (minBaseline + alignmentY * (maxBaseline - minBaseline));
		this.baseLineOffset = baseline;
		
		// Change bounds of renderables accordingly
		int x = 0;
		this.currentRenderState = this.startRenderState;
		for(int i = 0; i < rlength; i++) {
			Renderable r = rarray[i];
			if(r instanceof RWord) {
				RWord rword = (RWord) r;
				int w = rword.width;
				rword.setBounds(x, baseline - rword.ascentPlusLeading, w, rword.height);
				x += w;
			}
			else if(r instanceof RBlank) {
				RBlank rblank = (RBlank) r;
				int w = rblank.width;
				rblank.setBounds(x, baseline - rblank.ascentPlusLeading, w, rblank.height);
				x += w;
			}
			else if(r instanceof RUIControl) {
				RUIControl rwidget = (RUIControl) r;
				this.layoutUIControl(rwidget, x);
				x += rwidget.getBounds().width;
			}
			else if(r instanceof RStyleChanger) {
				this.currentRenderState = ((RStyleChanger) r).getRenderState();
			}
		}
		this.offset = x;
		this.actualWidth = x;
		//TODO: Could throw OverflowException when we add floating widgets
	}
	
	public void onMouseClick(java.awt.event.MouseEvent event, int x, int y) {
		Renderable[] rarray = (Renderable[]) this.renderables.toArray(Renderable.EMPTY_ARRAY);
		BoundableRenderable r = MarkupUtilities.findRenderable(rarray, x, y, false);
		if(r != null) {
			Rectangle rbounds = r.getBounds();
			r.onMouseClick(event, x - rbounds.x, y - rbounds.y);
		}
	}
    private BoundableRenderable mousePressTarget;
    
	public void onMousePressed(java.awt.event.MouseEvent event, int x, int y) {
		Renderable[] rarray = (Renderable[]) this.renderables.toArray(Renderable.EMPTY_ARRAY);
		BoundableRenderable r = MarkupUtilities.findRenderable(rarray, x, y, false);
		if(r != null) {
			this.mousePressTarget = r;
			Rectangle rbounds = r.getBounds();
			r.onMousePressed(event, x - rbounds.x, y - rbounds.y);
		}
	}
	public void onMouseReleased(java.awt.event.MouseEvent event, int x, int y) {
		Renderable[] rarray = (Renderable[]) this.renderables.toArray(Renderable.EMPTY_ARRAY);
		BoundableRenderable r = MarkupUtilities.findRenderable(rarray, x, y, false);
		if(r != null) {
			Rectangle rbounds = r.getBounds();
			r.onMouseReleased(event, x - rbounds.x, y - rbounds.y);
		}
	}
	public void onMouseDisarmed(java.awt.event.MouseEvent event) {
		BoundableRenderable target = this.mousePressTarget;
		if(target != null) {
			this.mousePressTarget = null;
			target.onMouseDisarmed(event);
		}
	}
	public Color getPaneColor() {
		return this.container.getBackground();
	}
	
	public final void adjustHorizontalBounds(int newX, int newWidth) throws OverflowException {
		this.bounds.x = newX;
		this.bounds.width = newWidth;
		int offsetX = newX - this.bounds.x;
		int topX = newX + newWidth;
		ArrayList renderables = this.renderables;
		int size = renderables.size();
		ArrayList overflown = null;
		for(int i = 0; i < size; i++) {
			Object r = renderables.get(i);
			if(overflown == null) {
				if(r instanceof BoundableRenderable) {
					BoundableRenderable br = (BoundableRenderable) r;
					Rectangle brb = br.getBounds();
					int x1 = brb.x + offsetX;
					int x2 = x1 + brb.width;
					if(x2 > topX) {
						overflown = new ArrayList(1);
					}
					else {
						br.setX(x1);
					}
				}
			}
			/* must not be else here */
			if(overflown != null) {
				//TODO: This could break a word across markup boundary.
				overflown.add(r);
				renderables.remove(i--);
				size--;
			}
		}
		if(overflown != null) {
			throw new OverflowException(overflown);
		}
	}

	/* (non-Javadoc)
	 * @see org.xamjwg.html.renderer.BoundableRenderable#invalidateState(org.xamjwg.html.renderer.RenderableContext)
	 */
	public void invalidateState(RenderableContext context) {
		Object[] renderables = this.renderables.toArray();
		int length = renderables.length;
		for(int i = 0; i < length; i++) {
			Object r = renderables[i];
			if(r instanceof BoundableRenderable) {
				((BoundableRenderable) r).invalidateState(context);
			}
		}
	}
	
	
}
