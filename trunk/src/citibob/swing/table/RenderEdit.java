/*
 * RenderEdit.java
 *
 * Created on January 23, 2006, 2:06 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.swing.table;

import javax.swing.table.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * A cell renderer and editor that go together for a table column.
 * This is a convenience class.
 * @author citibob
 */
public abstract class RenderEdit {

protected TableCellRenderer renderer;
protected TableCellEditor editor;

public TableCellRenderer getRenderer() { return renderer; }
public TableCellEditor getEditor() { return editor; }
	
}
