/*
 * SqlBatch.java
 *
 * Created on August 28, 2007, 9:14 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.sql;

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
int nextCurrent = Statement.CLOSE_ALL_RESULTS;
List<ResultSet> xrss = new ArrayList();

/** Creates a new instance of SqlBatch */
public SqlBatch() {
}

public int size() { return handlers.size(); }




public void execSql(String sql)
	{ execSql(sql, null); }

/** Adds SQL to the batch --- multiple ResultSets returned, and it can create
 additional SQL as needed. */
public void execSql(String sql, SqlRunnable rr)
{
	sqlBuf.append(sql);
	sqlBuf.append(";\n select 'hello' as __divider__;\n");
	handlers.add(rr);
}

public void execUpdate(UpdRunnable r)
{
	execSql("", r);
}


//public void exec(Connection dbb) throws Throwable
//{
//	if (size() == 0) return;
//	Statement st = null;
//	try {
//		st = dbb.createStatement();
//		exec(st, exp);
//	} catch(SQLException e) {
//		exp.consume(e);
//	} finally {
//		try {
//			st.close();
//		} catch(SQLException e) {
//			exp.consume(e);
//		}
//	}
//}


public void exec(ConnPool pool) throws Throwable
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
public void exec(Statement st) throws Throwable
{
	if (size() == 0) return;
	exec(this, st);
}

private static void exec(SqlBatch batch, Statement st) throws Throwable
{
	for (;;) {
		SqlBatch nextBatch = batch.execOneBatch(st);
		if (nextBatch == null) return;
		batch = nextBatch;
	}
}

/** Execute the SQL batch; puts any new queries in "nextBatch" */
SqlBatch execOneBatch(Statement st) throws Throwable
{
	SqlBatch nextBatch = new SqlBatch();
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
					((RssRunnable)handler).run(rss, nextBatch);
				} else if (handler instanceof RsRunnable) {		
					((RsRunnable)handler).run(rss[0]);
				} else if (handler instanceof UpdRunnable) {
					((UpdRunnable)handler).run();
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
	return (nextBatch.size() == 0 ? null : nextBatch);
}
}

