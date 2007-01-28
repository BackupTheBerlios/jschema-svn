/*
 * TypedHashMap.java
 *
 * Created on October 8, 2006, 10:38 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.wizard;

import java.util.*;

/**
 *
 * @author citibob
 */
public class TypedHashMap extends HashMap
{

public int getInt(Object key) { return ((Integer)get(key)).intValue(); }
public String getString(Object key) { return ((String)get(key)); }
public boolean getBool(Object key) { return ((Boolean)get(key)).booleanValue(); }
/** Sees if there's a key associated with a null value... */
public boolean containsNull()
{
	for (Iterator ii=entrySet().iterator(); ii.hasNext(); ) {
		Map.Entry e = (Map.Entry)ii.next();
		if (e.getValue() == null) return true;
	}
	return false;
}

}
