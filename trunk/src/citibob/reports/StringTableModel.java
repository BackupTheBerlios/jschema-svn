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
 * CSVReportOutput.java
 *
 * Created on February 14, 2007, 11:35 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.reports;

import bsh.This;
import citibob.swing.table.*;
import java.sql.*;
import citibob.text.*;
import citibob.sql.*;
import java.io.*;
import citibob.types.JType;
import com.Ostermiller.util.*;
import javax.swing.table.*;

/**
 * Wraps a TableModel, converting everything to String.
 WARNING: Does not bother to pass up events from the TableModel it wraps.
 * @author citibob
 */
public class StringTableModel extends AbstractTableModel {

SFormat[] formatters;		// Formatter for each column
JTypeTableModel mod;

public StringTableModel(JTypeTableModel mod, SFormat[] formatters)
{
	this.mod = mod;
	this.formatters = formatters;
}


/** @param colNames Name of each column in finished report --- Null if use underlying column names
 @param sColMap Name of each column in underlying uModel  --- Null if wish to use all underlying columns */
public StringTableModel(JTypeTableModel mod,
SFormatMap sfmap)
{
	this(mod, sfmap.newSFormats(mod));
}
public StringTableModel(JTypeTableModel mod,
SFormatMap sfmap, String[] scol, SFormat[] sfmt)
{
	this(mod, sfmap.newSFormats(mod, scol, sfmt));
}
/** Used to set a special (non-default) formatter for a particular column. */
public void setSFormat(String uname, SFormat fmt)
{
	int col = mod.findColumn(uname);
	formatters[col] = fmt;
}

/** Convenience function. */
public void setSFormat(String uname, java.text.Format fmt)
	{ setSFormat(uname, new FormatSFormat(fmt)); }
// -----------------------------------------------------------------------

public int getRowCount() { return mod.getRowCount(); }
public int getColumnCount() { return mod.getColumnCount(); }
public String 	getColumnName(int column) { return mod.getColumnName(column); }
public Object getValueAt(int row, int col) {
	try {
		return formatters[col].valueToString(mod.getValueAt(row,col));
	} catch(Exception e) {
		return e.toString();
	}
}
public Class 	getColumnClass(int columnIndex) { return String.class; }
}
