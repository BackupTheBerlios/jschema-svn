/*
JSchema: library for GUI-based database applications
This file Copyright (c) 2006-2007 by Robert Fischer

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
import javax.swing.table.*;

/**
 *
 * @author citibob
 */
public class JTablePrefSetter implements SwingPrefSetter {
/** Use prefix.xxx as name for our preferences. */
public void setPrefs(Component comp, final String prefix, final Preferences prefs)
{
	final JTable table = (JTable)comp;

	TableColumnModel cols = table.getColumnModel();
	for (int i = 0; i < cols.getColumnCount(); ++i) {
		TableColumn c = cols.getColumn(i);
		String propName = prefix + ".column[" + i + "]";
		int w = prefs.getInt(propName + ".width", c.getWidth());
		c.setPreferredWidth(w);
	}

	// Hack: save the preferences whenever mouse enters this component.	
	// There is no easy "column width changed" listener.
	table.addMouseListener(new MouseAdapter() {
	public void mouseExited(MouseEvent e) {
		TableColumnModel cols = table.getColumnModel();
		for (int i = 0; i < cols.getColumnCount(); ++i) {
			TableColumn c = cols.getColumn(i);
			String propName = prefix + ".column[" + i + "]";
			prefs.putInt(propName + ".width", c.getWidth());
		}
	}});
}

}

