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
 * ColPermuteTable.java
 *
 * Created on March 13, 2006, 9:25 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.swing;

import citibob.swing.typed.Swinger;
import javax.swing.table.*;
import javax.swing.*;
import javax.swing.event.*;
import java.sql.*;
import citibob.jschema.*;
import citibob.swing.table.*;
import citibob.swing.typed.*;
import citibob.text.*;
import citibob.types.*;

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
 * finds the column number in THIS table model based on a column name, not display name. */
public int findColumnU(String s)
{
	return ((ColPermuteTableModel)getModel()).findColumnU(s);
}
//public int getColumnU(int col_u)
//{
//	return ((ColPermuteTableModel)getModel()).getColumnU(col_u);
//}

public CitibobTableModel getModelU()
{
	return ((ColPermuteTableModel)getModel()).getModelU();	
}

/** Sets a render/edit on a colum, by UNDERLYING column name. */
public void setRenderEditU(String underlyingName, Swinger swinger)
	{ setRenderEdit(findColumnU(underlyingName), swinger); }

public void setRenderEditU(String underlyingName, Swinger.RenderEdit re)
	{ setRenderEdit(findColumnU(underlyingName), re); }

public void setRenderEditU(String underlyingName, KeyedModel kmodel)
	{ setRenderEdit(findColumnU(underlyingName), kmodel); }

/** Sets a render/edit on a colum, by UNDERLYING column name. */
public void setRenderEditU(String underlyingName, SFormat sfmt)
	{ setRenderEdit(findColumnU(underlyingName), sfmt); }

public void setRenderEditU(String underlyingName, java.text.Format fmt)
	{ setRenderEdit(findColumnU(underlyingName), fmt); }

public void setRenderEditU(String underlyingName, String fmtString)
	{ setRenderEdit(findColumnU(underlyingName), fmtString); }

public int rowOfValueU(Object val, int col_u)
	{ return rowOfValue(val, col_u, getModelU()); }
public int rowOfValueU(Object val, String underlyingName)
	{ return rowOfValue(val, getModelU().findColumn(underlyingName), getModelU()); }

public void setSelectedRowU(Object val, int col_u)
	{ setSelectedRow(val, col_u, getModelU()); }
public void setSelectedRowU(Object val, String underlyingName)
	{ setSelectedRow(val, getModelU().findColumn(underlyingName), getModelU()); }


/** Sets a renderer on a colum, by UNDERLYING column name.  Only sets
renderer, not editor; only used in special cases. */
public void setRendererU(String underlyingName, TableCellRenderer renderer)
{
	setRenderer(findColumnU(underlyingName), renderer);
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
