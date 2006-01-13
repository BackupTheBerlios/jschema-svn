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
/*
 * JDate.java
 *
 * Created on May 14, 2003, 8:52 PM
 */

package citibob.jschema.swing;

import java.text.DateFormat;
import java.util.Date;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

import citibob.swing.typed.*;
import citibob.exception.*;
import citibob.jschema.*;
import citibob.swing.*;
//import citibob.jschema.KeyedModel;

/**
 *
 * @author  citibob
 */
public class JSchemaKeyedButtonGroup
extends KeyedButtonGroup
implements RowModel.ColListener, SchemaRowModelBindable, ActionListener
{

SchemaRowModel bufRow;
int colNo;
String colName;
boolean inValueChanged = false;

// --------------------------------------------------------------
// Override in KeyedButtonGroup
public void add(Object key, AbstractButton b)
{
	super.add(key,b);
	b.addActionListener(this);
}

public AbstractButton remove(Object key)
{
	AbstractButton b = super.remove(key);
	b.removeActionListener(this);
	return b;
}
// -------------------------------------------------------------
public String getColName()
	{ return colName; }
public void setColName(String s)
	{ colName = s; }
// --------------------------------------------------------------
public JSchemaKeyedButtonGroup(SchemaRowModel bufRow, String colName)
{
	super();
	this.colName = colName;
	bind(bufRow);
}
public JSchemaKeyedButtonGroup()
	{ super(); }
protected void finalize()
{
	bufRow.removeColListener(colNo, this);
	bufRow = null;
}
// --------------------------------------------------------------
/** Binds this widget to listen/edit a particular column in a RowModel, using
 * the type for that column derived from the associated Schema.  NOTE: This
 * requires a correspondence in the numbering of columns in the Schema and
 * in the RowModel.  No permutions inbetween are allowed!  This should not
 * be a problem, just make sure the TableRowModel binds DIRECTLY to the source
 * SchemaBuf, not to some permutation thereof. */
public void bind(SchemaRowModel bufRow)
{
	System.out.println("JSKBG.bind");
	colNo = bufRow.getSchema().findCol(getColName());

//	/** Set ourselves up with the correct data type formatter / validater */
//	setTextConverter(schema.getCol(colNo).getType().getTextConverter());

	/** Bind as a listener to the RowModel (which fronts a SchemaBuf)... */
	this.bufRow = bufRow;
	bufRow.addColListener(colNo, this);

	/* Now, set the initial value. */
	valueChanged(colNo);
}


// ===============================================================
// Implementation of RowModel.Listener

/** Propagate data from underlying model to widget. */
public void valueChanged(int col)
{
	inValueChanged = true;
	setValue(bufRow.get(col));
	inValueChanged = false;
}

public void curRowChanged(int col)
{
	int row = bufRow.getCurRow();
	setValue(row == MultiRowModel.NOROW ? null : bufRow.get(col));

	boolean enabled = (row != MultiRowModel.NOROW);
	for (Iterator ii = map.values().iterator(); ii.hasNext(); ) {
		AbstractButton b = (AbstractButton)ii.next();
		b.setEnabled(enabled);
	}
}
// ===============================================================
// Implementation of ActionListener

/** Propagate data from widget to underlying model. */
public void actionPerformed(ActionEvent e)
{
System.out.println("JSchemaKBG.actionPerformed start" + inValueChanged);
	if (bufRow != null && !inValueChanged) {
		AbstractButton b = (AbstractButton)(e.getSource());
//System.out.println(getSelectedItem().getClass());
//		KeyedModel.Item it = (KeyedModel.Item)getSelectedItem();
		Object value = getValue(b);
System.out.println("JSchemaKBG.actionPerformed: " + value + ", " + b);
		if (value == null) bufRow.set(colNo, null);
		else bufRow.set(colNo, value);
	}
}

}

