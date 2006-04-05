/*
 * SqlTypeMap.java
 *
 * Created on March 15, 2006, 9:22 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.swing.typed;

import citibob.sql.*;
import java.util.*;


/**
 * Maps SqlType objects to various formatters, etc. required by graphical parts
 * of system.  Used to automatically construct GUIs appropriate for a schema.
 * @author citibob
 */
public class SwingerMap
{

//HashMap constMap = new HashMap();
HashMap makerMap = new HashMap();
	
// ===========================================================
protected static interface Maker
{
	JTypeSwinger newSwinger(JType sqlType);
}
// ===========================================================
//protected void addConst(SqlSwinger swing)
//{
//	constMap.put(swing.getSqlType().getClass(), swing);
//}
protected void addMaker(Class klass, Maker maker)
{
	makerMap.put(klass, maker);
}
public JTypeSwinger newSwinger(JType t)
{
	// Index on general class of the JType, or on its underlying
	// Java Class (for JavaJType)
	Class klass = t.getClass();
	if (klass == JavaJType.class) klass = ((JavaJType) t).klass;

	Maker m = (Maker)makerMap.get(klass);
	if (m != null) return m.newSwinger(t);
	
	return null;
}


}
