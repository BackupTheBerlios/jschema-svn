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
package citibob.types;

import java.sql.*;
import citibob.jschema.*;
import citibob.sql.*;
import citibob.types.KeyedModel;

/** For enumerate types... Nullable depends on KeyedModel. */
public class JEnum implements JType
{
	protected KeyedModel kmodel;
	
	/** nullText = string to use for null value, or else <null> if this is not nullable. */
	public JEnum(KeyedModel kmodel) {
		this.kmodel = kmodel;
	}
	/** Shortcut, equal to new JEnum(new KeyedModel(objs)); */
	public JEnum(Object[] objs) {
		this(new KeyedModel(objs));
	}
	public KeyedModel getKeyedModel() { return kmodel; }
	
	/** Java class used to represent this type */
	public Class getObjClass()
		{ return Object.class; }

	public boolean isInstance(Object o)
	{
		return (kmodel.containsKey(o)); //get(o) != null);
	}

}
