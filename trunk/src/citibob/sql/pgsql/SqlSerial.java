/*
 * SqlSerial.java
 *
 * Created on September 24, 2006, 10:57 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.sql.pgsql;

import java.sql.*;
import citibob.sql.*;

/**
 *
 * @author citibob
 */
public class SqlSerial extends SqlInteger implements SqlSequence
{
	String seq;		// Name of the postgreSQL sequence for this serial column
	
	public SqlSerial(String seq, boolean n) {
		super(n);
		this.seq = seq;
	}
	public SqlSerial(String seq) { this(seq, true); }

	public int getCurVal(Statement st) throws SQLException
	{
		ResultSet rs = st.executeQuery("select currval(" + SqlString.sql(seq) + ")");
		rs.next();
		int ret = rs.getInt(1);
		rs.close();
		return ret;
	}
	public int getNextVal(Statement st) throws SQLException
	{
		ResultSet rs = st.executeQuery("select nextval(" + SqlString.sql(seq) + ")");
		rs.next();
		int ret = rs.getInt(1);
		rs.close();
		return ret;		
	}
}
