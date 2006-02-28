package citibob.jschema.mysql;

import java.sql.*;


public class SqlBool
{

	public static String sql(Boolean ii)
		{ return ii == null ? "null" : sql(ii.booleanValue()); }
	public static String sql(boolean b)
	{
		return (b ? "'true'" : "'false'");
	}

}
