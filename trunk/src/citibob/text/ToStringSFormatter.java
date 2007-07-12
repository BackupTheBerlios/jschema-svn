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
 * FormatSFormatter.java
 *
 * Created on February 26, 2007, 12:52 AM
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
public class ToStringSFormatter implements SFormatter
{
//	Format fmt;
	String nullval = "";
	
	public String  valueToString(Object value) throws java.text.ParseException
	{
		return (value == null ? nullval : value.toString());
//		return value.toString();
	}
	public Object  stringToValue(String text)  throws java.text.ParseException
	{
		throw new ParseException("ToStringSFormatter doesn't know how to parse!", 0);
	}
	
}
