/*
JSchema: library for GUI-based database applications
This file Copyright (c) 2006 by Robert Fischer

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/
package citibob.swing;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.util.*;
import citibob.swing.table.*;

/** An improvement on TableRowModel, realizing this should have a SelectModel
 *in it too. */
public class TableRowModel
extends AbstractRowModel
implements MultiRowModel, TableModelListener, ListSelectionModel {

private CitibobTableModel tmodel;
int curRow;
/** Should we listen to the underlying CitibobTableModel for insert/delete events?
It's appropriate to be false only when this has been used as a JTable's
ListSelectionModel. */
boolean listenTableModel = true;

public CitibobTableModel getTableModel()
{ return tmodel; }

public TableRowModel(CitibobTableModel tmodel)
{
	this(tmodel, true);
}
public TableRowModel(CitibobTableModel tmodel, boolean listen)
{
	listenTableModel = listen;
	this.tmodel = tmodel;
	colListeners = new ColListener[tmodel.getColumnCount()];
	// Set to the first row, if there is one right now...
	if (tmodel.getRowCount() == 0) curRow = NOROW;
	else curRow = 0;
	tmodel.addTableModelListener(this);
}

protected void finalize() {
	tmodel.removeTableModelListener(this);
}

public int findColumn(String s)
	{ return tmodel.findColumn(s); }
// ===========================================================
// We also need a way for single-column widgets to get and set
// individual values in the current row.
public void set(int col, Object val)
{
	// If we try to set the null row, just ignore it, then notify
	// our listener of the "new" (null) value.
	if (curRow != NOROW) {
		tmodel.setValueAt(val, curRow, col);
	}
	fireValueChanged(col);
}
public Object get(int col)
{
System.out.println("   Xxtmodel = " + tmodel);
	if (curRow == NOROW) return null;
	return tmodel.getValueAt(curRow, col);
}
public int getColCount()
	{ return tmodel.getColumnCount(); }
// ------------------------------------------------------
public int getRowCount()
	{ return tmodel.getRowCount(); }
public int getCurRow()
	{ return curRow; }

/** Results in the listeners being called, if the row changes. */
public void setCheckCurRow(int row)
{
	if (row != curRow) {
		setCurRow(row);
//		fireAllValuesChanged();		// We should probably just do one or the other
	}
}
/** Results in the listeners being called. */
public void setCurRow(int row)
{
	int old = curRow;
	curRow = row;
	fireCurRowChanged();
	fireListSelectionListeners(old);
}
// ==========================================================
// TableModelListener Implementation...
public void tableChanged(TableModelEvent e)
{
//System.out.println("TableRowModel.tableChanged: " + e.getType());
	switch(e.getType()) {
		case TableModelEvent.INSERT : if (listenTableModel) {
			if (curRow == -1) {
				// We weren't looking at any row: look at the first
				// row just inserted.
				setCurRow(e.getFirstRow());
				//curRow = e.getFirstRow();
				//fireAllValuesChanged();
			} else {
				// Maintain ourselves at the same row in the table.
				// No (visible) change.  Thus, fireCurRowChanged()
				// need not be called.
				if (curRow >= e.getFirstRow()) {
					curRow += (e.getLastRow() - e.getFirstRow() + 1);
					// We're still on same row, no need to call listeners.
				}
			}
		} break;
		case TableModelEvent.DELETE : if (listenTableModel) {
			if (curRow > e.getLastRow()) {
				curRow -= (e.getLastRow() - e.getFirstRow() + 1);
				// We're still on same row, no need to call listeners.
			} else if (curRow >= e.getFirstRow()) {
				// Our row was deleted, we're forced to move to a different row
				int row = e.getFirstRow() - 1;
				if (row < 0) row = MultiRowModel.NOROW;
				setCurRow(row);
				// curRow = e.getFirstRow()-1;
				// fireCurRowChanged();
				// fireAllValuesChanged();
			}
		} break;
		case TableModelEvent.UPDATE :
			if (e.getFirstRow() <= curRow && curRow <= e.getLastRow()) {
				// Our row has changed.
				if (e.getColumn() == TableModelEvent.ALL_COLUMNS) {
					fireAllValuesChanged();
				} else {
					fireValueChanged(e.getColumn());
				}
			}
		break;
//		default :
//			System.out.println("TableRowModel.tableChanged: " + e.getType());
//		break;
	}
}

// ================================================================================
	/** Change the selection to be the set union of the current selection and the indices between index0 and index1 inclusive. */
	public void  addSelectionInterval(int index0, int index1)
		{ setCheckCurRow(index0); }
	/** Change the selection to the empty set. */
	public void  clearSelection()
		{ setCheckCurRow(NOROW); }
	/** Return the first index argument from the most recent call to setSelectionInterval(), addSelectionInterval() or removeSelectionInterval(). */
	public int  getAnchorSelectionIndex() {
		return curRow; }
	/** Return the second index argument from the most recent call to setSelectionInterval(), addSelectionInterval() or removeSelectionInterval(). */
	public int  getLeadSelectionIndex() {
		return curRow; }
	/** Returns the last selected index or NOROW if the selection is empty. */
	public int  getMaxSelectionIndex() {
		return curRow; }
	/** Returns the first selected index or NOROW if the selection is empty. */
	public int  getMinSelectionIndex() {
		return curRow; }
	/** Returns the current selection mode. */
	public int  getSelectionMode() {
		return SINGLE_SELECTION; }
	
	// ---------------------------------------------------
	boolean valueIsAdjusting = false;
	/** Returns true if the value is undergoing a series of changes. */
	public boolean  getValueIsAdjusting() {
		return valueIsAdjusting; }
	/** This property is true if upcoming changes to the value of the model should be considered a single event. */
	public void  setValueIsAdjusting(boolean valueIsAdjusting) {
		this.valueIsAdjusting = valueIsAdjusting; }
	// ---------------------------------------------------
	
	/** Insert length indices beginning before/after index. */
	public void  insertIndexInterval(int index, int length, boolean before) {
		// First row of inserted region (in new numbering scheme)
		int firstRow = (before ? index-1 : index+1);
		if (curRow == NOROW) {
			// We weren't looking at any row: look at the first
			// row just inserted.
			setCurRow(firstRow);
		} else {
			// Maintain ourselves at the same row in the table.
			// No (visible) change.  Thus, fireCurRowChanged()
			// need not be called.
			if (curRow >= firstRow) {
				curRow += length;
				// We're still on same row, no need to call listeners.
			}
		}
	}
	/** Remove the indices in the interval index0,index1 (inclusive) from the selection model. */
	public void  removeIndexInterval(int index0, int index1) {
		if (curRow > index1) {
			curRow -= (index1 - index0 + 1);
			// We're still on same row, no need to call listeners.
		} else if (curRow >= index0) {
			// Our row was deleted, we're forced to move to a different row
			int row = index0 - 1;
			if (row < 0) row = MultiRowModel.NOROW;
			setCurRow(row);
		}
	}
	// ---------------------------------------------------
	
	/** Returns true if the specified index is selected. */
	public boolean  isSelectedIndex(int index) {
		return index == curRow; }
	/** Returns true if no indices are selected. */
	public boolean  isSelectionEmpty() {
		return curRow == NOROW; }
	
	/** Change the selection to be the set difference of the current selection and the indices between index0 and index1 inclusive. */
	public void  removeSelectionInterval(int index0, int index1) {
		if (curRow >= index0 && curRow <= index1) setCurRow(NOROW);
	}
	
	/** Set the anchor selection index. */
	public void  setAnchorSelectionIndex(int index) {
		setCheckCurRow(index); }
	/** Set the lead selection index. */
	public void  setLeadSelectionIndex(int index) {
		setCheckCurRow(index); }
	/** Change the selection to be between index0 and index1 inclusive. */
	public void  setSelectionInterval(int index0, int index1) {
		setCheckCurRow(index0); }
	/** Set the selection mode. */
	public void  setSelectionMode(int selectionMode) {}
	
	// ============================================================
	LinkedList selListeners= new LinkedList();

	/** Add a listener to the list that's notified each time a change to the selection occurs. */
	public void  addListSelectionListener(ListSelectionListener x)
		{ selListeners.add(x); }

	/** Remove a listener from the list that's notified each time a change to the selection occurs. */
	public void  removeListSelectionListener(ListSelectionListener x)
		{ selListeners.remove(x); }
	
	void fireListSelectionListeners(int oldCurRow)
	{
		// Construct the event
		int min,max;
		if (curRow == -1 && oldCurRow == -1) return;	// No change
		if (oldCurRow == -1) min = max = curRow;
		else if (curRow == -1) min = max = oldCurRow;
		else {
			min = (curRow < oldCurRow ? curRow : oldCurRow);
			max = (curRow > oldCurRow ? curRow : oldCurRow);
		}
		ListSelectionEvent lse = new ListSelectionEvent(
			this, min, max,  valueIsAdjusting);
		
		// Fire the event
		for (Iterator ii=selListeners.iterator(); ii.hasNext(); ) {
			ListSelectionListener l = (ListSelectionListener)ii.next();
			l.valueChanged(lse);
		}
	}
}
