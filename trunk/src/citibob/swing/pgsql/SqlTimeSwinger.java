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

package citibob.swing.pgsql;

import citibob.sql.*;
import citibob.swing.swingers.TypedTextSwinger;
import citibob.types.JType;
import javax.swing.text.*;
import java.text.*;
import citibob.swing.typed.*;
import java.util.*;
import citibob.text.*;
import citibob.sql.pgsql.*;

public class SqlTimeSwinger extends TypedTextSwinger
{

String[] sfmt;
String nullText;

public SqlTimeSwinger(JType jType, String[] sfmt, String nullText)
{
	super(jType);
	this.sfmt = sfmt;
	this.nullText = nullText;
//if (nullText == null) {
//		System.out.println("SqlTimeSwinger hoi");
//}
}

public javax.swing.text.DefaultFormatterFactory newFormatterFactory()
{
	return JTypedTextField.newFormatterFactory(
		new DateFlexiFormat(sfmt, ((JDate)jType).getTimeZone()), nullText);
}

}




///**
// *
// * @author citibob
// */
//public class SqlTimeSwinger extends JDateSwinger
//{
//
//String fmt;
//DateFormat dfmt;
//
//// -------------------------------------------------------------------------
///** Creates a new instance of TypedWidgetSTFactory */
//public SqlTimeSwinger(JDateType sqlType, DateFormat dfmt) {
//	super(sqlType, dfmt);
//}
//public static DateFormat newTimeFormat(String fmt)
//{
//	DateFormat dff;
//	dff = new SimpleTimeFormat(fmt);
//	return dff;
//}
//public SqlTimeSwinger(JDateType sqlType, String fmt)
//{
//	this(sqlType, newTimeFormat(fmt));
//	this.fmt = fmt;
//}
//// -------------------------------------------------------------------------
//public boolean renderWithWidget() { return true; }
//
///** Create a widget suitable for editing this type of data. */
//protected citibob.swing.typed.TypedWidget createTypedWidget()
//{
//	JTypedDateChooser dc = new JTypedDateChooser(); 
//	if (fmt != null) dc.setDateFormatString(fmt);
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
