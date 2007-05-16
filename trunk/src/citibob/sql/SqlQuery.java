/*
 * SqlQuery.java
 *
 * Created on May 15, 2007, 10:54 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.sql;

/**
 *
 * @author citibob
 */
public interface SqlQuery
{
	public String getSql();
	/** Sql required to free any database resources after this query is finished running. */
	public String getCleanupSql();
	
}
