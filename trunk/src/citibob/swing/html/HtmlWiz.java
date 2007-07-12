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
 * HtmlWiz.java
 *
 * Created on October 8, 2006, 5:25 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.swing.html;

import citibob.swing.SwingWizard;
import citibob.wizard.*;
import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import citibob.swing.typed.*;
import java.awt.event.*;

/**
 *
 * @author citibob
 */
public class HtmlWiz extends HtmlDialog implements citibob.swing.SwingWiz
{

//protected HtmlPanel html;
protected boolean cacheWiz = true;
protected boolean cacheWizFwd = false;

/** Should this Wiz screen be cached when "Back" is pressed? */
public boolean getCacheWiz() { return cacheWiz; }
public boolean getCacheWizFwd() { return cacheWizFwd; }
	
public HtmlWiz(Frame owner, String title, citibob.swing.typed.SwingerMap swingers, boolean modal)
{
	super(owner, title, swingers, true);
	init();
}
/**
 * Creates a new instance of HtmlWiz 
 */
public HtmlWiz(Frame owner, String title, boolean modal)
{
	super(owner, title, true);
	init();
}
private void init()
{
	this.setSize(600, 400);
	this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

	// Standard Wizard buttons
	addSubmitButton("back", "<< Back");
	addSubmitButton("next", ">> Next");
	addSubmitButton("cancel", "Cancel");
	super.setTitle("HtmlWiz");
	

}

///** Presents the Wiz to the user; when it is finished, reports output values into map. */
//public void showWiz(java.util.Map map)
//{
//	setVisible(true);
//	this.getAllValues(map);
//}


	
}
