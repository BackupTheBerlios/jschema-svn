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
 * FormatUtils.java
 *
 * Created on November 9, 2007, 12:19 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.text;


import java.text.*;

/**
 *
 * @author citibob
 */
public class FormatUtils {

// ==================================================================
// Utility classes to create formats from format strings
public static Format newFormat(Class klass, String fmtString)
{
	if (Number.class.isAssignableFrom(klass))
		return new DecimalFormat(fmtString);
	else if (java.util.Date.class.isAssignableFrom(klass))
		return new SimpleDateFormat(fmtString);
	return null;
}
}
