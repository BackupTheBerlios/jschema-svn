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

/** Adds SQL to the batch --- multiple ResultSets returned, and it can create
 additional SQL as needed.
 @param rr one of RssRunnable, RsRunnable, UpdRunnable */
public void execSql(String sql, SqlRunnable rr);

/** Adds Sql to current batch without any processing code. */
public void execSql(String sql);

/** Adds processing code to run without any SQL. */
public void execUpdate(UpdRunnable r);

/** Gets the SqlRunner for the next batch --- used inside SqlRunnable
 to run things in sequence. */
public SqlRunner next();

/** While SqlRunnables are running --- store a value for retrieval by later SqlRunnable. */
public void put(Object key, Object val);

/** While SqlRunnables are running --- retrieve a previously stored value. */
public Object get(Object key);

}

