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
 * TemplateTableModel.java
 *
 * Created on August 11, 2007, 10:04 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.reports;

import freemarker.template.*;
import javax.swing.table.TableModel;

/**
 * Converts a StringTableModel to a Freemarker template.
 * @author citibob
 */
public class TemplateTableModel implements TemplateSequenceModel
{
	
StringTableModel model;

/** Wraps a TableModel for JodReports. */
   public TemplateTableModel(StringTableModel model)
{
	this.model = model;
}
	
public TemplateModel get(int index)
{
	return new RowModel(index);
}

public int size() 
{
	return model.getRowCount();
} 
// ============================================================
class RowModel implements TemplateHashModel {
	int row;
	public RowModel(int row) { this.row = row; }
	public TemplateModel get(java.lang.String key) {
		return new EleModel((String)model.getValueAt(row, model.findColumn(key)));
	}
	public boolean 	isEmpty() { return model.getColumnCount() == 0; }
}
// ============================================================
class EleModel implements TemplateScalarModel {
	String val;
	public EleModel(String val) { this.val = val; }
	public String getAsString() { return val; }
}
}
