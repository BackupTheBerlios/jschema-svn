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

import java.util.*;
import java.text.*;
import javax.swing.*;
import java.awt.event.*;

/**
 *
 * @author  citibob
 */
public class JMonthChooser
extends javax.swing.JPanel implements CalModel.Listener
{

/** Creates new form JMonthChooser */
public JMonthChooser() {
	initComponents();

	// Set up the month names --- just use default locale for now.
	Locale locale = Locale.getDefault();
	DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(locale);
	String[] monthNames = dateFormatSymbols.getMonths();
	for (int i = 0; i < 12; i++) monthBox.addItem(monthNames[i]);

	monthBox.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
		int month = monthBox.getSelectedIndex();
		if (month == getMonth()) return;
		setMonth(month);
    }});
//	// TODO: Java 1.4.2 doesn't work with a JComboBox in a JPopupMenu
//	// For now, disable the combo box.
	monthBox.setEnabled(false);
	
	spinner.getUp().addMouseListener(new SpinnerListener(-1));
	spinner.getDown().addMouseListener(new SpinnerListener(1));
}
int getMonth()
	{ return model.getCal().get(Calendar.MONTH); }
void setMonth(int m)
	{ model.set(Calendar.MONTH, m); }
// =====================================================
// Standard for all the JxxxChooser calendar sub-components
CalModel model;
public void setModel(CalModel m) {
	if (model != null) model.removeListener(this);
	model = m;
	model.addListener(this);
	calChanged();
}
public CalModel getModel() { return model; }
public void nullChanged() {
	// TODO: Don't for now, we're permanently disabled...
	// citibob.swing.WidgetTree.setEnabled(this, !model.isNull());
}
// =====================================================

// ===================================================================
class SpinnerListener extends MouseAdapter
{
int incr;
public SpinnerListener(int a) { incr=a; }
public void  mousePressed(MouseEvent e) 
{
		int month = getMonth();
		setMonth(month + incr);
}}
// ===================================================================
// CalModel.Listener
/**  Value has changed. */
public void calChanged()
{
	int month = getMonth();
	monthBox.setSelectedIndex(month);
}


/**  The "final" value has been changed. */
public void dayButtonSelected() {}
// ===================================================================
	
	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        monthBox = new javax.swing.JComboBox();
        spinner = new citibob.swing.calendar.JSpinnerButtons();

        setLayout(new java.awt.BorderLayout());

        add(monthBox, java.awt.BorderLayout.CENTER);

        add(spinner, java.awt.BorderLayout.EAST);

    }
    // </editor-fold>//GEN-END:initComponents
	
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox monthBox;
    private citibob.swing.calendar.JSpinnerButtons spinner;
    // End of variables declaration//GEN-END:variables

	
	public static void main(String[] args)
	{
		JFrame f = new JFrame();
		f.getContentPane().setLayout(new javax.swing.BoxLayout(f.getContentPane(), javax.swing.BoxLayout.Y_AXIS));
		
		CalModel cm = new CalModel();
		
		JMonthChooser jm = new JMonthChooser();
		jm.setModel(cm);
		f.getContentPane().add(jm);
		
		JYearChooser jy = new JYearChooser();
		jy.setModel(cm);
		f.getContentPane().add(jy);

		JDayChooser jd = new JDayChooser();
		jd.setModel(cm);
		f.getContentPane().add(jd);
		
		f.setSize(400,400);
		f.show();
	}
	
}
