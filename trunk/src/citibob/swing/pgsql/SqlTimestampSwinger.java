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
///*
// * TypedWidgetSTFactory.java
// *
// * Created on March 18, 2006, 6:14 PM
// *
// * To change this template, choose Tools | Options and locate the template under
// * the Source Creation and Management node. Right-click the template and choose
// * Open. You can then make changes to the template in the Source Editor.
// */
//
//package citibob.swing.pgsql;
//
//import citibob.sql.*;
//import javax.swing.text.*;
//import java.text.*;
//import citibob.swing.typed.*;
//import java.util.*;
//import citibob.swing.typed.JDateType;
//import citibob.swing.typed.JDateSwinger;
//
//
///**
// *
// * @author citibob
// */
//public class SqlTimestampSwinger extends JDateSwinger
//{
////String fmt;
////DateFormat dfmt;
//
//// -------------------------------------------------------------------------
/////** Creates a new instance of TypedWidgetSTFactory */
////public SqlTimestampSwinger(JDateType sqlType, DateFormat dfmt) {
////	super(sqlType, dfmt);
////}
//private static DateFormat newTimestampFormat(Calendar cal, String fmt)
//{
//	DateFormat dff;
//	if (fmt == null) dff = DateFormat.getTimeInstance();
//	else dff = new SimpleDateFormat(fmt);
//	if (cal != null) dff.setCalendar(cal);
//	return dff;
//}
//public SqlTimestampSwinger(JDateType sqlType, Calendar cal, String fmt)
//{
//	super(sqlType, newTimestampFormat(cal, fmt));
//	this.fmt = fmt;
//}
//public SqlTimestampSwinger(JDateType sqlType, TimeZone tz, String fmt)
//{
//	this(sqlType, Calendar.getInstance(tz), fmt);
//}
//public SqlTimestampSwinger(String sDatabaseTZ, TimeZone displayTZ, String fmt)
//{ this(new citibob.sql.pgsql.SqlTimestamp(sDatabaseTZ), displayTZ, fmt); }
//public SqlTimestampSwinger(TimeZone displayTZ, String fmt)
//{ this("GMT", displayTZ, fmt); }
//
//// -------------------------------------------------------------------------
//public boolean renderWithWidget() { return true; }
//
///** Create a widget suitable for editing this type of data. */
//protected citibob.swing.typed.TypedWidget createTypedWidget()
//{
//	JTypedTimestampChooser dc = new JTypedTimestampChooser(); 
//	if (fmt != null) dc.setDateFormatString(getCalendar(), fmt);
//	return dc;
//}
//
//public javax.swing.text.DefaultFormatterFactory newFormatterFactory()
//{
//	return new DefaultFormatterFactory(new DateFormatter(dfmt));
//}
//
//
//}
