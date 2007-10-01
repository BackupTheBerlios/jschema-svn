///*
//JSchema: library for GUI-based database applications
//This file Copyright (c) 2006-2007 by Robert Fischer
//
//This program is free software: you can redistribute it and/or modify
//it under the terms of the GNU General Public License as published by
//the Free Software Foundation, either version 3 of the License, or
//(at your option) any later version.
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program.  If not, see <http://www.gnu.org/licenses/>.
//*/
//package citibob.swing.pgsql;
//
//import citibob.sql.*;
//import citibob.swing.CitibobJTable;
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
//public SqlTimestampSwinger(JDateType sqlType, String[] sfmt, String nullText)
//{
//	super(sqlType, sfmt, nullText, new citibob.swing.calendar.JCalendarDateHHMM());
//}
//public SqlTimestampSwinger(String sDatabaseTZ, String fmt)
//	{ this(new citibob.sql.pgsql.SqlTimestamp(sDatabaseTZ), displayTZ, fmt); }
//public SqlTimestampSwinger(TimeZone displayTZ, String fmt)
//{ this("GMT", displayTZ, fmt); }
//
//
//}
