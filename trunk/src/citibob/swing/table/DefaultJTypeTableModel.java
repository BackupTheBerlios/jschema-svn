/*
 * DefaultJTypeTableModel.java
 *
 * Created on October 18, 2007, 9:17 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.swing.table;

import citibob.swing.CitibobJTable;
import citibob.swing.typed.*;

/**
 *
 * @author citibob
 */
public class DefaultJTypeTableModel extends javax.swing.table.DefaultTableModel
implements JTypeTableModel
{

protected citibob.swing.typed.JType[] jTypes;		// JType of each column

// -----------------------------------------------------------
public DefaultJTypeTableModel(Object[][] data, Object[] columnNames, JType[] jTypes)
{
	super(data, columnNames);
	this.jTypes = jTypes;
}
public JType getJType(int row, int col)
{
	return jTypes[col];
}
// -----------------------------------------------------------
// ================= Basic convenience functions implemented
public DefaultJTypeTableModel() { super(); }
public DefaultJTypeTableModel(String[] colNames, int nrow)
	{ super(colNames, nrow); }
public Object getValueAt(int row, String col)
	{ return getValueAt(row, findColumn(col)); }
public void setValueAt(Object val, int row, String col)
	{ setValueAt(val, row, findColumn(col)); }
public JType getJType(int row, String col)
	{ return getJType(row, findColumn(col)); }

// -----------------------------------------------------------
///** Default implementation; can override. */
//public Object getSortValueAt(int row, int col)
//	{ return getValueAt(row, col); }


}