/*
 * CurvalRunnable.java
 *
 * Created on September 2, 2007, 12:40 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.sql;

/**
 *
 * @author citibob
 */
public interface SeqRunnable extends SqlRunnable
{
	public void run(int val, SqlRunner nextBatch) throws Throwable;
}
