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
 * JTrePrefSetter.java
 *
 * Created on July 17, 2005, 8:41 PM
 */

package citibob.swing.prefs;

import java.awt.*;
import java.util.prefs.*;
import javax.swing.*;
import java.awt.event.*;

/**
 *
 * @author citibob
 */
public class JFramePrefSetter implements SwingPrefSetter {
/** Use prefix.xxx as name for our preferences. */
public void setPrefs(Component c, final String prefix, final Preferences prefs)
{
	final JFrame cc = (JFrame)c;

	// Set our own parameters from the preferences
	Dimension sz = cc.getSize();
	sz.width = prefs.getInt(prefix + ".size.width", sz.width);
	sz.height = prefs.getInt(prefix + ".size.height", sz.height);
	cc.setSize(sz);
System.out.println("JFrame: got size = " + sz);
    
    // Set up listener(s) to save preferences as our geometry changes.
	cc.addComponentListener(new ComponentAdapter() {
	public void componentResized(ComponentEvent e) {
		Dimension sz = cc.getSize();
		prefs.putInt(prefix + ".size.width", sz.width);
		prefs.putInt(prefix + ".size.height", sz.height);
System.out.println("Setting size: " + prefix + " = " + sz);
	}});
}

}

