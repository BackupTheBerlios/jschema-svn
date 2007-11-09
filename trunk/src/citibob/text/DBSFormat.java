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
/*
 * KeyedFormatter.java
 *
 * Created on March 18, 2006, 4:37 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.text;

import citibob.types.KeyedModel;
import javax.swing.*;
import citibob.sql.*;
import java.sql.*;
import citibob.multithread.*;

/**
 * An AbstractFormatter that must make a database query to do its formatting.
 * @author citibob
 */
public abstract class DBSFormat extends AbstractSFormat
{

ConnPool pool;
//protected String nullText = "";

public DBSFormat(ConnPool pool)
{
	this.pool = pool;
}

/** Not to be used */
public Object stringToValue(String text)
{
	return text;
}


/** Override this. */
public abstract String valueToString(Statement st, Object value)
throws java.sql.SQLException;

public String valueToString(Object value)
{
//	if (value == null) return nullText;
	
	String ret = null;
	Statement st = null;
	Connection dbb = null;
	try {
		dbb = pool.checkout();
		st = dbb.createStatement();
		ret = valueToString(st, value);
	} catch(Throwable e) {
		ret = e.toString();
//		eh.consume(e);
	} finally {
		try {
			if (st != null) st.close();
		} catch(SQLException se) {}
		try {
			pool.checkin(dbb);
		} catch(SQLException se) {}
	}
	return ret;
}
}
