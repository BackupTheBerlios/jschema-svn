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
