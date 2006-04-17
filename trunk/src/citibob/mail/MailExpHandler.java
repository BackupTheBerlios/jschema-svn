/*
 * MailExpHandler.java
 *
 * Created on April 12, 2006, 11:06 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.mail;

import citibob.multithread.*;
import java.io.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.net.*;
import javax.swing.text.*;

/**
 *
 * @author citibob
 */
public class MailExpHandler implements ExpHandler
{
	MailSender sender;
	InternetAddress bugRecipient;
	String programName;
	Document stdoutDoc;
	
	public MailExpHandler(MailSender sender, InternetAddress bugRecipient,
	String programName, Document stdoutDoc)
	{
		this.stdoutDoc = stdoutDoc;
		this.sender = sender;
		this.bugRecipient = bugRecipient;
		this.programName = programName;
	}
	public void consume(Throwable e)
	{
		// Get the last bit of the Java console stdout
		String outMsg = "";
		if (stdoutDoc != null) {
			try {
				int nsave = 4000;
				int docLen = stdoutDoc.getLength();
				int start = docLen - nsave;
				if (start < 0) start = 0;
				outMsg = stdoutDoc.getText(start, docLen - start);
			} catch(BadLocationException ee) {
			}
		}
		
		// Get the stack trace
		StringWriter ss = new StringWriter();
		PrintWriter pw = new PrintWriter(ss);
		e.printStackTrace(pw);
		String msgText = outMsg + "\n\n" + ss.getBuffer().toString();
		System.out.println(pw);
		System.err.println(msgText);
		
		// Let user fiddle with the stack trace
		final MailExpDialog dialog = new MailExpDialog(null, sender, msgText,"citibob/mail");
		dialog.setVisible(true);
		if (!dialog.getOK()) return;
		

		new Thread() {
		public void run() {
			try {
				// Define message
				MimeMessage msg = new MimeMessage(sender.getSession());
				//msg.setFrom(new InternetAddress("citibob@earthlink.net"));
				msg.setSubject("Bug in " + programName);
				msg.setText(dialog.getMsg());
				msg.addRecipient(Message.RecipientType.TO, bugRecipient);
				
				sender.sendMessage(msg);
			} catch(Exception ee) {
				System.out.println("Could not send bug report!!!");
				ee.printStackTrace(System.out);
			}
		}}.start();
	}

	/** Creates a new instance of MailExpHandler */
	public MailExpHandler() {
	}
	
}
