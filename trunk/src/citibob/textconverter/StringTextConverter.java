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
package citibob.textconverter;

import citibob.exception.FormatException;


public class StringTextConverter implements TextConverter
{

public static final int NOLIMIT = -1;
String nullString = "";

/** Maximum length of string allowed. */
int maxlen = NOLIMIT;

public StringTextConverter()
	{ maxlen = NOLIMIT; }
public StringTextConverter(int maxlen)
	{ this.maxlen = maxlen; }

/** Returns Java class of objects handled by this InputParser */
public Class getObjClass()
	{ return String.class; }

/** Tries to convert string to an object of getClass(). */
public Object parseObject(String s) throws Exception
{
    if (nullString.equals(s)) return null;

    if (maxlen != NOLIMIT && s.length() > maxlen) {
		throw new FormatException("String too long for field!");
    }
    return s;
}

public String toString(Object o)
{
//System.out.println("STC-toString: " + o);
    if (o == null) return nullString;
    return o.toString();
}
}
