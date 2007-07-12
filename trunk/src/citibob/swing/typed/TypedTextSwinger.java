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
 * TypedWidgetSTFactory.java
 *
 * Created on March 18, 2006, 6:14 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.swing.typed;

import citibob.sql.*;
import javax.swing.text.*;
import java.text.*;
import citibob.swing.typed.*;
import citibob.sql.pgsql.*;

/**
 * Base class for Swingers that involve formatted JTypedTextFields...
 * Subclasses must implement newFormatterFactory().
 * @author citibob
 */
public abstract class TypedTextSwinger extends TypedWidgetSwinger
{

/** Creates a new instance of TypedWidgetSTFactory */
public TypedTextSwinger(JType jType) {
	super(jType);
}

/** Display this type as a simple text label.  Do NOT use some fancy widget to display it. */
public boolean renderWithWidget() { return false; }

/** Create a widget suitable for editing this type of data. */
protected citibob.swing.typed.TypedWidget createTypedWidget()
	{ return new JTypedTextField(); }




/** Creates an AbstractFormatterFactory for a JFormattedTextField.  If this
 SqlType is never to be edited with a JFormattedTextField, it can just
 return null.  NOTE: This should return a new instance of AbstractFormatterFactory
 because one instance is required per JFormattedTextField.  It's OK for the
 factory to just store instances of 4 AbstractFormatters and return them as needed. */
//public javax.swing.text.DefaultFormatterFactory newFormatterFactory()
//{
//	SqlInteger tt = (SqlInteger)jType;
//
//	NumberFormat nf = NumberFormat.getIntegerInstance();
//	NumberFormatter nff = new NumberFormatter(nf);
//	return new DefaultFormatterFactory(nff);
//}


}
