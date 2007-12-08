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

import citibob.app.App;
import java.sql.*;
import citibob.multithread.*;
import java.util.*;

/**
 *
 * @author citibob
 */
public class SqlBatchSet implements SqlRunner {

HashMap map;				// Map of values to pass from from one SqlRunnable to the next
SqlBatch batch;			// The batch we're constructing

// ========================================================================
// Setting up the batch

public SqlBatchSet()
{
	// Set up initial batch
	batch = new SqlBatch();
	map = new HashMap();
}
public SqlRunner next() { return this; }

public void execSql(String sql)
	{ execSql(sql, null); }

/** Adds SQL to the batch --- multiple ResultSets returned, and it can create
 additional SQL as needed. */
public void execSql(String sql, SqlRunnable rr)
	{ batch.execSql(sql, rr); }

public void execUpdate(UpdRunnable r)
	{ batch.execSql("", r); }

// ========================================================================
/** While SqlRunnables are running --- store a value for retrieval by later SqlRunnable. */
public void put(Object key, Object val)
{ map.put(key, val); }

/** While SqlRunnables are running --- retrieve a previously stored value. */
public Object get(Object key)
{ return map.get(key); }

// ---------------------------------------

public void exec(App app)
{
	try {
		exec(app.getPool());
	} catch(Exception e) {
		app.getExpHandler().consume(e);
	}
}
public void exec(ConnPool pool) throws Exception
{
	if (batch.size() == 0) return;
	Throwable ret = null;
	Statement st = null;
	Connection dbb = null;
	try {
		dbb = pool.checkout();
		st = dbb.createStatement();
		this.exec(st);
	} finally {
		try {
			if (st != null) st.close();
		} catch(SQLException se) {}
		try {
			pool.checkin(dbb);
		} catch(SQLException se) {}
	}
}

/** Recursively executes this batch and all batches its execution creates. */
public void exec(Statement st) throws Exception
{
	if (batch.size() == 0) return;


	for (;;) {
		// Update to next batch...
		SqlBatch curBatch = batch;
		batch = new SqlBatch();
	
		curBatch.execOneBatch(st, this);
		if (batch.size() == 0) break;
	}
}

}

