/*
 * SigFormat.java
 *
 * Created on August 31, 2006, 9:38 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.text;

import ucar.unidata.util.*;
import java.text.*;

/**
 * Used for formatting doubles to a specified number of significant digits.
 * Only use the format() method.  Most other methods of NumberFormat() don't work.
 * @author fiscrob
 */
public class SigFormat
{

int min_sigfig;
int width;
	
/** Creates a new instance of SigFormat */
public SigFormat(int min_sigfig, int width)
{
	this.min_sigfig = min_sigfig;
	this.width = width;
}

public SigFormat(int min_sigfig) { this(min_sigfig, -1); }

public String format(double x)
{
	return ucar.unidata.util.Format.d(x, min_sigfig, width);
}

}
