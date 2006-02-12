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
 * CalModel.java
 *
 * Created on February 12, 2006, 12:15 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.swing.calendar;


import java.util.*;

/**
 * Serves as the model class for the JCalendar editor --- both standalone and dropdown (in JDateChooser).
 * @author citibob
 */
public class CalModel extends CalModelMVC
{

Calendar cal, finalCal;

/** Wraps a Calendar object. */
public CalModel(Calendar cal)
{
	this.cal = cal;
	finalCal = (Calendar)cal.clone();
}
public Calendar getCal()
{ return cal; }
public Calendar getFinalCal()
{ return finalCal; }
// ===========================================================
/** Make the current value final. */
public void saveFinal()
{
	finalCal = (Calendar)cal.clone();
	fireFinalChanged();
}
/** Restore the current value from the final value */
public void restoreFinal()
{
	cal = finalCal;
	fireChanged();
}
// ===========================================================
// ==== Stuff from Calendar
public void  add(int field, int amount)
{
	cal.add(field, amount);
	fireChanged();
}
public void clear()
{
	cal.clear();
	fireChanged();
}
public void clear(int field) 
{
	cal.clear();
	fireChanged();
}
public void  roll(int field, boolean up)
{
	cal.roll(field,up);
	fireChanged();
}
public   void  roll(int field, int amount) 
{
	cal.roll(field,amount);
	fireChanged();
}
public   void  set(int field, int value) 
{
	cal.set(field,value);
	fireChanged();
}
public   void  set(int year, int month, int date) 
{
	cal.set(year,month,date);
	fireChanged();
}
public   void  set(int year, int month, int date, int hour, int minute) 
{
	cal.set(year,month,date,hour,minute);
	fireChanged();
}
public   void  set(int year, int month, int date, int hour, int minute, int second) 
{
	cal.set(year,month,date,hour,minute,second);
	fireChanged();
}
public   void  setTime(Date date)
{
	cal.setTime(date);
	fireChanged();
}
   void  setTimeInMillis(long millis) 
{
	cal.setTimeInMillis(millis);
	fireChanged();
}
	
}
