/*
 * ColPermuteTable.java
 *
 * Created on March 13, 2006, 9:25 PM
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
import citibob.jschema.*;
import citibob.swing.table.*;
import citibob.swing.typed.*;


/**
 *
 * @author citibob
 */
public class ColPermuteTable extends CitibobJTable
{

//TableModel uModel;		// The underlying model

public void setModelU(CitibobTableModel uModel, String[] colNames, String[] sColMap, boolean[] editable)
{
//	this.uModel = uModel;
	
	// Set it up
	ColPermuteTableModel model = new ColPermuteTableModel(
		uModel, colNames, sColMap, editable);
	setModel(model);
}

/** Convenience function, to be used by subclasses:
 * finds the column number based on a column name, not display name. */
public int findColumnU(String s)
{
	return ((ColPermuteTableModel)getModel()).findColumnU(s);
}

public TableModel getModelU()
{
	return ((ColPermuteTableModel)getModel()).getModelU();	
}

/** Sets a render/edit on a colum, by UNDERLYING column name. */
public void setRenderEditU(String underlyingName, RenderEdit re)
{
	setRenderEdit(findColumnU(underlyingName), re);
}

/** Sets a render/edit on a colum, by UNDERLYING column name. */
public void setRenderU(String underlyingName, javax.swing.table.TableCellRenderer re)
{
	setRender(findColumnU(underlyingName), re);
}

public void setRenderEditU(String underlyingName, Swinger swinger)
{
	int col = findColumnU(underlyingName);
	setRenderEdit(col, swinger.newRenderEdit(isCellEditable(0, col)));
}
///** Sets a render/edit on a colum, by UNDERLYING column name,
// * according to the columns declared class getColumnClass(). */
//public void setRenderEditU(String underlyingName, RenderEditSet res)
//{
//	int col = findColumnU(underlyingName);
//	RenderEdit re = res.getRenderEdit(getModel().getColumnClass(col));
//	setRenderEdit(col, re);
//}

}
