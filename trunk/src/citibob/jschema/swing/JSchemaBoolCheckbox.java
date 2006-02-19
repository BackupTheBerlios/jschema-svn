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

import citibob.swing.typed.*;
import citibob.exception.*;
import citibob.jschema.*;
import citibob.jschema.KeyedModel;
import citibob.swing.*;

/**
 *
 * @author  citibob
 */
public class JSchemaBoolCheckbox
extends JBoolCheckbox
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
public JSchemaBoolCheckbox(SchemaRowModel bufRow, String colName)
{
	super();
	this.colName = colName;
	bind(bufRow);
}
public JSchemaBoolCheckbox()
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
	colNo = bufRow.getSchema().findCol(getColName());

//	/** Set ourselves up with the correct data type formatter / validater */
//	setTextConverter(schema.getCol(colNo).getType().getTextConverter());

	/** Bind as a listener to the RowModel (which fronts a SchemaBuf)... */
	this.bufRow = bufRow;
	bufRow.addColListener(colNo, this);
}


// ===============================================================
// Implementation of RowModel.Listener

/** Propagate data from underlying model to widget. */
public void valueChanged(int col)
{
	setValue((Boolean)bufRow.get(col));
}
public void curRowChanged(int col)
{
	int row = bufRow.getCurRow();
	setEnabled(row != MultiRowModel.NOROW);
	setValue(row == MultiRowModel.NOROW ? null : bufRow.get(col));
}

}
