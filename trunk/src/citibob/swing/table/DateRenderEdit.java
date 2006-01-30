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
 * DateItemCellRenderer.java
 *
 * Created on March 20, 2005, 6:46 PM
 */

package citibob.swing.table;

import javax.swing.table.*;
import citibob.swing.typed.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.event.*;
import java.text.*;
import java.util.Date;
import com.toedter.calendar.*;

/** Provides renderer and editor appropriate for an java.util.Date
 * (day only, no time). */
public class DateRenderEdit extends RenderEdit
{
	
public DateRenderEdit(String format)
{
//	renderer = new DateTableCellRenderer(format);
	renderer = new JDateChooserCellRenderer(new JTypedDateChooser(format));
	editor = new JDateChooserCellEditor(new JTypedDateChooser(format));
}
// ==================================================================
public static class DateTableCellRenderer extends DefaultTableCellRenderer {

DateFormat format;

/** Creates a new instance of DateItemCellRenderer */
public DateTableCellRenderer() {
	format = new SimpleDateFormat();
}
public DateTableCellRenderer(String fmt)
{
	this.format  = (fmt == null ? new SimpleDateFormat(): new SimpleDateFormat(fmt));
}
// ------------------------------------------------------

public void setValue(Object o) {
System.out.println("o = " + o);
	Date dt = (Date)o;
	setText(dt == null ? null : format.format(dt));
}
}
}
