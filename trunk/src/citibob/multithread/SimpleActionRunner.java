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

import java.sql.*;
import citibob.sql.*;

/**
 * Just run the CBRunnables in the current thread.  Route exceptions to the ExpHandler.
 * @author citibob
 */
public class SimpleActionRunner implements ActionRunner
{

RawRunner raw;
ExpHandler eh;

public SimpleActionRunner(RawRunner raw, ExpHandler eh)
{
	this.raw = raw;
	this.eh = eh;
}
public SimpleActionRunner(ConnPool pool, ExpHandler eh)
{
	this(new DefaultRawRunner(pool), eh);
}


public void doRun(CBRunnable rr)
{
	Throwable e = raw.doRun(rr);
	if (e != null && eh != null) eh.consume(e);
}

}
