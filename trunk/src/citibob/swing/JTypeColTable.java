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

import javax.swing.table.*;
import javax.swing.*;
import javax.swing.event.*;
import java.sql.*;
import citibob.sql.*;
import citibob.jschema.*;
import citibob.swing.table.*;
import citibob.swing.typed.*;
import citibob.swing.typed.JType;

/**
 * A table with one type per column.  Integrated with ColPermuteTable, so it's
 * convenient for editing SchemaBufs.
 * @author citibob
 */
public class JTypeColTable extends ColPermuteTable
{
	
/** @param schemaBuf Underling data buffer to use
 * @param typeCol Name of type column in the schema
 * @param xColNames Columns (other than type and status) from schema to display
 */
public void setModelU(JTypeTableModel schemaBuf,
		String[] colNames, String[] sColMap, boolean[] editable,
		citibob.swing.typed.SwingerMap swingers)
{
	super.setModelU(schemaBuf, colNames, sColMap, editable);
	ColPermuteTableModel model = (ColPermuteTableModel)getModel();
	if (editable != null) model.setEditable(editable);
	
	// Set the RenderEdit for each column, according to that column's SqlType.
	for (int c=0; c<sColMap.length; ++c) {
		int bcol = model.getColMap(c);
		JType sqlType = schemaBuf.getJType(0,bcol);
		if (sqlType == null) continue;
		JTypeSwinger swing = swingers.newSwinger(sqlType);
		if (swing == null) continue;
		setRenderEdit(c, swing.newRenderEdit());
	}
}
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
