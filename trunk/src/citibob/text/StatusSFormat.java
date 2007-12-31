/*
 * StatusSFormat.java
 *
 * Created on December 30, 2007, 11:27 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.text;

import static citibob.jschema.RowStatusConst.*;

/** Used to format the status column */
public class StatusSFormat extends AbstractSFormat
{
	public String valueToString(Object value) throws java.text.ParseException {
		if (value instanceof Integer) {
			String s = "";
			int status = ((Integer)value).intValue();
			if ((status & INSERTED) != 0) s += "I";
			if ((status & DELETED) != 0) s += "D";
			if ((status & CHANGED) != 0) s += "*";
			return s;
		} else {
			return "<ERROR>";
		}
	}
}
