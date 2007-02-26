///*
// * OuterJoinRSTableModel.java
// *
// * Created on February 12, 2007, 8:50 PM
// *
// * To change this template, choose Tools | Template Manager
// * and open the template in the editor.
// */
//
//package citibob.swing.table;
//
//import java.sql.*;
//import javax.swing.table.*;
//import javax.swing.event.*;
//import citibob.swing.typed.*;
//import java.util.*;
//import citibob.sql.*;
//
///**
// *
// * @author citibob
// */
//public class OuterJoinRSTableModel extends RSTableModel
//{
//
////public OuterJoinRSTableModel()
////{
////	super();
////}
//	
//JTypeTableModel main;	// The main table
//Map<Object,Integer> joinMap;
//String keyCol;
//
///** @param main main table we're doing outer join to
// @param main main table we're joining to
// @param joinMap indicates the row that each key value appears on
// @param keyCol name of column in this table to join to
// */
//public OuterJoinRSTableModel(JTypeTableModel main, Map<Object,Integer> joinMap, String keyCol,
//SqlTypeSet tset)
//{
//	super(tset);
//	this.main = main;
//	this.joinMap = joinMap;
//	this.keyCol = keyCol;
//}
//
//public void executeQuery(Statement st, String sql) throws SQLException
//{
//	setRowCount(main.getRowCount());
//	super.executeQuery(st, sql);
//}
//
//public void addAllRows(ResultSet rs)
//throws SQLException
//{
//	ResultSetMetaData meta = rs.getMetaData();
//	int ncol = meta.getColumnCount();
//	int iKeyCol = this.findColumn(keyCol);
//	while (rs.next()) {
//		int row = joinMap.get(rs.getObject(iKeyCol));
//		for (int i=0; i<ncol; ++i) {
//			this.setValueAt(rs.getObject(i), row, i);
//		}
//	}
//}
//}
