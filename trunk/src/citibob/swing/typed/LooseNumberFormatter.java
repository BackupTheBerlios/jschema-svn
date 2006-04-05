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
import javax.swing.text.*;
import java.text.*;

/**
 *
 * @author citibob
 */
public class LooseNumberFormatter extends NumberFormatter
{

public LooseNumberFormatter(NumberFormat nf)
{ super(nf); }

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
