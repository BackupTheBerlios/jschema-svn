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
 * AbstractApp.java
 *
 * Created on June 8, 2007, 7:30 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.app;

import java.sql.*;
import java.util.*;
import citibob.sql.*;
import citibob.multithread.*;
import citibob.swing.typed.*;
import citibob.mail.*;
import javax.mail.internet.*;
import citibob.jschema.*;
import citibob.swing.prefs.*;

public abstract class AbstractApp implements App
{
	public void setUserPrefs(java.awt.Component c, String base)
	{
		getSwingPrefs().setPrefs(c, userRoot().node("editterms"));
	}
}
