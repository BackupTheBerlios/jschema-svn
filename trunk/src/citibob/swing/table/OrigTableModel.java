/*
 * OrigTableModel.java
 *
 * Created on December 30, 2007, 3:20 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.swing.table;

/**
 *
 * @author citibob
 */
public interface OrigTableModel extends JTypeTableModel
{

/** Give the "original" value... */
public Object getOrigValueAt(int rowIndex, int colIndex);

/** Reset current value to original value */
public void resetValueAt(int rowIndex, int colIndex);

/** Tells us if the value has changed from the original */
public boolean valueChanged(int row, int col);
	
}
