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
package citibob.swing.typed;

import citibob.sql.*;
import citibob.types.JType;
import citibob.types.JavaJType;
import javax.swing.text.*;
import java.text.*;
import citibob.swing.typed.*;
import citibob.sql.pgsql.*;
import static citibob.swing.typed.JTypedTextField.*;

/**
 *
 * @author citibob
 */
public class JIntegerSwinger extends TypedTextSwinger
{

NumberFormat nf;
 
public JIntegerSwinger(JType jType)
{ this(jType, NumberFormat.getIntegerInstance()); }

public JIntegerSwinger(JType jType, NumberFormat nf)
{
	super(jType);
	this.nf = nf;
}

public JIntegerSwinger()
	{ this(true); }
public JIntegerSwinger(boolean nullable)
{
	super(new JavaJType(Integer.class, nullable));
	nf = new java.text.DecimalFormat("#");
}
	
///** Creates a new instance of TypedWidgetSTFactory */
//public SqlIntegerSwinger(SqlInteger sqlType) {
//	super(sqlType);
//}
//
//public boolean renderWithWidget() { return false; }
//
///** Create a widget suitable for editing this type of data. */
//protected citibob.swing.typed.TypedWidget createTypedWidget()
//	{ return new JTypedTextField(); }


/** Creates an AbstractFormatterFactory for a JFormattedTextField.  If this
 SqlType is never to be edited with a JFormattedTextField, it can just
 return null.  NOTE: This should return a new instance of AbstractFormatterFactory
 because one instance is required per JFormattedTextField.  It's OK for the
 factory to just store instances of 4 AbstractFormatters and return them as needed. */
public javax.swing.text.DefaultFormatterFactory newFormatterFactory()
{
	NumberFormatter nff = new NumberFormatter(nf);
	return citibob.swing.typed.JTypedTextField.newFormatterFactory(nff);
}
//import static citibob.swing.typed.JTypedTextField.*;


}
