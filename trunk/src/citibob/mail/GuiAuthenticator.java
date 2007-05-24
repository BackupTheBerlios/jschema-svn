package citibob.mail;

import javax.mail.*;

import java.util.prefs.*;
import javax.mail.*;
//import java.net.*;
import javax.mail.internet.*;
import org.solinger.cvspass.Scramble;

public class GuiAuthenticator extends Authenticator
{
public static final int OK = 0;
public static final int CANCEL = 1;

int buttonPressed;
String statusMessage = "";
String lastPassword = "";
String nodePath;
String guiNodePath;

public GuiAuthenticator(String nodePath, String guiNodePath)
{
	this.nodePath = nodePath;
	this.guiNodePath = nodePath;
}

public int getButtonPressed()
	{ return buttonPressed; }

// Sets an "extra" message to be displayed to the user.
public void setStatusMessage(String statusMessage)
{
	this.statusMessage = statusMessage;
}

protected  PasswordAuthentication getPasswordAuthentication()
{
	// Preferences p = Preferences.userNodeForPackage(this.getClass());
	Preferences prefs = Preferences.userRoot();
	prefs = prefs.node(nodePath);
	String host = prefs.get("mail.smtp.host", "<nohost>");
	String username = prefs.get("mail.smtp.user", "<nouser>");
	String password = prefs.get("mail.password", null);
	if (password.startsWith("A")) password =
		Scramble.descramble(prefs.get("mail.password", null));
	boolean rememberPassword = prefs.getBoolean("mail.rememberPassword", false);

	AuthenticatorDialog d = new AuthenticatorDialog(null,
		host, username,
		password == null ? 0 : password.length(),
		rememberPassword, statusMessage);
	d.show();

	if (!d.getOK()) {
		buttonPressed = CANCEL;
System.err.println("x CANCEL");
		return null;	// User cancelled.
	}
	buttonPressed = OK;
System.err.println("x OK");

	if (d.getPasswordChanged()) password = d.getPassword();

	rememberPassword = d.getRememberPassword();
	prefs.putBoolean("mail.rememberPassword", rememberPassword);
	if (rememberPassword) prefs.put("mail.password", Scramble.scramble(password));
	else prefs.remove("mail.password");

	return new PasswordAuthentication(username, password);
}


}
