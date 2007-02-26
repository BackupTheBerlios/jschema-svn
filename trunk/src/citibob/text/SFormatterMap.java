package citibob.text;

import citibob.sql.*;
import citibob.swing.typed.*;
import java.util.*;
import java.text.*;


/**
 * Maps SqlType objects to various formatters, etc. for String output.
 * This is a lot like SwingerMap, but much simpler.
 * @author citibob
 */
public class SFormatterMap
{

//HashMap constMap = new HashMap();
HashMap makerMap = new HashMap();
	
// ===========================================================
protected static interface Maker
{
	/** Gets a new swinger for an editable cell. */
	SFormatter newSFormatter(JType jType);
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
//public Swinger newSwinger(JType t)
//{ return newSwinger(t, true); }
/** Gets a new swinger for a cell of a certain type, depending on whether or not it is editable. */

public SFormatter newSFormatter(JType t)
//public Swinger newSwinger(JType t, boolean editable)
{
	// Index on general class of the JType, or on its underlying
	// Java Class (for JavaJType)
	Class klass = t.getClass();
	if (klass == JavaJType.class) klass = ((JavaJType) t).getObjClass();

	Maker m = null;
	for (;;) {
		m = (Maker)makerMap.get(klass);
		if (m != null) break;
		klass = klass.getSuperclass();
		if (klass == Object.class) break;
	}
	if (m != null) return m.newSFormatter(t);
	
	return null;
}


}
