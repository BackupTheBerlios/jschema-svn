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
package citibob.jschema;

import citibob.swing.RowModel;

 /** A row model that is attached to data associated with one Schema.  The columns in the RowModel must match EXACTLY the columns in the schema.  Thus, schema.findCol() returns column indices that are relevant to the SchemaRowModel. */
public interface SchemaRowModel extends RowModel
{
	Schema getSchema();
	Object getOrigValue(int col);
	int getCurRow();
}
