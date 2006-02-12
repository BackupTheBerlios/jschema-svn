package citibob.io;

import java.io.*;

public class TeeOutputStream extends OutputStream
{
    OutputStream[] outs;
    OutputStream out2;

    public TeeOutputStream(OutputStream[] outs)
    {
		this.outs = outs;
    }

    // Override methods of OutputStream
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

    // Implementation of Outputstream's abstract method
    public void write(int b)
            throws java.io.IOException
    {
		for (int i=0; i<outs.length; ++i) outs[i].write(b);
    }
    public void write(byte b[], int off, int len) throws IOException {
		for (int i=0; i<outs.length; ++i) outs[i].write(b,off,len);
	}
    public void write(byte b[]) throws IOException {
		for (int i=0; i<outs.length; ++i) outs[i].write(b);
	}

}