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
public class TypedWidgetRenderEdit implements Swinger.RenderEdit
{

	TableCellEditor editor;
	TableCellRenderer rendererEditable;
	TableCellRenderer rendererNotEditable;
	public TypedWidgetRenderEdit(TypedWidget tw)
	{
		rendererEditable = new TypedWidgetRenderer(tw);
		rendererNotEditable = rendererEditable;
		editor = new TypedWidgetEditor(tw);
	}
	public TypedWidgetRenderEdit(TypedWidget tw, SFormat sformat, boolean renderWithWidget)
	{
		rendererEditable = new TypedWidgetRenderer(tw);
		rendererNotEditable = (renderWithWidget ? rendererEditable :
			new SFormatRenderer(sformat));
		editor = new TypedWidgetEditor(tw);
	}
	public TableCellEditor getEditor()
		{return editor; }
	public TableCellRenderer getRenderer(boolean editable)
	{
		return editable ? rendererEditable : rendererNotEditable;
	}
}
