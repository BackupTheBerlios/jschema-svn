/*
 * TextFormatter.java
 *
 * Created on March 18, 2006, 7:21 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.swing.typed;

/**
 *
 * @author citibob
 */
public abstract class TextFormatter
extends javax.swing.JFormattedTextField.AbstractFormatter implements Cloneable
{
	
public Object clone() throws CloneNotSupportedException
{
	return super.clone();
}
	/** Creates a new instance of TextFormatter */
	public TextFormatter() {
	}
	
}
