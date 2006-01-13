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

import citibob.swing.typed.JNullTextField;
import citibob.swing.typed.JTypedTextField;
import citibob.exception.*;
import citibob.jschema.*;
import citibob.swing.*;

/**
 *
 * @author  citibob
 */
public class JSchemaTextField
extends JTypedTextField
implements RowModel.ColListener, SchemaRowModelBindable
{

SchemaRowModel bufRow;
int colNo;
String colName;

// --------------------------------------------------------------
public String getColName()
	{ return colName; }
public void setColName(String s)
	{ colName = s; }
// --------------------------------------------------------------
public JSchemaTextField(SchemaRowModel bufRow, String colName)
{
	super();
	this.colName = colName;
	bind(bufRow);
}
public JSchemaTextField()
	{ super(); }
protected void finalize()
{
	bufRow.removeColListener(colNo, this);
	bufRow = null;
}
// --------------------------------------------------------------
/** Binds this widget to listen/edit a particular column in a RowModel, using the type for that column derived from the associated Schema.  NOTE: This requires a correspondence in the numbering of columns in the Schema and in the RowModel.  No permutions inbetween are allowed!  This should not be a problem, just make sure the TableRowModel binds DIRECTLY to the source SchemaBuf, not to some permutation thereof. */
public void bind(SchemaRowModel bufRow)
{
	Schema schema = bufRow.getSchema();
	colNo = schema.findCol(getColName());

//System.out.println("Column " + getColName() + " bound to #" + colNo);
	/* Set ourselves up with the correct data type formatter / validater */
	setTextConverter(schema.getCol(colNo).getType().getTextConverter());
//System.out.println("type = " + schema.getCol(colNo).getType());
//System.out.println("textConverter set to " + getTextConverter());
	/* Bind as a listener to the RowModel (which fronts a SchemaBuf)... */
	this.bufRow = bufRow;
	bufRow.addColListener(colNo, this);

	/* Now, set the initial value. */
	valueChanged(colNo);
}

/** Propagate change in widget value to underlying RowModel. */
public void setValue(Object o)
{
	super.setValue(o);
	if (bufRow != null) bufRow.set(colNo, o);
}
public void resetValue()
{
	if (bufRow != null) setValue(bufRow.getOrigValue(colNo));
}
// ===============================================================
// Implementation of RowModel.Listener
/** Propagate change in underlying RowModel to widget value. */
public void valueChanged(int col)
{
//System.out.println("valueChanged(" + col + ") = " + bufRow.get(col));
	super.setValue(bufRow.get(col));
}
public void curRowChanged(int col)
{
	int row = bufRow.getCurRow();
	setEnabled(row != MultiRowModel.NOROW);
	setValue(row == MultiRowModel.NOROW ? null : bufRow.get(col));
}
}
