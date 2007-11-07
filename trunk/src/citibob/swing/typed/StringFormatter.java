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

/**
 *
 * @author citibob
 */
public class StringFormatter
extends JFormattedTextField.AbstractFormatter
implements citibob.text.SFormatter
{

int limit;
String nullText = "";

/** Creates a new instance of KeyedFormatter */
public StringFormatter(int limit) {
	this.limit = limit;
}

public StringFormatter()
	{ this(-1); }

public void setNullText(String s) { nullText = s; }

/** Not to be used */
public Object  stringToValue(String text)
{
	return text;
}
public String  valueToString(Object value)
{
	if (value == null) return nullText;
	else return (String)value;
}
}
