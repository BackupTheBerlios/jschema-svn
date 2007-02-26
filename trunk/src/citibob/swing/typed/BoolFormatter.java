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

/**
 *
 * @author citibob
 */
public class BoolFormatter extends JFormattedTextField.AbstractFormatter
implements citibob.text.SFormatter
{

int limit;
String nullText = "";

static TreeMap<String, Boolean> vals;
static {
	vals = new TreeMap();
	vals.put("false", Boolean.FALSE);
	vals.put("no", Boolean.FALSE);
	vals.put("f", Boolean.FALSE);
	vals.put("n", Boolean.FALSE);
	vals.put("true", Boolean.TRUE);
	vals.put("yes", Boolean.TRUE);
	vals.put("t", Boolean.TRUE);
	vals.put("y", Boolean.TRUE);
}
public void setNullText(String s) { nullText = s; }

/** Not to be used */
public Object  stringToValue(String text)
{
	if (text == null || text.equals(nullText)) return null;
	return vals.get(text.toLowerCase());
}
public String  valueToString(Object value)
{
	if (value == null) return nullText;
	boolean b = ((Boolean)value).booleanValue();
	return (b ? "true" : "false");
}
}
