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

import org.mozilla.javascript.*;
import java.lang.reflect.*;

public class JavaFunctionObject extends ScriptableObject implements Function {
	private final String className;
	private final Method method;
		
	public JavaFunctionObject(String name, Method method) {
		super();
		className = name;
		this.method = method;
	}

	public String getClassName() {
		return this.className;
	}

	public Object call(Context cx, Scriptable scope, Scriptable thisObj,
			Object[] args) {
		try {
			JavaObjectWrapper jcw = (JavaObjectWrapper) thisObj;
			Object[] actualArgs = args == null ? new Object[0] : new Object[args.length];
			for(int i = 0; i < actualArgs.length; i++) {
				Object arg = args[i];
				actualArgs[i] = arg instanceof JavaObjectWrapper ? ((JavaObjectWrapper) arg).getJavaObject() : arg;
			}
			Object raw = this.method.invoke(jcw.getJavaObject(), actualArgs);
			return JavaScript.getJavascriptObject(raw, scope);
		} catch(Exception err) {
			throw new IllegalStateException(err.getMessage());
		}
	}

	public Scriptable construct(Context cx, Scriptable scope, Object[] args) {
		throw new UnsupportedOperationException();
	}
}
