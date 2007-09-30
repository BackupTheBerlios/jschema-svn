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
 * JDateChooser2.java
 *
 * Created on August 21, 2007, 1:28 PM
 */

package citibob.swing.typed;

import citibob.swing.calendar.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

/**
 *
 * @author  citibob
 */
public class JTypedDateChooser extends javax.swing.JPanel implements TypedWidget
{

protected CalModel cmod;		// Underlying nullable value model
protected JPopupMenu popup;
protected JCalendar jcal;		// calenadar that pops up when user hits button
protected JDateType jType;
boolean myPopupState;			// What state do WE think the popup is in?

/** Creates new form JDateChooser2 */
public JTypedDateChooser() {
	initComponents();
	dtfield.setSelectOnSet(true);


// Doesn't quite work; how do we validate?
//	dtfield.addKeyListener(new KeyAdapter() {
//	public void keyPressed(KeyEvent e) {
//		if (e.getKeyChar() == '\r' || e.getKeyChar() == '\n') {
//			dtfield.requestFocus(false);
//		}
//	}});


//	dtfield.addFocusListener(new java.awt.event.FocusAdapter() {
//	public void focusGained(FocusEvent e) {
//		dtfield.selectAll();
//System.out.println("selected text = " + dtfield.getSelectedText());
//	}});
//
//	dtfield.getCaret().addChangeListener(new ChangeListener() {
//	public void stateChanged(ChangeEvent e) {
//		System.out.println("Caret Changed: " + e);
//	}});
}

/** Configures type being edited, dropdown to display, etc. --- will be called
manually or from JDateSwinger. */
public void setJType(JDateType jt, String[] sfmt, String nullText, JCalendar jcal)
{
	this.jcal = jcal;

	// Create CalModel to edit, based on our data type.
	jType = (JDateType)jt;
	cmod = new CalModel(jType);

	// Configure sub-components
	jcal.setModel(cmod);
	dtfield.setJType(jType, sfmt, nullText, cmod);

	// Make popup disappear when user chooses a day in jcal
	cmod.addListener(new CalModel.Adapter() {
	public void dayButtonSelected() {
			popupHide();
	}});

	// Configure popup to display the chosen JCalendar.
	popup = new JPopupMenu();
	popup.setLightWeightPopupEnabled(true);
	popup.add((java.awt.Component)jcal);
}
void popupHide()
{
//System.out.println("Hiding Popup");
	myPopupState = false;
	popup.setVisible(false);
}
void popupShow()
{
	// Un-set null if null is set
	if (cmod.isNull()) cmod.setNull(false);

	// Popup is not showing --- show it!
	int x = calendarButton.getWidth() - (int) popup.getPreferredSize().getWidth();
	int y = calendarButton.getY() + calendarButton.getHeight();
//System.out.println("showing popup");
	popup.requestFocus(true);
		
	// #ifdef Java 1.5
	myPopupState = true;
	popup.show(calendarButton, x, y);
//		// TODO: Get around a bug in Java 1.4.2
//		// See: http://www.codecomments.com/archive250-2005-9-596151.html
//		// #ifdef Java 1.4.2
//		// This workaround doesn't QUITE work when inside a JTable :-(
//		Point pt = calendarButton.getLocationOnScreen();
//		pt.x += x;
//		pt.y += y;
//		popup.show(null, pt.x,pt.y);
//		lastPopupVisible = true;
}
// ===========================================================
// TypedWidget
/** Returns last legal value of the widget.  Same as method in JFormattedTextField */
public Object getValue()
{
	return cmod.getTime();
}

/** Sets the value.  Same as method in JFormattedTextField.  Fires a
 * propertyChangeEvent("value") when calling setValue() changes the value. */
public void setValue(Object o)
{
	java.util.Date d = (java.util.Date)o;

	cmod.setTime(d);

// TODO: Temporarily allow null in ALL fields --- to make it work
// in the query editor for dates...
if (d != null) {
	if (!isInstance(d)) throw new ClassCastException("Bad type " + d.getClass() + " " + d);
}
	java.util.Date dt = (d == null ? null :  jType.truncate((java.util.Date)d));
	
System.out.println("JTDC: Setting date to " + dt );
	java.util.Date oldDt = cmod.getTime();
	cmod.setTime(dt);

}

/** Is this object an instance of the class available for this widget?
 * If so, then setValue() will work.  See SqlType.. */
public boolean isInstance(Object o) { return jType.isInstance(o); }

/** From TableCellEditor (in case this is being used in a TableCellEditor):
 * Tells the editor to stop editing and accept any partially edited value
 * as the value of the editor. The editor returns false if editing was not
 * stopped; this is useful for editors that validate and can not accept
 * invalid entries. */
public boolean stopEditing() { return true; }

String col;
/** Row (if any) in a RowModel we will bind this to at runtime. */
public String getColName() { return col; }
/** Row (if any) in a RowModel we will bind this to at runtime. */
public void setColName(String col) { this.col = col; }


// =====================================================
// Methods implemented in java.awt.Component
/** Implemented in java.awt.Component */
public void setEnabled(boolean enabled)
{
	dtfield.setEnabled(enabled);
	calendarButton.setEnabled(enabled);
}
///** Implemented in java.awt.Component --- property will be "value" */
//public void addPropertyChangeListener(String property, java.beans.PropertyChangeListener listener);
///** Implemented in java.awt.Component --- property will be "value"  */
//public void removePropertyChangeListener(String property, java.beans.PropertyChangeListener listener);
// =======================================================

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        calendarButton = new javax.swing.JButton();
        dtfield = new citibob.swing.calendar.JCalModelTextField();

        setLayout(new java.awt.BorderLayout());

        setMinimumSize(new java.awt.Dimension(83, 19));
        setPreferredSize(new java.awt.Dimension(146, 19));
        calendarButton.setText("v");
        calendarButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        calendarButton.setPreferredSize(new java.awt.Dimension(30, 19));
        calendarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calendarButtonActionPerformed(evt);
            }
        });

        add(calendarButton, java.awt.BorderLayout.EAST);

        dtfield.setText("jCalModelTextField1");
        add(dtfield, java.awt.BorderLayout.CENTER);

    }// </editor-fold>//GEN-END:initComponents

private void calendarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calendarButtonActionPerformed
	if (myPopupState) {
		// Hide popup if it's showing'
		popupHide();
	} else {
		popupShow();
	}
}//GEN-LAST:event_calendarButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton calendarButton;
    private citibob.swing.calendar.JCalModelTextField dtfield;
    // End of variables declaration//GEN-END:variables


// =========================================================
/** Testing code */
private static JTypedDateChooser newField(JDateType jdt)
{
//	JDateSwinger swinger = new JDateSwinger(new JDate(),
//		TimeZone.getDefault(), "yyyy-MM-dd");
//	CalModel cmod = swinger.newCalModel();

	JTypedDateChooser tf;
	tf = new JTypedDateChooser();
	tf.setJType(jdt,
		new String[] {"MM/dd/yyyy", "yyyy-MM-dd", "MM/dd/yy", "MMddyy", "MMddyyyy"},
		"", new JCalendarDateOnly());
//	tf.setModel(cal);
	tf.setPreferredSize(new java.awt.Dimension(200,19));

	return tf;
}


public static void main(String[] args)
throws Exception
{
	JFrame f = new JFrame();

	JDateType jdt = new JDate();		// Includes TimeZone
//	CalModel model = new CalModel(jdt);

	java.awt.Container c = f.getContentPane();
	c.setLayout(new java.awt.FlowLayout());

	JTypedDateChooser tf;
	tf = newField(jdt);
	tf.setValue(null);
	c.add(tf);

	tf = newField(jdt);
	tf.setValue(new java.util.Date());
tf.setEnabled(false);
	c.add(tf);

	c.add(new JButton("Hello"));
//		f.getContentPane().add(new JLabel("    "));

	f.setSize(200,200);
	f.pack();
	f.setVisible(true);
}



}
