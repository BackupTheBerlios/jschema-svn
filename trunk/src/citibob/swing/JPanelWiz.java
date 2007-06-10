/*
 * JPanelWiz.java
 *
 * Created on January 27, 2007, 9:39 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.swing;

/**
 *
 * @author citibob
 */
public abstract class JPanelWiz extends javax.swing.JPanel implements citibob.wizard.Wiz
{
	protected JPanelWizWrapper wrapper;
	protected String title;
	protected boolean cacheWiz = true;
	protected boolean cacheWizFwd;
	
	public String getTitle() { return title; }
	
	public JPanelWiz(String title) { this.title = title; }

	/** Called by the wrapper; not for users. */
	void setWrapper(JPanelWizWrapper wrapper) { this.wrapper = wrapper; }
	
	/** Override to take action when the "<< Back" button is pressed. */
	public void backPressed() {}
	public void nextPressed() {}
	public void cancelPressed() {}
	///** After the Wiz is done running, report its output into a Map. */
	//public void getAllValues(java.util.Map map);

	// ===========================================
	// Wiz
	public boolean getCacheWiz() { return cacheWiz; }
	public boolean getCacheWizFwd() { return cacheWizFwd; }
	public void setTitle(String title) { this.title = title; }
	
}
