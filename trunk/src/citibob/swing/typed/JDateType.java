/*
 * JDateType.java
 *
 * Created on March 15, 2006, 10:35 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.swing.typed;

import java.util.*;
import citibob.jschema.*;

/**
 * An SqlType for representing dates...
 * @author citibob
 */
public interface JDateType extends JType
{

public Calendar getCalendar();
public java.util.Date get(java.sql.ResultSet rs, int col);
public java.util.Date get(java.sql.ResultSet rs, String col);

/** Returns a truncated version of the input; i.e. if this is a SqlDate,
 *then truncates off hour, minute, second. */
public java.util.Date truncate(java.util.Date dt);


}
