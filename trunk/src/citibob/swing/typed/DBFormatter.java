/*
 * KeyedFormatter.java
 *
 * Created on March 18, 2006, 4:37 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.swing.typed;

import citibob.util.KeyedModel;
import javax.swing.*;
import citibob.sql.*;
import java.sql.*;
import citibob.multithread.*;

/**
 * An AbstractFormatter that must make a database query to do its formatting.
 * @author citibob
 */
public abstract class DBFormatter extends JFormattedTextField.AbstractFormatter
{

ConnPool pool;
//protected String nullText = "";

public DBFormatter(ConnPool pool)
{
	this.pool = pool;
}

/** Not to be used */
public Object  stringToValue(String text)
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
