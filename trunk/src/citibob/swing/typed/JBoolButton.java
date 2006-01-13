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

import javax.swing.*;

/**
 * A boolean checkbox that allows Y/N/null.
 * @author  citibob
 */
public class JBoolButton extends JButton implements TypedWidget {
	
	Boolean val;

	String trueLabel = "Y";
	String falseLabel = "N";
	
	public void setValue(Object d)
	{
		if (d != null && d.getClass() != Boolean.class)
			throw new ClassCastException("Expected Boolean");
		val = (Boolean)d;
		setText(val == null ? "-" :
			val.booleanValue() ? trueLabel : falseLabel);
	}
	public void setValue(boolean b)
		{ setValue(new Boolean(b)); }
	
	public Object getValue()
		{ return val; }
	public Class getObjClass()
		{ return Boolean.class; }
	public void resetValue() {}
	public void setLatestValue() {}
	public boolean isValueValid() { return true; }

	public JBoolButton()
	{
		super();
        setText("-");
		setMargin(new java.awt.Insets(2, 2, 2, 2));
        addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
			if (val == null) setValue(true);
			else if (!val.booleanValue()) setValue(null);
			else setValue(false);
        }});
	}
}
