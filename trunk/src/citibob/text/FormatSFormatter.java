/*
 * FormatSFormatter.java
 *
 * Created on February 26, 2007, 12:52 AM
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
public class FormatSFormatter implements SFormatter
{
	Format fmt;
	
	/** Creates a new instance of FormatSFormatter */
	public FormatSFormatter(Format fmt)
	{
		this.fmt = fmt;
	}
	public Object  stringToValue(String text)  throws java.text.ParseException
	{
		return fmt.parseObject(text);
	}
	public String  valueToString(Object value) throws java.text.ParseException
	{
		return fmt.format(value);
	}
	
}
