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
 * JDateSFormat.java
 *
 * Created on February 26, 2007, 1:23 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.text;


import citibob.swing.typed.*;
import java.util.*;
import java.text.*;

/**
 *
 * @author citibob
 */
public class JDateSFormat extends javax.swing.text.DateFormatter implements SFormat
{
    /**
     * This is shorthand for
     * <code>new DateFormatter(DateFormat.getDateInstance())</code>.
     */
    public JDateSFormat() { super(); }

    /**
     * Returns a DateFormatter configured with the specified
     * <code>Format</code> instance.
     *
     * @param format Format used to dictate legal values
     */
    public JDateSFormat(DateFormat format) { super(format); }

	
//public static DateFormat newDateFormat(TimeZone tz, String fmt)
//{
//	DateFormat dff;
//	if (fmt == null) dff = DateFormat.getDateInstance();
//	else dff = new SimpleDateFormat(fmt);
//	if (tz != null) dff.setTimeZone(tz);
//	return dff;
//}
//
public JDateSFormat(String sfmt, TimeZone tz)
{
	this(DateFlexiFormat.newFormat(sfmt, tz));
}
public JDateSFormat(String sfmt)
{
	this(sfmt, null);
}

	
}
