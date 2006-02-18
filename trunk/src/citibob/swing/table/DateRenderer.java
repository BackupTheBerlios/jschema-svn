/*
JSchema: library for GUI-based database applications
This file Copyright (c) 2006 by Robert Fischer

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/
/*
 * KeyedItemCellRenderer.java
 *
 * Created on March 20, 2005, 6:46 PM
 */

package citibob.swing.table;

import javax.swing.table.*;
import citibob.swing.typed.*;
import java.util.*;
import java.text.*;
import citibob.jschema.KeyedModel;
import javax.swing.*;
import javax.swing.event.*;

public class DateRenderer extends DefaultTableCellRenderer {

DateFormat fmt;
String nullString = "";

/** Creates a new instance of KeyedItemCellRenderer */
public DateRenderer(DateFormat fmt) {
	this.fmt = fmt;
}
// ------------------------------------------------------

public void setNullString(String s)
	{ nullString = s; }


public void setValue(Object o)
{
	Date dt = (Date)o;
	if (o == null) setText(nullString);
	setText(fmt.format(dt));
}
// ==========================================================
public static List makeDateList(Date first, Date last, long periodMS)
{
	ArrayList ret = new ArrayList();
	Date dt = (Date)first.clone();
	while (dt.getTime() <= last.getTime()) {
		ret.add(dt);
		dt = new Date(dt.getTime() + periodMS);
	}
	return ret;
}


public static List makeDateList(Calendar cal, int firstHr, int firstMin, int lastHr, int lastMin, long periodMS)
{
	if (cal == null) cal = Calendar.getInstance(); //new GregorianCalendar();
	cal.setTimeInMillis(0);
	
	cal.set(Calendar.HOUR_OF_DAY, firstHr);
	cal.set(Calendar.MINUTE, firstMin);
	java.util.Date first = new java.util.Date(cal.getTimeInMillis());
	
	cal.set(Calendar.HOUR_OF_DAY, lastHr);
	cal.set(Calendar.MINUTE, lastMin);
	java.util.Date last = new java.util.Date(cal.getTimeInMillis());

	return makeDateList(first, last, periodMS);
	
}

}
