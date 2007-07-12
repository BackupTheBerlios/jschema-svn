/*
JSchema: library for GUI-based database applications
This file Copyright (c) 2006-2007 by Robert Fischer

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package citibob.web;
/*
 * ConnectionFilter.java
 *
 * Created on October 14, 2005, 10:53 PM
 */
 
import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;

import citibob.sql.*;
import java.sql.*;

/** 
 *
 * @author  citibob
 * @version 
 */



public class DbFilter implements Filter {

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

	protected ConnPool pool;	// Must be set by subclassed constructor!!!
	
    public DbFilter() {}

    /**
     *
     * @param request The servlet request we are processing
     * @param result The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
public void doFilter(ServletRequest request,
ServletResponse response, FilterChain chain)
throws IOException, ServletException
{
//System.out.println("ConnectionFilter Start");
	Throwable problem = null;

	Connection con = null;
	Statement st = null;
	try {
		con = pool.checkout();
		st = con.createStatement();
		request.setAttribute("dbb", con);
		request.setAttribute("st", st);
//System.out.println("st = " + st);
		chain.doFilter(request, response);
	} catch(Throwable t) {
	    problem = t;
	    // t.printStackTrace(new PrintStream(response.getOutputStream()));
	} finally {
		try {
			st.close();
		} catch(Throwable e) {}
		
		try {
			pool.checkin(con);
		} catch(Throwable e) {}
	}

	//
	// If there was a problem, we want to rethrow it if it is
	// a known type, otherwise log it.
	//
	if (problem != null) {
	    if (problem instanceof ServletException) throw (ServletException)problem;
	    if (problem instanceof IOException) throw (IOException)problem;
	    ErrorOutput.sendProcessingError(problem, response);
	}
System.out.println("ConnectionFilter Finish");
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
public void init(FilterConfig filterConfig)
{
	this.filterConfig = filterConfig;
	if (filterConfig != null) {
	    if (debug) { 
		log("ConnectionFilter:Initializing filter");
	    }
	}
}

    /**
     * Return a String representation of this object.
     */
    public String toString() {

	if (filterConfig == null) return ("ConnectionFilter()");
	StringBuffer sb = new StringBuffer("ConnectionFilter(");
	sb.append(filterConfig);
	sb.append(")");
	return (sb.toString());

    }





    public void log(String msg) {
	filterConfig.getServletContext().log(msg); 
    }

    private static final boolean debug = true;
}
