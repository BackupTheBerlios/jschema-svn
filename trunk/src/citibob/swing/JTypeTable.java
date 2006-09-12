/*
 * JTypeTable.java
 *
 * Created on April 1, 2006, 10:11 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.swing;

import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import citibob.sql.*;
import citibob.swing.table.*;
import citibob.swing.typed.*;

/**
 * Allows a table with an arbitrary JType in each cell; sets up renderers
 * and editors properly, according to a SwingerMap.  Should be paired with
 * an JTypeTableModel.
 * NOTE: This class caches RenderEdit objects it makes.  They could grow
 * over time, if it keeps being used to edit different JTypes.
 * @author citibob
 */
public class JTypeTable extends CitibobJTable
{
	SwingerMap smap;
	HashMap RenderEditMap;		// HashMap of RenderEdit objects created for table cells.
	
	public void setSwingerMap(SwingerMap smap)
		{ this.smap = smap; }

	/** Creates a new instance of JTypeTable */
	public JTypeTable() {
		RenderEditMap = new HashMap();
	}

	public TableCellEditor getCellEditor(int row, int col)
	{
		RenderEdit re = getRenderEdit(row, col);
//System.out.println("JTypeTable: got RenderEdit for edit: " + re + "(JType = " + ((JTypeTableModel)getModel()).getJType(row, col));
		return (re != null ? re.getEditor() : super.getCellEditor(row, col));
	}
	public TableCellRenderer getCellRenderer(int row, int col)
	{
		RenderEdit re = getRenderEdit(row, col);
		return (re != null ? re.getRenderer() : super.getCellRenderer(row, col));
	}
	public RenderEdit getRenderEdit(int row, int col)
	{
		if (smap == null) return null;
		
		JTypeTableModel mod = (JTypeTableModel)getModel();
		JType jType = mod.getJType(row, col);
		if (jType == null) return null;
		
		// See if we have cached a RenderEdit for this type
		RenderEdit re = (RenderEdit)RenderEditMap.get(jType);
		if (re != null) return re;
		
		// Get the swinger to make us a new RenderEdit
		JTypeSwinger swinger = smap.newSwinger(jType);
		if (swinger == null) return null;
		re = swinger.newRenderEdit();
		RenderEditMap.put(jType, re);
		
		return re;
	}
}
