package citibob.jschema.mysql;

import java.sql.Date;
import java.sql.*;

public class SqlDate
{

	public static String sql(Date ts)
	{
		return ts == null ? "null" : '\'' + ts.toString() + '\'';
	}

}
