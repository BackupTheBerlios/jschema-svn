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

public interface ConnPool {

/** Get a connection from the pool. */
Connection checkout() throws SQLException;

/** Return a connection */
void checkin(Connection c) throws SQLException;

/** Free up resources when you'return done with this conn pool. */
public void dispose();

//public void doRun(StRunnable r);
////	{ DefaultRawRunner.run(r, this); }
//public void doRun(DbRunnable r);
////	{ DefaultRawRunner.run(r, this); }

}
