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
import citibob.swing.typed.SwingerMap;
import java.sql.*;
import java.util.*;
import citibob.sql.*;
import citibob.multithread.*;
import citibob.swing.typed.*;
import citibob.mail.*;
import javax.mail.internet.*;
import citibob.jschema.*;
import citibob.swing.prefs.*;

public abstract class App
{

// =================================================================
// Configuration, Properts and Preferences
/** Directory containing configuration files, etc. for this application. */
public java.io.File getConfigDir() { return null; }
/** Gets properties loaded from an application configuration file. */
public Properties getProps() { return null; }
public SwingPrefs getSwingPrefs() { return null; }
public void setUserPrefs(java.awt.Component c, String base)
	{ getSwingPrefs().setPrefs(c, userRoot().node(base)); }

/** @returns Root user preferences node for this application */
public java.util.prefs.Preferences userRoot() { return null; }

/** @returns Root system preferences node for this application */
public java.util.prefs.Preferences systemRoot() { return null; }
// =================================================================
// Connection Pools, Exception Handlers and Runners
/** Connection pool of the default database */
public ConnPool getPool() { return null; }
public SqlBatchSet getBatchSet() { return null; }
public void pushBatchSet() {}
public void popBatchSet() throws Exception {}

/** Handler for all unhandled exceptions */
public ExpHandler getExpHandler() { return null; }

public ActionRunner getAppRunner() { return null; }		// Useful for some things that need it.

protected ActionRunner getGuiRunner() { return null; }
/** Runs an action started from a specific Swing component. */
public void runGui(java.awt.Component c, CBRunnable r)
	{ getGuiRunner().doRun(r); }
public void runApp(CBRunnable r)
	{ getAppRunner().doRun(r); }

/** Only runs the action if logged-in user is a member of the correct group */
public void runGui(java.awt.Component c, String permissionGroup, CBRunnable r) {  }
public void runGui(java.awt.Component c, String[] permissionGroups, CBRunnable r) {  }
// ============================================================================
// Type conversions, Swingers, SFormats
/** Override this. */
public SchemaSet getSchemaSet() { return null; }
public Schema getSchema(String name)
	{ return getSchemaSet().get(name); }

public SwingerMap getSwingerMap() { return null; }
public citibob.text.SFormatMap getSFormatMap() { return null; }
/** Get default conversion between database types and SqlType objects */
public citibob.sql.SqlTypeSet getSqlTypeSet() { return null; }
public DbChangeModel getDbChange() { return null; }

// ============================================================================
// Misc
/** Allow to log all database changes */
public citibob.jschema.log.QueryLogger getLogger() { return null; }

public MailSender getMailSender() { return null; }

/** Object for report generation */
public citibob.reports.Reports getReports() { return null; }

/** Default time zone for desktop application */
public TimeZone getTimeZone() { return null; }

// =======================================================================

}
