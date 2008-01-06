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
 * AbstractSFormat.java
 *
 * Created on November 8, 2007, 11:06 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.text;

/**
 *
 * @author citibob
 */
public abstract class AbstractSFormat implements SFormat
{

protected String nullText;


public AbstractSFormat()
	{ this(""); }

/** Creates a new instance of AbstractSFormat */
public AbstractSFormat(String nullText) {
	this.nullText = nullText;
}
public String getNullText() { return nullText; }

/** This is OK for read-only SFormats */
public Object stringToValue(String text) throws java.text.ParseException { return null; }

}
