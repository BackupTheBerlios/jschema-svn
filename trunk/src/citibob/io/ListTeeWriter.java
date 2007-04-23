package citibob.io;

import java.io.*;
import java.util.*;

public class ListTeeWriter extends Writer
{
    List<Writer> outs;
    Writer out2;

    public ListTeeWriter(List<Writer> outs)
    {
		this.outs = outs;
    }

    // Override methods of Writer
    public void close()
        throws java.io.IOException
    {
		for (Writer w : outs) w.close();
    }

    public void flush()
            throws java.io.IOException
    {
		for (Writer w : outs) w.flush();
    }

    // Implementation of Writer's abstract method
    public void write(int b)
            throws java.io.IOException
    {
		for (Writer w : outs) w.write(b);
    }
    public void write(char b[], int off, int len) throws IOException {
		for (Writer w : outs) w.write(b,off,len);
	}
    public void write(char b[]) throws IOException {
		for (Writer w : outs) w.write(b);
	}
	public void write(String str) throws IOException {
		for (Writer w : outs) w.write(str);
	}
	public void write(String str, int off, int len) throws IOException {
		for (Writer w : outs) w.write(str, off, len);
	}

}