/****************************************************************************
* HeartBit									                                *
* Copyright (C) 2005 The HeartBit Project  									*
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

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.*;
import javax.swing.*;
import java.util.*;

import com.toedter.calendar.JDateChooser;


public class JDateChooserCellEditor 
		extends AbstractCellEditor 
		implements TableCellEditor {
	

	private static final long serialVersionUID = 2500889390699008056L;

	public JDateChooserCellEditor(JDateChooser component)
	{
		this.component = component;
	}
	
	// This is the component that will handle the editing of the cell value
    JComponent component; // = new JDateChooser();

    // This method is called when a cell value is edited by the user.
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int rowIndex, int vColIndex) {
        
    	// 'value' is value contained in the cell located at (rowIndex, vColIndex)
    	Date date ;
		if (value instanceof Date) 
			date = (Date) value; // nb: date can be null
		else 
			date = null;
		
		if( date != null )
			((JDateChooser)component).setDate((Date)date);
		else 
			((JDateChooser)component).startEmpty = true;
		
        // Return the configured component
        return component;
    }
    
    // This method is called when editing is completed.
    // It must return the new value to be stored in the cell.
    public Object getCellEditorValue() {
        return ((JDateChooser)component).getDate();
    }
}