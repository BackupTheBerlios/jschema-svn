/*
JSchema: library for GUI-based database applications
This file Copyright (c) 2006 by Robert Fischer

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/
package citibob.jschema;

import citibob.jschema.*;
import java.sql.*;
import citibob.sql.SqlQuery;
//import java.util.*;

/** Description of a set of queries we would like to do and load into a PersonsBuf. */
public class WhereClauseDbModel extends SchemaBufDbModel
{

///** Key fields to control who gets displayed. */
//int idValue;
String whereClause;
String orderClause;

public void setWhereClause(String whereClause)
{
	this.whereClause = whereClause;
}
public void setOrderClause(String orderClause)
	{ this.orderClause = orderClause; }

public WhereClauseDbModel(Schema schema, String whereClause, String orderClause)
{
	this(new SchemaBuf(schema), whereClause, orderClause);
}
public WhereClauseDbModel(SchemaBuf sbuf, String whereClause, String orderClause)
{
	super(sbuf);
	this.whereClause = whereClause;
	this.orderClause = orderClause;
}

public void setSelectWhere(SqlQuery q)
{
	q.addWhereClause(whereClause);
	q.addOrderClause(orderClause);
}

/** Typically, this will be a no-op, WhereClauseDbModel is usually used
for more ad-hoc things in which the where clause doesn't add any key fields.
If this is not the case, just override setInesertKeys(). */
public void setInsertKeys(int row, SqlQuery q) {}

}
