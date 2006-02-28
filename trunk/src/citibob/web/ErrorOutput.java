/*
JSchema: library for GUI-based database applications
This file Copyright (c) 2006 by Robert Fischer

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/
package citibob.web;

/**
 *
 * @author citibob
 */

import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;

import citibob.sql.*;
import java.sql.*;

public class ErrorOutput {

public static void sendProcessingError(Throwable t, ServletResponse response) {
	
	String stackTrace = getStackTrace(t); 

	if(stackTrace != null && !stackTrace.equals("")) {

	    try {
		    
			response.setContentType("text/html");
			PrintStream ps = new PrintStream(response.getOutputStream());
			PrintWriter pw = new PrintWriter(ps); 
			pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

			// PENDING! Localize this for next official release
			pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n"); 
			pw.print(stackTrace); 
			pw.print("</pre></body>\n</html>"); //NOI18N
			pw.close();
			ps.close();
			response.getOutputStream().close();;
	    } catch(Exception ex){ }
	}
	else {
	    try {
			PrintStream ps = new PrintStream(response.getOutputStream());
			t.printStackTrace(ps);
			ps.close();
			response.getOutputStream().close();;
	    }
	    catch(Exception ex){ }
	}
}

public static String getStackTrace(Throwable t) {

	String stackTrace = null;
	    
	try {
	    StringWriter sw = new StringWriter();
	    PrintWriter pw = new PrintWriter(sw);
	    t.printStackTrace(pw);
	    pw.close();
	    sw.close();
	    stackTrace = sw.getBuffer().toString();
	}
	catch(Exception ex) {}
	return stackTrace;
}
	
}
