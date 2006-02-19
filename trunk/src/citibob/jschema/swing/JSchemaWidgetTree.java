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
package citibob.jschema.swing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import citibob.exception.*;
import citibob.jschema.*;
import citibob.sql.*;
import citibob.multithread.*;

/** Static utilities for dealing with an entire tree of widgets (widget tree / widget hierarchy). */
public class JSchemaWidgetTree
{

/** Binds all components in a widget tree to a (Schema, RowModel), if they implement SchemaRowBinder. */
public static void bindToSchemaRow(Component c, SchemaRowModel bufRow)
{
	// Take care of yourself
	if (c instanceof SchemaRowModelBindable) {
		SchemaRowModelBindable rmb = (SchemaRowModelBindable)c;
		if (rmb.getColName() != null)
			((SchemaRowModelBindable)c).bind(bufRow);
	}

	// Take care of your children
	if (c instanceof Container) {
	    Component[] child = ((Container)c).getComponents();
	    for (int i = 0; i < child.length; ++i) {
			bindToSchemaRow(child[i], bufRow);
		}
	}
}


///** Binds all components in a widget tree to an ActionRunner.  Gives them an opportunity
// * to re-query the database on an as-neede basis. */
//public static void bindToRunner(Component c, ActionRunner runner)
//throws java.sql.SQLException
//{
//	// Take care of yourself
//	if (c instanceof DbBindable) {
//		((DbBindable)c).bind(pool);
//	}
//
//	// Take care of your children
//	if (c instanceof Container) {
//	    Component[] child = ((Container)c).getComponents();
//	    for (int i = 0; i < child.length; ++i) {
//			bindToPool(child[i], pool);
//		}
//	}
//}

/** Allows components to initialize themselves using a Statement object,
 * if they implement StatementInitializable.  Components may use the Statement,
   but may NOT keep it for later use! */
public static void initWithStatement(Component c, java.sql.Statement st)
throws java.sql.SQLException
{
	// Take care of yourself
	if (c instanceof StatementInitializable)
		((StatementInitializable)c).init(st);

	// Take care of your children
	if (c instanceof Container) {
	    Component[] child = ((Container)c).getComponents();
	    for (int i = 0; i < child.length; ++i) {
			initWithStatement(child[i], st);
		}
	}
}

}
