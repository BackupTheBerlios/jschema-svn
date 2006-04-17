/*
 * SqlDateColumn.java
 *
 * Created on March 13, 2006, 11:23 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.jschema;

import citibob.sql.*;
import java.util.*;

/**
 *
 * @author citibob
 */
public class ColumnDefaultNow extends citibob.jschema.Column
{

// ===================================================================
/** @param dateType must be an SqlDate from some package... */
public ColumnDefaultNow(SqlDateType dateType, String name, boolean key)
{
	super(dateType, name, key);
}
// ===================================================================

/** Default value for column when inserting new row in buffers. 
 This method will be overridden. */
public Object getDefault()
{
	SqlDateType jtype = (SqlDateType)super.getType();
	java.util.Date dt = jtype.truncate(new Date());
System.out.println("DefaultNow = " + dt + "(jtype = " + jtype.getClass());
	return dt;
}
	
}
