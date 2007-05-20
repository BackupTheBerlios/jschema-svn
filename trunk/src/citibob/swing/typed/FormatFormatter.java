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
