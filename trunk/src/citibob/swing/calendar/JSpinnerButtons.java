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
package citibob.swing.calendar;

import javax.swing.*;

/**
 * These don't auto-repeat (yet).  For that, see:
 * http://www.javaresearch.org/source/jdk142/com/sun/java/swing/plaf/gtk/SynthSpinnerUI.java.html
 * @author  citibob
 */
public class JSpinnerButtons extends javax.swing.JPanel {
	
JButton bUp;
JButton bDown;
	
	/** Creates new form JSpinnerButtons */
	public JSpinnerButtons() {
		initComponents();
		
//		bUp = new BasicArrowButton(SwingConstants.NORTH);
		bUp = new FocusArrowButton(SwingConstants.NORTH);
	    bUp.setRequestFocusEnabled(true);
		bUp.setFocusable(true);
		add(bUp);
		bDown = new FocusArrowButton(SwingConstants.SOUTH);
		add(bDown);
	    bDown.setRequestFocusEnabled(true);
		bDown.setFocusable(true);
		
		java.awt.Dimension sz = bUp.getPreferredSize();
		sz.height = 6;
//		sz.height = sz.height * 2/3;
//		sz.height = 6;
//		bUp.setPreferredSize(sz);
//		bDown.setPreferredSize(sz);
		
//		this.setPreferredSize(new java.awt.Dimension(20,7));
		this.setPreferredSize(sz);
	}

	JButton getUp() { return bUp; }
	JButton getDown() { return bDown; }
	
	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));

    }
    // </editor-fold>//GEN-END:initComponents
	
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
	
}
