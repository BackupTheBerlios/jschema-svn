/*
* JDateChooser.java - A bean for choosing a date
* Copyright (C) 2004 Kai Toedter
* kai@toedter.com
* www.toedter.com
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU Lesser General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
*/
/*------------------------------------------------------------------------------
* Modified on 24 of November 2005
* Gregory Kotsaftis (http://sourceforge.net/projects/zeus-jscl/)
* mailto: gregkotsaftis@yahoo.com
*
* Changes:
* - Made the Alt + 'C' mnemonic optional. Now you can have more than one
* JDateChooser instance within a single panel with no mnemonics at all.
* - Also applied Chunju Tseng suggested fixes for issue:
* 'workaround for JDateChooser with startEmpty or null date', forum link
* http://www.toedter.com/forum/post.php?cat=1&fid=1&pid=58&page=1
* THANKS Chunju Tseng, this is exactly what I need!
* - Also applied Martin Pietruschka fix for setModel().
*/
package citibob.swing.calendar;

import java.awt.*;
import java.awt.event.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.net.URL;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
* A date chooser containig a date spinner and a button, that makes a JCalendar
* visible for choosing a date.
*
* @author Kai Toedter
* @version 1.2.1
*/
public class JDateChooser extends JPanel implements ActionListener,
CalModel.Listener, ChangeListener {

	private static final long serialVersionUID = 1L;

	protected JButton calendarButton;
	protected JSpinner dateSpinner;
	protected JSpinner.DateEditor editor;
	protected JPanel datePanel;
	protected JCalendar jcalendar;
	protected JPopupMenu popup;
	protected ReverseSpinnerDateModel sModel;
	protected String dateFormatString;
//	protected boolean dateSelected;
//	protected boolean isInitialized;
//	protected Date lastSelectedDate;
	protected boolean startEmpty;
CalModel model;
boolean lastPopupVisible = false;
//boolean buttonsEnabled = true;		// Are we responding to button clicks?

/**
* Creates a new JDateChooser object.
*/
public JDateChooser()
{
	this(null, null, true, null);
}

/**
* Creates a new JDateChooser object.
*
* @param icon the new icon
*/
public JDateChooser(ImageIcon icon)
{
	this(null, icon, true, null);
}

/**
* Creates a new JDateChooser object with given date format string.
* The default date format string is "MMMMM d, yyyy".
*
* @param dateFormatString the date format string
* @param startEmpty true, if the date field should be empty
*/
public JDateChooser(String dateFormatString)
{
	this(dateFormatString, null, true, null);
}


/**
* Creates a new JDateChooser.
*
* @param jcalendar the jcalendar or null
* @param dateFormatString the date format string or null (then "MMMMM d, yyyy" is used)
* @param startEmpty true, if the date field should be empty
* @param icon the icon or null (then an internal icon is used)
* @param mnemonic false, if you wish to disable the mnemonic code
*/
public JDateChooser(String dateFormatString,
ImageIcon icon, boolean mnemonic, CalModel model)
{
	
	jcalendar = new JCalendar();

//	if (dateFormatString == null) dateFormatString = "MMMMM d, yyyy";
	if (dateFormatString == null) dateFormatString = "MM/dd/yyyy";
	this.dateFormatString = dateFormatString;

//	this.startEmpty = startEmpty;
//this.startEmpty = false;		// TODO: startEmpty doesn't work.  For now, this cannot handle null dates.

	setLayout(new BorderLayout());

	// ------------- Set up spinner model
	sModel = new ReverseSpinnerDateModel();
	setSModel(sModel);
	dateSpinner = new JSpinner(sModel);
//	{
//	public void setEnabled(boolean enabled) {
//		// TODO: I don't know why this is.
//		super.setEnabled(enabled);
//		calendarButton.setEnabled(enabled);
//	}};

	// Allow to have either date or null showing.
	datePanel = new JPanel();
    datePanel.setLayout(new java.awt.CardLayout());
	datePanel.add(dateSpinner, "date");
	datePanel.add(new JLabel("null"), "null");
		
	String tempDateFormatString = "";

	if (!startEmpty) tempDateFormatString = dateFormatString;

	editor = new JSpinner.DateEditor(dateSpinner, tempDateFormatString);
	dateSpinner.setEditor(editor);
	add(datePanel, BorderLayout.CENTER);

	// ------------- Display a calendar button with an icon
	if (icon == null) {
//		 URL iconURL = getClass().getResource("images/JDateChooserIcon.gif");
		 URL iconURL = getClass().getResource("/citibob/swing/calendar/images/JDateChooserIcon.gif");
	System.out.println("iconURL = " + iconURL);
		 icon = new ImageIcon(iconURL);
	}

	calendarButton = new JButton(icon);
	calendarButton.setMargin(new Insets(0, 0, 0, 0));
	calendarButton.addActionListener(this);
	calendarButton.addMouseListener(new java.awt.event.MouseAdapter() {
		public void  mouseEntered(java.awt.event.MouseEvent e)
		{
			lastPopupVisible = popup.isVisible();
//			System.out.println("Mouse entered, popup status = " + popup.isVisible());
		}
	});

	// check by Gregory Kotsaftis
	if( mnemonic ) {
		// Alt + 'C' selects the calendar.
		calendarButton.setMnemonic(KeyEvent.VK_C);
	}
	add(calendarButton, BorderLayout.EAST);

	calendarButton.setMargin(new Insets(0, 0, 0, 0));
	popup = new JPopupMenu();
//	{
//	public void setVisible(boolean b) {
//		Boolean IsCanceled = (Boolean) getClientProperty(
//		"JPopupMenu.firePopupMenuCanceled");
//		boolean isCanceled = (IsCanceled == null ? false : IsCanceled.booleanValue());
//		
//		if (b || (!b && dateSelected) || (!b && isCanceled)) super.setVisible(b);
//	}};
//popup.setFocusable(false);
//calendarButton.setFocusable(false);

	popup.setLightWeightPopupEnabled(true);

	popup.add(jcalendar);

//	this.addFocusListener(new FocusListener() {
//		public void  focusGained(FocusEvent e) {
//System.out.println("Focus Gained");
//			
//		}
//		public void  focusLost(FocusEvent e) {
//System.out.println("Focus Lost");
//			// TODO: Get around bug in Java 1.4.2 (see elsewhere in this file)
//			setPopupVisible(false);
//		}
//	});
	
	
	if (model == null) model = new CalModel();
	setModel(model);
}

//public void setButtonsEnabled(boolean e)
//{
//	buttonsEnabled = e;
//	sModel.setEnabled(e);
//}

public void setModel(CalModel m)
{
	if (m != null) m.removeListener(this);
	this.model = m;
	model.addListener(this);
	jcalendar.setModel(model);
}
public CalModel getModel() { return model; }

void setPopupVisible(boolean b)
{
	popup.setVisible(b);
	lastPopupVisible = b;
}

/**
* Called when the jalendar button was pressed.
*
* @param e the action event
*/
public void actionPerformed(ActionEvent e)
{
//	if (!buttonsEnabled) return;
System.out.println("Cal button action performed!");
	if (lastPopupVisible) return;		// Do nothing; already visible.
//	System.out.println("hiding popup");
//		calendarButton.requestFocus();
//		setPopupVisible(false);
//	} else {
		model.setNull(false);
		int x = calendarButton.getWidth() - (int) popup.getPreferredSize().getWidth();
		int y = calendarButton.getY() + calendarButton.getHeight();
System.out.println("showing popup");
		popup.requestFocus(true);
		
//		// TODO: Get around a bug in Java 1.4.2
//		// See: http://www.codecomments.com/archive250-2005-9-596151.html
//		// #ifdef Java 1.4.2
//		// This workaround doesn't QUITE work when inside a JTable :-(
//		Point pt = calendarButton.getLocationOnScreen();
//		pt.x += x;
//		pt.y += y;
//		popup.show(null, pt.x,pt.y);
//		lastPopupVisible = true;
		
		// #ifdef Java 1.5
		popup.show(calendarButton, x, y);
//	}
}
// ===================================================================
// CalModel.Listener
/**  Listens for a change event on the model,
 * updates the dateSpinner and closes the popup.*/
public void calChanged()
{
	calModel2SModel();
}
public void dayButtonSelected() {
	setPopupVisible(false);
}
public void nullChanged() {
	//citibob.swing.WidgetTree.setEnabled(this, !model.isNull());
	//calendarButton.setEnabled(true);
	CardLayout cl = (CardLayout)(datePanel.getLayout());
	if (model.isNull()) cl.show(datePanel, "null");
	else cl.show(datePanel, "date");
}
// ===================================================================

	/**
	* Updates the UI of itself and the popup --- Look & Feel changes
	*/
	public void updateUI()
	{
		super.updateUI();
		if (jcalendar != null) SwingUtilities.updateComponentTreeUI(popup);
	}

	/**
	* Sets the locale.
	*
	* @param l The new locale value
	*/
	public void setLocale(Locale l)
	{
		// see http://toedter.com/forum/post.php?cat=1&fid=2&pid=26&page=1
		if(dateSpinner == null) {
			return;
		}
		
		dateSpinner.setLocale(l);
		editor = new JSpinner.DateEditor(dateSpinner, dateFormatString);
		dateSpinner.setEditor(editor);
		jcalendar.setLocale(l);
	}

/**
* Gets the date format string.
*
* @return Returns the dateFormatString.
*/
public String getDateFormatString()
{
	return dateFormatString;
}

/**
* Sets the date format string. E.g "MMMMM d, yyyy" will result in
* "July 21, 2004" if this is the selected date and locale is English.
*
* @param dateFormatString The dateFormatString to set.
*/
public void setDateFormatString(String dateFormatString)
{
	this.dateFormatString = dateFormatString;
	editor.getFormat().applyPattern(dateFormatString);
	invalidate();
}

/**
* Returns "JDateChooser".
*
* @return the name value
*/
public String getName()
{
	return "JDateChooser";
}

// ---------------------------------------------------------
/**
* Sets the date in the JSpinner, based on the date in the CalModel.
* @param date the new date.
*/
public void calModel2SModel()
{
	Date date = model.getCalTime();
	if (date.equals(sModel.getDate())) return;

	sModel.setValue(date);
}
public void sModel2CalModel()
{
	Date date = sModel.getDate();
	if (date.equals(model.getCalTime())) return;
	model.setTime(date);
}
// ---------------------------------------------------------
// LISTENER for DateSpinner
/**
 * When spinner's state changes, update it in the model.
 * @param e the change event
*/
public void stateChanged(ChangeEvent e)
{
	sModel2CalModel();
	if( getParent() != null) getParent().validate();
}

/*
* The methods:
* public JSpinner getSpinner()
* public SpinnerDateModel getModel()
* public void setModel(SpinnerDateModel mdl)
*
* were added by Mark Brown on 24 Aug 2004. They were added to allow the
* setting of the SpinnerDateModel from a source outside the JDateChooser
* control. This was necessary in order to allow the JDateChooser to be
* integrated with applications using persistence frameworks,
* like Oracle's ADF/BC4J.
*/

/**
* Return this controls JSpinner control.
*
* @return the JSpinner control
*/
public JSpinner getSpinner()
	{ return dateSpinner; }

/**
* Return the SpinnerDateModel associated with this control.
*
* @return the SpinnerDateModel
*/
public SpinnerDateModel getSModel()
	{ return sModel; }

/**
* Set the SpinnerDateModel for this control. This method allows the
* JDateChooser control to be used with some persistence frameworks,
* (ie. Oracle ADF) to bind the control to the database Date value.
*
* @param mdl the SpinnerDateModel
*/
public void setSModel(ReverseSpinnerDateModel mdl)
{
	sModel = mdl;
	sModel.setCalendarField(java.util.Calendar.WEEK_OF_MONTH);
	sModel.addChangeListener(this);

	// Begin Code change by Martin Pietruschka on 16 Sep 2004
	if( dateSpinner != null) dateSpinner.setModel(sModel);
}
	public static void main(String[] args)
	throws Exception
	{
		JFrame f = new JFrame();
		
		CalModel cm = new CalModel();

		JDateChooser jd = new JDateChooser();
		f.getContentPane().add(jd);
		
		f.setSize(200,200);
		f.pack();
		f.show();
	}
}


