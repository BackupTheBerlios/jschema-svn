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
 * JDate.java
 *
 * Created on May 14, 2003, 8:52 PM
 */

package citibob.swing.typed;

import java.text.DateFormat;
import java.util.Date;
import javax.swing.*;
import citibob.swing.calendar.*;
import citibob.sql.*;
import java.beans.*;
import java.util.*;

/**
 * @author  citibob
 */
public class JTypedDateChooser extends BaseJTypedDateChooser
{
public JTypedDateChooser()
{
	super(new JCalendarDateOnly());
}
/** Set up widget to edit a specific JType.  Note that this widget does not
 have to be able to edit ALL JTypes... it can throw a ClassCastException
 if asked to edit a JType it doesn't like. */
public void setJType(citibob.swing.typed.Swinger f) throws ClassCastException
{
//	super.setJType(f, ((JDateSwinger)f).getCalendar());
	jType = (JDateType)f.getJType();
	// Normally, we render dates using the same TimeZone used to read them in
	// from the database.
	super.setJType(f, jType.getCalendar());
}
}
