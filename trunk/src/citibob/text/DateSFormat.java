/*
 * DateSFormat.java
 *
 * Created on November 7, 2007, 10:16 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.text;

import java.util.*;
import java.text.*;

/**
 *
 * @author citibob
 */
public class DateSFormat extends FormatSFormat
{

TimeZone displayTZ;

/** Creates a new instance of DateSFormat */
public DateSFormat(DateFormat fmt, String nullText)
{
	super(fmt, nullText);
	displayTZ = fmt.getTimeZone();
}
public DateSFormat(DateFlexiFormat fmt, String nullText)
{
	super(fmt, nullText);
	displayTZ = fmt.getTimeZone();
}

/** Convenience */
public DateSFormat(String[] sfmts, String nullText, TimeZone displayTZ)
{
	super(new DateFlexiFormat(sfmts, displayTZ), nullText);
}

public TimeZone getDisplayTZ() { return displayTZ; }


}
