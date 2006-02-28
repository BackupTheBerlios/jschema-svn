package citibob.jschema.mysql;

import java.sql.*;

public class SqlInteger {
	public static String sql(Integer ii)
		{ return ii == null ? "null" : ii.toString(); }
	public static String sql(int i)
		{ return ""+i; }

}
