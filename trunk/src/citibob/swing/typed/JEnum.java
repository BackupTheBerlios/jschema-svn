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
package citibob.swing.typed;

import java.sql.*;
import citibob.jschema.*;
import citibob.sql.*;
import citibob.util.KeyedModel;

/** For enumerate types... Nullable depends on KeyedModel. */
public class JEnum implements JType
{
	KeyedModel kmodel;
	
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
		return (kmodel.get(o) != null);
	}

}