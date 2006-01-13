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

public interface TextConverter
{
	/** Returns Java class of objects handled by this InputParser */
	Class getObjClass();

	/** Tries to convert string to an object of getClass().  String could be <null>.*/
	Object parseObject(String s) throws Exception;

	/** Converts an object of getClass() to a String. */
	String toString(Object o);
}
