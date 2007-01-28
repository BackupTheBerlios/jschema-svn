package citibob.app;
import java.sql.*;
import java.util.*;
import citibob.sql.*;
import citibob.multithread.*;
import citibob.swing.typed.*;
import citibob.mail.*;
import javax.mail.internet.*;
import citibob.jschema.*;

public interface App
{

public ConnPool getPool();
public void runGui(CBRunnable r);
public void runApp(CBRunnable r);
public MailSender getMailSender();
public SwingerMap getSwingerMap();
public SchemaSet getSchemaSet();
public Schema getSchema(String name);	// Get schema by name
/** @returns Root user preferences node for this application */
public java.util.prefs.Preferences userRoot();

/** @returns Root system preferences node for this application */
public java.util.prefs.Preferences systemRoot();


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
