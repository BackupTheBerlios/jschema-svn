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
package citibob.jschema;

import java.util.*;
import java.sql.*;

/** A DbModel that controls many sub DbModels */
public class MultiDbModel implements DbModel
{

ArrayList models = new ArrayList();

public MultiDbModel() {}
public MultiDbModel(DbModel[] mm) {init(mm); }
public void init(DbModel[] mm)
{
	for (DbModel m : mm) add(m);
}
// ---------------------------------------------------
public void add(DbModel m)
	{ models.add(m); }
public void remove(DbModel m)
	{ models.remove(m); }
// ---------------------------------------------------
private int getStatus()
	{ return 0; }	// Not needed, we're overriding doUpdate() and doDelete().

// ---------------------------------------------------
public void doUpdate(Statement st)
throws java.sql.SQLException
{
	for (Iterator ii = models.iterator(); ii.hasNext(); ) {
		DbModel m = (DbModel)ii.next();
		m.doUpdate(st);
	}
}

// public void doSimpleUpdate(Statement st)
// throws java.sql.SQLException
// {
// 	for (Iterator ii = models.iterator(); ii.hasNext(); ) {
// 		DbModel m = (DbModel)ii.next();
// 		m.doSimpleUpdate(st);
// 	}
// }

public void doDelete(Statement st)
throws java.sql.SQLException
{
	for (Iterator ii = models.iterator(); ii.hasNext(); ) {
		DbModel m = (DbModel)ii.next();
		m.doDelete(st);
	}
}
// public void doSimpleDelete(Statement st)
// throws java.sql.SQLException
// {
// 	for (Iterator ii = models.iterator(); ii.hasNext(); ) {
// 		DbModel m = (DbModel)ii.next();
// 		m.doSimpleDelete(st);
// 	}
// }
// ---------------------------------------------------
public void doInit(Statement st)
throws java.sql.SQLException
{
	for (Iterator ii = models.iterator(); ii.hasNext(); ) {
		DbModel m = (DbModel)ii.next();
		m.doInit(st);
	}
}
public void doSelect(Statement st)
throws java.sql.SQLException
{
	for (Iterator ii = models.iterator(); ii.hasNext(); ) {
		DbModel m = (DbModel)ii.next();
		m.doSelect(st);
	}
}
public void doInsert(Statement st)
throws java.sql.SQLException
{
	for (Iterator ii = models.iterator(); ii.hasNext(); ) {
		DbModel m = (DbModel)ii.next();
		m.doInsert(st);
	}
}
public boolean valueChanged()
{
	for (Iterator ii = models.iterator(); ii.hasNext(); ) {
		DbModel m = (DbModel)ii.next();
		if (m.valueChanged()) return true;
	}
	return false;
}
public void doClear()
{
	for (Iterator ii = models.iterator(); ii.hasNext(); ) {
		DbModel m = (DbModel)ii.next();
		m.doClear();
	}
}
public void setKey(Object[] key)
{
	for (Iterator ii = models.iterator(); ii.hasNext(); ) {
		DbModel m = (DbModel)ii.next();
		m.setKey(key);
	}	
}
// ---------------------------------------------------
int intKey;
/** This method will only work if all our sub-models are IntKeyedDbModel. */
public void setKey(Integer ID)
{
	if (ID != null) intKey = ID;
	setKey(new Integer[] {ID});
}
/** This method will only work if all our sub-models are IntKeyedDbModel. */
public void setKey(int id)
{
	intKey = id;
	setKey(id);
}
public int getIntKey()
{ return intKey; }

// ---------------------------------------------------
}
