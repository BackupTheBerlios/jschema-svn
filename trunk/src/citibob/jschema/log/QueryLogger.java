/*
 * QueryLogger.java
 *
 * Created on June 8, 2007, 1:13 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.jschema.log;

import static citibob.sql.ConsSqlQuery.*;
import java.sql.*;
import citibob.sql.*;
import java.util.*;
import citibob.jschema.*;

/**
 *
 * @author citibob
 */
public interface QueryLogger
{

public void log(QueryLogRec rec);
	
}
