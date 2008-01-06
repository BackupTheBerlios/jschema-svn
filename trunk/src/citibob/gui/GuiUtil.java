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
 * GuiUtil.java
 *
 * Created on June 11, 2007, 11:41 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.gui;

import javax.swing.*;
import java.awt.*;
import citibob.app.*;
import citibob.swing.*;

/**
 *
 * @author citibob
 */
public class GuiUtil
{

/** @param dialogName Name to use in Swing Preferences.  If null, take name from jPanel.getName() */
public static void showJPanel(Component c, JPanel panel, App app, String title, String dialogName, boolean modal)
{
	if (dialogName == null) dialogName = panel.getName();
//	JFrame frame = new JFrame();
	JFrame root = (javax.swing.JFrame)WidgetTree.getRoot(c);
	JDialog frame = new JDialog(root, modal);
	frame.setTitle(title);
	frame.setSize(500,300);
	frame.getContentPane().add(panel);
	if (dialogName != null) app.setUserPrefs(frame, dialogName);
	frame.setVisible(true);
}

}
