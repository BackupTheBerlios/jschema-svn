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
/*
 * SqlTypeMap.java
 *
 * Created on March 15, 2006, 9:22 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.swing.typed;

import citibob.sql.*;
import citibob.types.JType;
import citibob.types.JavaJType;
import java.util.*;
import citibob.text.*;
import citibob.swing.table.*;

/**
 * Maps SqlType objects to various formatters, etc. required by graphical parts
 * of system.  Used to automatically construct GUIs appropriate for a schema.
 * @author citibob
 */
public abstract class SwingerMap
{
	public Swinger newSwinger(JType t)
		{ return newSwinger(t, null); }
	
	/** Allows for special column names to be hard-wired with specific Swingers,
	based on their name, not JType. */
	public abstract Swinger newSwinger(JType t, String colName);

	public Swinger newSwinger(Class klass) { return newSwinger(new JavaJType(klass)); }
	public TypedWidget newWidget(JType t) { return newSwinger(t).newWidget(); }
	public TypedWidget newWidget(Class klass) { return newSwinger(klass).newWidget(); }
	public abstract Swinger[] newSwingers(JTypeTableModel model);
	public abstract Swinger[] newSwingers(JTypeTableModel model, String[] scol, Swinger[] sfmt);
}
