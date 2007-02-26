/*
 * JDateSFormatter.java
 *
 * Created on February 26, 2007, 1:23 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.text;


import citibob.swing.typed.*;
import java.util.*;
import java.text.*;

/**
 *
 * @author citibob
 */
public class JDateSFormatter extends javax.swing.text.DateFormatter implements SFormatter
{
    /**
     * This is shorthand for
     * <code>new DateFormatter(DateFormat.getDateInstance())</code>.
     */
    public JDateSFormatter() { super(); }

    /**
     * Returns a DateFormatter configured with the specified
     * <code>Format</code> instance.
     *
     * @param format Format used to dictate legal values
     */
    public JDateSFormatter(DateFormat format) { super(format); }

	
//public static DateFormat newDateFormat(TimeZone tz, String fmt)
//{
//	DateFormat dff;
//	if (fmt == null) dff = DateFormat.getDateInstance();
//	else dff = new SimpleDateFormat(fmt);
//	if (tz != null) dff.setTimeZone(tz);
//	return dff;
//}
//
public JDateSFormatter(TimeZone tz, String fmt)
{
	this(citibob.swing.typed.JDateSwinger.newDateFormat(tz, fmt));
}
public JDateSFormatter(String fmt)
{
	this(null, fmt);
}

	
}
