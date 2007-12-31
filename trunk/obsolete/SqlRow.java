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
package citibob.jschema;

class SqlRow
{
	/** Status bits; @see DbModel. */
	int status = 0;

	Object[] data;
	/** The "original" version of this data.  Used to see if values have changed. */
	Object[] origData;
	public SqlRow(int ncols)
	{
		data = new Object[ncols];
		origData = new Object[ncols];
	}

	public int getStatus()
		{ return status; }
	public void setStatus(int status)
		{ this.status = status; }
	public Object get(int col)
		{ return data[col]; }
/*
	public void set(int col, Object o)
		{ data[col] = o; }
	public Object getOrig(int col)
		{ return origData[col]; }
*/
}
