/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The XAMJ Project

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: info@xamjwg.org
*/

package org.xamjwg.html.gui;

import org.xamjwg.html.HtmlParserContext;
import org.xamjwg.html.HttpRequest;

/**
 * The <code>NullHtmlParserContext</code> is a dummy implementation
 * of the {@link org.xamjwg.html.HtmlParserContext} interface.
 * @author J. H. S.
 */
public class NullHtmlParserContext implements HtmlParserContext {
	public NullHtmlParserContext() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.xamjwg.html.HtmlContext#createHttpRequest()
	 */
	public HttpRequest createHttpRequest() {
//		return new SimpleHttpRequest(this);
		return null;
	}	

	public String getCookie() {
		return "";
	}
	
	public void setCookie(String cookie) {
		this.warn("setCookie(): Not overridden");
	}
	
	public void warn(String message, Throwable throwable) {
		System.out.println("WARN: " + message);
		throwable.printStackTrace();
	}
	
	public void error(String message, Throwable throwable) {
		System.out.println("ERROR: " + message);
		throwable.printStackTrace();		
	}
	
	public void warn(String message) {
		System.out.println("WARN: " + message);
	}
	
	public void error(String message) {
		System.out.println("ERROR: " + message);
	}	
}

