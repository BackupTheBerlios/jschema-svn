package citibob.mail;

import javax.mail.*;

import java.util.*;
import javax.mail.*;
//import java.net.*;
import javax.mail.internet.*;

public class MailTest
{

public static void main(String[] args) throws Exception
{
System.out.println("Hello");
	String to = "citibob@earthlink.net";

	// Get system properties
//	Properties props = System.getProperties();
	Properties props = new Properties();

	// Setup mail server
	props.put("mail.from", "citibob@earthlink.net");
	props.put("mail.transport.protocol", "smtp");
	props.put("mail.smtp.host", "smtpauth.earthlink.net");
	props.put("mail.smtp.auth", "true");
	props.put("mail.user", "citibob@earthlink.net");

	// Get session
//	Authenticator auth = new GuiAuthenticator();
//	Session session = Session.getDefaultInstance(props, auth);
	GuiMailSender sender = new GuiMailSender();
	
	// Define message
	MimeMessage msg = new MimeMessage(sender.getSession());
	//msg.setFrom(new InternetAddress("citibob@earthlink.net"));
	msg.addRecipient(Message.RecipientType.TO, 
		new InternetAddress(to));
	msg.setSubject("Hello JavaMail");
	msg.setText("Welcome to JavaMail");

	msg.writeTo(System.out);

	// Send message
	sender.sendMessage(msg);
	//Transport.send(msg);

	System.exit(0);
}

}