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
 * TimeKeyedModel.java
 *
 * Created on February 11, 2006, 11:48 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.jschema;

//import java.sql.*;
import java.text.*;
import java.util.Date;
import java.util.*;
import citibob.util.KeyedModel;

/**
 * Sets up a keyed model for days of the week, 1 = Sunday (Java convention)
 * @author citibob
 */
public class DayOfWeekKeyedModel extends KeyedModel
{

static DateFormat fmt = new SimpleDateFormat("HH:mm");

/** Creates a new instance of TimeKeyedModel */
public DayOfWeekKeyedModel()
{
	this.addItem(new Integer(1), "Sun");
	this.addItem(new Integer(2), "Mon");
	this.addItem(new Integer(3), "Tue");
	this.addItem(new Integer(4), "Wed");
	this.addItem(new Integer(5), "Thr");
	this.addItem(new Integer(6), "Fri");
	this.addItem(new Integer(7), "Sat");
}

}
