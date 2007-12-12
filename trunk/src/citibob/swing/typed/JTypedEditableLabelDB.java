/*
JSchema: library for GUI-based database applications
This file Copyright (c) 2006-2007 by Robert Fischer

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
/*
 * JAdultLabel.java
 *
 * Created on June 29, 2007, 9:59 PM
 */

package citibob.swing.typed;

import citibob.types.JType;
import java.text.DateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import citibob.exception.*;
import citibob.sql.*;
import java.beans.*;

/**
 *
 * @author  citibob
 */
public class JTypedEditableLabelDB
extends javax.swing.JPanel
implements TextTypedWidget, PropertyChangeListener, ActionListener
{

protected TypedWidget popupWidget;		// The widget we display in the popup to change the value.
String colName;

/** Creates new form JAdultLabel */
public JTypedEditableLabelDB() {
	initComponents();
	ckNull.addActionListener(this);

	popup.add(popupPanel);

}

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        popup = new javax.swing.JPopupMenu();
        popupPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        ckNull = new javax.swing.JCheckBox();
        bClose = new javax.swing.JButton();
        btnChange = new javax.swing.JButton();
        label = new offstage.swing.typed.JTypedLabelDB();

        popupPanel.setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.BorderLayout());

        ckNull.setText(null);
        ckNull.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        ckNull.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel1.add(ckNull, java.awt.BorderLayout.WEST);

        bClose.setText("x");
        bClose.setFocusable(false);
        bClose.setMargin(new java.awt.Insets(0, 2, 0, 2));
        bClose.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bCloseActionPerformed(evt);
            }
        });

        jPanel1.add(bClose, java.awt.BorderLayout.EAST);

        popupPanel.add(jPanel1, java.awt.BorderLayout.NORTH);

        btnChange.setText("v");
        btnChange.setPreferredSize(new java.awt.Dimension(35, 29));
        btnChange.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnChangeActionPerformed(evt);
            }
        });

        setLayout(new java.awt.BorderLayout());

        setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setPreferredSize(new java.awt.Dimension(122, 19));
        label.setText("jTypedLabelDB1");
        label.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
        {
            public void mouseDragged(java.awt.event.MouseEvent evt)
            {
                labelMouseDragged(evt);
            }
        });
        label.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                labelMouseClicked(evt);
            }
        });

        add(label, java.awt.BorderLayout.CENTER);

    }// </editor-fold>//GEN-END:initComponents

	private void labelMouseDragged(java.awt.event.MouseEvent evt)//GEN-FIRST:event_labelMouseDragged
	{//GEN-HEADEREND:event_labelMouseDragged
// TODO add your handling code here:
	}//GEN-LAST:event_labelMouseDragged

	private void labelMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_labelMouseClicked
	{//GEN-HEADEREND:event_labelMouseClicked
	showPopup();
// TODO add your handling code here:
	}//GEN-LAST:event_labelMouseClicked

	private void bCloseActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bCloseActionPerformed
	{//GEN-HEADEREND:event_bCloseActionPerformed
		closePopup();
	}//GEN-LAST:event_bCloseActionPerformed

protected void showPopup()
{
	popup.setPopupSize(getWidth(), 300);
	popup.pack();
	popup.show(this, 0, this.getHeight());	
}
protected void closePopup()
{
	popup.setVisible(false);
}	
protected void togglePopup()
{
	if (!popup.isVisible()) showPopup();
	else closePopup();
}

private void btnChangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangeActionPerformed
	showPopup();
	//togglePopup();
}//GEN-LAST:event_btnChangeActionPerformed


/** Lets the user select a value when the button is clicked.  Override this. */
protected Object selectValue()
{ return null; }


//public JTypedEditableLabel(JType jt, JFormattedTextField.AbstractFormatter formatter)
//{ setJType(jt, formatter); }

// --------------------------------------------------------------
/** Shouldn't be used... */
public void setJType(JType jt, citibob.text.SFormat sformat)
{
	label.setJType(jt, sformat);
	ckNull.setEnabled(jt.isInstance(null));	
}
// Must override stuff in TextTypedWidget
public void setJType(JType jt, SqlRunner str, citibob.text.DBFormat dbformat)
{
	label.setJType(str, dbformat);
	ckNull.setEnabled(jt.isInstance(null));	
}


public void setPopupWidget(TypedWidget w)
{
	popupPanel.add((Component)w, java.awt.BorderLayout.CENTER);
	popupWidget = w;
	w.addPropertyChangeListener("value", this);
}

///** Convenience function.
// @param nullText String to use for null value, or else <null> if this is not nullable. */
//public void setJType(citibob.util.KeyedModel kmodel, String nullText)
//{ label.setJType(kmodel, nullText); }


// --------------------------------------------------------------

public boolean isInstance(Object o)
	{ return label.isInstance(o); }
public boolean stopEditing()
	{  return true; }
// --------------------------------------------------------------
/** This can be overridden */
public void setValue(Object o)
	{ label.setValue(o); }

/** Once a formatter has figured out what the underlying value and display
 should be, set it.  This is for DBFormatter, when we need to make a DB
 query to format an item.  Only need to implement this method if we're
 planning on making a "DB" subclass of this widget. */
public void setDisplayValue(Object val, String display)
	{ label.setDisplayValue(val, display); }

public Object getValue()
	{ return label.getValue(); }
// --------------------------------------------------------------
//** Row (if any) in a RowModel we will bind this to at runtime. */
public String getColName() { return colName; }
/** Row (if any) in a RowModel we will bind this to at runtime. */
public void setColName(String col) { colName = col; }
//public Object clone() throws CloneNotSupportedException { return super.clone(); }
// ---------------------------------------------------


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bClose;
    protected javax.swing.JButton btnChange;
    protected javax.swing.JCheckBox ckNull;
    private javax.swing.JPanel jPanel1;
    private offstage.swing.typed.JTypedLabelDB label;
    private javax.swing.JPopupMenu popup;
    private javax.swing.JPanel popupPanel;
    // End of variables declaration//GEN-END:variables

// ============================================================
/** Called when popup widget's value changes.
 @return old value. */
public Object propertyChangeNoFire(PropertyChangeEvent evt)
{
	Object newval = popupWidget.getValue();
	if (newval == null) return null;		// Ignore nulls from popup widget!!!
	
	closePopup();
	Object oldval = getValue();
	ckNull.setSelected(false);
	setValue(newval);
	return oldval;
}
public void propertyChange(PropertyChangeEvent evt)
{
	Object oldval = propertyChangeNoFire(evt);
	if (oldval == null) return;
	Object newval = getValue();
	firePropertyChange("value", oldval, newval);
}

/** Called when the null checkbox is clicked. */
public void actionPerformed(ActionEvent evt)
{
	closePopup();
	Object oldval = getValue();
	Object newval = null;
	ckNull.setSelected(true);
	setValue(newval);
	firePropertyChange("value", oldval, newval);
	
}
}
