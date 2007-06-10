/*
Offstage CRM: Enterprise Database for Arts Organizations
This file Copyright (c) 2006 by Robert Fischer

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/
/*
 * CompleteStatusPanel.java
 *
 * Created on February 3, 2006, 11:38 PM
 */

package citibob.jschema.gui;

import java.sql.*;
import citibob.jschema.*;
import citibob.jschema.swing.*;
//import citibob.jschema.swing.JSchemaWidgetTree;
import citibob.swing.table.*;
import citibob.swing.typed.*;
import citibob.multithread.*;
import citibob.jschema.swing.StatusTable;
import citibob.sql.*;
import citibob.app.*;

/**
 * StatusPNC = Panel 'n Controller
 * @author  citibob
 */
public class StatusPNC extends javax.swing.JPanel {

	
SchemaBuf schemaBuf;
//ActionRunner runner;
SchemaBufDbModel dbm;
App app;

	/** Creates new form CompleteStatusPanel */
	public StatusPNC() {
		initComponents();
	}
	
	
	public void initRuntime(SchemaBufDbModel dbm,
	String[] xColNames, String[] xSColMap, boolean[] editable, App app)
//	SwingerMap swingers,
//	ActionRunner runner)
	{
		this.dbm = dbm;
		this.schemaBuf = dbm.getSchemaBuf();
		this.app = app;
		table.setModelU(schemaBuf, xColNames, xSColMap, editable, app.getSwingerMap());
		table.setRowSelectionAllowed(false);
	}

	
	
	public StatusTable getTable() { return table; }

	/** Convenience Function */
	ColPermuteTableModel getTableModel()
	{
		ColPermuteTableModel model = (ColPermuteTableModel)getTable().getModel();
		return model;
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        bAdd = new javax.swing.JButton();
        bDel = new javax.swing.JButton();
        bRestore = new javax.swing.JButton();
        bSave = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new citibob.jschema.swing.StatusTable();

        setLayout(new java.awt.BorderLayout());

        bAdd.setText("Add");
        bAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAddActionPerformed(evt);
            }
        });

        jPanel1.add(bAdd);

        bDel.setText("Del");
        bDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDelActionPerformed(evt);
            }
        });

        jPanel1.add(bDel);

        bRestore.setText("Restore");
        bRestore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRestoreActionPerformed(evt);
            }
        });

        jPanel1.add(bRestore);

        bSave.setText("Save");
        bSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSaveActionPerformed(evt);
            }
        });

        jPanel1.add(bSave);

        add(jPanel1, java.awt.BorderLayout.SOUTH);

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(table);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

    }
    // </editor-fold>//GEN-END:initComponents

private void bSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSaveActionPerformed
		app.runGui(StatusPNC.this, new StRunnable() { public void run(Statement st) throws Exception {
			dbm.doUpdate(st);
			dbm.doSelect(st);
		}});
// TODO add your handling code here:
}//GEN-LAST:event_bSaveActionPerformed

	private void bRestoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRestoreActionPerformed
		app.runGui(StatusPNC.this, new StRunnable() { public void run(Statement st) throws Exception {
			dbm.doSelect(st);
		}});
// TODO add your handling code here:
	}//GEN-LAST:event_bRestoreActionPerformed

	private void bDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDelActionPerformed
		app.runGui(StatusPNC.this, new ERunnable() { public void run() throws Exception {
			int selected = table.getSelectedRow();
			if (selected != -1) {
//	System.out.println("Deleting row: " + selected);
				schemaBuf.deleteRow(selected);
			}
		}});
	}//GEN-LAST:event_bDelActionPerformed

	private void bAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAddActionPerformed
		app.runGui(StatusPNC.this, new ERunnable() { public void run() throws Exception {
			schemaBuf.insertRow(-1);
		}});
// TODO add your handling code here:
	}//GEN-LAST:event_bAddActionPerformed
	
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bAdd;
    private javax.swing.JButton bDel;
    private javax.swing.JButton bRestore;
    private javax.swing.JButton bSave;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private citibob.jschema.swing.StatusTable table;
    // End of variables declaration//GEN-END:variables
	
}
