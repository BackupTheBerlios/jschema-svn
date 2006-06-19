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
import citibob.multithread.*;
import java.util.*;
/**
 *
 * @author fiscrob
 */
public abstract class RealConnPool extends SimpleConnPool
{

LinkedList reserves = new LinkedList();	// Our reserve connections, not being used for now

//ExceptionHandler ehandler;
//
//public SimpleConnectionPool(ExceptionHandler eh)
//{
//	this.ehandler = eh;
//}

/** Get a connection from the pool. */
public Connection checkout() throws SQLException
{
	if (reserves.size() == 0) return create();
	else return (Connection)reserves.removeFirst();
}

/** Return a connection */
public void checkin(Connection c) throws SQLException
{
	if (reserves.size() >= 5) c.close();
	else reserves.addLast(c);
}
public void dispose() {}

//public void doRun(StRunnable r)
//	{ DbRawRunner.run(r, this); }
//public void doRun(DbRunnable r)
//	{ DbRawRunner.run(r, this); }

}
