/*
 * AbstractWizState.java
 *
 * Created on December 1, 2007, 11:08 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.wizard;

/**
 *
 * @author citibob
 */
public abstract class AbstractWizState implements WizState
{
	public String name;		// Name of this Wiz screen.
	public String back;		// Wiz normally traversed to on back button
	public String next;
	
	public AbstractWizState(String name, String back, String next) {
		this.name = name;
		this.back = back;
		this.next = next;
	}
	public String getName() { return name; }
	public String getBack() { return back; }
	public String getNext() { return next; }
	
	public AbstractWizState(String name) {
		this(name, null, null);
	}
	/** Runs before the Wiz, even if cached Wiz is being re-used. */
	public void pre(Wizard.Context con) throws Exception {}

}
