/*
 * SqlTypeFactory.java
 *
 * Created on January 28, 2007, 9:02 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.sql.pgsql;

import java.util.*;
import static java.sql.Types.*;
import java.sql.*;
import citibob.sql.*;

/**
 *
 * @author citibob
 */
public class PgsqlTypeSet extends BaseSqlTypeSet
{
	
TimeZone tz;

public PgsqlTypeSet(TimeZone tz)
{
	this.tz = tz;
}
public PgsqlTypeSet()
{
	this(TimeZone.getDefault());
}


/** @param col the first column is 1, the second is 2, ...
 @returns an SqlType, given one of the basic types in java.sql.Types.  If N/A,
 or not yet implemented as an SqlType, returns null. */
public SqlType getSqlType(int type, int precision, int scale, boolean nullable)
{
	switch (type) {
		case ARRAY : return null;
		case BIGINT : return null;
		case BINARY : return null;
		case BIT : return new SqlBool(nullable);
		case BLOB : return null;
		case BOOLEAN : return new SqlBool(nullable);
		case CHAR : return new SqlChar(nullable);
		case CLOB : return new SqlString(nullable);
		case DATALINK : return null;
		case DATE : return new SqlDate(tz, nullable);
		case DECIMAL : return new SqlNumeric(precision, scale, nullable);
		case DISTINCT : return null;
		case DOUBLE : return new SqlDouble(nullable);
		case FLOAT : return null;
		case INTEGER : return new SqlInteger(nullable) ;
		case JAVA_OBJECT : return null;
		case LONGVARBINARY : return null;
		case LONGVARCHAR : return new SqlString(nullable);
		case NULL : return null;
		case NUMERIC : return new SqlNumeric(precision, scale, nullable);
		case OTHER : return null;
		case REAL : return null;
		case REF : return null;
		case SMALLINT : return new SqlInteger(nullable);
		case STRUCT : return null;
		case TIME : return null;
		case TIMESTAMP : return new SqlTimestamp(tz, nullable);
		case TINYINT : return null;
		case VARBINARY : return null;
		case VARCHAR : return new SqlString(nullable);
	}
	return null;
}

	
}
