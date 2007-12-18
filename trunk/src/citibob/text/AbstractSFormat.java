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
