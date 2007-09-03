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
package citibob.multithread;

import java.sql.*;
import citibob.sql.*;

/**
 * Just run the CBRunnables in the current thread.  Also used as a basis
 * for running the polymorphic runnable types.
 * @author citibob
 */
public class DbRawRunner implements RawRunner
{

ConnPool pool;

public ConnPool getPool() { return pool; }
public DbRawRunner(ConnPool pool)
{
	this.pool = pool;
}

public static Throwable run(ERunnable r)
{
	try {
		r.run();		
	} catch(Throwable e) {
		return e;
//		eh.consume(e);
	}
	return null;
}

public static Throwable run(StRunnable r, ConnPool pool)
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
	
public static Throwable run(BatchRunnable r, ConnPool pool)
{
	SqlBatch batch = new SqlBatch();
	try {
		r.run(batch);
		batch.exec(pool);
	} catch(Throwable e) {
		return e;
	}
	return null;
}

//public static Throwable run(BatchRunnable r, ConnPool pool)
//{
//	Throwable ret = null;
//	Statement st = null;
//	Connection dbb = null;
//	try {
//		SqlBatch batch = new SqlBatch();
//		r.run(batch);
//		dbb = pool.checkout();
//		st = dbb.createStatement();
//		batch.exec(st);
//	} catch(Throwable e) {
//		ret = e;
////		eh.consume(e);
//	} finally {
//		try {
//			if (st != null) st.close();
//		} catch(SQLException se) {}
//		try {
//			pool.checkin(dbb);
//		} catch(SQLException se) {}
//	}
//	return ret;
//}

public static Throwable run(DbRunnable r, ConnPool pool)
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
		return run(r, pool);
	}
	if (rr instanceof DbRunnable) {
		DbRunnable r = (DbRunnable)rr;
		return run(r, pool);
	}
	if (rr instanceof BatchRunnable) {
		BatchRunnable r = (BatchRunnable)rr;
		return run(r, pool);
	}
	return new ClassCastException("CBRunnable of class " + rr.getClass() + " is not one of ERunnable, StRunnable or DbRunnable");
}

}
