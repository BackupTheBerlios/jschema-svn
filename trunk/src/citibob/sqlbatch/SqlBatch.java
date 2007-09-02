/*
 * SqlBatch.java
 *
 * Created on August 28, 2007, 9:14 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.sqlbatch;

import java.sql.*;
import citibob.multithread.*;
import java.util.*;

/**
 *
 * @author citibob
 */
public class SqlBatch {

// Used while building query
StringBuffer sqlBuf = new StringBuffer();
List<RssRunnable> handlers = new ArrayList();

// Used while interpreting results
int nextCurrent = Statement.CLOSE_ALL_RESULTS;
List<ResultSet> xrss = new ArrayList();

/** Creates a new instance of SqlBatch */
public SqlBatch() {
}

public void addSql(String sql, RssRunnable r)
{
	sqlBuf.append(sql);
	sqlBuf.append("\n select 'hello' as __divider__;\n");
	handlers.add(r);
}

/** Convenience method --- the sql block returns only one result set */
public void addSql(String sql, final RsRunnable r)
{
	addSql(sql, new RssRunnable() {
	public void run(ResultSet[] rss) throws Throwable {
		r.run(rss[0]);
	}});
}

public void exec(Connection dbb, ExpHandler exp)
{
	Statement st = null;
	try {
		st = dbb.createStatement();
		exec(st, exp);
	} catch(SQLException e) {
		exp.consume(e);
	} finally {
		try {
			st.close();
		} catch(SQLException e) {
			exp.consume(e);
		}
	}
}

/** Execute the SQL batch */
public void exec(Statement st, ExpHandler exp) throws SQLException
{
	String sql = sqlBuf.toString();
	st.execute(sql);

	ResultSet rs;
	Iterator<RssRunnable> curHandler = handlers.iterator();
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
			System.out.println("DIVIDER");
			ResultSet[] rss = new ResultSet[xrss.size()];
			xrss.toArray(rss);

			// Process this batch of ResultSets
			try {
				RssRunnable handler = curHandler.next();
				handler.run(rss);
			} catch(Throwable e) {
				exp.consume(e);
			}

			// Get ready for new batch
			xrss.clear();
			nextCurrent = Statement.CLOSE_ALL_RESULTS;
		} else {
			// Just a regular ResultSet --- buffer it
			xrss.add(rs);
		}

	}
}
}

