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
