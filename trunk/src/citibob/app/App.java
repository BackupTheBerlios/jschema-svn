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

public interface App
{

public Properties getProps();
public ConnPool getPool();
public ExpHandler getExpHandler();
/** Allow to log all database changes */
public citibob.jschema.log.QueryLogger getLogger();
/** Directory containing configuration files, etc. for this application. */
public java.io.File getConfigDir();

/** Runs an action started from a specific Swing component. */
public void runGui(java.awt.Component c, CBRunnable r);
/** Only runs the action if logged-in user is a member of the correct group */
public void runGui(java.awt.Component c, String permissionGroup, CBRunnable r);
public void runGui(java.awt.Component c, String[] permissionGroups, CBRunnable r);
public void runApp(CBRunnable r);
//public ActionRunner getGuiRunner();
public ActionRunner getAppRunner();		// Useful for some things that need it.
public MailSender getMailSender();
public SwingerMap getSwingerMap();
public SwingPrefs getSwingPrefs();
public void setUserPrefs(java.awt.Component c, String base);
public citibob.text.SFormatterMap getSFormatterMap();
public SchemaSet getSchemaSet();
public Schema getSchema(String name);	// Get schema by name
public citibob.reports.Reports getReports();

/** Get default conversion between database types and SqlType objects */
public citibob.sql.SqlTypeSet getSqlTypeSet();
/** @returns Root user preferences node for this application */
public java.util.prefs.Preferences userRoot();

/** @returns Root system preferences node for this application */
public java.util.prefs.Preferences systemRoot();

public TimeZone getTimeZone();

//protected ConnPool pool;
//protected SwingerMap swingerMap;
//protected ActionRunner guiRunner;		// Run user-initiated actions; when user hits button, etc.
//	// This will put on queue, etc.
//protected ActionRunner appRunner;		// Run secondary events, in response to other events.  Just run immediately
//protected MailSender mailSender;	// Way to send mail (TODO: make this class MVC.)
// -------------------------------------------------------
//public ConnPool getPool() { return pool; }
//public ActionRunner getGuiRunner() { return guiRunner; }
//public ActionRunner getAppRunner() { return appRunner; }
//public MailSender getMailSender() { return mailSender; }
//public SwingerMap getSwingerMap() { return swingerMap; }

}
