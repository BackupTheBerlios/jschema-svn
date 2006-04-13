package citibob.mail;

import java.io.*;

/** Stuff for messing with (system-dependent) file permissions.  This one works on Linux. */
public class FilePermissions
{
	/** Sets so that only the user can do anything with the file.  chmod go-rwx xxx */
	public static void setPrivate(String fname) throws IOException
	{
		Process p = Runtime.getRuntime().exec("chmod go-rwx " + fname);
		try {
			p.waitFor();
		} catch(InterruptedException e) {}
	}
}
