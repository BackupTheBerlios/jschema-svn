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
 * ConfigPanel.java
 *
 * Created on August 17, 2004, 12:21 PM
 */

package citibob.mail;

import citibob.swingers.JIntegerSwinger;
import java.util.prefs.*;
import javax.swing.*;

/**
 *
 * @author  citibob
 */
public class MailPrefsDialog extends javax.swing.JDialog {
   
Preferences prefs;

    /** Creates new form ConfigPanel */
    public MailPrefsDialog(JFrame owner) {
        super(owner, true);     // make it modal
		initComponents();
		
		new JIntegerSwinger().configureWidget(port);
		//this.port.setJType(new citibob.swing.typed.JIntegerSwinger());
		pack();

//		// Mess with preferences
//		Preferences guiPrefs = Preferences.userNodeForPackage(this.getClass());
//		guiPrefs = guiPrefs.node("MailPrefsDialog");
//		new citibob.swing.prefs.SwingPrefs().setPrefs(this, "", guiPrefs);

		
		
		prefs = Preferences.userRoot();
		prefs = prefs.node("citibob/mail");
		readConfig();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        bgUseSSL = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        useNamePasswd = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        serverName = new javax.swing.JTextField();
        userName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        port = new citibob.swing.typed.JTypedTextField();
        bOK = new javax.swing.JButton();
        bReset = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        useSSLNever = new javax.swing.JRadioButton();
        useSSLWhenAvailable = new javax.swing.JRadioButton();
        useSSLAlways = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        yourName = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        emailAddress = new javax.swing.JTextField();
        replyToAddress = new javax.swing.JTextField();
        organization = new javax.swing.JTextField();

        getContentPane().setLayout(new java.awt.GridBagLayout());

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jLabel1.setText("Server Name:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        getContentPane().add(jLabel1, gridBagConstraints);

        jLabel2.setText("Port:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        getContentPane().add(jLabel2, gridBagConstraints);

        useNamePasswd.setText("Use name and password");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        getContentPane().add(useNamePasswd, gridBagConstraints);

        jLabel3.setText("User Name:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        getContentPane().add(jLabel3, gridBagConstraints);

        serverName.setText("jTextField1");
        serverName.setPreferredSize(new java.awt.Dimension(200, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        getContentPane().add(serverName, gridBagConstraints);

        userName.setText("jTextField3");
        userName.setMinimumSize(new java.awt.Dimension(200, 21));
        userName.setPreferredSize(new java.awt.Dimension(200, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        getContentPane().add(userName, gridBagConstraints);

        jLabel4.setText("Use secure connection (SSL):");
        jLabel4.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        getContentPane().add(jLabel4, gridBagConstraints);

        port.setMinimumSize(new java.awt.Dimension(50, 21));
        port.setPreferredSize(new java.awt.Dimension(50, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        getContentPane().add(port, gridBagConstraints);

        bOK.setText("OK");
        bOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bOKActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        getContentPane().add(bOK, gridBagConstraints);

        bReset.setText("Cancel");
        bReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bResetActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        getContentPane().add(bReset, gridBagConstraints);

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.X_AXIS));

        bgUseSSL.add(useSSLNever);
        useSSLNever.setText("Never");
        useSSLNever.setEnabled(false);
        jPanel1.add(useSSLNever);

        bgUseSSL.add(useSSLWhenAvailable);
        useSSLWhenAvailable.setText("When available");
        useSSLWhenAvailable.setEnabled(false);
        jPanel1.add(useSSLWhenAvailable);

        bgUseSSL.add(useSSLAlways);
        useSSLAlways.setText("Always");
        useSSLAlways.setEnabled(false);
        jPanel1.add(useSSLAlways);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        getContentPane().add(jPanel1, gridBagConstraints);

        jLabel5.setText("Your Name:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        getContentPane().add(jLabel5, gridBagConstraints);

        yourName.setText("jTextField1");
        yourName.setPreferredSize(new java.awt.Dimension(200, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        getContentPane().add(yourName, gridBagConstraints);

        jLabel6.setText("Email Address:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        getContentPane().add(jLabel6, gridBagConstraints);

        jLabel7.setText("Reply-to Address: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        getContentPane().add(jLabel7, gridBagConstraints);

        jLabel8.setText("Organization: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        getContentPane().add(jLabel8, gridBagConstraints);

        emailAddress.setText("jTextField1");
        emailAddress.setMinimumSize(new java.awt.Dimension(200, 21));
        emailAddress.setPreferredSize(new java.awt.Dimension(200, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        getContentPane().add(emailAddress, gridBagConstraints);

        replyToAddress.setText("jTextField1");
        replyToAddress.setPreferredSize(new java.awt.Dimension(200, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        getContentPane().add(replyToAddress, gridBagConstraints);

        organization.setText("jTextField1");
        organization.setPreferredSize(new java.awt.Dimension(200, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        getContentPane().add(organization, gridBagConstraints);

    }
    // </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        dispose();
    }//GEN-LAST:event_formWindowClosed

    private void bResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bResetActionPerformed
        hide(); // readConfig();
    }//GEN-LAST:event_bResetActionPerformed

    private void bOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bOKActionPerformed
        writeConfig();
		hide();
    }//GEN-LAST:event_bOKActionPerformed
    

//// -----------------------------------------------------    
//public static boolean isConfigured()
//{
//	String host = prefs.get("mail.smtp.host", "");
//	return !("".equals(host));
//}
// -----------------------------------------------------    
public void readConfig(Preferences p)
{
	yourName.setText(p.get("mailx.from.name", ""));
	emailAddress.setText(p.get("mail.from", ""));
	replyToAddress.setText(p.get("mailx.replyto", ""));
	organization.setText(p.get("mailx.organization", ""));

	serverName.setText(p.get("mail.smtp.host", ""));
	port.setValue(new Integer(p.getInt("mail.smtp.port", 25)));
	userName.setText(p.get("mail.smtp.user", ""));
	useNamePasswd.setSelected(p.getBoolean("mail.smtp.auth", false));
}
// -----------------------------------------------------    
public void readConfig()
{
	readConfig(prefs);
}
// -----------------------------------------------------    
public void writeConfig(Preferences p) {
	p.put("mailx.from.name", yourName.getText());
	p.put("mail.from", emailAddress.getText());
	p.put("mailx.replyto", replyToAddress.getText());
	p.put("mailx.organization", organization.getText());

	p.put("mail.smtp.host", serverName.getText());
	Integer IPort = (Integer)port.getValue();
	int iport = (IPort == null ? 25 : IPort.intValue());
	p.putInt("mail.smtp.port", iport);
	p.put("mail.smtp.user", userName.getText());
	p.putBoolean("mail.smtp.auth", useNamePasswd.isSelected());
	try {
		p.flush();
	} catch(BackingStoreException e) {}
}
// -----------------------------------------------------    
public void writeConfig()
{
	writeConfig(prefs);
}
// -----------------------------------------------------    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bOK;
    private javax.swing.JButton bReset;
    private javax.swing.ButtonGroup bgUseSSL;
    private javax.swing.JTextField emailAddress;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField organization;
    private citibob.swing.typed.JTypedTextField port;
    private javax.swing.JTextField replyToAddress;
    private javax.swing.JTextField serverName;
    private javax.swing.JCheckBox useNamePasswd;
    private javax.swing.JRadioButton useSSLAlways;
    private javax.swing.JRadioButton useSSLNever;
    private javax.swing.JRadioButton useSSLWhenAvailable;
    private javax.swing.JTextField userName;
    private javax.swing.JTextField yourName;
    // End of variables declaration//GEN-END:variables
    
}
