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

public interface RowStatusConst
{

	// Data status bits

	// Data was inserted into data model but not yet into database.
	public static final int INSERTED = 1;

	// Data was deleted from data model, but not yet from database.
	public static final int DELETED = 2;

	// A column was changed in data model, not yet written back to database.
	public static final int CHANGED = 4;
}
