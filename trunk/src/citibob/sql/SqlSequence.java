/*
 * SqlSequence.java
 *
 * Created on September 24, 2006, 11:03 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.sql;


import java.sql.*;

/**
 * Abstracted idea of an integer sequence in a SQL database.
 * @author citibob
 */
public interface SqlSequence {

	/** Return current value of the sequence (after an INSERT has been called that incremented it.) */
	public int getCurVal(Statement st) throws SQLException;
	
	/** Increment the sequence and return its value. */
	public int getNextVal(Statement st) throws SQLException;
	
}
