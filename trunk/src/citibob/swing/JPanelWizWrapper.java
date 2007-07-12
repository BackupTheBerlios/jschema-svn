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
 * BaseJDialogWiz.java
 *
 * Created on January 27, 2007, 9:17 PM
 */

package citibob.swing;

import javax.swing.*;
import citibob.wizard.*;
import java.awt.event.*;

/**
 * Wraps a content-ful JPanel (that is a Wiz) to create a Wiz.
 * @author  citibob
 */
public class JPanelWizWrapper extends JDialogWiz
{
protected String submit;
JPanelWiz wiz;

/** @params wiz must be a JComponent (JPanel) as well as a Wiz. */
public JPanelWizWrapper(java.awt.Frame owner, JPanelWiz xwiz)
{
	super(owner, xwiz.getTitle(), true);
	initComponents();
	this.wiz = xwiz;
	wiz.setWrapper(this);
	getContentPane().add(xwiz, java.awt.BorderLayout.CENTER);
	pack();
	
	
    // Add a listener for the close event
    addWindowListener(new java.awt.event.WindowAdapter() {
	public void windowClosing(WindowEvent evt) {
		submit = "cancel";
		wiz.cancelPressed();
		setVisible(false);
    }});
}
/** @param sBack (a) null if no back button, (b) "" if default back button,
 (c) "Name" for back button with text of "Name" */
public JPanelWizWrapper(java.awt.Frame owner, String sBack, String snext, JPanelWiz wiz)
{
	this(owner, wiz);
	
	if (sBack == null) bBack.setEnabled(false);
	else if (!"".equals(sBack)) bBack.setText(sBack);
	
	if (snext == null) bNext.setEnabled(false);
	else if (!"".equals(snext)) bNext.setText(snext);
}
public void getAllValues(java.util.Map map)
{
	map.put("submit", submit);
	wiz.getAllValues(map);
}
	
public void setTitle(String title) { super.setTitle(title); }

public void doSubmit(String submit)
{
	this.submit = submit;
	setVisible(false);
}

/** Should this Wiz screen be cached when "Back" is pressed? */
public boolean getCacheWiz() { return wiz.getCacheWiz(); }
public boolean getCacheWizFwd() { return wiz.getCacheWizFwd(); }

	
	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        jPanel1 = new javax.swing.JPanel();
        bBack = new javax.swing.JButton();
        bNext = new javax.swing.JButton();
        bCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        bBack.setText("<< Back");
        bBack.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bBackActionPerformed(evt);
            }
        });

        jPanel1.add(bBack);

        bNext.setText(">> Next");
        bNext.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bNextActionPerformed(evt);
            }
        });

        jPanel1.add(bNext);

        bCancel.setText("Cancel");
        bCancel.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bCancelActionPerformed(evt);
            }
        });

        jPanel1.add(bCancel);

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void bCancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bCancelActionPerformed
	{//GEN-HEADEREND:event_bCancelActionPerformed
		submit = "cancel";
		wiz.cancelPressed();
		setVisible(false);
// TODO add your handling code here:
	}//GEN-LAST:event_bCancelActionPerformed

	private void bNextActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bNextActionPerformed
	{//GEN-HEADEREND:event_bNextActionPerformed
		submit = "next";
		wiz.nextPressed();
		setVisible(false);
// TODO add your handling code here:
	}//GEN-LAST:event_bNextActionPerformed

	private void bBackActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bBackActionPerformed
	{//GEN-HEADEREND:event_bBackActionPerformed
// TODO add your handling code here:
		submit = "back";
		wiz.backPressed();
		setVisible(false);
	}//GEN-LAST:event_bBackActionPerformed
	
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bBack;
    private javax.swing.JButton bCancel;
    private javax.swing.JButton bNext;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
	
}
