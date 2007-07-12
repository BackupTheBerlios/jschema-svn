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

import java.io.*;
import java.net.*;
import java.sql.*;

import javax.servlet.*;
import javax.servlet.http.*;

/** 
 *
 * @author citibob
 * @version 
 */
public abstract class DbServlet extends HttpServlet {
   
    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
	
	

	public abstract void dbRequest(HttpServletRequest request, HttpServletResponse response,
		HttpSession sess, Statement st) throws Exception;
	
	/** Convenience method. */
	public void redirect(HttpServletRequest request, HttpServletResponse response, String path)
	throws IOException
	{
		response.sendRedirect(request.getContextPath()+ path);
	}
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
		try {
			response.setContentType("text/html;charset=UTF-8");

			HttpSession sess = request.getSession();
			Statement st = (Statement)request.getAttribute("st");
			//PrintWriter out = response.getWriter();

			dbRequest(request, response, sess, st);
		} catch(Exception e) {
			ErrorOutput.sendProcessingError(e, response);
		}
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** Handles the HTTP <code>GET</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** Handles the HTTP <code>POST</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** Returns a short description of the servlet.
    */
    public String getServletInfo() {
        return "DbServlet Instance";
    }
    // </editor-fold>
}
