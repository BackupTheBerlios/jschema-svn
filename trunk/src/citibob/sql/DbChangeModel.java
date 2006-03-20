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
package citibob.sql;

import java.sql.*;
import java.util.*;



/**
 *
 * @author citibob
 */
public class DbChangeModel
{
public static interface Listener {
//    /** Something has changed in the terms table. */
//    termsChanged(Statement st) throws SQLException;

//    /** Something has changed in the courses table. */
//    coursesChanged(Statement st) throws SQLException;

    public void tableChanged(Statement st, String table) throws SQLException;
}
// ======================================================
public static class Adapter implements DbChangeModel.Listener {
//    /** Something has changed in the terms table. */
//    termsChanged(Statement st) throws SQLException;

//    /** Something has changed in the courses table. */
//    coursesChanged(Statement st) throws SQLException;

    public void tableChanged(Statement st, String table) throws SQLException
	{}
}
// ======================================================
HashMap tListeners = new HashMap();
//java.util.LinkedList listeners = new java.util.LinkedList();
public void addListener(String table, DbChangeModel.Listener l)
{
	LinkedList llist = (LinkedList)tListeners.get(table);
	if (llist == null) {
		llist = new LinkedList();
		tListeners.put(table, llist);
	}
	llist.add(l);
}
public void removeListener(String table, DbChangeModel.Listener l)
{
	LinkedList llist = (LinkedList)tListeners.get(table);
	if (llist == null) return;
	llist.remove(l);
}

// ======================================================
public void fireTableChanged(Statement st, String table)
throws SQLException
{
	LinkedList listeners = (LinkedList)tListeners.get(table);
	if (listeners == null) return;
	for (java.util.Iterator ii=listeners.iterator(); ii.hasNext(); ) {
		DbChangeModel.Listener l = (DbChangeModel.Listener)ii.next();
		l.tableChanged(st, table);
	}
}
}
