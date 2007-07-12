/*
JSchema: library for GUI-based database applications
This file Copyright (c) 2006-2007 by Robert Fischer

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
/*
 * SchemaBufTable.java
 *
 * Created on July 9, 2006, 2:17 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.jschema.swing;

import citibob.swing.*;
import citibob.jschema.*;
import javax.swing.table.*;
import javax.swing.event.*;
import citibob.multithread.*;
import java.sql.*;

/**
 *
 * @author citibob
 */
public class SchemaBufTable extends JTypeColTable
{

SchemaBufDbModel dbModel;

public void setDbModelU(SchemaBufDbModel dbModel,
String[] colNames, String[] sColMap, boolean[] editable,
citibob.swing.typed.SwingerMap swingers)
{
	this.dbModel = dbModel;
	super.setModelU(dbModel.getSchemaBuf(), colNames, sColMap, editable, swingers);
}

/** Creates a SubSchema & SchemaBuf for this table.
 @param colNames Display name of each column.  If null, the column will be
 included in the SubSchema but will not be displayed in the TableModel
 @param sColMap Name of each column in the Schema. */
public SchemaBuf setSubSchema(Schema schema,
String[] colNames, String[] sColMap, boolean[] editable,
citibob.swing.typed.SwingerMap swingers)
{	
	Schema subSchema = new SubSchema(schema, sColMap);
	SchemaBuf buf = new SchemaBuf(subSchema);
	super.setModelU(buf, colNames, sColMap, editable, swingers);
	return buf;
}

/** Creates a SubSchema &amp; SchemaBuf &amp; SchemaBufDbModel for this table.
 @param colNames Display name of each column.  If null, the column will be
 included in the SubSchema but will not be displayed in the TableModel
 @param sColMap Name of each column in the Schema. */
public SchemaBufDbModel setSubSchemaDm(Schema schema,
String[] colNames, String[] sColMap, boolean[] editable,
citibob.swing.typed.SwingerMap swingers)
{
	SchemaBuf sb = setSubSchema(schema, colNames, sColMap, editable, swingers);
	return new SchemaBufDbModel(sb);
}

///** Does an update on the underlying 
//public void doUpdate(java.sql.Statment st)

}
