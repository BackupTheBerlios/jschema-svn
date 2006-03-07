/*
 * DoubleClickTableMouseListener.java
 *
 * Created on March 6, 2006, 11:04 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.swing.table;

import javax.swing.*;
import java.awt.event.*;

/**
 *
 * @author citibob
 */
public abstract class DClickTableMouseListener implements MouseListener
{
	
	JTable table;
	
	/** Creates a new instance of DoubleClickTableMouseListener */
	public DClickTableMouseListener(JTable table) {
		this.table = table;
	}
	
	public abstract void doubleClicked(int row);
	
// Mouse Listener
/**
   * Invoked when the mouse button has been clicked (pressed
   * and released) on a component.
   */
  public void mouseClicked(MouseEvent e)
  {
    if( e.getClickCount() == 2 )
    {
      int row = this.table.rowAtPoint( e.getPoint() );
      if( row != -1 ) {
		  doubleClicked(row);
      }
    }
  }
  /**
   * Invoked when a mouse button has been pressed on a component.
   */
  public void mousePressed(MouseEvent e)
  {
  }
  /**
   * Invoked when a mouse button has been released on a component.
   */
  public void mouseReleased(MouseEvent e)
  {
  }
  /**
   * Invoked when the mouse enters a component.
   */
  public void mouseEntered(MouseEvent e)
  {
  }
  /**
   * Invoked when the mouse exits a component.
   */
  public void mouseExited(MouseEvent e)
  {
  }
  
}
