/*
 * RowModelTableModel.java
 *
 * Created on December 29, 2007, 12:28 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.swing.table;

import citibob.swing.RowModel;
import citibob.types.JType;
import citibob.types.JavaJType;

/**
 *
 * @author citibob
 */
public class RowModelTableModel extends AbstractJTypeTableModel
{

RowModel rm;
String[] colNames;
int[] colMap;
boolean editable = true;

/** Creates a new instance of RowModelTableModel */
public RowModelTableModel(RowModel rm, String[] colNames, int[] colMap)
	{ init(rm, colNames, colMap); }

void init(RowModel rm, String[] colNames, int[] colMap)
{
	this.rm = rm;
	this.colNames = colNames;
	this.colMap = colMap;
}

public RowModelTableModel(RowModel rm, String[] colNames, String[] sColMap)
{
	int[] colMap;
	if (sColMap == null) {
		colMap = new int[rm.getColCount()];
		for (int i=0; i<colMap.length; ++i) colMap[i] = i;
	} else {
		colMap = new int[sColMap.length];
		for (int i = 0; i < colMap.length; ++i) {
				colMap[i] = (sColMap[i] == null ? -1 : rm.findColumn(sColMap[i]));
		}
	}
	
	init(rm, colNames, colMap);
}



 public boolean isCellEditable(int row, int col)
{
	if (col == 0) return false;
	return editable;
}
 public JType getJType(int row, int col)
{
	if (col == 0) return JavaJType.jtString;
	return rm.getJType(colMap[row]);
}
 public Class getColumnClass(int row, int col)
{
	if (col == 0) return String.class;
	return rm.getColumnClass(colMap[row]);
}
 public void setValueAt(Object val, int row, int col)
{
	if (col != 1) return;
	rm.set(colMap[row], val);
}
 public Object getValueAt(int row, int col)
{
	switch(col) {
		case 0 : return colNames[row];
		case 1 : return rm.get(colMap[row]);
	}
	return null;
}
 public int getColumnCount()
	{ return 2; }
 public int getRowCount()
	{ return colMap.length; }
}
