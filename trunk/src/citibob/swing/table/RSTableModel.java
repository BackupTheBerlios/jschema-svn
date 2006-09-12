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
package citibob.swing.table;

import java.sql.*;
import javax.swing.table.*;
import javax.swing.event.*;
import citibob.swing.typed.*;
import java.util.*;

/** Reads in a record set and makes the data available as a (read-only) table model. */
public class RSTableModel
extends DefaultTableModel
implements JTypeTableModel
{

List proto;		// Prototypes for CitibobTableModel
JType[] jTypes;		// JType of each column

// =====================================================
// DefaultTableModel handles data storage

// ===============================================================
// Implementation of SqlGen: Read rows from the database


/** Appends a row in the data */
public void addRow(ResultSet rs) throws java.sql.SQLException
{
	ResultSetMetaData meta = rs.getMetaData();
	int ncol = meta.getColumnCount();
	Object[] data = new Object[ncol];
	for (int i = 0; i < ncol; ++i) data[i] = rs.getObject(i+1);
	addRow(data);
}
// --------------------------------------------------
/** Add data from a result set */
public void addAllRows(ResultSet rs) throws java.sql.SQLException
{
	// Set number of columns
	ResultSetMetaData meta = rs.getMetaData();
	int ncol = meta.getColumnCount();
	setColumnCount(ncol);

	// Set column headers
	Object[] ids = new Object[ncol];
	for (int i = 0; i < ncol; ++i) {
		ids[i] = meta.getColumnLabel(i+1);
System.out.println("addAllRows: ids = " + ids[i]);
	}
	setColumnIdentifiers(ids);
	
	// Set data
	while (rs.next()) addRow(rs);
}
// ===============================================================
public RSTableModel()
{
	super();
}
public RSTableModel(Statement st, String sql, JType[] jTypes) throws SQLException
{
	super();
	this.jTypes = jTypes;
	ResultSet rs = null;
	try {
		rs = st.executeQuery(sql);
		addAllRows(rs);
	} finally {
		try { rs.close(); } catch(Exception e) {}
	}
}
// ===============================================================
// Implementation of TableModel

// --------------------------------------------------
public int findColumn(String colName)
{
	for (int i = 0; i < getColumnCount(); ++i) {
		if (colName.equals(getColumnName(i))) return i;
	}
	return -1;
}
// --------------------------------------------------
/** Allow editing of all non-key fields. */
public boolean isCellEditable(int rowIndex, int columnIndex)
	{ return false; }
// --------------------------------------------------
/** This is a NOP: Values are immutable once inserted */
public void setValueAt(Object val, int rowIndex, int colIndex)
	{ }
// --------------------------------------------------

// ===============================================================
/** Return SqlType for a cell */
public JType getJType(int row, int col)
	{ return jTypes[col]; }

///** Return SqlType for an entire column --- or null, if this column does not have a single SqlType. */
//public JType getColumnJType(int col)
//	{ return jTypes[col]; }
//
///** Return SqlType for a cell */
//public JType getJType(int row, int col)
//	{ return jTypes[col]; }


//// ===============================================================
//// Implementation of CitibobTableModel (prototype stuff)
//public List getPrototypes()
//	{ return proto; }
//public void setPrototypes(List proto)
//	{ this.proto = proto; }
//public void setPrototypes(Object[] pr)
//{
//	proto = new ArrayList(pr.length);
//	for (int i = 0; i < pr.length; ++i) {
//		proto.add(pr[i]);
//	}
//}

}
