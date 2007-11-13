/*
 * TypedWidgetRenderEdit.java
 *
 * Created on November 9, 2007, 1:01 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.swingers;

import citibob.swing.typed.*;
import citibob.swing.typed.Swinger;
import citibob.types.JType;
import java.text.*;
import javax.swing.text.*;
import javax.swing.*;
import java.util.*;
import javax.swing.table.*;
import citibob.text.*;

/**
 *
 * @author citibob
 */
public class DefaultRenderEdit implements Swinger.RenderEdit
{

	protected TableCellEditor editor;
	protected TableCellRenderer rendererEditable;
	protected TableCellRenderer rendererNotEditable;
	protected DefaultRenderEdit() {}

	public DefaultRenderEdit(
		TableCellRenderer rendererNotEditable,
		TableCellRenderer rendererEditable,
		TableCellEditor editor)
	{
		this.rendererNotEditable = rendererNotEditable;
		this.rendererEditable = rendererEditable;
		this.editor = editor;
	}
	
	public TableCellEditor getEditor()
		{return editor; }
	public TableCellRenderer getRenderer(boolean editable)
	{
		return editable ? rendererEditable : rendererNotEditable;
	}
}
