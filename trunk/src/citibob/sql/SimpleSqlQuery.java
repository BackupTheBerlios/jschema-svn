/*
 * SimpleSqlQuery.java
 *
 * Created on May 15, 2007, 10:57 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.sql;

/**
 *
 * @author citibob
 */
public class SimpleSqlQuery
{
String sql, cleanup;

	/** Creates a new instance of SimpleSqlQuery */
	public SimpleSqlQuery(String sql, String cleanup)
	{
		this.sql = sql;
		this.cleanup = cleanup;
	}
	public String getSql() { return sql; }
	/** Sql required to free any database resources after this query is finished running. */
	public String getCleanupSql() { return cleanup; }
}
