/*
 * FormatUtils.java
 *
 * Created on November 9, 2007, 12:19 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.text;


import java.text.*;

/**
 *
 * @author citibob
 */
public class FormatUtils {

// ==================================================================
// Utility classes to create formats from format strings
public static Format newFormat(Class klass, String fmtString)
{
	if (Number.class.isAssignableFrom(klass))
		return new DecimalFormat(fmtString);
	else if (java.util.Date.class.isAssignableFrom(klass))
		return new SimpleDateFormat(fmtString);
	return null;
}
}
