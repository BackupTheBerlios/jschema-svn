/*
 * AbstractLineWriter.java
 *
 * Created on December 6, 2005, 11:13 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.io;

import java.io.*;
import java.util.*;

/**
 * Buffers one line at a time
 * @author fiscrob
 */
public class LineBufferWriter extends AbstractLineWriter
{

Writer out;

public LineBufferWriter(Writer out)
{
	this.out = out;
}
public void processLine() throws IOException
{
	//if (line.length() == 0) return;
	out.write(line.toString());
	out.write('\n');
	line.delete(0, line.length());
}
}

