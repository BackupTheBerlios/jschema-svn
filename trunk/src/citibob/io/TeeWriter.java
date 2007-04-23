package citibob.io;

import java.io.*;

public class TeeWriter extends Writer
{
    Writer[] outs;
    Writer out2;

    public TeeWriter(Writer[] outs)
    {
		this.outs = outs;
    }

    // Override methods of Writer
    public void close()
        throws java.io.IOException
    {
		for (int i=0; i<outs.length; ++i) outs[i].close();
    }

    public void flush()
            throws java.io.IOException
    {
		for (int i=0; i<outs.length; ++i) outs[i].flush();
    }

    // Implementation of Writer's abstract method
    public void write(int b)
            throws java.io.IOException
    {
		for (int i=0; i<outs.length; ++i) outs[i].write(b);
    }
    public void write(char b[], int off, int len) throws IOException {
		for (int i=0; i<outs.length; ++i) outs[i].write(b,off,len);
	}
    public void write(char b[]) throws IOException {
		for (int i=0; i<outs.length; ++i) outs[i].write(b);
	}
	public void write(String str) throws IOException {
		for (int i=0; i<outs.length; ++i) outs[i].write(str);
	}
	public void write(String str, int off, int len) throws IOException {
		for (int i=0; i<outs.length; ++i) outs[i].write(str, off, len);
	}

}