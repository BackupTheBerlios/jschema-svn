/*
JSchema: library for GUI-based database applications
This file Copyright (c) 2006 by Robert Fischer

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/
/*
 * SwingPrefs.java
 *
 * Created on July 17, 2005, 8:35 PM
 */

package citibob.swing.prefs;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import citibob.exception.*;
import java.util.prefs.*;
import citibob.swing.prefs.*;
import java.util.*;

/**
 *
 * @author citibob
 */
public class SwingPrefs implements SwingPrefSetter {
	
HashMap settersType;		// type --> SwingPrefSetter
//HashMap objs;		// Individual objects --> SwingPrefSetter; however, we might need to use a HashMap that compares actual pointers.
HashMap nullCount;	// component type --> # we've seen; to generate names	

public SwingPrefs()
{
	settersType = new HashMap();
	settersType.put(JFrame.class, new JFramePrefSetter());
	settersType.put(JDialog.class, new JDialogPrefSetter());
	settersType.put(JTable.class, new JTablePrefSetter());
	settersType.put(JSplitPane.class, new JSplitPanePrefSetter());
	settersType.put(JFileChooser.class, new JFileChooserPrefSetter());
}

public void setPrefs(Component c, String prefix, Preferences prefs)
{
	nullCount = new HashMap();
	setPrefsRecurse(c, prefix + ".", prefs);
}

public SwingPrefSetter getSetter(Class c)
{
	while (c != null) {
		SwingPrefSetter setter = (SwingPrefSetter)settersType.get(c);
		if (setter != null) return setter;
		c = c.getSuperclass();
	}
	return null;
}

/** Loads preferences for an entire widget tree.  Also sets listeners
 * so they will be saved as they change. */
private void setPrefsRecurse(Component c, String prefix, Preferences prefs)
{
	SwingPrefSetter setter = getSetter(c.getClass());
	if (setter != null || c instanceof PrefWidget) {
		// Make up name for component
		Class klass = c.getClass();
		String name = c.getName();
		if (name == null || "".equals(name) || "null".equals(name)) {
			Integer Count = (Integer)nullCount.get(klass);
			int nCount;
			if (Count == null) nCount = 1; //Count = new Integer(1);
			else nCount = Count.intValue() + 1;
			nullCount.put(klass, new Integer(nCount));

			// Get class name
			String pkgName = klass.getPackage().getName();
			String className = klass.getName();
			//System.out.println(className.length());
			String leafName = className.substring(pkgName.length()+1);
			name = leafName + nCount;
		}

		prefs = prefs.node(name);
		prefix = "";

		// Take care of yourself
//System.out.println("Setting Pref (node = " + prefs.absolutePath() + ") for " + c);
		if (setter != null) setter.setPrefs(c, prefix, prefs);
		else ((PrefWidget)c).setPrefs(prefix, prefs);
	}

	// Take care of your children
	if (c instanceof Container) {
	    Component[] child = ((Container)c).getComponents();
	    for (int i = 0; i < child.length; ++i) {
			setPrefsRecurse(child[i], prefix, prefs);
		}
	}
}
	
}
