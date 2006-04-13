package citibob.mail;

import javax.mail.*;

import java.util.prefs.*;
import javax.mail.*;
//import java.net.*;
import javax.mail.internet.*;

public class GuiAuthenticator extends Authenticator
{
public static final int OK = 0;
public static final int CANCEL = 1;

int buttonPressed;
String statusMessage = "";
String lastPassword = "";

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
	Preferences p = Preferences.userNodeForPackage(GuiMailSender.class);
	String host = p.get("mail.smtp.host", "<nohost>");
	String username = p.get("mail.smtp.user", "<nouser>");
	String password = p.get("mail.password", null);
	boolean rememberPassword = p.getBoolean("mail.rememberPassword", false);

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
	p.putBoolean("mail.rememberPassword", rememberPassword);
	if (rememberPassword) p.put("mail.password", password);
	else p.remove("mail.password");

	return new PasswordAuthentication(username, password);
}

}
