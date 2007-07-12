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
import citibob.text.*;
import citibob.sql.pgsql.*;

public class SqlTimeSwinger extends TypedTextSwinger
{

DateFormat dfmt;
	
public SqlTimeSwinger(JType jType, String format)
{
	super(jType);
	dfmt = new SimpleTimeFormat(format);
}

public SqlTimeSwinger()
	{ this(true, "HH:mm"); }
public SqlTimeSwinger(boolean nullable, String format)
{
	this(new SqlTime(nullable), format);
}

/** Creates an AbstractFormatterFactory for a JFormattedTextField.  If this
 SqlType is never to be edited with a JFormattedTextField, it can just
 return null.  NOTE: This should return a new instance of AbstractFormatterFactory
 because one instance is required per JFormattedTextField.  It's OK for the
 factory to just store instances of 4 AbstractFormatters and return them as needed. */
public javax.swing.text.DefaultFormatterFactory newFormatterFactory()
{
//	SqlTime type = (SqlTime)jType;
//	type.
//	NumberFormat nf = NumberFormat.getIntegerInstance();
//	NumberFormatter nff = new NumberFormatter(nf);
	return new DefaultFormatterFactory(new FormatFormatter(dfmt));
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
