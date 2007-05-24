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
public KeyedFormatter(KeyedModel kmodel) {
	this.kmodel = kmodel;
}

public void setNullText(String s) { nullText = s; }

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
