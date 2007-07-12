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
*//*
 * ERunnable.java
 *
 * Created on January 29, 2006, 7:50 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.multithread;

/**
 * A runnable with addiontal stuff..
 */
public class CBTask
{
String name;
CBRunnable runnable;
java.util.Date dTime;
String[] permissions;		// Describes who can and cannot run this task.

public CBTask(String name, String[] permissions, CBRunnable runnable)
{
	this.name = name;
	this.runnable = runnable;
	this.dTime = new java.util.Date();	
	this.permissions = permissions;
}
public CBTask(String name, CBRunnable runnable)
{
	this(name, (String[])null, runnable);
}	
public CBTask(String name, String permission, CBRunnable runnable)
{
	this(name, new String[]{ permission}, runnable);
}

public String[] getPermissions() { return permissions; }
public String getName() { return name; }
public java.util.Date getDTime() { return dTime; }
public CBRunnable getCBRunnable() { return runnable; }

}
