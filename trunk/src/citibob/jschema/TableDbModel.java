/*
 * TableDbModel.java
 *
 * Created on February 12, 2007, 10:29 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.jschema;

/**
 * A DbModel that has an associated buffer with it.
 * @author citibob
 */
public interface TableDbModel extends DbModel
{
	/** Return the JTypeTableModel associated with this DbModel */
	public citibob.swing.table.JTypeTableModel getTableModel();
}
