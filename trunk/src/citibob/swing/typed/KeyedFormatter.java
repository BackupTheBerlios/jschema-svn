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
public class KeyedFormatter extends JFormattedTextField.AbstractFormatter
implements citibob.text.SFormatter
{

KeyedModel kmodel;
String nullText;

/** Creates a new instance of KeyedFormatter */
public KeyedFormatter(KeyedModel kmodel, String nullText) {
	this.kmodel = kmodel;
	this.nullText = nullText;
}
public KeyedFormatter(KeyedModel kmodel) { this(kmodel, ""); }

/** Not to be used */
public Object  stringToValue(String text)
{
	return null;
}
public String  valueToString(Object value)
{
	String s = kmodel.toString(value);
	if (s == null) {	// Not found in kmodel
		if (value == null) return nullText;
		return "x" + value.toString();
	}
	return s;
//	
//	if (value == null) {
//		
//	if (s == null && value == null) return nullText;
//		
//		String ret = kmodel.toString(null);
//		if (ret != null) return ret;
//		return nullText;
//	}
//	if (s != null) return s;
//	return "x" + value.toString();
	
}
}
