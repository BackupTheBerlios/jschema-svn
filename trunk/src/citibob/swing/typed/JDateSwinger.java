/*
 * TypedWidgetSTFactory.java
 *
 * Created on March 18, 2006, 6:14 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.swing.typed;

import citibob.sql.*;
import javax.swing.text.*;
import java.text.*;
import java.util.*;


/**
 *
 * @author citibob
 */
public class JDateSwinger extends TypedWidgetSwinger
{
protected String fmt;
protected DateFormat dfmt;

// -------------------------------------------------------------------------
/** Creates a new instance of TypedWidgetSTFactory */
public JDateSwinger(JDateType sqlType, DateFormat dfmt) {
	super(sqlType);
	this.dfmt = dfmt;
}
public static DateFormat newDateFormat(TimeZone tz, String fmt)
{
	DateFormat dff;
	if (fmt == null) dff = DateFormat.getDateInstance();
	else dff = new SimpleDateFormat(fmt);
	if (tz != null) dff.setTimeZone(tz);
	return dff;
}
public JDateSwinger(JDateType sqlType, TimeZone tz, String fmt)
{
	this(sqlType, newDateFormat(tz, fmt));
	this.fmt = fmt;
}
//public JDateSwinger(JDateType sqlType, String fmt)
//{
//	this(sqlType, TimeZone.getDefault(), fmt);
//}
// -------------------------------------------------------------------------
public boolean renderWithWidget() { return true; }

/** Create a widget suitable for editing this type of data. */
protected citibob.swing.typed.TypedWidget createTypedWidget()
{
	JTypedDateChooser dc = new JTypedDateChooser(); 
	if (fmt != null) dc.setDateFormatString(fmt);
	return dc;
}

public javax.swing.text.DefaultFormatterFactory newFormatterFactory()
{
	return new DefaultFormatterFactory(new DateFormatter(dfmt));
}


}
