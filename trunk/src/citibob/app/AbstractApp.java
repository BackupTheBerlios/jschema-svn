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
