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
public citibob.swing.table.RenderEdit newRenderEdit()
	{ return new TypedWidgetRenderEdit(this); }

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
