/*
 * JNullChooser.java
 *
 * Created on March 19, 2006, 6:22 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.swing.calendar;

import java.util.*;
import java.text.*;
import javax.swing.*;
import java.awt.event.*;

/**
 *
 * @author citibob
 */
public class JNullChooser extends JCheckBox
implements CalModel.Listener, ItemListener
{

/** Creates a new instance of JNullChooser */
public JNullChooser() {
	this.addItemListener(this);
	this.setText("null");
}

/** Make it work correctly inside a dropdown and a JTable. */
public boolean isFocusTraversable() { return false; }
// =====================================================
// Standard for all the JxxxChooser calendar sub-components
CalModel model;
public void setCalModel(CalModel m) {
	if (model != null) model.removeListener(this);
	model = m;
	model.addListener(this);
	nullChanged();
	setEnabled(model.isNullable());
}
public CalModel getCalModel() { return model; }
// =====================================================
// ===================================================================
// CalModel.Listener
/**  Value has changed. */
public void nullChanged()
{
	boolean nll = model.isNull();
	if (nll != this.isSelected()) this.setSelected(nll);
}
public void calChanged() {}
/**  The "final" value has been changed. */
public void dayButtonSelected() {}

// ===================================================================
public void itemStateChanged(ItemEvent e) {
	boolean nll = this.isSelected();
	if (nll != model.isNull()) model.setNull(nll);
	if (nll) model.fireDayButtonSelected();
}


}
