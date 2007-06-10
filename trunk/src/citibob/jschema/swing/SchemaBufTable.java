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
