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
package citibob.swing.calendar;

import javax.swing.*;
import java.net.URL;
import java.awt.*;
import java.util.*;

/**
 *
 * @author  citibob
 */
public class JCalendarDateHHMM extends JCalendar {
	
	CalModel model;
	
	/** Creates new form JCalendar */
	public JCalendarDateHHMM() {
		initComponents();
		URL iconURL = getClass().getResource("images/window-close.png");
		Icon icon = new ImageIcon(iconURL);
		bClose.setIcon(icon);
		bClose.setText(null);
		bClose1.setIcon(icon);
		bClose1.setText(null);
		jHour.initRuntime(Calendar.HOUR_OF_DAY, "00");
		jMinute.initRuntime(Calendar.MINUTE, "00");
	}

	public void setModel(CalModel m)
	{
		model = m;
		jMonth.setModel(m);
		jYear.setModel(m);
		jDay.setModel(m);
		jDayTF.setModel(m);
		jNull.setCalModel(m);
		jHour.setModel(m);
		jMinute.setModel(m);
	}
	
	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jMonth = new citibob.swing.calendar.JMonthChooser();
        jDayTF = new citibob.swing.calendar.JDayTFChooser();
        jYear = new citibob.swing.calendar.JYearChooser();
        bClose = new javax.swing.JButton();
        jNull = new citibob.swing.calendar.JNullChooser();
        jDay = new citibob.swing.calendar.JDayChooser();
        jPanel2 = new javax.swing.JPanel();
        jHour = new citibob.swing.calendar.JDateFieldChooser();
        jLabel1 = new javax.swing.JLabel();
        jMinute = new citibob.swing.calendar.JDateFieldChooser();
        bClose1 = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.GridBagLayout());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel1.add(jMonth, gridBagConstraints);

        jDayTF.setMinimumSize(new java.awt.Dimension(20, 19));
        jDayTF.setPreferredSize(new java.awt.Dimension(40, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        jPanel1.add(jDayTF, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        jPanel1.add(jYear, gridBagConstraints);

        bClose.setText("x");
        bClose.setFocusable(false);
        bClose.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bCloseActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel1.add(bClose, gridBagConstraints);

        jNull.setText(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        jPanel1.add(jNull, gridBagConstraints);

        add(jPanel1, java.awt.BorderLayout.NORTH);

        add(jDay, java.awt.BorderLayout.CENTER);

        jHour.setPreferredSize(new java.awt.Dimension(40, 19));
        jPanel2.add(jHour);

        jLabel1.setText(":");
        jPanel2.add(jLabel1);

        jMinute.setPreferredSize(new java.awt.Dimension(40, 19));
        jPanel2.add(jMinute);

        bClose1.setText("x");
        bClose1.setFocusable(false);
        bClose1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bClose1ActionPerformed(evt);
            }
        });

        jPanel2.add(bClose1);

        add(jPanel2, java.awt.BorderLayout.SOUTH);

    }// </editor-fold>//GEN-END:initComponents

	private void bClose1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bClose1ActionPerformed
	{//GEN-HEADEREND:event_bClose1ActionPerformed
		bCloseActionPerformed(evt);
// TODO add your handling code here:
	}//GEN-LAST:event_bClose1ActionPerformed

	private void bCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCloseActionPerformed
		model.fireDayButtonSelected();
// TODO add your handling code here:
	}//GEN-LAST:event_bCloseActionPerformed
	
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bClose;
    private javax.swing.JButton bClose1;
    private citibob.swing.calendar.JDayChooser jDay;
    private citibob.swing.calendar.JDayTFChooser jDayTF;
    private citibob.swing.calendar.JDateFieldChooser jHour;
    private javax.swing.JLabel jLabel1;
    private citibob.swing.calendar.JDateFieldChooser jMinute;
    private citibob.swing.calendar.JMonthChooser jMonth;
    private citibob.swing.calendar.JNullChooser jNull;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private citibob.swing.calendar.JYearChooser jYear;
    // End of variables declaration//GEN-END:variables

	
	public static void main(String[] args)
	throws Exception
	{
		JFrame f = new JFrame();
		
		CalModel cm = new CalModel(Calendar.getInstance(), true);
		
		JCalendar jc = new JCalendarDateHHMM();
		jc.setModel(cm);
		f.getContentPane().add(jc);
		
		f.setSize(400,400);
		f.pack();
		f.show();
	}
}