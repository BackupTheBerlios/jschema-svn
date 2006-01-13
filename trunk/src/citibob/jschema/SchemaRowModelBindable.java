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
/** Promises that an object can be bound to a partiular column in a RowModel (which must be based on a Schema in this case). */
public interface SchemaRowModelBindable
{

/** What column should we be bound to? */
String getColName();

void setColName(String s);

/** Do whatever you do to bind to a the named column in the schema set. */
void bind(SchemaRowModel bufRow);

}
