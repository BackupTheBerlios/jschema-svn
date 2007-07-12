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
package citibob.sql;

import java.sql.*;
import citibob.multithread.*;

public abstract class SimpleConnPool implements ConnPool
{

//ExceptionHandler ehandler;
//
//public SimpleConnectionPool(ExceptionHandler eh)
//{
//	this.ehandler = eh;
//}

/** Create an actual connection --- used by pool implementations, should not
 * be called by user. */
protected abstract Connection create() throws SQLException;

/** Get a connection from the pool. */
public Connection checkout() throws SQLException
{
	//return new WrapperConn(create(), this);	// Caused inifinte recursion on checkin
	return create();
}

/** Return a connection */
public void checkin(Connection c) throws SQLException
{
	c.close();
}
public void dispose() {}

//public void doRun(StRunnable r)
//	{ DbRawRunner.run(r, this); }
//public void doRun(DbRunnable r)
//	{ DbRawRunner.run(r, this); }

}
