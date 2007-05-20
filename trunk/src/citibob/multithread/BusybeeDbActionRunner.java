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
package citibob.multithread;

import citibob.swing.*;
import java.sql.*;
import citibob.sql.*;
import java.awt.*;
import javax.swing.*;

/**
 * Just run the CBRunnables in the current thread.  Route exceptions to the ExpHandler.
 * @author citibob
 */
public class BusybeeDbActionRunner implements SwingActionRunner
{

DbRawRunner raw;
ExpHandler eh;

//public ConnPool getPool() { return raw.getPool(); }

public BusybeeDbActionRunner(DbRawRunner raw, ExpHandler eh)
{
	this.raw = raw;
	this.eh = eh;
}
public BusybeeDbActionRunner(ConnPool pool, ExpHandler eh)
{
	this(new DbRawRunner(pool), eh);
}
public BusybeeDbActionRunner(ConnPool pool)
{
	this(new DbRawRunner(pool), new SimpleExpHandler());
}

public void doRun(Component component, CBRunnable rr)
{
	SwingUtil.setCursor(component, Cursor.WAIT_CURSOR);
	Throwable e = raw.doRun(rr);
	SwingUtil.setCursor(component, Cursor.DEFAULT_CURSOR);
	if (e != null && eh != null) eh.consume(e);
}

}
