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

package citibob.swing.typed;

import citibob.sql.*;
import citibob.types.JDateType;
import javax.swing.text.*;
import java.text.*;
import java.util.*;
import citibob.swing.calendar.*;
import javax.swing.*;
import citibob.text.*;

/**
 *
 * @author citibob
 */
public class JDateSwinger extends TypedWidgetSwinger
{
protected String[] sfmt;
protected String nullText;
//protected JCalendar jcal;
protected Class jcalClass;
protected TimeZone displayTZ;

//public static JCalendar newJcalDateOnly() { return new JCalendarDateOnly(); }
//public static JCalendar newJcalDateHHMM() { return new JCalendarDateHHMM(); }

// -------------------------------------------------------------------------
/** Creates a new instance of TypedWidgetSTFactory */
public JDateSwinger(JDateType jt, String[] sfmt, String nullText, TimeZone displayTZ, Class jcalClass) {
	super(jt);
	this.sfmt = sfmt;
	this.nullText = nullText;
	this.jcalClass = jcalClass;
	this.displayTZ = displayTZ;
}
///** Creates a new instance of TypedWidgetSTFactory */
//public JDateSwinger(JDateType jt, String[] sfmt, JCalendar jcal) {
//	this(jt, sfmt, "", jcal);
//}
// -------------------------------------------------------------------------
//public boolean renderWithWidget() { return true; }

public void configureWidget(TypedWidget tw)
{
	if (tw instanceof JTypedDateChooser) {
		JTypedDateChooser jtdc = (JTypedDateChooser)tw;
		try {
			JCalendar jcal = (JCalendar)jcalClass.newInstance();
			jtdc.setJType((JDateType)jType, sfmt, nullText, displayTZ, jcal);
		} catch(Exception e) {
			// Shouldn't happen
			e.printStackTrace();
		}
	} else if (tw instanceof TextTypedWidget) {
		TextTypedWidget tf = (TextTypedWidget)tw;
		tf.setJType(jType, newFormatterFactory());
	}
}


/** Create a widget suitable for editing this type of data. */
public citibob.swing.typed.TypedWidget createWidget()
{
	return new JTypedDateChooser(); 
}

public javax.swing.text.DefaultFormatterFactory newFormatterFactory()
{
	TimeZone tz = (displayTZ == null ? ((JDate)jType).getTimeZone() : displayTZ);
	return JTypedTextField.newFormatterFactory(new DateFlexiFormat(sfmt, tz));
}
//public citibob.swing.table.RenderEdit newRenderEdit(boolean editable)
//{
//	TypedWidgetRenderEdit twre = new TypedWidgetRenderEdit(this, editable);
//	
//	// Remove border from Date chooser widgets in table
//	TypedWidgetRenderEdit.Renderer r = (TypedWidgetRenderEdit.Renderer)twre.getRenderer();
//	TypedWidgetRenderEdit.Editor e = (TypedWidgetRenderEdit.Editor)twre.getEditor();
//	if (r != null) ((JComponent)r.tw).setBorder(null);
//	if (e != null) ((JComponent)e.tw).setBorder(null);
//	return twre;
//}

// ================================================================
//static class NullableDateFormatter extends DateFormatter
//{
//	String nullText = "";
//	public NullableDateFormatter(DateFormat dfmt, String nullText) {
//		super(dfmt);
//		this.nullText = nullText;
//	}
//	public Object stringToValue(String text)throws java.text.ParseException {
//		if (text == null || nullText.equals(text)) return null;
//		return super.stringToValue(text);
//	}
//	public String valueToString(Object val)throws java.text.ParseException {
//		if (val == null) return nullText;
//		return super.valueToString(val);
//	}
//}
// ================================================================
}
