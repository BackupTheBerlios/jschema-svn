/*
 * FlushedOutputStream.java
 *
 * Created on November 29, 2005, 12:58 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.io;

import java.io.*;

/**
 *
 * @author fiscrob
 */
public class FlushedOutputStream extends FilterOutputStream
{

int n=0;
final int max;

private void addn(int len)
throws IOException
{
	n += len;
	if (n > max) {
System.err.println(n + " Flushing...");
		flush();
	}
}

public void flush()
throws IOException
{
	super.flush();
	n=0;
}

public void write(byte[] b)
throws IOException
{
	super.write(b);
	addn(b.length);
}
public void write(byte[] b, int off, int len)
throws IOException
{
	super.write(b,off,len);
	addn(len);
}
public void write(int b)
throws IOException
{
	super.write(b);
	addn(1);
}

 
/** Creates a new instance of FlushedOutputStream */
public FlushedOutputStream(OutputStream out, int max) {
	super(out);
	this.max = max;
}
	
}
