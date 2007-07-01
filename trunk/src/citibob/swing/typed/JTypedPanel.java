/*
 * JTypedScrollPane.java
 *
 * Created on June 8, 2007, 10:01 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.swing.typed;

/**
 * Base class to create complex typed widgets
 * @author citibob
 */
public class JTypedPanel
extends javax.swing.JPanel implements TypedWidget, java.beans.PropertyChangeListener
{

/** Set by overriding class; the value of this object will be passed along.
 Often, this will be a JTypedSelectTable. */
private TypedWidget sub;

java.beans.PropertyChangeListener listener;

public TypedWidget getSubWidget() { return sub; }
public void setSubWidget(TypedWidget xsub)
{
	if (sub != null) sub.removePropertyChangeListener("value", this);
	this.sub = xsub;
	sub.addPropertyChangeListener("value", this);
}


/** Returns last legal value of the widget.  Same as method in JFormattedTextField */
public Object getValue() { return getSubWidget().getValue(); }

/** Sets the value.  Same as method in JFormattedTextField.  Fires a
 * propertyChangeEvent("value") when calling setValue() changes the value. */
public void setValue(Object o) { getSubWidget().setValue(o); }

/** From TableCellEditor (in case this is being used in a TableCellEditor):
 * Tells the editor to stop editing and accept any partially edited value
 * as the value of the editor. The editor returns false if editing was not
 * stopped; this is useful for editors that validate and can not accept
 * invalid entries. */
public boolean stopEditing() { return getSubWidget().stopEditing(); }

/** Is this object an instance of the class available for this widget?
 * If so, then setValue() will work.  See SqlType.. */
public boolean isInstance(Object o) {return getSubWidget().isInstance(o); }

/** Set up widget to edit a specific SqlType.  Note that this widget does not
 have to be able to edit ALL SqlTypes... it can throw a ClassCastException
 if asked to edit a SqlType it doesn't like. */
public void setJType(citibob.swing.typed.Swinger f) throws ClassCastException
{ getSubWidget().setJType(f); }

/** Row (if any) in a RowModel we will bind this to at runtime. */
public String getColName() { return getSubWidget().getColName(); }

/** Row (if any) in a RowModel we will bind this to at runtime. */
public void setColName(String col) { getSubWidget().setColName(col); }

// ===========================================================
/** Pass along change in value from underlying typed widget. */
public void propertyChange(java.beans.PropertyChangeEvent evt) {
	firePropertyChange("value", evt.getOldValue(), evt.getNewValue());
}	


}
