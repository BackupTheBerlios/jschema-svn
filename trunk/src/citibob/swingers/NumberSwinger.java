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
package citibob.swingers;

import citibob.sql.*;
import citibob.types.JType;
import citibob.types.JavaJType;
import javax.swing.text.*;
import java.text.*;
import citibob.swing.typed.*;
import citibob.sql.pgsql.*;
import static citibob.swing.typed.JTypedTextField.*;
import citibob.text.*;

/**
 *
 * @author citibob
 */
public class NumberSwinger extends TypedTextSwinger
{

public NumberSwinger(JType jType, NumberFormat nf)
	{ super(jType, nf); }


public javax.swing.JFormattedTextField.AbstractFormatter newAbsFormatter()
{
	FormatSFormat sfmt = (FormatSFormat)getSFormat();
	NumberFormatter nff = new NumberFormatter((NumberFormat)sfmt.getFormat());
	return nff;
}


}
