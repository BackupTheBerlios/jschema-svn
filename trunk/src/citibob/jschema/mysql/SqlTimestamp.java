package citibob.jschema.mysql;

import java.sql.Timestamp;
import java.sql.*;

public class SqlTimestamp
{


	public static String sql(Timestamp ts)
	{
		return ts == null ? "null" :
			('\'' + ts.toString() + '\'');
	}
}
