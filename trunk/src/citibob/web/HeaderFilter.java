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

import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/** 
 *
 * @author  citibob
 * @version 
 */

public class HeaderFilter implements Filter {

	// The filter configuration object we are associated with.  If
	// this value is null, this filter instance is not currently
	// configured. 
	private FilterConfig filterConfig = null;
	private String headerJsp;
	private String footerJsp;

	/**
	 *
	 * @param request The servlet request we are processing
	 * @param result The servlet response we are creating
	 * @param chain The filter chain we are processing
	 *
	 * @exception IOException if an input/output error occurs
	 * @exception ServletException if a servlet error occurs
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {
		// Do our own filter URL matching, since the URL matching
		// in Tomcat seems to be buggy.
		HttpServletRequest hr = (HttpServletRequest)request;
		String uri = hr.getRequestURI();
		if (!(uri.endsWith("/") || uri.endsWith(".jsp"))) {
			// Forward request down chain without worrying about it.
			chain.doFilter(request, response);
			return;
		}

//		// Include the header
//		RequestDispatcher header = request.getRequestDispatcher("/header.jsp");
//		header.include(request, response);

		
		// HACK: make header.include() use response.getWriter()
		// instead of response.getOutputStream()
		response.getWriter();

		RequestDispatcher header = request.getRequestDispatcher(headerJsp);
		header.include(request, response);

		Throwable problem = null;
		try {
			chain.doFilter(request, response);
		}
		catch(Throwable t) {
			//
			// If an exception is thrown somewhere down the filter chain,
			// we still want to execute our after processing, and then
			// rethrow the problem after that.
			//
			problem = t;
			t.printStackTrace();
		}
		RequestDispatcher footer = request.getRequestDispatcher(footerJsp);
		footer.include(request, response);


		//
		// If there was a problem, we want to rethrow it if it is
		// a known type, otherwise log it.
		//
		if (problem != null) {
			if (problem instanceof ServletException) throw (ServletException)problem;
			if (problem instanceof IOException) throw (IOException)problem;
			sendProcessingError(problem, response);
		}
System.out.println("HeaderFilter Finish");
	}

	
	/**
	 * Return the filter configuration object for this filter.
	 */
	public FilterConfig getFilterConfig() {
		return (this.filterConfig);
	}


	/**
	 * Set the filter configuration object for this filter.
	 *
	 * @param filterConfig The filter configuration object
	 */
	public void setFilterConfig(FilterConfig filterConfig) {

		this.filterConfig = filterConfig;
	}

	/**
	 * Destroy method for this filter 
	 *
	 */
	public void destroy() { 
	}


	/**
	 * Init method for this filter 
	 *
	 */
	public void init(FilterConfig filterConfig) { 
		headerJsp = filterConfig.getInitParameter("headerJsp");
		if (headerJsp == null) headerJsp = "/header.jsp";
		footerJsp = filterConfig.getInitParameter("footerJsp");
		if (footerJsp == null) footerJsp = "/footer.jsp";
		this.filterConfig = filterConfig;
		if (filterConfig != null) {
			if (debug) { 
				log("MyFilter:Initializing filter");
			}
		}
	}

	/**
	 * Return a String representation of this object.
	 */
	public String toString() {

		if (filterConfig == null) return ("HeaderFilter()");
		StringBuffer sb = new StringBuffer("HeaderFilter(");
		sb.append(filterConfig);
		sb.append(")");
		return (sb.toString());

	}



	private void sendProcessingError(Throwable t, ServletResponse response) {
		
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
			}
				
			catch(Exception ex){ }
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

	public void log(String msg) {
		filterConfig.getServletContext().log(msg); 
	}

	private static final boolean debug = true;
}
