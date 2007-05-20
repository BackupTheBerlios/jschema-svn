/*
 * TypedWidgetSTFactory.java
 *
 * Created on March 18, 2006, 6:14 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.swing.pgsql;

import citibob.sql.*;
import javax.swing.text.*;
import java.text.*;
import citibob.swing.typed.*;
import java.util.*;
import citibob.swing.typed.JDateType;
import citibob.swing.typed.JDateSwinger;


/**
 *
 * @author citibob
 */
public class SqlTimestampSwinger extends JDateSwinger
{
//String fmt;
//DateFormat dfmt;

// -------------------------------------------------------------------------
/** Creates a new instance of TypedWidgetSTFactory */
public SqlTimestampSwinger(JDateType sqlType, DateFormat dfmt) {
	super(sqlType, dfmt);
}
public static DateFormat newTimestampFormat(Calendar cal, String fmt)
{
	DateFormat dff;
	if (fmt == null) dff = DateFormat.getTimeInstance();
	else dff = new SimpleDateFormat(fmt);
	if (cal != null) dff.setCalendar(cal);
	return dff;
}
public SqlTimestampSwinger(JDateType sqlType, Calendar cal, String fmt)
{
	this(sqlType, newTimestampFormat(cal, fmt));
	this.fmt = fmt;
}
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
