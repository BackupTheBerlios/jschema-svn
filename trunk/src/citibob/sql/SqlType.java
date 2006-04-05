/*
 * SqlType.java
 *
 * Created on April 1, 2006, 12:39 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.sql;

/**
 *
 * @author citibob
 */
public interface SqlType extends citibob.swing.typed.JType
{
	/** Convert an element of this type to an Sql string for use in a query */
	String toSql(Object o);
	
}
