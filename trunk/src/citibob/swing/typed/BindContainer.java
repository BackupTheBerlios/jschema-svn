/*
 * BindContainer.java
 *
 * Created on August 7, 2007, 1:23 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.swing.typed;

/**
 * Allows invisible elements (under "other components") to be included in
 automated tree walking.  See TypedWidgetBinder.java
 * @author citibob
 */
public interface BindContainer
{
public java.awt.Component[] getBindComponents();	
}
