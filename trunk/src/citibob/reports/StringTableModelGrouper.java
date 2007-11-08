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
import citibob.text.*;

/**
 *
 * @author citibob
 */
public class StringTableModelGrouper extends TableModelGrouper
{

SFormatter[] formatters;
	
/** Do the grouping, plus a bit of extra packaging for JodReports */
public Map groupRowsMap()
{
	List list = super.groupRowsList();
	Map map = new TreeMap();
	map.put("g0", list);
	return map;
}

Object newTableModel(int firstrow, int nextrow)
{
	JTypeTableModel mod = (JTypeTableModel)super.newTableModel(firstrow, nextrow);
	return new TemplateTableModel(new StringTableModel(mod, formatters));
}
Object formatVal(Object val, int col)
{
	try {
		return formatters[col].valueToString(val);
	} catch(java.text.ParseException e) {
		return "x"+val.toString();
	}
}

public StringTableModelGrouper(JTypeTableModel model, String[][] sgcols,
SFormatter[] formatters)
{
	this(model, getGcols(model, sgcols), formatters);
}
public StringTableModelGrouper(JTypeTableModel model, int[][] gcols,
SFormatter[] formatters)
{
	super(model, gcols);
	this.formatters = formatters;
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
	
	int[][] gcols = {{0}};
	TableModelGrouper group = new TableModelGrouper(mod, gcols);
	
	print(group.groupRowsList(), 0);
}

}

