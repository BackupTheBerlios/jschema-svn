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
 * GroupPanel.java
 *
 * Created on June 5, 2005, 2:31 PM
 */

package citibob.jschema.gui;

import java.sql.*;
import citibob.jschema.*;
import citibob.jschema.swing.*;
import citibob.jschema.swing.JSchemaWidgetTree;
import citibob.swing.table.*;
import citibob.multithread.*;

/**
 *
 * @author  citibob
 */
public class TypedItemPanel extends javax.swing.JPanel {
	
SchemaBuf schemaBuf;
ActionRunner runner;
String typeCol;

	public TypedItemTable getTable() { return table; }

	/** Creates new form GroupPanel */
	public TypedItemPanel() {
		initComponents();
	}
	
	public void initRuntime(SchemaBuf schemaBuf,
	String typeCol, KeyedModel typeKeyedModel,
	String[] xColNames, String[] xSColMap,
	ActionRunner runner)
	{
		this.runner = runner;
		this.typeCol = typeCol;
		table.initRuntime(schemaBuf, typeCol, typeKeyedModel, xColNames, xSColMap);
		this.schemaBuf = schemaBuf;
		addType.setModel(typeKeyedModel);
	}
	
	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        GroupScrollPanel = new javax.swing.JScrollPane();
        table = new citibob.jschema.gui.TypedItemTable();
        controller = new javax.swing.JPanel();
        addType = new citibob.swing.typed.JKeyedComboBox();
        jPanel1 = new javax.swing.JPanel();
        addBtn = new javax.swing.JButton();
        delBtn = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

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
        GroupScrollPanel.setViewportView(table);

        add(GroupScrollPanel, java.awt.BorderLayout.CENTER);

        controller.setLayout(new java.awt.BorderLayout());

        controller.add(addType, java.awt.BorderLayout.CENTER);

        addBtn.setText("Add");
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });

        jPanel1.add(addBtn);

        delBtn.setText("Del");
        delBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delBtnActionPerformed(evt);
            }
        });

        jPanel1.add(delBtn);

        controller.add(jPanel1, java.awt.BorderLayout.EAST);

        add(controller, java.awt.BorderLayout.SOUTH);

    }
    // </editor-fold>//GEN-END:initComponents

	private void delBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delBtnActionPerformed
		int selected = table.getSelectedRow();
		if (selected != -1) {
System.out.println("Deleting row: " + selected);
			schemaBuf.deleteRow(selected);
		}
	}//GEN-LAST:event_delBtnActionPerformed

	private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
		runner.doRun(new ERunnable() { public void run() throws Exception {
			schemaBuf.insertRow(-1, typeCol, addType.getValue());
		}});
	}//GEN-LAST:event_addBtnActionPerformed
	
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane GroupScrollPanel;
    private javax.swing.JButton addBtn;
    private citibob.swing.typed.JKeyedComboBox addType;
    private javax.swing.JPanel controller;
    private javax.swing.JButton delBtn;
    private javax.swing.JPanel jPanel1;
    private citibob.jschema.gui.TypedItemTable table;
    // End of variables declaration//GEN-END:variables
	
}
