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
/*
 * SqlBatch.java
 *
 * Created on August 28, 2007, 9:14 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
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
public class SqlBatch implements SqlRunner {

// Used while building query
StringBuffer sqlBuf = new StringBuffer();
List<SqlRunnable> handlers = new ArrayList();

// Used while interpreting results
boolean inExec = false;
HashMap map;				// Map of values to pass from from one SqlRunnable to the next
SqlBatch nextBatch;			// The batch we're constructing for the next set of stuff
int nextCurrent = Statement.CLOSE_ALL_RESULTS;
List<ResultSet> xrss = new ArrayList();

/** Creates a new instance of SqlBatch */
public SqlBatch() {
}

public int size() { return handlers.size(); }




// ========================================================================
// Setting up the batch

public void execSql(String sql)
	{ execSql(sql, null); }

/** Adds SQL to the batch --- multiple ResultSets returned, and it can create
 additional SQL as needed. */
public void execSql(String sql, SqlRunnable rr)
{
if (sql != null && sql.endsWith("FROM persons")) {
	System.out.println("hoi");
}
	if (inExec) throw new IllegalStateException("Cannot use execSql() or execUpdate() while executing the SQL batch.");
	sqlBuf.append(sql);
	sqlBuf.append(";\n select 'hello' as __divider__;\n");
	handlers.add(rr);
}

public void execUpdate(UpdRunnable r)
{
	execSql("", r);
}

// ========================================================================
// Running the batch
public SqlBatch next() { return nextBatch; }
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
	if (size() == 0) return;
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
	if (size() == 0) return;
	map = new HashMap();
	inExec = true;
	exec(this, st);
	inExec = false;
}

// ---------------------------------------
private static void exec(SqlBatch batch, Statement st) throws Exception
{
	for (;;) {
		batch.execOneBatch(st);
		if (batch.next().size() == 0) return;
		batch.next().map = batch.map;		// Transfer values over
		batch = batch.next();
	}
}

/** Execute the SQL batch; puts any new queries in "nextBatch" */
private void execOneBatch(Statement st) throws Exception
{
	nextBatch = new SqlBatch();
	String sql = sqlBuf.toString();
System.out.println("Executing batch with " + size() + " segments: \n" + sql +
"=================================================");
	st.execute(sql);

	ResultSet rs;
	Iterator<SqlRunnable> curHandler = handlers.iterator();
	for (int n=0;; ++n,st.getMoreResults(nextCurrent)) {
		nextCurrent = Statement.KEEP_CURRENT_RESULT;
		rs = st.getResultSet();
		if (rs == null) {
			// Not an update or a select --- we're done with all result sets
			if (st.getUpdateCount() == -1) break;
			// It was an update --- go on to next result set
			continue;
		}

		// See if this is a divider
		ResultSetMetaData meta = rs.getMetaData();
		if (meta.getColumnCount() > 0 && "__divider__".equals(meta.getColumnName(1))) {
			rs.close();

			// It was --- process all buffered result sets
//			System.out.println("DIVIDER");
			ResultSet[] rss = new ResultSet[xrss.size()];
			xrss.toArray(rss);

			// Process this batch of ResultSets
			SqlRunnable handler = curHandler.next();
			if (handler != null) {
				if (handler instanceof RssRunnable) {
					((RssRunnable)handler).run(this, rss);
				} else if (handler instanceof RsRunnable) {		
					((RsRunnable)handler).run(this, rss[0]);
				} else if (handler instanceof UpdRunnable) {
					((UpdRunnable)handler).run(this);
				}
			}

			// Get ready for new batch
			xrss.clear();
			nextCurrent = Statement.CLOSE_ALL_RESULTS;
		} else {
			// Just a regular ResultSet --- buffer it
			xrss.add(rs);
		}

	}
//	return (nextBatch.size() == 0 ? null : nextBatch);
}
}

