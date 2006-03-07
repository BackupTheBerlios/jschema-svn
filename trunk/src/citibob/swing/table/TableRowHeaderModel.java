/** JTable Row Headers
 * Taken from: http://www.jguru.com/faq/view.jsp?EID=87579
 * By: Maksim Kovalenko
 * Oct 23, 2001
 */

package citibob.swing.table;

import javax.swing.*;

/* * Class JavaDoc */
public class TableRowHeaderModel extends AbstractListModel {
	private JTable table;
	public TableRowHeaderModel(JTable table) {
		this.table = table;
	}

	public int getSize() {
		return table.getRowCount();
	}

	public Object getElementAt(int index) {
		return null;
	}
}
