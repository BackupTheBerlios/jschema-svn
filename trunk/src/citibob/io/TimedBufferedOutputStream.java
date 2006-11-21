package citibob.io;

import java.awt.event.ActionEvent;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class TimedBufferedOutputStream extends FilterOutputStream implements java.awt.event.ActionListener
{
	byte buffer[];
	int index=0;
	
	/** Creates a new instance of TimedBufferedFilterOutputStream */
	public TimedBufferedOutputStream(OutputStream writer)
	{
		super(writer);
		buffer = new byte[25000];
		javax.swing.Timer timer = new javax.swing.Timer(3000, this);
		timer.start();
		System.err.println("timer writer delay is 3000 ms");
	}
	
	public TimedBufferedOutputStream(OutputStream writer, int millisec_delay, int buffersize)
	{
		super(writer);
		if (buffersize>1000 && buffersize<1000000) // between 1,000 and 1,000,000
		{
			buffer = new byte[buffersize];
		}
		else if (buffersize<=1000)
		{
			buffer = new byte[1000];
		}
		else if (buffersize>=1000000)
		{
			buffer = new byte[1000000];
		}
		
		javax.swing.Timer timer = new javax.swing.Timer(millisec_delay, this);
		timer.start();
		System.err.println("timer writer delay is "+millisec_delay+" ms");
	}

	public void write(int b) throws IOException
	{
		buffer[index++] = (byte)b;
		if (index > buffer.length-1)
		{
			flush();
		}
	}

	public void write(byte[] cbuf, int off, int len) throws IOException
	{
		for (int i=off; i<len; i++) write(cbuf[i]);
	}

	public void write(byte[] cbuf) throws IOException
		{ write(cbuf, 0, cbuf.length); }
	
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
