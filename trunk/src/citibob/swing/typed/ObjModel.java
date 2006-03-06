/*
 * ObjModel.java
 *
 * Created on March 4, 2006, 3:31 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.swing.typed;

/**
 * Used to abstract away the storage from the TypedWidgets.
 * @author citibob
 */
public interface ObjModel {
	public void setValue(Object o);
	public Object getValue();
}
