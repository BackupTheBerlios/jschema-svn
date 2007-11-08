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

import citibob.types.KeyedModel;
import javax.swing.*;
import javax.swing.text.*;
import java.text.*;

/**
 *
 * @author citibob
 */
public class LooseNumberFormatter extends NumberFormatter
implements citibob.text.SFormat
{

public LooseNumberFormatter() { super(); }

public LooseNumberFormatter(NumberFormat nf)
{ super(nf); }

public LooseNumberFormatter(citibob.sql.SqlNumeric tt)
{
	NumberFormat nf;
	if (tt.getScale() == 0) {
		nf = NumberFormat.getIntegerInstance();
	} else {
		nf = NumberFormat.getInstance();
		nf.setMinimumIntegerDigits(0);
		nf.setMaximumIntegerDigits(tt.getPrecision() - tt.getScale());
		nf.setMinimumFractionDigits(tt.getScale());
		nf.setMaximumFractionDigits(tt.getScale());
	}
	setFormat(nf);
}

/** Convert any valid numeric entry to the correct format. */
public Object  stringToValue(String text)
throws java.text.ParseException
{
	Object o;
	try {
		Double d = new Double(Double.parseDouble(text));
		o = super.stringToValue(valueToString(d));
	} catch(Exception e) {}
	o = super.stringToValue(text);
//	Class oclass = (o == null ? null : o.getClass());
//	System.out.println("LooseNF.stringToValue(" + text + ") = " + o + "(" + oclass + ")");
	return o;
}

//public String  valueToString(Object value)
//throws java.text.ParseException
//{
//	String s = super.valueToString(value);
//	Class oclass = (value == null ? null : value.getClass());
////	System.out.println("LooseNF.valueToString(" + value + ") = " + s + "(" + oclass + ")");
//	return s;
//}

}
