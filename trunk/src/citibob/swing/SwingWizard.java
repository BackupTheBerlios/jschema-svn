/*
 * NewRecordWizard.java
 *
 * Created on October 8, 2006, 10:41 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.swing;

import citibob.swing.html.*;
import citibob.wizard.*;
import javax.swing.*;
import java.util.*;
import java.awt.Component;

/**
 * Assumes Wiz components of class SwingWiz (and JDialog)
 * @author citibob
 */
public class SwingWizard extends Wizard {


protected java.awt.Frame frame;



/*   --------- Sample Wizard creation code
new State("", "", "") {
	public HtmlWiz newWiz()
		{ return new }
	public void process()
	{
		
	}
}
*/

/** @param frame Parent frame for the modal dialogs created by the Wizard. */
public SwingWizard(String wizardName, java.awt.Frame frame, String startState)
{
	super(wizardName, startState);
//	this.wizardName = wizardName;
	this.frame = frame;
//	this.startState = startState;
//	states = new HashMap();
}

protected boolean checkFieldsFilledIn()
{
	if (super.checkFieldsFilledIn()) return true;
	
	JOptionPane.showMessageDialog((JDialog)wiz,
		"You must fill in all the fields.");
	return false;
}


protected void runWiz(Wiz wiz) throws Exception
{
	java.awt.Component pane = (Component)wiz;
	
	wiz.setTitle(wizardName);
	pane.setVisible(true);
}

protected boolean reallyCancel() throws Exception
{
	int ret = JOptionPane.showConfirmDialog(frame,
		"Are you sure you wish to cancel the Wizard?",
		"Really Cancel?", JOptionPane.YES_NO_OPTION);
	return (ret == JOptionPane.YES_OPTION);
}


	
}
