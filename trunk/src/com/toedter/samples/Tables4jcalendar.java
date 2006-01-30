/*****************************************************************************
 * HeartBit									                               *
 * Copyright (C) 2005 Philippe Barthelemy 									 *
 *                                                                           *
 * This program is free software; you can redistribute it and/or modify      *
 * it under the terms of the GNU General Public License as published by      *
 * the Free Software Foundation; either version 2 of the License, or         *
 * (at your option) any later version.                                       *
 *                                                                           *
 * This program is distributed in the hope that it will be useful,           *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of            *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the             *
 * GNU General Public License for more details.                              *
 *                                                                           *
 * You should have received a copy of the GNU General Public License         *
 * along with this program; if not, write to the Free Software               *
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA *
 *                                                                           *
 *****************************************************************************/


package com.toedter.samples;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Date;

import javax.swing.*;

import com.toedter.calendar.*;
import com.toedter.samples.TableModel4jcalendar;

public class Tables4jcalendar extends JFrame {

	private static final long serialVersionUID = 3579987277267258062L;

	private javax.swing.JPanel jContentPane = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private JLabel jLabelDateNull = null;
	private JDateChooser jDateChooserNullDate = null;

	public Tables4jcalendar() {
		super();
		initialize();
	}

	private void initialize() {
		this.setSize(663, 198);
		this.setContentPane(getJContentPane());
		this.setTitle("JCalendar-hb1");
		this.pack();
	}

	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new GridBagLayout());
			
			GridBagConstraints c = new GridBagConstraints();
			
			c.weightx = 0.5;
			c.gridx = 0;
			c.gridy = 0;
		
			jContentPane.add(getJLabelDateNull(), c);
			
			c.gridx = 1;
			c.gridy = 0;
			jContentPane.add(getJDateChooserNullDate(), c);

			c.weightx = 1.0;
			c.gridwidth = 2;
			c.gridx = 0;
			c.gridy = 1;
			c.anchor = GridBagConstraints.PAGE_END;
			c.gridwidth = GridBagConstraints.REMAINDER;
			jContentPane.add(getJScrollPane(), c);
		}
		return jContentPane;
	}
	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

  
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable(new TableModel4jcalendar());
			
			jTable.setDefaultRenderer(Date.class, new JDateChooserCellRenderer(new JDateChooser()));
			jTable.setDefaultEditor(Date.class, new JDateChooserCellEditor(new JDateChooser()));
				
			jTable.setShowGrid(true);
		}
		return jTable;
	}

	
	
	public JDateChooser getJDateChooserNullDate() {
		if (jDateChooserNullDate == null) {
			jDateChooserNullDate = new JDateChooser(true);
			// Is that the solution to the small null date components ?
			//jDateChooserNullDate.setMinimumSize();
		}
		return jDateChooserNullDate;
	}

	public JLabel getJLabelDateNull() {
		if ( jLabelDateNull == null ) {
			jLabelDateNull = new JLabel();
			jLabelDateNull.setText("a null date example");
			jLabelDateNull.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return jLabelDateNull;
	}

	public static void main(String[] args) {
   	 try {
   	      // UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
   	} catch (Exception e) {}
  		Tables4jcalendar application = new Tables4jcalendar();
		application.show();
	}
  } 