package citibob.io;

import java.io.*;
import java.util.*;

public class GrepWriter extends AbstractLineWriter
{

void processLine()
{
	//if (line.length() == 0) return;
	String s = line.toString();
	Listener l = (Listener)patterns.get(s);
	if (l != null) l.matched(s);
//	System.err.println(">>>" + line + "<<<");
	line.delete(0, line.length());
}

// ----------------------------------------------------------------
public static interface Listener {
	public void matched(String line);
}
TreeMap patterns = new TreeMap();
public void setPattern(String pat, Listener l)
{
	if (l == null) patterns.remove(pat);
	else patterns.put(pat,l);
}
// ----------------------------------------------------------------
public static void main(String[] args) throws Exception
{
	GrepWriter gw = new GrepWriter();
	gw.setPattern("Hello There", new Listener() { public void matched(String line) {
		System.out.println("Found HelloThere!!!");
	}});
	gw.write("Hello ");
	gw.write("There\nHow are you today?");
	gw.write("\nI like to go");
	gw.close();
}
}
