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

package citibob.swing.typed;

//import citibob.sql.*;
//import citibob.jschema.JType;

/**
 *
 * @author citibob
 */
public abstract class TypedWidgetSwinger implements Swinger
{

//TypedWidget tw;
protected JType jType;


/** Creates a new instance of TypedWidgetSTFactory */
public TypedWidgetSwinger(JType jType) {
	//this.tw = tw;
	this.jType = jType;
}

public JType getJType() { return jType; }

/** Renderer and editor for a CitibobJTable.  If JTable's default
 renderer and editor is desired, just return null.  Normally, this will
 just return new TypedWidgetRenderEdit(newTypedWidget()) */
public citibob.swing.table.RenderEdit newRenderEdit(boolean editable)
	{ return new TypedWidgetRenderEdit(this, editable); }

/** By default, no associated text formatter; render with widget. */
public javax.swing.text.DefaultFormatterFactory newFormatterFactory()
	{ return null; }
public boolean renderWithWidget() { return true; }

/** Create a widget suitable for editing this type of data. */
public citibob.swing.typed.TypedWidget newTypedWidget()
{
	TypedWidget tww = createTypedWidget();
	tww.setJType(this);	// relies on newFormatterFactory
	return tww;	
}

/** Just create the widget with a new command. */
abstract protected citibob.swing.typed.TypedWidget createTypedWidget();

}
