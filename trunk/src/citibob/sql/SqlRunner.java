/*
 * SqlBatch.java
 *
 * Created on August 28, 2007, 9:14 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.sql;

import java.sql.*;
import citibob.multithread.*;
import java.util.*;

/**
 * A way to run SQL queries --- either in batch, or one at a time.
 * @author citibob
 */
public interface SqlRunner {

public void execSql(String sql);

/** Adds SQL to the batch --- multiple ResultSets returned, and it can create
 additional SQL as needed. */
public void execSql(String sql, SqlRunnable r);

/** Adds code to run, in sequence, between other SQL queries. */
public void execUpdate(UpdRunnable r);

}

