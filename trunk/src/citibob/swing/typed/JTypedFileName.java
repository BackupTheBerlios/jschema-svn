/*
 * JTypedFileName.java
 *
 * Created on December 23, 2007, 1:25 AM
 */

package citibob.swing.typed;

import citibob.text.FileSFormat;
import citibob.types.JFile;
import citibob.types.JavaJType;
import java.io.*;
import javax.swing.JFileChooser;

/**
 * Used to choose an existing file.
 * @author  citibob
 */
public class JTypedFileName extends JTypedPanel
{
	JFile jType;
	
	/** Creates new form JTypedFileName */
	public JTypedFileName()
	{
		initComponents();
		fileName.setJType(new JavaJType(File.class), new FileSFormat());
		super.setSubWidget(fileName);
	}

	public void setJType(JFile jType)
	{
		this.jType = jType;
	}
	
	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        java.awt.GridBagConstraints gridBagConstraints;

        fileName = new citibob.swing.typed.JTypedTextField();
        showSelector = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());

        setPreferredSize(new java.awt.Dimension(124, 19));
        fileName.setText("jTypedTextField1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(fileName, gridBagConstraints);

        showSelector.setFont(new java.awt.Font("Dialog", 0, 12));
        showSelector.setText("...");
        showSelector.setMargin(new java.awt.Insets(0, 0, 0, 0));
        showSelector.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                showSelectorActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 1.0;
        add(showSelector, gridBagConstraints);

    }// </editor-fold>//GEN-END:initComponents

	private void showSelectorActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_showSelectorActionPerformed
	{//GEN-HEADEREND:event_showSelectorActionPerformed

		File dir = (File)fileName.getValue();
		if (dir == null) dir = jType.getDefaultDir();
		else dir = dir.getParentFile();
		
		JFileChooser fc = new JFileChooser(dir);
		fc.addChoosableFileFilter(jType.getFilter());
			
		// Show open dialog; this method does not return until the dialog is closed
		int result = fc.showOpenDialog(this);
		if (result != JFileChooser.APPROVE_OPTION) return;
		File selFile = fc.getSelectedFile();
		fileName.setValue(selFile);
// TODO add your handling code here:
	}//GEN-LAST:event_showSelectorActionPerformed
	
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private citibob.swing.typed.JTypedTextField fileName;
    private javax.swing.JButton showSelector;
    // End of variables declaration//GEN-END:variables

	
// ===================================================================

}
