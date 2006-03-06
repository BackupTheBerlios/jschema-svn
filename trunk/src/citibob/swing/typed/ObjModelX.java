/*
 * ObjModel.java
 *
 * Created on March 4, 2006, 1:42 PM
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
public class ObjModelX extends ObjModelXMVC
{
	
Object val;

public Object getValue() { return val; }
public void setValue(Object o) {
	val = o;
	fireValueChanged();
}
	
}
