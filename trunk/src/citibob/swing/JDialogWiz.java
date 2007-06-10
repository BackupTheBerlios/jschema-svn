/*
 * JDialogWiz.java
 *
 * Created on January 27, 2007, 6:40 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.swing;

import citibob.wizard.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author citibob
 */
public abstract class JDialogWiz extends JDialog implements SwingWiz
{
	
public JDialogWiz(Frame owner, String title, boolean modal)
{
	super(owner, title, modal);
}

/** Should this Wiz screen be cached when "Back" is pressed? */
public boolean getCacheWiz() { return true; }

}
