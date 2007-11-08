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
package citibob.text;

import citibob.sql.*;
import citibob.swing.typed.*;
import citibob.swing.table.JTypeTableModel;
import citibob.types.JType;
import citibob.types.JavaJType;
import java.util.*;
import java.text.*;


/**
 * Maps SqlType objects to various formatters, etc. for String output.
 * This is a lot like SwingerMap, but much simpler.
 * @author citibob
 */
public interface SFormatMap
{

public SFormat newSFormat(JType t);
public SFormat[] newSFormats(JTypeTableModel model);
public SFormat[] newSFormats(JTypeTableModel model, String[] scol, SFormat[] sfmt);


}
