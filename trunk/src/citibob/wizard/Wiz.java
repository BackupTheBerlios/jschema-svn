/*
 * Wiz.java
 *
 * Created on January 27, 2007, 6:31 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.wizard;

/**
 * Represents (logically) one Wizard screen.
 * @author citibob
 */
public interface Wiz
{
	
public void setTitle(String title);

/** Should this Wiz screen be cached when "Back" is pressed? */
public boolean getCacheWiz();

/** Should old version of this Wiz be used when we go back "forward" over it again? */
public boolean getCacheWizFwd();

/** After the Wiz is done running, report its output into a Map. */
public void getAllValues(java.util.Map map);

///** Presents the Wiz to the user; when it is finished, reports output values into map. */
//public void showWiz(java.util.Map map);

}
