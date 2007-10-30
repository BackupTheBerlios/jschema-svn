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
import org.xamjwg.util.*;
import org.w3c.dom.*;
import org.w3c.dom.css.CSS2Properties;
import org.xamjwg.html.domimpl.*;
import javax.swing.*;
import java.util.*;

/**
 * @author J. H. S.
 */
public class BodyLayout {
	// NOTES:
	// - Renderables should be "lines" in positional order
	// to allow binary searches by position.
	// - Actual size of container is not necessarily same used for rendering.
	
	private final ArrayList renderables = new ArrayList();
	private final Container container;
	private final int listNesting;
	private RLine currentLine;
	private int maxX;
	private int availHeight;
	private int preNesting;
	private RenderState currentRenderState;
	private RenderableContext currentRenderableContext;
	private boolean skipParagraphBreakBefore;
	private boolean skipLineBreakBefore;
	private static final Map elementLayout = new HashMap();
	LayoutArgs layoutArgs;
	
	static {
		Map el = elementLayout;
		EmLayout em = new EmLayout();
		el.put("I", em);
		el.put("EM", em);
		el.put("CITE", em);
		TtLayout tt = new TtLayout();
		el.put("TT", tt);
		el.put("CODE", tt);
		el.put("H1", new HLayout(24));
		el.put("H2", new HLayout(18));
		el.put("H3", new HLayout(15));
		el.put("H4", new HLayout(12));
		el.put("H5", new HLayout(10));
		el.put("H6", new HLayout(8));
		StrongLayout strong = new StrongLayout();
		el.put("B", strong);
		el.put("STRONG", strong);
		el.put("TH", strong);
		el.put("U", new ULayout());
		el.put("STRIKE", new StrikeLayout());
		el.put("BR", new BrLayout());
		el.put("P", new PLayout());
		NopLayout nop = new NopLayout();
		el.put("SCRIPT", nop);
		el.put("NOSCRIPT", nop);
		el.put("HEAD", nop);
		el.put("TITLE", nop);
		el.put("META", nop);
		el.put("STYLE", nop);
		el.put("LINK", nop);
		el.put("IMG", new ImgLayout());
		el.put("TABLE", new TableLayout());
		AnchorLayout anchor = new AnchorLayout();
		el.put("A", anchor);
		el.put("ANCHOR", anchor);
		el.put("INPUT", new InputLayout());
		el.put("TEXTAREA", new TextAreaLayout());
		ListLayout list = new ListLayout();
		el.put("UL", list);
		el.put("OL", list);
		el.put("PRE", new PreLayout());
		el.put("CENTER", new CenterLayout());
		DivLayout div = new DivLayout();
		el.put("DIV", div);
		el.put("BODY", div);
		el.put("HR", new HrLayout());
		el.put("OBJECT", new ObjectLayout());	// RPF
	}
	
	public BodyLayout(Container container, int listNesting, LayoutArgs layoutArgs) {
		this.container = container;
		this.listNesting = listNesting;
		this.layoutArgs = layoutArgs;
	}
	
	/**
	 * Note: Returned dimension needs to be actual size needed for rendered content,
	 * not the available container size. This is relied upon by table layout.
	 */
	public Dimension layout(Dimension containerSize, int availHeight, NodeImpl rootNode, int alignXPercent, int alignYPercent, RenderState rootRenderState) {
		Diagnostics.Assert(EventQueue.isDispatchThread(), "Expected call to be in event thread");
		this.availHeight = availHeight;		
		Container container = this.container;	
		Insets insets = container.getInsets();
		// This invalidates, but that's fine. doLayout() ends up validating?
		container.removeAll();
		ArrayList renderables = this.renderables;
		renderables.clear();
		this.maxX = insets.left;
		//if(logger.isDebugEnabled()) logger.debug("layout(): starting maxX=" + this.maxX);
		this.currentRenderState = rootRenderState;
		this.currentRenderableContext = null;
		this.currentLine = this.addLine(containerSize, insets, null);
		this.currentLine.addRenderState(this.currentRenderState);
		this.skipLineBreakBefore = true;
		this.skipParagraphBreakBefore = true;
		this.leftMargin = null;
		this.rightMargin = null;
		
		if(rootNode instanceof HTMLElementImpl) {
			this.layoutMarkup(container, containerSize, insets, (HTMLElementImpl) rootNode, layoutArgs);
		}
		else {
			this.layoutChildren(container, containerSize, insets, rootNode);
		}
		
		// The following not right because HtmlBlock root
		// node is what created the HtmlBlock.
		
//		if(rootNode instanceof HTMLElementImpl) {
//			String nodeName = rootNode.getNodeName().toUpperCase();
//			MarkupLayout ml = (MarkupLayout) elementLayout.get(nodeName);
//			if(ml != null) {
//				ml.layoutMarkup(this, container, containerSize, insets, (HTMLElementImpl) rootNode);
//			}
//			else {
//				this.layoutMarkup(container, containerSize, insets, (HTMLElementImpl) rootNode);
//			}
//		}
//		else {
//			this.layoutChildren(container, containerSize, insets, rootNode);
//		}
		
		
		RLine lastLine = this.currentLine;
		int lastWidth = lastLine.getExactWidth();
		if(insets.left + lastWidth > this.maxX) {
			this.maxX = insets.left + lastWidth;
		}
		Rectangle lastBounds = this.currentLine.getBounds();
		int maxY = lastBounds.y + lastBounds.height;
		// Horizontal alignment
		//TODO: Alignments do not consider margins
		if(alignXPercent > 0) {
			int availLineWidth = containerSize.width - insets.left - insets.right;
			int numRenderables = renderables.size();
			for(int i = 0; i < numRenderables; i++) {
				Object r = renderables.get(i);
				if(r instanceof RLine) {
					RLine line = (RLine) r;
					int lineWidth = line.getExactWidth();
					int difference = availLineWidth - lineWidth;
					if(difference > 0) {
						int shift = (difference * alignXPercent) / 100;
						line.shiftContentsX(shift);
					}
				}
			}
		}
		
		// Vertical alignment
		if(alignYPercent > 0) {
			int availContentHeight = containerSize.height - insets.top - insets.bottom;
			int usedHeight = maxY - insets.top;
			int difference = availContentHeight - usedHeight;
			if(difference > 0) {
				int shift = (difference * alignYPercent) / 100;
				int numRenderables = renderables.size();
				for(int i = 0; i < numRenderables; i++) {
					Object r = renderables.get(i);
					if(r instanceof RLine) {
						RLine line = (RLine) r;
						Rectangle oldBounds = line.getBounds();
						line.setY(oldBounds.y + shift);
					}
				}
			}
		}
		// Note: This is expected to return the dimensions actually occupied
		// by the content, specifically not the width of the container.
		return new Dimension(insets.right + this.maxX, maxY + insets.bottom);
	}
	
	private RLine addLine(Dimension containerSize, Insets insets, RLine prevLine) {
		int availHeight = this.availHeight;
		Rectangle prevBounds = prevLine == null ? null : prevLine.getBounds();
		int newLineY = prevLine == null ? insets.top : prevBounds.y + prevBounds.height;
		int leftOffset = this.fetchLeftOffset(newLineY);
		int newX = insets.left + leftOffset;
		int newWidth = containerSize.width - insets.left - insets.right - this.fetchRightOffset(newLineY) - leftOffset;
		RLine rline;
		if(prevLine == null) {
			rline = new RLine(this.currentRenderableContext, this.container, this.currentRenderState, availHeight, newX, newLineY, newWidth, 0);
		}
		else {
			int prevLineWidth = prevLine.getExactWidth();
			if(insets.left + prevLineWidth > this.maxX) {
				this.maxX = insets.left + prevLineWidth;
			}
			rline = new RLine(this.currentRenderableContext, this.container, this.currentRenderState, availHeight, newX, newLineY, newWidth, 0);
		}
		// TODO Not terribly efficient to add this to every line:
		rline.addRenderState(this.currentRenderState);
		this.renderables.add(rline);
		this.currentLine = rline;
		return rline;
	}
	
	private void layoutChildren(Container container, Dimension containerSize, Insets insets, NodeImpl node) {
		NodeImpl[] childrenArray = node.getChildrenArray();
		if(childrenArray != null) {
			int length = childrenArray.length;
			for(int i = 0; i < length; i++) {
				NodeImpl child = childrenArray[i];
				short nodeType = child.getNodeType();
				if(nodeType == Node.TEXT_NODE) {
					this.layoutText(container, containerSize, insets, child.getNodeValue());					
				}
				else if(nodeType == Node.ELEMENT_NODE) {
					String nodeName = child.getNodeName().toUpperCase();
					MarkupLayout ml = (MarkupLayout) elementLayout.get(nodeName);
					if(ml != null) {
						ml.layoutMarkup(this, container, containerSize, insets, (HTMLElementImpl) child, layoutArgs);
					}
					else {
						RenderState oldState = this.currentRenderState;
						HTMLElementImpl markupElement = (HTMLElementImpl) child;
						RenderState state = getNewRenderState(markupElement, oldState);
						this.layoutMarkupWithRenderState(container, containerSize, insets, markupElement, state);
					}
				}
				else if(nodeType == Node.COMMENT_NODE) {
					// ignore
				}
				else {
					throw new IllegalStateException("Unknown node: " + child);
				}
			}
		}
	}

	private final void layoutBlock(Container container, Dimension containerSize, Insets insets, HTMLElementImpl markupElement, LayoutArgs layoutArgs) {
		Renderable renderable = (Renderable) markupElement.getContainingBlockContext();
		if(renderable == null) {
			HtmlBlock block = new HtmlBlock(this.listNesting, ColorFactory.TRANSPARENT, this.currentRenderState, false, layoutArgs);
			block.setRootNode(markupElement, false);
			renderable = this.setupNewUIControl(container, markupElement, block);
		}
		else {
			renderable.invalidate();
			container.add((Component) ((RUIControl) renderable).widget);
		}
		RLine prevLine = this.currentLine;
		if(prevLine.getExactWidth() > 0) {
			this.addLineBreak(container, containerSize, insets);
		}
		String align = markupElement.getAttribute("align");
		if(align != null) {
			if("left".equalsIgnoreCase(align)) {
				this.addToLeftMargin(container, containerSize, insets, renderable);
			}
			else if("right".equalsIgnoreCase(align)) {
				this.addToRightMargin(container, containerSize, insets, renderable);				
			}
			else {
				//TODO: other alignments
				this.addRenderable(container, containerSize, insets, renderable);		
			}	
		}
		else {			
			this.addRenderable(container, containerSize, insets, renderable);		
		}		
		this.addLineBreak(container, containerSize, insets);
	}
	
	private final void layoutMarkup(Container container, Dimension containerSize, Insets insets, HTMLElementImpl markupElement, LayoutArgs layoutArgs) {
		RenderState oldState = this.currentRenderState;
		RenderState state = getNewRenderState(markupElement, oldState);
		this.layoutMarkupWithRenderState(container, containerSize, insets, markupElement, state);
	}
	
	private final void layoutMarkupWithStyle(Container container, Dimension containerSize, Insets insets, HTMLElementImpl markupElement, RenderState parentRenderState) {
		RenderState oldState = parentRenderState;
		RenderState state = getNewRenderState(markupElement, oldState);
		this.layoutMarkupWithRenderState(container, containerSize, insets, markupElement, state);
	}

	private final void layoutMarkupWithRenderState(Container container, Dimension containerSize, Insets insets, HTMLElementImpl markupElement, RenderState newRenderState) {
		RenderState oldState = this.currentRenderState;
		boolean changingState = oldState != newRenderState;
		RenderableContext oldMarkupElement = this.currentRenderableContext;
		this.currentRenderableContext = markupElement;
		try {
			if(changingState) {
				this.currentRenderState = newRenderState;
				this.currentLine.addRenderState(newRenderState);
			}
			this.layoutChildren(container, containerSize, insets, markupElement);
		} finally {
			this.currentRenderableContext = oldMarkupElement;
			if(changingState) {
				this.currentRenderState = oldState;
				this.currentLine.addRenderState(oldState);
			}
		}
	}
	private static RenderState getNewRenderState(HTMLElementImpl element, RenderState oldState) {
		//TODO: getStyleNoCreate might be more efficient, but it only
		// works on first render. Changes to the element style are not applied.
		CSS2PropertiesImpl cssprops = element.getStyle();
		if(cssprops != null) {
			return new TextRenderState(oldState, cssprops);
		}
		else {
			return oldState;
		}
	}
	
	private void addParagraphBreak(Container container, Dimension containerSize, Insets insets) {
		this.addLineBreak(container, containerSize, insets);
		this.addLineBreak(container, containerSize, insets);
	}
	private void addLineBreak(Container container, Dimension containerSize, Insets insets) {
		RLine line = this.currentLine;
		if(line.getBounds().height == 0) {
			RenderState rs = this.currentRenderState;
			line.setHeight(rs.getFontMetrics().getHeight());
		}
		this.currentLine = this.addLine(containerSize, insets, this.currentLine);
	}
	
	private void addRenderable(Container container, Dimension containerSize, Insets insets, Renderable renderable) {
		this.skipLineBreakBefore = false;
		this.skipParagraphBreakBefore = false;
		RLine line = this.currentLine;
		try {
			line.add(renderable);
		} catch(OverflowException oe) {
			this.addLine(containerSize, insets, line);
			Collection renderables = oe.getRenderables();
			Iterator i = renderables.iterator();
			while(i.hasNext()) {
				Renderable r = (Renderable) i.next();
				this.addRenderable(container, containerSize, insets, r);
			}
		}
		
	}
			
	private void layoutText(Container container, Dimension containerSize, Insets insets, String text) {
		if(this.preNesting == 0) {
//			StringTokenizer tok = new StringTokenizer(text, " \r\n\t", true);
//			boolean lastBlank = false;
//			while(tok.hasMoreTokens()) {
//				String token = tok.nextToken().trim();
//				if("".equals(token)) {
//					if(!lastBlank) {
//						// Do not add as renderable or
//						// fix skip*BreakBefore.
//						this.currentLine.addBlank(this.currentRenderableContext);
//					}
//					lastBlank = true;
//				}
//				else {
//					this.layoutWord(container, containerSize, insets, token);
//					lastBlank = false;
//				}
//			}
			
			int length = text.length();
			StringBuffer word = new StringBuffer();
			for(int i = 0; i < length; i++) {
				char ch = text.charAt(i);
				if(Character.isWhitespace(ch)) {
					int wlen = word.length();
					if(wlen > 0) {
						this.layoutWord(container, containerSize, insets, word.toString());
						word.delete(0, wlen);
					}
					this.currentLine.addBlank(this.currentRenderableContext);					
					for(i++; i < length; i++) {
						ch = text.charAt(i);
						if(!Character.isWhitespace(ch)) {
							word.append(ch);
							break;
						}
					}
				}
				else {
					word.append(ch);
				}
			}
			if(word.length() > 0) {
				this.layoutWord(container, containerSize, insets, word.toString());
			}
		}
		else {
			int length = text.length();
			boolean lastCharSlashR = false;
			StringBuffer line = new StringBuffer();
			for(int i = 0; i < length; i++) {
				char ch = text.charAt(i);
				switch(ch) {
				case '\r':
					lastCharSlashR = true;
					break;
				case '\n':
					int llen = line.length();
					if(llen > 0) {
						this.layoutWord(container, containerSize, insets, line.toString());
						line.delete(0, line.length());
					}
					this.addLine(containerSize, insets, this.currentLine);
					break;
				default:
					if(lastCharSlashR) {
						line.append('\r');
						lastCharSlashR = false;
					}
					line.append(ch);
					break;
				}
			}
			if(line.length() > 0) {
				this.layoutWord(container, containerSize, insets, line.toString());
			}
		}
	}

	private void layoutWord(Container container, Dimension containerSize, Insets insets, String word) {
		RLine line = this.currentLine;
		RWord rword = line.getRWord(this.currentRenderableContext, word);
		this.addRenderable(container, containerSize, insets, rword);
	}
	
	public Renderable[] getRenderables() {
		return (Renderable[]) this.renderables.toArray(Renderable.EMPTY_ARRAY);
	}
	
	public Iterator getRenderables(Rectangle clipBounds) {
		Diagnostics.Assert(EventQueue.isDispatchThread(), "Expected call to be in event thread");
		Renderable[] array = (Renderable[]) this.renderables.toArray(Renderable.EMPTY_ARRAY);
		Range range = MarkupUtilities.findRenderables(array, clipBounds, true);
		return org.xamjwg.util.ArrayUtilities.iterator(array, range.offset, range.length);
	}
	
	public BoundableRenderable getRenderable(java.awt.Point point) {
		Diagnostics.Assert(EventQueue.isDispatchThread(), "Expected call to be in event thread");
		Renderable[] array = (Renderable[]) this.renderables.toArray(Renderable.EMPTY_ARRAY);
		return MarkupUtilities.findRenderable(array, point, true);
	}
	
	private Renderable setupNewUIControl(Container container, HTMLElementImpl element, UIControl control) {
		BoundableRenderable renderable = new RUIControl(element, control);
		element.setContainingBlockContext((ContainingBlockContext) renderable);
		container.add((Component) control);
		return renderable;
	}

	private final void addAlignable(Container container, Dimension containerSize, Insets insets, HTMLElementImpl markupElement, Renderable renderable) {
		String align = markupElement.getAttribute("align");
		if(align != null) {
			if("left".equalsIgnoreCase(align)) {
				this.addToLeftMargin(container, containerSize, insets, renderable);
			}
			else if("right".equalsIgnoreCase(align)) {
				this.addToRightMargin(container, containerSize, insets, renderable);				
			}
			else {
				//TODO: other alignments
				this.addRenderable(container, containerSize, insets, renderable);		
			}	
		}
		else {			
			this.addRenderable(container, containerSize, insets, renderable);		
		}		
	}
	
	private final void addAlignable2(Container container, Dimension containerSize, Insets insets, HTMLElementImpl markupElement, Renderable renderable) {
		boolean regularAdd = false;
		String align = markupElement.getAttribute("align");
		if(align != null) {
			if("left".equalsIgnoreCase(align)) {
				this.addToLeftMargin(container, containerSize, insets, renderable);
			}
			else if("right".equalsIgnoreCase(align)) {
				this.addToRightMargin(container, containerSize, insets, renderable);				
			}
			else {
				regularAdd = true;
			}	
		}
		else {
			regularAdd = true;
		}		
		if(regularAdd) {
			RLine prevLine = this.currentLine;
			if(prevLine.getExactWidth() > 0) {
				this.addLineBreak(container, containerSize, insets);
			}
			this.addRenderable(container, containerSize, insets, renderable);		
			this.addLineBreak(container, containerSize, insets);
		}
	}

	private final void layoutImg(Container container, Dimension containerSize, Insets insets, HTMLElementImpl markupElement, LayoutArgs layoutArgs) {
		Renderable renderable = (Renderable) markupElement.getContainingBlockContext();
		if(renderable == null) {
			renderable = this.setupNewUIControl(container, markupElement, new ImgControl(markupElement));
		}
		else {
			renderable.invalidate();
			container.add((Component) ((RUIControl) renderable).widget);
		}
		this.addAlignable(container, containerSize, insets, markupElement, renderable);
	}

	private final void layoutHr(Container container, Dimension containerSize, Insets insets, HTMLElementImpl markupElement, LayoutArgs layoutArgs) {
		Renderable renderable = (Renderable) markupElement.getContainingBlockContext();
		if(renderable == null) {
			renderable = this.setupNewUIControl(container, markupElement, new HrControl(markupElement, this.currentRenderState));
		}
		else {
			renderable.invalidate();
			container.add((Component) ((RUIControl) renderable).widget);
		}
		this.addAlignable(container, containerSize, insets, markupElement, renderable);
	}

	private final BaseInputControl createInputControl(HTMLElementImpl markupElement, LayoutArgs layoutArgs) {
//	private final BaseControl createInputControl(HTMLElementImpl markupElement, LayoutArgs layoutArgs) {
		String type = markupElement.getAttribute("type");
		if(type == null) {
			return new InputTextControl(markupElement, this.currentRenderState);
		}
		type = type.toLowerCase();
		if("text".equals(type) || "".equals(type)) {
			return new InputTextControl(markupElement, this.currentRenderState);
		}
		else if("hidden".equals(type)) {
			return null;
		}
		else if("submit".equals(type)) {
			return new InputButtonControl(markupElement);
		}
		else if("password".equals(type)) {
			return new InputPasswordControl(markupElement, this.currentRenderState);
		}
		else if("radio".equals(type)) {
			return new InputRadioControl(markupElement);
		}
		else if("checkbox".equals(type)) {
			return new InputCheckboxControl(markupElement);
		}
		else if("reset".equals(type)) {
			return new InputButtonControl(markupElement);
		}
//		else if("obj".equals(type)) {
//			String name = markupElement.getAttribute("name");
//			if (name == null) return null;	// Can't seem to create it.
//			JComponent widget = (JComponent)layoutArgs.getWidgetMap().get(name);
////			return new ObjectControl(markupElement, widget);
//			return new InputButtonControl(markupElement);
//		}
		else {
			return null;
		}
	}
	
	
	private final void layoutInput(Container container, Dimension containerSize, Insets insets, HTMLElementImpl markupElement, LayoutArgs layoutArgs) {
		Renderable renderable = (Renderable) markupElement.getContainingBlockContext();
		if(renderable == null) {
			BaseInputControl uiControl = this.createInputControl(markupElement, layoutArgs);
//if (uiControl instanceof BaseInputControl) {
//BaseInputControl uic = (BaseInputControl)uiControl;
			if(uiControl == null) {
				return;
			}
			((HTMLInputElementImpl) markupElement).setInputContext(uiControl);
//}
			renderable = this.setupNewUIControl(container, markupElement, uiControl);
		}
		else {
			renderable.invalidate();
			container.add((Component) ((RUIControl) renderable).widget);
		}
		this.addAlignable(container, containerSize, insets, markupElement, renderable);
	}
	
	private final void layoutObject(Container container, Dimension containerSize, Insets insets, HTMLElementImpl markupElement, LayoutArgs layoutArgs) {
		Renderable renderable = (Renderable) markupElement.getContainingBlockContext();
		if(renderable == null) {
			String name = markupElement.getAttribute("name");
			if (name == null) return;	// Can't seem to create it.
			JComponent widget = (JComponent)layoutArgs.getWidgetMap().get(name);
			if (widget == null) return;
			BaseControl uiControl = new ObjectControl(markupElement, widget);
//uiControl = new InputButtonControl(markupElement);
			renderable = this.setupNewUIControl(container, markupElement, uiControl);
		}
		else {
			renderable.invalidate();
			container.add((Component) ((RUIControl) renderable).widget);
		}
		this.addAlignable(container, containerSize, insets, markupElement, renderable);
	}
	
	private final void layoutTextArea(Container container, Dimension containerSize, Insets insets, HTMLElementImpl markupElement, LayoutArgs layoutArgs) {
		Renderable renderable = (Renderable) markupElement.getContainingBlockContext();
		if(renderable == null) {
			BaseInputControl uiControl = new InputTextAreaControl(markupElement);
			if(uiControl == null) {
				return;
			}
			((HTMLBaseInputElement) markupElement).setInputContext(uiControl);
			renderable = this.setupNewUIControl(container, markupElement, uiControl);
		}
		else {
			renderable.invalidate();
			container.add((Component) ((RUIControl) renderable).widget);
		}
		this.addAlignable(container, containerSize, insets, markupElement, renderable);
	}

	private LineMargin leftMargin = null;
	private LineMargin rightMargin = null;
	
	private final int fetchLeftOffset(int newLineY) {
		RLine line = this.currentLine;
		if(line == null) {
			return 0;
		}
		LineMargin left = this.leftMargin;
		int baseY = newLineY;
		while(left != null) {
			if(baseY < left.getClearY()) {
				return left.getOffset();
			}
			else {
				left = left.getNext();
				this.leftMargin = left;
			}
		}
		return 0;
	}
	
	private final int fetchRightOffset(int newLineY) {
		RLine line = this.currentLine;
		if(line == null) {
			return 0;
		}
		LineMargin right = this.rightMargin;
		int baseY = newLineY;
		while(right != null) {
			if(baseY < right.getClearY()) {
				return right.getOffset();
			}
			else {
				right = right.getNext();
				this.rightMargin = right;
			}
		}
		return 0;
	}
	private final void checkLineOverflow(Dimension containerSize, Insets insets) {
		RLine line = this.currentLine;
		Rectangle bounds = line.getBounds();
		int leftOffset = this.fetchLeftOffset(bounds.y);
		int rightOffset = this.fetchRightOffset(bounds.y);
		int newX = insets.left + leftOffset;
		int newWidth = containerSize.width - insets.left - insets.right - rightOffset - leftOffset;
		if(newX != bounds.x || newWidth != bounds.width) {
			try {
				line.adjustHorizontalBounds(newX, newWidth);
			} catch(OverflowException oe) {
				this.addLine(containerSize, insets, line);
				Collection renderables = oe.getRenderables();
				Iterator i = renderables.iterator();
				while(i.hasNext()) {
					Renderable r = (Renderable) i.next();
					this.addRenderable(container, containerSize, insets, r);
				}
			}
		}
	}
	
	private final void addToLeftMargin(Container container, Dimension containerSize, Insets insets, Renderable renderable) {
		UIControl uicontrol = ((RUIControl) renderable).widget;
		int y = this.currentLine.getBounds().y;
		int leftOffset = this.fetchLeftOffset(y);
		int x = insets.left + leftOffset;
		int availWidth = containerSize.width - insets.left - insets.right;
		int availHeight = this.availHeight;
		int width = uicontrol.getPreferredWidth(availWidth, availHeight);
		int height = uicontrol.getPreferredHeight(availWidth, availHeight);
		uicontrol.setBounds(x, y, width, height);
		LineMargin newMargin = new LineMargin(this.leftMargin, y + height, leftOffset + width);
		this.leftMargin = newMargin;
		this.checkLineOverflow(containerSize, insets);
	}
	
	private final void addToRightMargin(Container container, Dimension containerSize, Insets insets, Renderable renderable) {
		UIControl uicontrol = ((RUIControl) renderable).widget;
		int y = this.currentLine.getBounds().y;
		int rightOffset = this.fetchRightOffset(y);
		int availWidth = containerSize.width - insets.left - insets.right;
		int availHeight = this.availHeight;
		int width = uicontrol.getPreferredWidth(availWidth, availHeight);
		int height = uicontrol.getPreferredHeight(availWidth, availHeight);
		int x = containerSize.width - insets.right - rightOffset - width;
		uicontrol.setBounds(x, y, width, height);
		LineMargin newMargin = new LineMargin(this.rightMargin, y + height, rightOffset + width);
		this.rightMargin = newMargin;
		this.checkLineOverflow(containerSize, insets);
	}

	public int getFirstLineHeight() {
		ArrayList renderables = this.renderables;
		int size = renderables.size();
		if(size == 0) {
			return 0;
		}
		BoundableRenderable br = (BoundableRenderable) renderables.get(0);
		return br.getBounds().height;		
	}

	private static class NopLayout implements MarkupLayout {
		/* (non-Javadoc)
		 * @see org.xamjwg.html.renderer.MarkupLayout#layoutMarkup(java.awt.Container, java.awt.Insets, org.xamjwg.html.domimpl.HTMLElementImpl)
		 */
		public void layoutMarkup(BodyLayout bodyLayout, Container container, Dimension containerSize, Insets insets, HTMLElementImpl markupElement, LayoutArgs layoutArgs) {
		}
	}

	private static class EmLayout implements MarkupLayout {
		/* (non-Javadoc)
		 * @see org.xamjwg.html.renderer.MarkupLayout#layoutMarkup(java.awt.Container, java.awt.Insets, org.xamjwg.html.domimpl.HTMLElementImpl)
		 */
		public void layoutMarkup(BodyLayout bodyLayout, Container container, Dimension containerSize, Insets insets, HTMLElementImpl markupElement, LayoutArgs layoutArgs) {
			RenderState oldRenderState = bodyLayout.currentRenderState;
			RenderState newState = new FontStyleRenderState(oldRenderState, Font.ITALIC);
			bodyLayout.layoutMarkupWithStyle(container, containerSize, insets, markupElement, newState);
		}
	}

	private static class TtLayout implements MarkupLayout {
		/* (non-Javadoc)
		 * @see org.xamjwg.html.renderer.MarkupLayout#layoutMarkup(java.awt.Container, java.awt.Insets, org.xamjwg.html.domimpl.HTMLElementImpl)
		 */
		public void layoutMarkup(BodyLayout bodyLayout, Container container, Dimension containerSize, Insets insets, HTMLElementImpl markupElement, LayoutArgs layoutArgs) {
			RenderState oldRenderState = bodyLayout.currentRenderState;
			RenderState newState = new FontNameRenderState(oldRenderState, "Monospaced");
			bodyLayout.layoutMarkupWithStyle(container, containerSize, insets, markupElement, newState);
		}
	}

	private static class HLayout implements MarkupLayout {
		private final int fontSize;
		
		public HLayout(int fontSize) {
			this.fontSize = fontSize;
		}
		
		/* (non-Javadoc)
		 * @see org.xamjwg.html.renderer.MarkupLayout#layoutMarkup(java.awt.Container, java.awt.Insets, org.xamjwg.html.domimpl.HTMLElementImpl)
		 */
		public void layoutMarkup(BodyLayout bodyLayout, Container container, Dimension containerSize, Insets insets, HTMLElementImpl markupElement, LayoutArgs layoutArgs) {
			boolean spb = bodyLayout.skipParagraphBreakBefore;
			if(!spb) {
				bodyLayout.addParagraphBreak(container, containerSize, insets);
				bodyLayout.skipParagraphBreakBefore = true;
			}
			RenderState oldRenderState = bodyLayout.currentRenderState;
			RenderState newState = new FontSizeRenderState(oldRenderState, fontSize, Font.BOLD);
			bodyLayout.layoutMarkupWithStyle(container, containerSize, insets, markupElement, newState);			
			bodyLayout.addParagraphBreak(container, containerSize, insets);
			bodyLayout.skipParagraphBreakBefore = true;				
		}
	}

	private static class ULayout implements MarkupLayout {
		/* (non-Javadoc)
		 * @see org.xamjwg.html.renderer.MarkupLayout#layoutMarkup(java.awt.Container, java.awt.Insets, org.xamjwg.html.domimpl.HTMLElementImpl)
		 */
		public void layoutMarkup(BodyLayout bodyLayout, Container container, Dimension containerSize, Insets insets, HTMLElementImpl markupElement, LayoutArgs layoutArgs) {
			RenderState oldRenderState = bodyLayout.currentRenderState;
			RenderState newState = new TextDecorationRenderState(oldRenderState, RenderState.MASK_TEXTDECORATION_UNDERLINE);
			bodyLayout.layoutMarkupWithStyle(container, containerSize, insets, markupElement, newState);
		}
	}

	private static class StrikeLayout implements MarkupLayout {
		/* (non-Javadoc)
		 * @see org.xamjwg.html.renderer.MarkupLayout#layoutMarkup(java.awt.Container, java.awt.Insets, org.xamjwg.html.domimpl.HTMLElementImpl)
		 */
		public void layoutMarkup(BodyLayout bodyLayout, Container container, Dimension containerSize, Insets insets, HTMLElementImpl markupElement, LayoutArgs layoutArgs) {
			RenderState oldRenderState = bodyLayout.currentRenderState;
			RenderState newState = new TextDecorationRenderState(oldRenderState, RenderState.MASK_TEXTDECORATION_LINE_THROUGH);
			bodyLayout.layoutMarkupWithStyle(container, containerSize, insets, markupElement, newState);
		}
	}

	private static class StrongLayout implements MarkupLayout {
		/* (non-Javadoc)
		 * @see org.xamjwg.html.renderer.MarkupLayout#layoutMarkup(java.awt.Container, java.awt.Insets, org.xamjwg.html.domimpl.HTMLElementImpl)
		 */
		public void layoutMarkup(BodyLayout bodyLayout, Container container, Dimension containerSize, Insets insets, HTMLElementImpl markupElement, LayoutArgs layoutArgs) {
			RenderState oldRenderState = bodyLayout.currentRenderState;
			RenderState newRenderState = new FontStyleRenderState(oldRenderState, Font.BOLD);
			bodyLayout.layoutMarkupWithStyle(container, containerSize, insets, markupElement, newRenderState);
		}
	}
	
	private static class AnchorLayout implements MarkupLayout {
		/* (non-Javadoc)
		 * @see org.xamjwg.html.renderer.MarkupLayout#layoutMarkup(java.awt.Container, java.awt.Insets, org.xamjwg.html.domimpl.HTMLElementImpl)
		 */
		public void layoutMarkup(BodyLayout bodyLayout, Container container, Dimension containerSize, Insets insets, HTMLElementImpl markupElement, LayoutArgs layoutArgs) {
			RenderState oldRenderState = bodyLayout.currentRenderState;
			RenderState interState = new TextDecorationRenderState(oldRenderState, RenderState.MASK_TEXTDECORATION_UNDERLINE);
			RenderState newRenderState = new ColorRenderState(interState, Color.BLUE);
			bodyLayout.layoutMarkupWithStyle(container, containerSize, insets, markupElement, newRenderState);
		}
	}
	
	private static class PLayout implements MarkupLayout {
		/* (non-Javadoc)
		 * @see org.xamjwg.html.renderer.MarkupLayout#layoutMarkup(java.awt.Container, java.awt.Insets, org.xamjwg.html.domimpl.HTMLElementImpl)
		 */
		public void layoutMarkup(BodyLayout bodyLayout, Container container, Dimension containerSize, Insets insets, HTMLElementImpl markupElement, LayoutArgs layoutArgs) {
			boolean spb = bodyLayout.skipParagraphBreakBefore;
			if(!spb) {
				bodyLayout.addParagraphBreak(container, containerSize, insets);
				bodyLayout.skipParagraphBreakBefore = true;
			}
			bodyLayout.layoutMarkup(container, containerSize, insets, markupElement, layoutArgs);
			if(markupElement.hasChildNodes()) {
				bodyLayout.addParagraphBreak(container, containerSize, insets);
				bodyLayout.skipParagraphBreakBefore = true;				
			}
		}
	}

	private static class BrLayout implements MarkupLayout {
		/* (non-Javadoc)
		 * @see org.xamjwg.html.renderer.MarkupLayout#layoutMarkup(java.awt.Container, java.awt.Insets, org.xamjwg.html.domimpl.HTMLElementImpl)
		 */
		public void layoutMarkup(BodyLayout bodyLayout, Container container, Dimension containerSize, Insets insets, HTMLElementImpl markupElement, LayoutArgs layoutArgs) {
			bodyLayout.addLineBreak(container, containerSize, insets);
		}
	}

	private static class ImgLayout implements MarkupLayout {
		/* (non-Javadoc)
		 * @see org.xamjwg.html.renderer.MarkupLayout#layoutMarkup(java.awt.Container, java.awt.Insets, org.xamjwg.html.domimpl.HTMLElementImpl)
		 */
		public void layoutMarkup(BodyLayout bodyLayout, Container container, Dimension containerSize, Insets insets, HTMLElementImpl markupElement, LayoutArgs layoutArgs) {
			bodyLayout.layoutImg(container, containerSize, insets, markupElement, layoutArgs);
		}
	}

	private static class HrLayout implements MarkupLayout {
		/* (non-Javadoc)
		 * @see org.xamjwg.html.renderer.MarkupLayout#layoutMarkup(java.awt.Container, java.awt.Insets, org.xamjwg.html.domimpl.HTMLElementImpl)
		 */
		public void layoutMarkup(BodyLayout bodyLayout, Container container, Dimension containerSize, Insets insets, HTMLElementImpl markupElement, LayoutArgs layoutArgs) {
			bodyLayout.layoutHr(container, containerSize, insets, markupElement, layoutArgs);
		}
	}

	private static class InputLayout implements MarkupLayout {
		/* (non-Javadoc)
		 * @see org.xamjwg.html.renderer.MarkupLayout#layoutMarkup(java.awt.Container, java.awt.Insets, org.xamjwg.html.domimpl.HTMLElementImpl)
		 */
		public void layoutMarkup(BodyLayout bodyLayout, Container container, Dimension containerSize, Insets insets, HTMLElementImpl markupElement, LayoutArgs layoutArgs) {
			bodyLayout.layoutInput(container, containerSize, insets, markupElement, layoutArgs);
		}
	}
	
	/* --------- RPF */
	private static class ObjectLayout implements MarkupLayout {
		/* (non-Javadoc)
		 * @see org.xamjwg.html.renderer.MarkupLayout#layoutMarkup(java.awt.Container, java.awt.Insets, org.xamjwg.html.domimpl.HTMLElementImpl)
		 */
		public void layoutMarkup(BodyLayout bodyLayout, Container container, Dimension containerSize, Insets insets, HTMLElementImpl markupElement, LayoutArgs layoutArgs) {
			bodyLayout.layoutObject(container, containerSize, insets, markupElement, layoutArgs);
		}
	}

	private static class TextAreaLayout implements MarkupLayout {
		/* (non-Javadoc)
		 * @see org.xamjwg.html.renderer.MarkupLayout#layoutMarkup(java.awt.Container, java.awt.Insets, org.xamjwg.html.domimpl.HTMLElementImpl)
		 */
		public void layoutMarkup(BodyLayout bodyLayout, Container container, Dimension containerSize, Insets insets, HTMLElementImpl markupElement, LayoutArgs layoutArgs) {
			bodyLayout.layoutTextArea(container, containerSize, insets, markupElement, layoutArgs);
		}
	}

	private static class TableLayout implements MarkupLayout {
		/* (non-Javadoc)
		 * @see org.xamjwg.html.renderer.MarkupLayout#layoutMarkup(java.awt.Container, java.awt.Insets, org.xamjwg.html.domimpl.HTMLElementImpl)
		 */
		public void layoutMarkup(BodyLayout bodyLayout, Container container, Dimension containerSize, Insets insets, HTMLElementImpl markupElement, LayoutArgs layoutArgs) {
			Renderable renderable = (Renderable) markupElement.getContainingBlockContext();
			if(renderable == null) {
				renderable = bodyLayout.setupNewUIControl(container, markupElement,
					new TableControl((HTMLTableElementImpl) markupElement, layoutArgs));
			}
			else {
				renderable.invalidate();
				container.add((Component) ((RUIControl) renderable).widget);
			}
			bodyLayout.addAlignable2(container, containerSize, insets, markupElement, renderable);
		}
	}

	private static class ListLayout implements MarkupLayout {
		
		/* (non-Javadoc)
		 * @see org.xamjwg.html.renderer.MarkupLayout#layoutMarkup(java.awt.Container, java.awt.Insets, org.xamjwg.html.domimpl.HTMLElementImpl)
		 */
		public void layoutMarkup(BodyLayout bodyLayout, Container container, Dimension containerSize, Insets insets, HTMLElementImpl markupElement, LayoutArgs layoutArgs) {
			Renderable renderable = (Renderable) markupElement.getContainingBlockContext();
			if(renderable == null) {
				renderable = bodyLayout.setupNewUIControl(container, markupElement,
					new ListControl(markupElement, bodyLayout.listNesting, layoutArgs));
			}
			else {
				renderable.invalidate();
				container.add((Component) ((RUIControl) renderable).widget);
			}
			RLine prevLine = bodyLayout.currentLine;
			if(prevLine.getExactWidth() > 0) {
				bodyLayout.addLineBreak(container, containerSize, insets);
			}
			bodyLayout.addRenderable(container, containerSize, insets, renderable);
			bodyLayout.addLineBreak(container, containerSize, insets);
		}
	}

	private static class PreLayout implements MarkupLayout {
		/* (non-Javadoc)
		 * @see org.xamjwg.html.renderer.MarkupLayout#layoutMarkup(java.awt.Container, java.awt.Insets, org.xamjwg.html.domimpl.HTMLElementImpl)
		 */
		public void layoutMarkup(BodyLayout bodyLayout, Container container, Dimension containerSize, Insets insets, HTMLElementImpl markupElement, LayoutArgs layoutArgs) {
			bodyLayout.preNesting++;
			try {
				bodyLayout.layoutMarkup(container, containerSize, insets, markupElement, layoutArgs);
			} finally {
				bodyLayout.preNesting--;
			}
		}
	}

	private static class CenterLayout implements MarkupLayout {
		/* (non-Javadoc)
		 * @see org.xamjwg.html.renderer.MarkupLayout#layoutMarkup(java.awt.Container, java.awt.Insets, org.xamjwg.html.domimpl.HTMLElementImpl)
		 */
		public void layoutMarkup(BodyLayout bodyLayout, Container container, Dimension containerSize, Insets insets, HTMLElementImpl markupElement, LayoutArgs layoutArgs) {
			CSS2Properties props = markupElement.getStyle();
			props.setTextAlign("center");
			bodyLayout.layoutBlock(container, containerSize, insets, markupElement, layoutArgs);
		}
	}

	private static class DivLayout implements MarkupLayout {
		/* (non-Javadoc)
		 * @see org.xamjwg.html.renderer.MarkupLayout#layoutMarkup(java.awt.Container, java.awt.Insets, org.xamjwg.html.domimpl.HTMLElementImpl)
		 */
		public void layoutMarkup(BodyLayout bodyLayout, Container container, Dimension containerSize, Insets insets, HTMLElementImpl markupElement, LayoutArgs layoutArgs) {
			bodyLayout.layoutBlock(container, containerSize, insets, markupElement, layoutArgs);
		}
	}
}
