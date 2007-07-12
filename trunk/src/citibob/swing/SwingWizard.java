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

protected String vsql(Object val, String col, citibob.jschema.Schema schema)
{
	return schema.getCol(col).getType().toSql(val);
}
protected String vsql(String col, citibob.jschema.Schema schema)
{
	return vsql(v.get(col), col, schema);
}

}
