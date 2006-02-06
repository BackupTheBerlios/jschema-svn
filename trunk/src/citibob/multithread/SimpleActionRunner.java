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
*//*
 * SimpleActionRunner.java
 *
 * Created on January 29, 2006, 7:51 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.multithread;

import java.sql.*;

/**
 *
 * @author citibob
 */
public class SimpleActionRunner implements ActionRunner
{

Connection dbb;

public SimpleActionRunner(Connection dbb)
{
	this.dbb = dbb;
}
	
public void run(ERunnable r)
{
	try {
		r.run();		
	} catch(Throwable e) {
		e.printStackTrace();
	}
}

public void run(StRunnable r)
{
	Statement st = null;
	try {
		st = dbb.createStatement();
		r.run(st);
	} catch(Throwable e) {
		e.printStackTrace();
	} finally {
		try {
			if (st != null) st.close();
		} catch(SQLException se) {}
	}
}
	
public void run(DbRunnable r)
{
	try {
		r.run(dbb);
	} catch(Throwable e) {
		e.printStackTrace();
	}
}

}
