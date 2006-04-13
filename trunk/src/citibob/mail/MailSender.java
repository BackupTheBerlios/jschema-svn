package citibob.mail;

import java.util.*;
import java.io.*;
//import gnu.net.local.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.prefs.*;

public interface MailSender
{
public static final int SENT = 0;			// message sent successfully
public static final int CANCELLED = 1;		// User chose not to send
public static final int FAILED = 2;			// Could not send
// ====================================================
	public Session getSession();
	public int sendMessage(MimeMessage msg) throws Exception;
}
