/*
JSchema: library for GUI-based database applications
This file Copyright (c) 2006-2007 by Robert Fischer

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
/*
 * TypedWidgetSTFactory.java
 *
 * Created on March 18, 2006, 6:14 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.swingers;

//import citibob.sql.*;
//import citibob.jschema.JType;
import citibob.swing.typed.*;
import citibob.swing.typed.Swinger;
import citibob.types.JType;
import java.text.*;
import javax.swing.text.*;
import javax.swing.*;
import java.util.*;
import javax.swing.table.*;
import citibob.text.*;

/**
 *
 * @author citibob
 */
public abstract class AbstractSwinger implements Swinger
{

protected JType jType;
protected SFormat sformat;
boolean renderWithWidget;		// Should the editor use the TypedWidget (or just a standard String label)?

/** Creates a new instance of TypedWidgetSTFactory */
public AbstractSwinger(JType jType, SFormat sformat)
	{ this(jType, sformat, false); }
public AbstractSwinger(JType jType, SFormat sformat, boolean renderWithWidget)
{
	this.jType = jType;
	this.sformat = sformat;
	this.renderWithWidget = renderWithWidget;
}


public void setRenderWithWidget(boolean b) { this.renderWithWidget = b; }
public boolean isRenderWithWidget() { return this.renderWithWidget; }

public SFormat getSFormat() { return sformat; }
public JType getJType() { return jType; }

/** Renderer and editor for a CitibobJTable.  If JTable's default
 renderer and editor is desired, just return null.  Normally, this will
 just return new TypedWidgetRenderEdit(newTypedWidget()) */
public RenderEdit newRenderEdit()
{
	TableCellRenderer rendererNotEditable = new SFormatRenderer(sformat);
	TableCellRenderer rendererEditable =
		(renderWithWidget ? new TypedWidgetRenderer(newWidget()) : rendererNotEditable);
	TableCellEditor editor = new TypedWidgetEditor(newWidget());
	return new DefaultRenderEdit(rendererNotEditable, rendererEditable, editor);
//	return new DefaultRenderEdit(rendererNotEditable, rendererEditable, editor);
//	return new TypedWidgetRenderEdit(newWidget(), getSFormat(), renderWithWidget);
}


// -------------------------------------------------------------------
// -------------------------------------------------------------------
/** Create a widget suitable for editing this type of data. */
public citibob.swing.typed.TypedWidget newWidget()
{
	TypedWidget tw = createWidget();
	configureWidget(tw);
	return tw;
}

/** Just create the widget, do not configure it. */
abstract protected citibob.swing.typed.TypedWidget createWidget();

///** Override this.  Most swingers don't have to configure their widgets,
//but Swingers for complex widget types do. */
//public void configureWidget(TypedWidget tw) {}

// ============================================================

}
