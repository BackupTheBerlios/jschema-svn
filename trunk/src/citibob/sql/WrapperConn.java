/**
Code from:
Advanced Programming for the Java 2 Platform
http://java.sun.com/developer/onlineTraining/Programming/JDCBook/Code/JDCConnectionDriver.java
*/
package citibob.sql;

import java.sql.*;
import java.util.*;
import java.io.*;

public class WrapperConn implements Connection {

    private ConnPool pool;
    private Connection conn;

public int	getHoldability() throws SQLException 
{ return conn.getHoldability(); }
public void	setHoldability(int holdability)  throws SQLException 
{ conn.setHoldability(holdability); }
public  void	setTypeMap(Map map) throws SQLException 
{ conn.setTypeMap(map); }
public  Map	getTypeMap() throws SQLException 
{ return conn.getTypeMap(); }
public CallableStatement	prepareCall(String sql, int resultSetType, int resultSetConcurrency)  throws SQLException 
{ return conn.prepareCall(sql, resultSetType, resultSetConcurrency); }

public PreparedStatement	prepareStatement(String sql, int resultSetType, int resultSetConcurrency)  throws SQLException 
{ return conn.prepareStatement( sql,  resultSetType,  resultSetConcurrency); }

public PreparedStatement	prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability)  throws SQLException 
{ return conn.prepareStatement( sql,  resultSetType,  resultSetConcurrency, resultSetHoldability); }


public Statement	createStatement(int resultSetType, int resultSetConcurrency)  throws SQLException 
{ return conn.createStatement(   resultSetType,  resultSetConcurrency); }

public Statement	createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)  throws SQLException 
{ return conn.createStatement(  resultSetType,  resultSetConcurrency, resultSetHoldability); }


    public WrapperConn(Connection conn, ConnPool pool) {
        this.conn=conn;
        this.pool=pool;
    }

	public Connection getUnderlying() { return conn; }
//    public synchronized boolean lease() {
//       if(inuse)  {
//           return false;
//       } else {
//          inuse=true;
//          timestamp=System.currentTimeMillis();
//          return true;
//       }
//    }
////    public boolean validate() {
////	try {
////            conn.getMetaData();
////        }catch (Exception e) {
////	    return false;
////	}
////	return true;
////    }
//
//    public boolean inUse() {
//        return inuse;
//    }
//
//    public long getLastUse() {
//        return timestamp;
//    }

    public void close() throws SQLException {
		pool.checkin(this);
//        pool.returnConnection(this);
    }

//    protected void expireLease() {
//        inuse=false;
//    }

//    protected Connection getConnection() {
//        return conn;
//    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return conn.prepareStatement(sql);
    }
    public PreparedStatement prepareStatement(String sql, String[] args) throws SQLException {
        return conn.prepareStatement(sql, args);
    }
    public PreparedStatement prepareStatement(String sql, int[] args) throws SQLException {
        return conn.prepareStatement(sql, args);
    }
    public PreparedStatement prepareStatement(String sql, int args) throws SQLException {
        return conn.prepareStatement(sql, args);
    }

    public CallableStatement prepareCall(String sql) throws SQLException {
        return conn.prepareCall(sql);
    }
    public CallableStatement prepareCall(String sql, int a1, int a2, int a3) throws SQLException {
        return conn.prepareCall(sql,a1,a2,a3);
    }

    public Statement createStatement() throws SQLException {
        return conn.createStatement();
    }

    public String nativeSQL(String sql) throws SQLException {
        return conn.nativeSQL(sql);
    }

    public void setAutoCommit(boolean autoCommit) throws SQLException {
        conn.setAutoCommit(autoCommit);
    }

    public boolean getAutoCommit() throws SQLException {
        return conn.getAutoCommit();
    }

    public void commit() throws SQLException {
        conn.commit();
    }

    public void rollback() throws SQLException {
        conn.rollback();
    }

    public boolean isClosed() throws SQLException {
        return conn.isClosed();
    }

    public DatabaseMetaData getMetaData() throws SQLException {
        return conn.getMetaData();
    }

    public void setReadOnly(boolean readOnly) throws SQLException {
        conn.setReadOnly(readOnly);
    }
  
    public boolean isReadOnly() throws SQLException {
        return conn.isReadOnly();
    }

    public void setCatalog(String catalog) throws SQLException {
        conn.setCatalog(catalog);
    }

    public String getCatalog() throws SQLException {
        return conn.getCatalog();
    }

    public void setTransactionIsolation(int level) throws SQLException {
        conn.setTransactionIsolation(level);
    }

    public int getTransactionIsolation() throws SQLException {
        return conn.getTransactionIsolation();
    }

    public SQLWarning getWarnings() throws SQLException {
        return conn.getWarnings();
    }

    public void clearWarnings() throws SQLException {
        conn.clearWarnings();
    }

public Savepoint setSavepoint(String sp) throws SQLException {
	return conn.setSavepoint(sp);
}
public Savepoint setSavepoint() throws SQLException {
	return conn.setSavepoint();
}
public void releaseSavepoint(Savepoint sp) throws SQLException {
	conn.releaseSavepoint(sp);
}
public void rollback(Savepoint sp) throws SQLException {
	conn.rollback(sp);
}

}
