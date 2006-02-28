package citibob.jschema.mysql;

import java.sql.*;

public class SqlString
{
/** Converts a Java String to a form appropriate for inclusion in an
Sql query.  This is done by single-quoting the input and repeating any
single qoutes found in it (Sql convention for quoting a quote).  If
the input is null, the string "null" is returned. */
	public static String sql(String s, boolean quotes)
	{
		if (s == null) return "null";
		StringBuffer str = new StringBuffer();
		if (quotes) str.append('\'');
		int len = s.length();
		for (int i = 0; i < len; ++i) {
			char ch = s.charAt(i);
			switch(ch) {
				case '\'' : str.append("''"); break;
				default: str.append(ch); break;
			}
		}
		if (quotes) str.append('\'');
		return str.toString();
	}
	public static String sql(String s)
		{ return sql(s, true); }

}
