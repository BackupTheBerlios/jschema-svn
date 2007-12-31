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
 * JTypeColTable.java
 *
 * Created on March 13, 2006, 9:28 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.swing;

//import bsh.This;
import citibob.swing.typed.Swinger;
import javax.swing.table.*;
import javax.swing.*;
import javax.swing.event.*;
import java.sql.*;
import citibob.sql.*;
import citibob.jschema.*;
import citibob.swing.table.*;
import citibob.swing.typed.*;
import citibob.types.JType;
import java.awt.*;
import citibob.text.*;

/**
 * A table with one type per column.  Integrated with ColPermuteTable, so it's
 * convenient for editing JTypeTableModels.
 * @author citibob
 */
public class JTypeColTable extends ColPermuteTable
{

ColPermuteTableModel ttModel;	// Tooltips for each column
SFormat[] ttFmt;				// Formatter for each tooltip

///** Do tooltips.  This could be pushed up the class hierarchy if we wish. */
//public Component prepareRenderer(TableCellRenderer renderer,
// int rowIndex, int vColIndex) {
//	Component c = super.prepareRenderer(renderer, rowIndex, vColIndex);
//	if (c instanceof JComponent) {
//		JComponent jc = (JComponent)c;
//		String ttip = getTooltip(rowIndex, vColIndex);
////	System.out.println(ttip);
//		jc.setToolTipText(ttip);
////		jc.setToolTipText("<html>This is the first line<br>This is the second line</html>");
//	}
//	return c;
//}


/** Override this to do tooltips in custom manner.  For now, we return the "tooltip column" */
public String getTooltip(int row, int col)
{
	if (ttModel == null) return null;
	try {
		if (ttFmt[col] == null) return null;
		return ttFmt[col].valueToString(ttModel.getValueAt(row, col)); // + "\nHoi";
	} catch(java.text.ParseException e) {
		return "<JTypeColTable: ParseException>\n" + e.getMessage();
	}
}

///** Wraps in a StatusSchemaBuf if needed */
//protected JTypeTableModel wrapModel(JTypeTableModel sb, String[] sColMap)
//{
//	if (!(sb instanceof SchemaBuf)) return sb;
//	if (sColMap == null) return sb;
//	for (String s : sColMap) {
//		if (s.startsWith("__")) {
//			return new StatusSchemaBuf((SchemaBuf)sb);
//		}
//	}
//	return sb;
//}

/** @param jtModel Underling data buffer to use
 * @param typeCol Name of type column in the schema
 * @param xColNames Columns (other than type and status) from schema to display
 */
public JTypeTableModel setModelU(JTypeTableModel jtModel,
		String[] colNames, String[] sColMap, boolean[] editable,
		citibob.swing.typed.SwingerMap smap)
{
//	jtModel = wrapModel(jtModel, sColMap);
	super.setModelU(jtModel, colNames, sColMap, editable);
	ColPermuteTableModel model = (ColPermuteTableModel)getModel();
	if (editable != null) model.setEditable(editable);
	
	// Set the RenderEdit for each column, according to that column's SqlType.
//	for (int c=0; c<sColMap.length; ++c) {
	for (int c=0; c<this.getColumnCount(); ++c) {
		int bcol = model.getColMap(c);
		JType sqlType = jtModel.getJType(0,bcol);
		if (sqlType == null) continue;
		String colName = jtModel.getColumnName(bcol);
		Swinger swing = smap.newSwinger(sqlType, colName);
		if (swing == null) continue;
		setRenderEdit(c, swing);
	}
	
	return jtModel;
}

/** @param jtModel Underling data buffer to use
 * @param typeCol Name of type column in the schema
 * @param xColNames Columns (other than type and status) from schema to display
 * @param ttColMap Column in underlying table to display as tooltip for each column in displayed table.
 */
public void setModelU(JTypeTableModel jtModel,
		String[] colNames, String[] sColMap, String[] ttColMap, boolean[] editable,
		citibob.swing.typed.SwingerMap smap)
{
	jtModel = this.setModelU(jtModel, colNames, sColMap, editable, smap);
	
	// Come up with model for all the tooltips
	ttModel = new ColPermuteTableModel(jtModel, colNames, ttColMap, editable);
	ttFmt = new SFormat[ttModel.getColumnCount()];
	for (int i=0; i<ttModel.getColumnCount(); ++i) {
		int colU = ttModel.getColMap(i);
		if (colU < 0) continue;
		JType jt = jtModel.getJType(0, colU);
		String colName = jtModel.getColumnName(colU);
		if (jt == null) continue;
		ttFmt[i] = smap.newSwinger(jt, colName).getSFormat();
	}
}

///** Convenience function --- allows us to set formatter for common
// data types based soley on a format string. */
//public void setFormat(int col, String fmtString)
//{
//	JTypeTableModel model = (JTypeTableModel)this.getModel();
//	JType jtype = model.getJType(0, col);
//	Class klass = jtype.getClass();
//	Format fmt;
//	TableCellRendererer re = null;
//
//	if (Number.class.isAssignableFrom(klass)) {
//		fmt = new DecimalFormat(fmtString);
//	} else if (Date.class.isAssignableFrom(klass)) {
//		fmt = new SimpleDateFormat(fmtString);
//	}
//	if (fmt != null) setRenderer()
//		re = new citibob.swing.typed.FormatTableCellRenderer(fmt);
//}
///** @pa */
//public void setFormatU(String scol, Format fmt)

/** Returns the value of a column (in the underlying table) of the first selected row. */
public Object getOneSelectedValU(String colU)
{
	int sel = getSelectedRow();
	if (sel < 0) return null;
	
	CitibobTableModel modelU = getModelU();
	int col = modelU.findColumn(colU);
	return modelU.getValueAt(sel, col);	
}
///** Sets the render and editor (and JType) on a column */
//public void setSwinger(int col, Swinger swing)
//{
//	ColPermuteTableModel model = (ColPermuteTableModel)getModel();
//	setRenderEdit(col, swing.newRenderEdit(model.isCellEditable(0, col)));
//}
//	
///** Sets a render/edit on a colum, by UNDERLYING column name,
// * according to the columns declared SqlType: getColumnJType(). */
//public void setRenderEditU(String underlyingName, RenderEditSet res)
//{
//	int col = findColumnU(underlyingName);
//	SchemaBuf model = (SchemaBuf)getModel();
//	RenderEdit re = res.getRenderEdit(model.getColumnJType(col));
//	setRenderEdit(col, re);
//}

}
