/*
JSchema: library for GUI-based database applications
This file Copyright (c) 2006-2007 by Robert Fischer

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package citibob.sql;

import java.util.*;

public class ConsSqlQuery
{
public static final int SELECT = 0;
public static final int INSERT = 1;
public static final int UPDATE = 2;
public static final int DELETE = 3;

// ========================================
public static class NVPair
{
	public String name;
	public String value;
	public NVPair(String name, String value)
		{ this.name = name; this.value = value;}
}
// ========================================
protected String mainTable = null;
protected ArrayList<NVPair> columns = new ArrayList();
protected ArrayList<String> tables = new ArrayList();
protected TreeSet tableSet = new TreeSet();
protected ArrayList whereClauses = new ArrayList();
protected int type = SELECT;
protected ArrayList orderClauses = new ArrayList();
protected boolean distinct = false;

// --------------------------------------------------------
public Object clone()
{
	ConsSqlQuery ret = new ConsSqlQuery();
	ret.mainTable = mainTable;
	ret.columns = (ArrayList)columns.clone();
	ret.tables = (ArrayList)tables.clone();
	ret.tableSet = (TreeSet)tableSet.clone();
	return ret;
}
// --------------------------------------------------------
private ConsSqlQuery() {}
public ConsSqlQuery(int type)
{
	setType(type);
}
//public SqlQuery(String mainTable)
//{
//	setMainTable(mainTable);
//}
public ConsSqlQuery(String mainTable, int type)
{
	setMainTable(mainTable);
	setType(type);
}

public void setDistinct(boolean d)
	{ distinct = d; }

/** Add a name-value pair to the list of columns (for update statements).
 Value must be encoded for SQL.
 @see SQL*/
public void addColumn(String name, String value)
	{ columns.add(new NVPair(name, value)); }
/** Add a name-only pair to the list of columns (for select statements) */
public void addColumn(String name)
	{ columns.add(new NVPair(name, null)); }

/** Adds to the list of tables being joined in this query */
public void addTable(String t)
{
	tables.add(t);
	tableSet.add(t);
}

/** Adds table: A as A' */
public void addTable(String tableName, String asName)
{
	String name = tableName;
	if (!(asName == null || asName.equals(tableName))) {
		// asName is different
		name = name + " as " + asName;
	}
	addTable(name);
}

/** Adds table: A as A' Inner/Outer JOIN (xxxxxxx) */
public void addTable(String tableName, String asName, String joinLogic)
{
	String name = tableName;
	if (!(asName == null || asName.equals(tableName))) {
		// asName is different
		name = name + " as " + asName;
		asName = tableName;
	}
	if (joinLogic != null) {
		name = name + " " + joinLogic;
	}
	addTable(name);
}


public boolean containsColumn(String t)
{
	for (NVPair nv : columns) {
		if (t == nv.name) return true;
		if (nv.name.equals(t)) return true;
	}
	return false;
}

public boolean containsTable(String t)
{ return (tableSet.contains(t)); }

/** Adds a where clause (used when joining tables */
public void addWhereClause(String wc)
	{ if (wc != null) whereClauses.add(wc); }
/** Returns the number of where clauses in the query. */
public int numWhereClauses()
	{ return whereClauses.size(); }

/** Adds a where clause (used when joining tables */
public void addOrderClause(String wc)
	{ if (wc != null) orderClauses.add(wc); }


/** Sets the "special" table (for UPDATE and INSERT, not SELECT) */
public void setMainTable(String t)
	{ mainTable = t; }
public String getMainTable() { return mainTable; }

/** Chooses whether this is SELECT, UPDATE or INSERT query.
 This parameter is not essential. */
public void setType(int type)
	{ this.type = type; }
/** Returns whether this is SELECT, UPDATE or INSERT query */
public int getType()
	{ return type;}

// ======================================================
/** Builds a fully-formed where clause for the query */
public String getWhereClause()
{
	StringBuffer ret = new StringBuffer();
	boolean first = true;
	for (Iterator ii = whereClauses.iterator(); ii.hasNext(); ) {
		String s = (String)ii.next();
		if (first) {
			ret.append(" where ");
			first = false;
		} else ret.append(" and ");
		ret.append(s);
		ret.append('\n');
	}
	return ret.toString();
}

/** Builds a fully-formed order clause for the query */
public String getOrderClause()
{
	StringBuffer ret = new StringBuffer();
	boolean first = true;
	for (Iterator ii = orderClauses.iterator(); ii.hasNext(); ) {
		String s = (String)ii.next();
		if (first) {
			ret.append("\norder by ");
			first = false;
		} else ret.append(", ");
		ret.append(s);
		ret.append('\n');
	}
	return ret.toString();
}

/** Builds a full-formed FROM clause */
public String getFromClause()
{
	StringBuffer ret = new StringBuffer();
	boolean first = true;
	for (Iterator ii = tables.iterator(); ii.hasNext(); ) {
		String s = (String)ii.next();
		if (first) {
			ret.append(" FROM ");
			first = false;
		} else {
			if (s.indexOf("join") < 0) {
				ret.append(", ");
			}
		}
		ret.append(s);
	}
	return ret.toString();
}

/** Returns list of columns */
public String getColumnList()
{
	StringBuffer ret = new StringBuffer();
	boolean first = true;
	for (Iterator ii = columns.iterator(); ii.hasNext(); ) {
		NVPair nv = (NVPair)ii.next();
		if (first) first = false;
		else ret.append(", ");
		ret.append(nv.name);
	}
	return ret.toString();
}


public List<NVPair> getColumns() { return columns; }
//public List<String> getTables() { return tables; }

///** Returns names of the columns involved in the query */
//public String[] getColumnNames()
//{
//	String[] ret = new String[columns.size()];
//	int n=0;
//	for (Iterator ii = columns.iterator(); ii.hasNext(); ) {
//		NVPair nv = (NVPair)ii.next();
//		ret[n++] = nv.name;
//	}
//	return ret;
//}

/** Returns a permuted list of columns. */
public String getColumnList(int[] selectList)
{
	StringBuffer ret = new StringBuffer();
	boolean first = true;
	for (int i = 0; i < selectList.length; ++i) {
		NVPair nv = (NVPair)columns.get(selectList[i]);
		if (first) first = false;
		else ret.append(", ");
		ret.append(nv.name);
	}
	return ret.toString();
}

/** Returns list of column=value, ... */
public String getValueList()
{
	StringBuffer ret = new StringBuffer();
	boolean first = true;
	for (Iterator ii = columns.iterator(); ii.hasNext(); ) {
		NVPair nv = (NVPair)ii.next();
		if (first) first = false;
		else ret.append(", ");
		ret.append(nv.value);
	}
	return ret.toString();
}

/** Returns fully-formed set clause.
 @see #getValueList */
public String getSetClause()
{
	StringBuffer ret = new StringBuffer();
	boolean first = true;
	for (Iterator ii = columns.iterator(); ii.hasNext(); ) {
		NVPair nv = (NVPair)ii.next();

		if (first) {
			ret.append(" SET ");
			first = false;
		} else ret.append(", ");
		ret.append(nv.name);
		ret.append(" = ");
		ret.append(nv.value);
	}
	return ret.toString();
}

// =============================================================
/** Returns fully-formed SELECT statement */
private String getSelectSQL()
{
	return
		" SELECT "  + (distinct ? "distinct " : "") +
		getColumnList() + "\n" +
		getFromClause() + getWhereClause() + getOrderClause();
}
///** Returns fully-formed SELECT statement, with the columns permuted from the original ordering. */
//public String getSelectSQL(int[] selectList)
//{
//	return
//		" SELECT "  + (distinct ? "distinct " : "") +
//		getColumnList(selectList) + "\n" +
//		getFromClause() + getWhereClause() + getOrderClause();
//}

/** Returns fully-formed INSERT statement */
private String getInsertSQL()
{
	return
		" insert into " + mainTable + "(\n" +
		getColumnList() + ") values (\n" +
		getValueList() + ")";
}
/** Returns fully-formed UPDATE statement */
private String getUpdateSQL()
{
	return
		" UPDATE " + mainTable +
		getSetClause() + "\n" +
		getFromClause() + getWhereClause();
}
private String getDeleteSQL()
{
	return
		" DELETE from " + mainTable +
		getWhereClause();
}
// =============================================================
/** Returns fully-formed SQL statement, based on getType(). */
public String getSql()
{
	switch(type) {
		case SELECT : return getSelectSQL();
		case INSERT : return getInsertSQL();
		case UPDATE : return getUpdateSQL();
		case DELETE : return getDeleteSQL();
	}
	return null;
}
public String getCleanupSql() { return null; }

}

