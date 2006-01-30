/*****************************************************************************
 * HeartBit									                                 *
 * Copyright (C) 2005 The HeartBit Project 									 *
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

package com.toedter.calendar;

import javax.swing.*;
import javax.swing.event.*;
import java.text.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.text.*;

public class JTimeChooser 
		extends JSpinner {
   JFormattedTextField tf;

   public JTimeChooser(){
   	super();
   	final SpinnerDateModel model = new SpinnerDateModel();
   	this.setModel(model);
    
    JSpinner.DateEditor editor = new
    	JSpinner.DateEditor(this,"HH:mm:ss");
    this.setEditor(editor);

    tf = ((JSpinner.DateEditor)this.getEditor()).getTextField();
    tf.setEditable(true);
    tf.setBackground(Color.white);
    tf.setSelectionColor(Color.blue);
    tf.setSelectedTextColor(Color.white);
    DefaultFormatterFactory factory =
       (DefaultFormatterFactory)tf.getFormatterFactory();
    DateFormatter formatter =
       (DateFormatter)factory.getDefaultFormatter();
    formatter.setAllowsInvalid(true);
    
    //this.setValue(null);
   }

/* (non-Javadoc)
 * @see javax.swing.JSpinner#setValue(java.lang.Object)
 */
   public void setValue(Object value) {
	 //  if ( value == null )
	//	   tf.setText("");
	//   else	
		   super.setValue(value);
   }
  
   
   
} 