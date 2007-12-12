/*
 * TMGrouper.java
 *
 * Created on August 11, 2007, 11:02 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.reports;

import citibob.types.JType;
import citibob.types.JavaJType;
import com.sun.crypto.provider.RSACipher;
import java.io.*;
import java.util.*;
import citibob.sql.pgsql.*;
import java.sql.*;
import citibob.sql.*;
import citibob.swing.table.*;
import citibob.swing.typed.*;

/**
 *
 * @author citibob
 */
public class TableModelGrouper
{
	
// Params to be made available globally
JTypeTableModel model;
int[][] gcols;

// Scan state
int curRow;			// Current row # we're scanning'
int start;			// Starting row of current lowest-level grouping
int level;		// min. level at which current row doesn't match last row at this level
Object[][] last;	// Value of each grouping level on last row

// Active working set in building tree structure
Map<String,Object>[] maps;			// Currently active map for each grouper level
List[] lists;		// Currently active list for each grouper level --- maps[i].contains(lists[i])


protected static int[][] getGcols(JTypeTableModel model, String[][] sgcols)
{
	// Allocate gcols
	int[][] gcols = new int[sgcols.length][];
	for (int i = 0; i < sgcols.length; ++i) gcols[i] = new int[sgcols[i].length];
	
	// Fill it in
	for (int i = 0; i < sgcols.length; ++i) {
	for (int j=0; j< sgcols[i].length; ++j) {
		gcols[i][j] = model.findColumn(sgcols[i][j]);
	}}
	return gcols;
}

public TableModelGrouper(JTypeTableModel model, String[][] sgcols)
{
	this(model, getGcols(model, sgcols));
}
public TableModelGrouper(JTypeTableModel model, int[][] gcols)
{
	this.model = model;
	this.gcols = gcols;
}

/** Do the grouping... */
public List<Map> groupRowsList()
{
	level = -1;
	curRow = -1;

	// Allocate memory
	lists = new List[gcols.length];
	lists[0] = new LinkedList();
	maps = new Map[gcols.length];
	last = new Object[gcols.length][];
	for (int i = 0; i < gcols.length; ++i) last[i] = new Object[gcols[i].length];

	// Process...
	int nrow = 0;
	outer : while (next()) {
		++nrow;
		
		// See at what level current and last row mis-match
		int mismatch;
		for (mismatch = 0; mismatch < gcols.length; ++mismatch) {
		for (int j=0; j< gcols[mismatch].length; ++j) {
			Object curVal = model.getValueAt(curRow, gcols[mismatch][j]);
			Object lastVal = last[mismatch][j];
			if (!equals(lastVal, curVal)) {
				// Copy out new "last" values from mismatch on
				for (int ii=mismatch; ii<gcols.length; ++ii) {
				for (int jj=0; jj<gcols[ii].length; ++jj) {
					last[ii][jj] = model.getValueAt(curRow, gcols[ii][jj]);
				}}
				
				// Go up and down levels
				if (level >= 0) for (int i=gcols.length-1; i >= mismatch; --i) groupEnd(i);
				for (int i=mismatch; i < gcols.length; ++i) groupStart(i);
				level = mismatch;
				
System.out.println("row " + curRow + " mismatch = " + mismatch);
				continue outer;
			}
		}}
		// No mismatch: we're OK, just keep scanning
System.out.println("row " + curRow + " mismatch = " + mismatch);
		
	}
	
	// No Data
	if (nrow == 0) return null;

	// Finish off all groupings
	for (int i=gcols.length-1; i >= 0; --i) groupEnd(i);
	
	return lists[0];
}

boolean next()
{
	++curRow;
	return (curRow < model.getRowCount());
}

void groupStart(int level)
{
System.out.println("<groupStart(" + level + ")>");
	Map map = new TreeMap();
	maps[level] = map;
	lists[level].add(map);

	// Make new list for our children
	if (level < gcols.length-1) {
		List list = new LinkedList();
		lists[level+1] = list;
		map.put("g"+(level+1), list);
	}
			
	// Add scalars to map
	for (int i=0; i<=level; ++i) {
	for (int j=0; j<gcols[i].length; ++j) {
		int col = gcols[i][j];
		Object val = model.getValueAt(curRow, col);
		Object formattedVal = formatVal(val, col);
		map.put(model.getColumnName(col), formattedVal);
	}}
	
	// Store in data structures
//	if (level > 0) lists[level-1].add(map);
	start = curRow;
//System.out.println("</groupStart(" + level + ")>");
}
void groupEnd(int level)
{
System.out.println("<groupEnd(" + level + ")>");
	if (level == gcols.length - 1) {
		maps[level].put("rs", newTableModel(start,curRow));
		start = curRow;
	}
//System.out.println("</groupEnd(" + level + ")>");
}

Object newTableModel(int firstrow, int nextrow)
{
	return new citibob.swing.table.SubrowTableModel(model, firstrow, nextrow);
}
Object formatVal(Object val, int col)
{
	return val;
}
///** Called after groupChanged() for each row. */
//void processRow() {}

// ----------------------------------------
// ----------------------------------------

/** Compares for equality, taking null into account */
private boolean equals(Object a, Object b)
{
	if (a == null) return (b == null);
	if (b == null) return false;
	return a.equals(b);
}
// ====================================================================
// Test Code

static void print(Map map, int level)
{
if (level > 5) return;

	List list = null;
	for (Iterator<Map.Entry> ii=map.entrySet().iterator(); ii.hasNext(); ) {
		Map.Entry e = ii.next();
		Class klass = e.getValue().getClass();
		if (List.class.isAssignableFrom(klass)) {
			// sub-list
			spaces(level);
			System.out.println(e.getKey() + " = (List)");
			print((List)e.getValue(), level+1);
		} else if (JTypeTableModel.class.isAssignableFrom(klass)) {
			spaces(level);
			System.out.println(e.getKey() + " = (Table)");
			print((JTypeTableModel)e.getValue(), level+1);
		} else if (Map.class.isAssignableFrom(klass)) {
			spaces(level);
			System.out.println(e.getKey() + " = (Map)");
			print((Map)e.getValue(), level+1);
		} else {
			spaces(level);
			System.out.println(e.getKey() + " = " + e.getValue());
		}
	}
	if (list != null) print(list, level+1);
}

static void print(JTypeTableModel mod, int level)
{
	for (int i=0; i<mod.getRowCount(); ++i) {
		spaces(level);
		for (int j=0; j<mod.getColumnCount(); ++j) {
			System.out.print(mod.getValueAt(i,j) + " ");
		}
		System.out.println();
	}
}

static void print(List list, int level)
{
	for (Object o : list) {
		print((Map)o, level);
	}
}

static void spaces(int level)
{
	for (int i=0; i<level*2; ++i) System.out.print(" ");
}
public static void main(String[] args)
{
	JType jString = new JavaJType(String.class);
	DefaultJTypeTableModel mod = new DefaultJTypeTableModel(new String[][] {
		{"A", "1", "a"},
		{"A", "2", "b"},
		{"A", "2", "c"},
		{"B", "3", "d"},
		{"B", "3", "e"},
		{"B", "4", "f"}
	}, new String[] {"c0", "c1", "c2"},
	new JType[] {jString, jString, jString});
	
	int[][] gcols = {{0},{1}};
	TableModelGrouper group = new TableModelGrouper(mod, gcols);
	
	print(group.groupRowsList(), 0);
}

}

