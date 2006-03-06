/*
 * SimpleObjmodel.java
 *
 * Created on March 4, 2006, 3:32 PM
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
public class DefaultObjModel implements ObjModel
{
	Object obj;
	
	public void setValue(Object o) {obj = o; }
	public Object getValue() {return obj; }
}
