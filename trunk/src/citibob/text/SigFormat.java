/*
 * SigFormat.java
 *
 */

package citibob.text;

import ucar.unidata.util.*;
import java.text.*;

/**
 * Used for formatting doubles to a specified number of significant digits.
 * Only use the format() method.  Most other methods of NumberFormat() don't work.
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
