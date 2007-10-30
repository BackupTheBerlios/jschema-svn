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
package org.xamjwg.js;

import org.mozilla.javascript.Scriptable;

public class JavaScript {
	/**
	 * Returns an object that may be used by
	 * the Javascript engine.
	 * @param raw
	 * @return
	 */
	public static Object getJavascriptObject(Object raw, Scriptable scope) {
		if(raw instanceof String || raw instanceof Scriptable) {
			return raw;
		}
		else if(raw == null) {
			return null;
		}
		else if(raw.getClass().isPrimitive()) {
			return raw;
		}
		else {
			Class javaClass = raw.getClass();
			JavaClassWrapper wrapper = JavaClassWrapperFactory.getInstance().getClassWrapper(javaClass);
			JavaObjectWrapper jow = new JavaObjectWrapper(wrapper, raw);
			jow.setParentScope(scope);
			return jow;
		}
	}
}
