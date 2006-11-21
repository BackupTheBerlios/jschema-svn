package citibob.objserver;

import java.io.*;
import java.util.*;

public class LimObjOutputStream implements Runnable {
	
LinkedList queue;
ObjectOutputStream out;
IOException exception;			// Stored IOException from consumer thread
Thread thread;					// Consumer thread

/** Creates a new instance of LimObjOutputStream */
public LimObjOutputStream(ObjectOutputStream out)
{
	System.out.println("AA0");
	this.out = out;
	queue = new LinkedList();
	thread = new Thread(this);
	thread.start();
}

public void close() throws IOException
{
	if (thread != null) thread.interrupt();
	if (out != null) {
		out.close();
		out = null;
	}
}
public void flush() throws IOException
{
	if (out != null) {
		out.flush();
	}
}
public void writeObject(Object o) throws IOException
{
	synchronized(queue) {
		if (exception != null) throw exception;
System.out.println("queue length = " + queue.size());
		if (queue.size() > 3) {
System.out.println("Queue too long; throwing IOException.");
			try { close(); } catch(IOException e) {}
			throw new IOException("Queue too long; terminating the stream.");
			// System.out.println("******* Queue too long; dropping");
		}
		queue.add(o);
//System.out.println("writeObject2");
		queue.notify();
//System.out.println("writeObject Done");
	}
}

/** Consume queued objects and send them to the output stream. */
public void run()
{
//System.out.println("AA1");
	while (!Thread.interrupted()) {
		try {
			Object o = null;
//System.out.println("AA3");
			synchronized (queue) {
				if (queue.isEmpty()) {
					queue.wait();
				}
				o = queue.remove(0);
			}
			out.writeObject(o);
		} catch (InterruptedException ie) {
			// Thread.currentThread().interrupt();
			System.out.println("LimObjOutputStream Thread interrupted; exiting now.");
			break;
		} catch (IOException e) {
			// Just give up on IOException; next call will get one.
			try { close(); } catch(IOException ioe) {}
			exception = e;
			break;
		}
	}
//System.out.println("AA2");
	thread = null;
}
// ==============================================================
}
