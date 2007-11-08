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
 * FlexiDateFormat.java
 *
 * Created on September 6, 2007, 10:04 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.text;

import java.text.*;
import java.util.*;

/**
 *
 * @author citibob
 */
public class DateFlexiFormat extends FlexiFormat
{

public TimeZone getTimeZone()
{ return ((DateFormat)super.formats[0]).getTimeZone(); }

public DateFlexiFormat(String[] sfmts, TimeZone tz)
{
	super(newFormats(sfmts, tz));
}

public static Format[] newFormats(String[] sfmts, TimeZone tz)
{
	Format[] fmts = new Format[sfmts.length];
	for (int i=0; i<fmts.length; ++i) {
		fmts[i] = newFormat(sfmts[i], tz);
	}
	return fmts;
}

public static DateFormat newFormat(String sfmt, TimeZone tz)
{
	DateFormat dff;
	if (sfmt == null) dff = DateFormat.getDateInstance();
	else dff = new SimpleDateFormat(sfmt);
	if (tz != null) dff.setTimeZone(tz);
	return dff;
}

}
