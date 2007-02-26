/*
 * SqlSwinger.java
 *
 * Created on March 18, 2006, 6:57 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.swing.typed;

/**
 * Class that generates various Swing-related renderers, formatters and editors
 * based on an SqlType.
 * @author citibob
 */
public interface Swinger {
	
	/** Set up which type this Swinger is making stuff for. */
	//public void setSqlType(citibob.sql.SqlType sqlType);
	
	public citibob.swing.typed.JType getJType();
	
	/** Renderer and editor for a CitibobJTable.  If JTable's default
	 renderer and editor is desired, just return null.  Normally, this will
	 just return new TypedWidgetRenderEdit(newTypedWidget())
	 @param editable Is the item we want to render & edit editable?
	 Used to determine which form of renderer is used*/
	public citibob.swing.table.RenderEdit newRenderEdit(boolean editable);
	
	/** Creates an AbstractFormatterFactory for a JFormattedTextField.  If this
	 SqlType is never to be edited with a JFormattedTextField, it can just
	 return null.  NOTE: This should return a new instance of AbstractFormatterFactory
	 because one instance is required per JFormattedTextField.  It's OK for the
	 factory to just store instances of 4 AbstractFormatters and return them as needed. */
	public javax.swing.text.DefaultFormatterFactory newFormatterFactory();

	/** Create a widget suitable for editing this type of data. */
	public citibob.swing.typed.TypedWidget newTypedWidget();
	
	public boolean renderWithWidget();
	
}
