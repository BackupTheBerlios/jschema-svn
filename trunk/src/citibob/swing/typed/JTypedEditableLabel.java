/*
 * JAdultLabel.java
 *
 * Created on June 29, 2007, 9:59 PM
 */

package citibob.swing.typed;

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
public class JTypedEditableLabel
extends javax.swing.JPanel
implements TypedWidget, PropertyChangeListener, ActionListener
{

TypedWidget popupWidget;		// The widget we display in the popup to change the value.
String colName;
	
//JFormattedTextField.AbstractFormatter
    /** Creates new form JAdultLabel */
    public JTypedEditableLabel() {
        initComponents();
		ckNull.addActionListener(this);
		
		popup.add(popupPanel);
		
		// Set image on the close button
//		bClose.setIcon(new ImageIcon(getClass().getResource(
//			"images/window-close.png")));
//		bClose.setText(null);
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
        label = new citibob.swing.typed.JTypedLabel();
        btnChange = new javax.swing.JButton();

        popupPanel.setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.BorderLayout());

        ckNull.setText("null");
        ckNull.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        ckNull.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel1.add(ckNull, java.awt.BorderLayout.WEST);

        bClose.setText("x");
        bClose.setFocusable(false);
        bClose.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bCloseActionPerformed(evt);
            }
        });

        jPanel1.add(bClose, java.awt.BorderLayout.EAST);

        popupPanel.add(jPanel1, java.awt.BorderLayout.NORTH);

        setLayout(new java.awt.BorderLayout());

        label.setText("jTypedLabel1");
        add(label, java.awt.BorderLayout.CENTER);

        btnChange.setText("...");
        btnChange.setPreferredSize(new java.awt.Dimension(35, 29));
        btnChange.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnChangeActionPerformed(evt);
            }
        });

        add(btnChange, java.awt.BorderLayout.EAST);

    }// </editor-fold>//GEN-END:initComponents

	private void bCloseActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bCloseActionPerformed
	{//GEN-HEADEREND:event_bCloseActionPerformed
		popup.setVisible(false);
// TODO add your handling code here:
	}//GEN-LAST:event_bCloseActionPerformed

protected void showPopup()
{
	popup.setPopupSize(getWidth(), 300);
	popup.pack();
	popup.show(this, 0, this.getHeight());	
}
	
private void btnChangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangeActionPerformed
	showPopup();
}//GEN-LAST:event_btnChangeActionPerformed


/** Lets the user select a value when the button is clicked.  Override this. */
protected Object selectValue()
{ return null; }


public JTypedEditableLabel(JType jt, JFormattedTextField.AbstractFormatter formatter)
{ setJType(jt, formatter); }

// --------------------------------------------------------------
public void setJType(Swinger f)
{
	label.setJType(f);
	JType jt = f.getJType();
	ckNull.setEnabled(jt.isInstance(null));
}

public void setJType(JType jt, JFormattedTextField.AbstractFormatter formatter)
{
	label.setJType(jt, formatter);
	ckNull.setEnabled(jt.isInstance(null));
}

public void setNullText(String s) {
	ckNull.setText(s);
	label.setNullText(s);
}
public String getNullText(String s) { return label.getNullText(); }

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
    private javax.swing.JCheckBox ckNull;
    private javax.swing.JPanel jPanel1;
    private citibob.swing.typed.JTypedLabel label;
    private javax.swing.JPopupMenu popup;
    private javax.swing.JPanel popupPanel;
    // End of variables declaration//GEN-END:variables

// ============================================================
/** Called when popup widget's value changes. */
public void propertyChange(PropertyChangeEvent evt)
{
	Object newval = popupWidget.getValue();
	if (newval == null) return;		// Ignore nulls from popup widget!!!
	
	popup.setVisible(false);
	Object oldval = getValue();
	ckNull.setSelected(false);
	setValue(newval);
	firePropertyChange("value", oldval, newval);
}

/** Called when the null checkbox is clicked. */
public void actionPerformed(ActionEvent evt)
{
	popup.setVisible(false);
	Object oldval = getValue();
	Object newval = null;
	ckNull.setSelected(true);
	setValue(newval);
	firePropertyChange("value", oldval, newval);
	
}
}