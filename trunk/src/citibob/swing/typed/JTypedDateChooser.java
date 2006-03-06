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
/*
 * JDate.java
 *
 * Created on May 14, 2003, 8:52 PM
 */

package citibob.swing.typed;

import java.text.DateFormat;
import java.util.Date;
import javax.swing.*;
import citibob.swing.calendar.*;
//import com.toedter.components.*;

/**
 * TODO: This class need sthe once-over!
 * @author  citibob
 * Doesn't hold NULL values.
 */
public class JTypedDateChooser extends JDateChooser implements TypedWidget {

	
ObjModel model;
public ObjModel getObjModel() { return model; }
public void setObjModel(ObjModel m) { model = m; }

	public JTypedDateChooser(String fmt)
	{
		super();
//		super(fmt, true);
		//setDateFormatString(fmt);
	}
	
	public void setValue(Object d)
	{
		if (d.getClass() != Date.class)
			throw new ClassCastException("Expected java.util.Date");
		Date val = (Date)d;
System.out.println("JTypedDateChooser: Setting date to " + val + "(" + this + ")");
		// TODO: Bug in JDateChooser doesn't work well with null dates.
		// It gets confused when embedded in a table.
//		if (val == null) val = new Date();
		getModel().setTime(val);
	}
	
	public Object getValue()
		{ return getModel().getCal().getTime(); }
	public Class getObjClass()
		{ return Date.class; }
	public void resetValue() {}
	public void setLatestValue() {}
	public boolean isValueValid() { return true; }

}
