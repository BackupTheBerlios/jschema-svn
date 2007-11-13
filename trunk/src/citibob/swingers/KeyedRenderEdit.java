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
import citibob.types.*;

/**
 *
 * @author citibob
 */
public class KeyedRenderEdit extends DefaultRenderEdit
{
	public KeyedRenderEdit(KeyedModel kmodel)
	{
		editor = new TypedWidgetEditor(new JKeyedComboBox(kmodel));
		rendererEditable = rendererNotEditable =
			new SFormatRenderer(new KeyedSFormat(kmodel));
	}
}
