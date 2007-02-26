/*
 * SFormat.java
 *
 * Created on February 26, 2007, 12:46 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.text;

/**
 * SFormat = Simple Format or String Format.  A subset of the JFormattedTextString.AbstractFormatter functionality.
 * @author citibob
 */
public interface SFormatter
{
	public Object  stringToValue(String text) throws java.text.ParseException;
	public String  valueToString(Object value) throws java.text.ParseException;
}
