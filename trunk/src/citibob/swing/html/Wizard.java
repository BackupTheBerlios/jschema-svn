/*
 * NewRecordWizard.java
 *
 * Created on October 8, 2006, 10:41 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.swing.html;

import javax.swing.*;
import java.util.*;

/**
 *
 * @author citibob
 */
public class Wizard {

HashMap wizCache;	// Wiz screens are cached through course of a run...
	// This allows us to go back to previous screens without having to re-load
	// their data.

protected java.awt.Frame frame;
protected String startState = "start";

protected String state;
protected State stateRec;
protected HtmlDialog wiz;			// The current Wizard for the current state
protected TypedHashMap v;		// Info we get out of the wizard screens
protected HashMap states;

protected abstract class State {
	public String name;		// Name of this Wiz screen.
	public String back;		// Wiz normally traversed to on back button
	public String next;
	public abstract HtmlDialog newWiz() throws Exception;
	public abstract void process() throws Exception;
	
	public State(String name, String back, String next) {
		this.name = name;
		this.back = back;
		this.next = next;
	}
}

/*   --------- Sample Wizard creation code
new State("", "", "") {
	public HtmlDialog newWiz()
		{ return new }
	public void process()
	{
		
	}
}
*/

protected void addState(State st)
{
	states.put(st.name, st);
}

public Wizard(java.awt.Frame frame, String startState)
{
	this.frame = frame;
	this.startState = startState;
	states = new HashMap();
}

/** Returns the values collected from the Wizard (for any work not
accomplished by Wizard already). */
public TypedHashMap runWizard() throws Exception
{
	try {
		state = startState;
		v = new TypedHashMap();
		wizCache = new HashMap();
		for (state = startState; state != null;) {
			stateRec = (State)states.get(state);
			if (stateRec == null) return v;		// Fell off the state graph
			wiz = (HtmlDialog)wizCache.get(state);
			if (wiz == null) {
				wiz = stateRec.newWiz();
				wizCache.put(state, wiz);
			}
			wiz.setVisible(true);
			wiz.getAllValues(v);

			// Do default navigation; process() can change this.
			String submit = v.getString("submit");
	System.out.println("submit = " + submit);
			if ("next".equals(submit)) state = stateRec.next;
			else if ("back".equals(submit)) state = stateRec.back;
			else if ("cancel".equals(submit)) {
				int ret = JOptionPane.showConfirmDialog(wiz,
					"Are you sure you wish to cancel the Wizard?",
					"Really Cancel?", JOptionPane.YES_NO_OPTION);
				if (ret == JOptionPane.YES_OPTION) break;
				continue;
			}

			// Do screen-specific processing
			stateRec.process();
		}
		return v;
	} finally {
		wizCache = null;		// Free memory...
	}
}

protected boolean checkFieldsFilledIn()
{
	// Make sure all fields are filled in
	TypedHashMap m = new TypedHashMap();
	wiz.getAllValues(m);
	if (m.containsNull()) {
		JOptionPane.showMessageDialog(wiz,
			"You must fill in all the fields.");
		state = stateRec.name;
		return false;
	}
	return true;
}
	
}
