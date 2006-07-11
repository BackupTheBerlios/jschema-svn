/* TimedBufferedFilterWriter.java
 * Created on June 29, 2006, 10:18 AM
 */

package citibob.swing;

import java.awt.event.ActionEvent;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.CharBuffer;

/**
 * @author crowjon
 */
public class TimedBufferedWriter extends FilterWriter implements java.awt.event.ActionListener
{
	char buffer[];
	int index=0;
	
	/** Creates a new instance of TimedBufferedFilterWriter */
	public TimedBufferedWriter(Writer writer)
	{
		super(writer);
		buffer = new char[25000];
		javax.swing.Timer timer = new javax.swing.Timer(3000, this);
		timer.start();
		System.err.println("timer writer delay is 3000 ms");
	}
	
	public TimedBufferedWriter(Writer writer, int millisec_delay, int buffersize)
	{
		super(writer);
		if (buffersize>1000 && buffersize<1000000) // between 1,000 and 1,000,000
		{
			buffer = new char[buffersize];
		}
		else if (buffersize<=1000)
		{
			buffer = new char[1000];
		}
		else if (buffersize>=1000000)
		{
			buffer = new char[1000000];
		}
		
		javax.swing.Timer timer = new javax.swing.Timer(millisec_delay, this);
		timer.start();
		System.err.println("timer writer delay is "+millisec_delay+" ms");
	}

	public void write(int c) throws IOException
	{
		buffer[index++] = (char) c;
		if (index > buffer.length-1)
		{
			flush();
		}
	}

	public void write(String str, int off, int len) throws IOException
	{
		char chars[] = str.toCharArray();
		write(chars, off, len);
	}

	public void write(char[] cbuf, int off, int len) throws IOException
	{
		for (int i=off; i<len; i++) write(cbuf[i]);
	}

	public synchronized void flush()
	{
		try
		{
			out.write(buffer, 0, index);
			out.flush();
			index=0;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public synchronized void actionPerformed(ActionEvent e)
	{
		if (index>0) flush();
	}

}
