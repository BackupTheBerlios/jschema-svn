package de.chka.swing.components;

import javax.swing.JTable;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableColumn;

import javax.swing.table.TableCellRenderer;

import java.awt.Component;

import java.util.List;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import javax.swing.event.TableColumnModelListener;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;


// TBD: deferred relayout?
// TBD: Serialization?
// TBD: how to add/remove renderer components (really necessary)?
// TBD: row margin overflow?
// TBD: row argument?
// TBD: garbage collection?
// TBD: What if someone else changes the row height?
// TBD: restore old height on uninstall?
//This does not support changing renderers (due to lack of events)

public class RowHeightUpdater
{
    private JTable table;
    
    private List prototypes;


    private Listener listener;


    /** prototypes: one for each *model* column. */
    // The implementation has to support 'prototypes' being not large
    // enough for tableStructureChanged event - should allow this here?
    public RowHeightUpdater(JTable t, List prototypes)
    {
        this.table = t;
        this.prototypes = prototypes;
    }


    public JTable table()
    {
        return table;
    }

    public void setTable(JTable t)
    {
        if (table == t)
            return;

        boolean enabled = isEnabled();

        if (enabled)
            dispose();

        table = t;
        
        if (enabled)
        {
            init();

            update();
        }
    }

    public List prototypes()
    {
        return prototypes;
    }

    public void setPrototypes(List value)
    {
        prototypes = value;

        if (isEnabled())
            update();
    }

    public boolean isEnabled()
    {
        return listener != null;
    }

    /** default: false. */
    public void setEnabled(boolean value)
    {
        if (value == isEnabled())
            return;

        if (value)
        {
            init();
            
            update();
        }
        else
            dispose();
    }

    
    private void init()
    {
        listener = createListener();

        table.addPropertyChangeListener(listener);
        table.getColumnModel().addColumnModelListener(listener);
    }

    private void dispose()
    {
        table.getColumnModel().removeColumnModelListener(listener);
        table.removePropertyChangeListener(listener);

        listener = null;
    }


        



    private int preferredHeight(int column)
    {
        // cannot use JTable.getCellRenderer (there need not be a row)
        
        TableColumn c = table.getColumnModel().getColumn(column);

        // mainly a workaround for model structure change / replace
        // (then the column structure will be inconsistent no matter what)

        if (prototypes.size() <= c.getModelIndex() || table.getModel().getColumnCount() <= c.getModelIndex())
            return 0;
        
        TableCellRenderer r = c.getCellRenderer();
        
        if (r == null)
            r = table.getDefaultRenderer(table.getColumnClass(column));

        Component d = r.getTableCellRendererComponent
            (table, prototypes.get(c.getModelIndex()), false, false,
                -1, column); // What to pass for 'row'?
        
        // should add/remove?
        
        return d.getPreferredSize().height;
    }


    private void update()
    {
        int height = 1;

        for (int i = table.getColumnCount() - 1; i >= 0; --i)
            height = Math.max(preferredHeight(i), height);

        table.setRowHeight(height + table.getRowMargin());
    }


    /** update the row heights once. */
    // This implementation is unoptimized.
    public static void update(JTable t, List prototypes)
    {
        RowHeightUpdater u = new RowHeightUpdater(t, prototypes);

        try
        {
            u.setEnabled(true);
        }
        finally
        {
            u.dispose();
        }
    }



    private Listener createListener()
    {
        return new Listener();
    }
    
    private class Listener
        implements TableColumnModelListener, PropertyChangeListener
    {
        public Listener()
        {
        }
        
        public void propertyChange(PropertyChangeEvent e)
        {
            if (e.getPropertyName().equals("columnModel"))
            {
                ((TableColumnModel)e.getOldValue()).removeColumnModelListener(listener);
                ((TableColumnModel)e.getNewValue()).addColumnModelListener(listener);
            }
            else if (e.getPropertyName().equals("rowMargin"))
            {
                table.setRowHeight(table.getRowHeight() - ((Integer)e.getOldValue()).intValue() + ((Integer)e.getNewValue()).intValue());
            }
            else if (e.getPropertyName().equals("font"))
            {
                update();
            }
        }


        public void columnAdded(TableColumnModelEvent e)
        {
            int height = preferredHeight(e.getToIndex()) + table.getRowMargin();

            if (height > table.getRowHeight())
                table.setRowHeight(height);
        }

        public void columnRemoved(TableColumnModelEvent e)
        {
            update();
        }

        public void columnMarginChanged(ChangeEvent e)
        {
        }

        public void columnMoved(TableColumnModelEvent e)
        {
        }

        public void columnSelectionChanged(ListSelectionEvent e)
        {
        }
    }
}
