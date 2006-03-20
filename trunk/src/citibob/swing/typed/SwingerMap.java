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
	SqlSwinger newSwinger(SqlType sqlType);
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
public SqlSwinger getSwinger(SqlType t)
{
//	SqlSwinger swing;
	Class klass = t.getClass();
//	swing = (SqlSwinger)constMap.get(klass);
//	if (swing != null) return swing;
	
	Maker m = (Maker)makerMap.get(klass);
	if (m != null) return m.newSwinger(t);
	
	return null;
}


}
