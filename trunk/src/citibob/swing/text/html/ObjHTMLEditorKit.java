/*
 * ObjHTMLEditorKit.java
 *
 * Created on October 7, 2006, 7:00 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 *
 */

package citibob.swing.text.html;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.Cursor;

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.io.Writer;

import javax.accessibility.Accessible;
import javax.accessibility.AccessibleContext;

import javax.swing.Action;
import javax.swing.JEditorPane;
import javax.swing.text.html.*;
import javax.swing.text.*;
import javax.swing.text.html.parser.ParserDelegator;
import java.util.*;

/**
 *
 * @author citibob
 */
public class ObjHTMLEditorKit extends javax.swing.text.html.HTMLEditorKit
{
	
protected ViewFactory vf;
protected HashMap map;
/** Creates a new instance of ObjHTMLEditorKit */
public ObjHTMLEditorKit(HashMap map) {
	this.map = map;
}
/**
* Gets a factory suitable for producing views of any 
* models that are produced by this kit.
* 
* @return the view factory suitable for producing views.
*/
public ViewFactory getViewFactory()
{
	if (vf == null)
	vf = new ObjHTMLFactory();
	return vf;
}
// ==============================================================
class ObjHTMLFactory extends HTMLFactory
{
	/**
     * Creates a {@link View} for the specified <code>Element</code>.
     *
     * @param element the <code>Element</code> to create a <code>View</code>
     *        for
     * @return the <code>View</code> for the specified <code>Element</code>
     *         or <code>null</code> if the type of <code>element</code> is
     *         not supported
     */
	public View create(Element element)
	{
		View view = null;
		Object attr = element.getAttributes().getAttribute(StyleConstants.NameAttribute);
		if (attr instanceof HTML.Tag) {
			HTML.Tag tag = (HTML.Tag) attr;
			if (tag.equals(HTML.Tag.IMPLIED) || tag.equals(HTML.Tag.P)) {
				view = new MinParagraphView(element);
			} else if (tag.equals(HTML.Tag.OBJECT)) {
				view = new ConstObjectView(element, map);
			}
		}
		return (view != null ? view : super.create(element));
	}
}

}