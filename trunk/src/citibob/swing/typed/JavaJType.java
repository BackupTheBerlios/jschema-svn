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
package citibob.swing.typed;

import java.sql.*;

/** General JType wrapper for Java classes */
public class JavaJType implements JType
{	
	boolean nullable = true;
	Class klass;
	
	public JavaJType(Class klass, boolean nullable) {
		this.klass = klass;
		this.nullable = nullable;
	}
	public JavaJType(Class klass) { this(klass, true); }
	
	/** Java class used to represent this type */
	public Class getObjClass()
		{ return klass; }

	public boolean isInstance(Object o)
		{ return (klass.isInstance(o) || (nullable && o == null)); }

}
