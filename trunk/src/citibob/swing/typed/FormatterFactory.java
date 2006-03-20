/*
 * FmtterFactory.java
 *
 * Created on March 18, 2006, 7:40 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.swing.typed;

import javax.swing.text.*;

/**
 *
 * @author citibob
 */
public class FormatterFactory extends javax.swing.text.DefaultFormatterFactory
		implements Cloneable
{
	
public Object clone() throws CloneNotSupportedException
	{ return super.clone(); }

public FormatterFactory() { super(); }
            
public  FormatterFactory(DefaultFormatter defaultFmt)
{ super(defaultFmt); }
public  FormatterFactory(DefaultFormatter defaultFmt, DefaultFormatter displayFmt) 
{ super(defaultFmt, displayFmt); }
public  FormatterFactory(DefaultFormatter defaultFmt, DefaultFormatter displayFmt, DefaultFormatter editFmt) 
{ super(defaultFmt, displayFmt, editFmt); }
public  FormatterFactory(DefaultFormatter defaultFmt, DefaultFormatter displayFmt, DefaultFormatter editFmt, DefaultFormatter nullFmt) 	
{ super(defaultFmt, displayFmt, editFmt, nullFmt); }
}
