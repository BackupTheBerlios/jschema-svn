/*
JSchema: library for GUI-based database applications
This file Copyright (c) 2006-2007 by Robert Fischer

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
/*
 * Wizard.java
 *
 * Created on January 27, 2007, 6:51 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.wizard;


import java.util.*;

/**
 *
 * @author citibob
 */
public abstract class Wizard
{
	
protected HashMap wizCache;	// Wiz screens are cached through course of a run...
	// This allows us to go back to previous screens without having to re-load
	// their data.

protected String startState = "start";

protected String state;
protected State stateRec;
protected Wiz wiz;			// The current Wizard for the current state
protected TypedHashMap v;		// Info we get out of the wizard screens
protected HashMap states;
protected String wizardName;

protected abstract class State {
	public String name;		// Name of this Wiz screen.
	public String back;		// Wiz normally traversed to on back button
	public String next;
	public abstract Wiz newWiz() throws Exception;	// User implements this
	/** Runs before the Wiz */
	public void pre() throws Exception {}
	/** Runs after the Wiz */
	public abstract void process() throws Exception;
	
	public State(String name, String back, String next) {
		this.name = name;
		this.back = back;
		this.next = next;
	}
}

/** Presents one Wiz to the user */
protected abstract void runWiz(Wiz wiz) throws Exception;
protected boolean reallyCancel() throws Exception { return true; }

public Wizard(String wizardName, String startState)
{
	this.wizardName = wizardName;
	this.startState = startState;
	states = new HashMap();
}

protected void addState(State st)
{
	states.put(st.name, st);
}

protected boolean checkFieldsFilledIn()
{
	// Make sure all fields are filled in
	TypedHashMap m = new TypedHashMap();
	wiz.getAllValues(m);
	if (m.containsNull()) {
		state = stateRec.name;
		return false;
	}
	return true;
}

/** Override this to post-process wiz after it's created */
protected Wiz createWiz(State stateRec) throws Exception {
	return stateRec.newWiz();
}

public TypedHashMap runWizard() throws Exception
{
	return runWizard(startState);
}
/** Returns the values collected from the Wizard (for any work not
accomplished by Wizard already). */
public TypedHashMap runWizard(String startState) throws Exception
{
	state = startState;
	try {
		v = new TypedHashMap();
		wizCache = new HashMap();
		for (state = startState; state != null;) {
			stateRec = (State)states.get(state);
			if (stateRec == null) return v;		// Fell off the state graph
			wiz = (Wiz)wizCache.get(state);
			if (wiz == null) {
				wiz = createWiz(stateRec);
				if (wiz.getCacheWiz()) wizCache.put(state, wiz);
			}
			stateRec.pre();		// Prepare the Wiz...
			runWiz(wiz);
			wiz.getAllValues(v);

			// Do default navigation; process() can change this.
			String submit = v.getString("submit");
	System.out.println("submit = " + submit);
			if ("next".equals(submit)) state = stateRec.next;
			else if ("back".equals(submit)) {
				// Remove it from the cache so we re-make
				// it going "forward" in the Wizard
				if (!wiz.getCacheWizFwd()) wizCache.remove(state);
				state = stateRec.back;
				continue;
			} else if ("cancel".equals(submit) && reallyCancel()) break;

			// Do screen-specific processing
			stateRec.process();
		}
		return v;
	} finally {
		wizCache = null;		// Free memory...
	}
}

}
