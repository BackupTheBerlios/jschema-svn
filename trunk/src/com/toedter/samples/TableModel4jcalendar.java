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

import javax.swing.table.AbstractTableModel;
import java.util.Date;
import java.text.DateFormat;

public class TableModel4jcalendar extends AbstractTableModel {

	private static final long serialVersionUID = 6921768144627011508L;
	private String[] columnNames = { 
			"JDateChooser", "as string" 
								};
	private Object[][] data = {
			{ (Date) new Date(), "" },
			{ (Date) new Date(), "" },
			{ (Date) new Date(), "" }
	};

	public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }


	public void setValueAt(Object value, int row, int col) {
		data[row][col] = value;
		
		// update the Date as string display
		if( col == 0) {
			Date newDate;
			newDate = (Date)  value; // value is indeed a java.util.Date 
			data[row][1] =  DateFormat.getDateInstance().format(newDate);
		}
		
	      fireTableCellUpdated(row, col);
	}
    public Class getColumnClass(int c) {
    	// using something like the follwing line is impossible if
    	// 		you want to be able to use null Dates
        // return getValueAt(0, c).getClass();
    	
    	Class clazz;
       	switch(c){
	    	case 0: 
	    		clazz = Date.class;
	    		break;
	    	case 1 :
	    		clazz = String.class;
	    		break;
	    	default : // just in case...
	    		clazz = String.class;
				break;
    	}
    	return clazz;
    }

    public boolean isCellEditable(int row, int col) {
    		// the cells displaying the Date as a string is not editable 
        if (col == 1) {
          return false;
        } else {
          return true;
        }
      }
}