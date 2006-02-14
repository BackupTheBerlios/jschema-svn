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
 * Just run the CBRunnables in the current thread.  Also used as a basis
 * for running the polymorphic runnable types.
 * @author citibob
 */
public class DefaultRawRunner implements RawRunner
{

ConnPool pool;

public DefaultRawRunner(ConnPool pool)
{
	this.pool = pool;
}

public Throwable run(ERunnable r)
{
	try {
		r.run();		
	} catch(Throwable e) {
		return e;
//		eh.consume(e);
	}
	return null;
}

public Throwable run(StRunnable r)
{
	Throwable ret = null;
	Statement st = null;
	Connection dbb = null;
	try {
		dbb = pool.checkout();
		st = dbb.createStatement();
		r.run(st);
	} catch(Throwable e) {
		ret = e;
//		eh.consume(e);
	} finally {
		try {
			if (st != null) st.close();
		} catch(SQLException se) {}
		try {
			pool.checkin(dbb);
		} catch(SQLException se) {}
	}
	return ret;
}
	
public Throwable run(DbRunnable r)
{
	Connection dbb = null;
	Throwable ret = null;
	try {
		dbb = pool.checkout();
		r.run(dbb);
	} catch(Throwable e) {
		ret = e;
	} finally {
		try {
			pool.checkin(dbb);
		} catch(SQLException se) {}
	}
	return ret;
}

public Throwable doRun(CBRunnable rr)
{
	if (rr instanceof ERunnable) {
		ERunnable r = (ERunnable)rr;
		return run(r);
	}
	if (rr instanceof StRunnable) {
		StRunnable r = (StRunnable)rr;
		return run(r);
	}
	if (rr instanceof DbRunnable) {
		DbRunnable r = (DbRunnable)rr;
		return run(r);
	}
	return new ClassCastException("CBRunnable of class " + rr.getClass() + " is not one of ERunnable, StRunnable or DbRunnable");
}

}
