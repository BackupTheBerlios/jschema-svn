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
 * KeyedFormatter.java
 *
 * Created on March 18, 2006, 4:37 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.swing.typed;

import citibob.util.KeyedModel;
import javax.swing.*;
import java.util.*;
import java.text.Format;

/**
 * AbstractFormatter based on any kind of java.text.Format object
 * Mostly used for dates.
 * @author citibob
 */
public class FormatFormatter extends JFormattedTextField.AbstractFormatter
implements citibob.text.SFormatter
{

java.text.Format format;

public FormatFormatter(Format format)
{
	this.format = format;
}
public Object  stringToValue(String text)
{
	try {
		return format.parseObject(text);
	} catch(java.text.ParseException e) {
		return null;
	}
}
public String  valueToString(Object value)
{
	if (value == null) return null;
//if (format instanceof java.text.NumberFormat) {
//		System.out.println("ohi");
//}
	return format.format(value);
}
}
