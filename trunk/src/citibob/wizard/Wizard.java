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


import bsh.This;
import java.util.*;
import java.sql.*;
import citibob.sql.*;
import citibob.app.*;

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

protected App app;
protected String stateName;
protected WizState stateRec;
protected Wiz wiz;			// The current Wizard for the current state
protected TypedHashMap v;		// Info we get out of the wizard screens
protected HashMap states;
protected String wizardName;
protected Navigator navigator;
//protected SqlBatch str;		// Batch SQL queries service used by Wiz constructors & State.process()

/** Returns output from Wizard. */
public Object getVal(String name) { return v.get(name); }
public void setVal(String name, Object val) { v.put(name, val); }

/** Gets the name of a resource in the same package as this class. */
protected String getResourceName(String rname)
{
	return getClass().getPackage().getName().replace('.', '/') + "/" + rname;

}

/** Presents one Wiz to the user */
protected abstract void runWiz(Wiz wiz) throws Exception;
protected boolean reallyCancel() throws Exception { return true; }

public Wizard(String wizardName, App app, String startState)
{
	this.wizardName = wizardName;
	this.startState = startState;
	this.app = app;
	this.v = new TypedHashMap();
	states = new HashMap();
	navigator = new Navigator() {
		public String getNext(WizState stateRec) { return stateRec.getNext(); }
		public String getBack(WizState stateRec) { return stateRec.getBack(); }
	};
}

public void setNavigator(Navigator navigator)
{
	this.navigator = navigator;
}
public void setStartState(String startState)
{
	this.startState = startState;
}
protected void addState(WizState st)
{
	states.put(st.getName(), st);
}
public void setWizardName(String name)
	{ this.wizardName = name; }
protected boolean checkFieldsFilledIn()
{
	// Make sure all fields are filled in
	TypedHashMap m = new TypedHashMap();
	wiz.getAllValues(m);
	if (m.containsNull()) {
		stateName = stateRec.getName();
		return false;
	}
	return true;
}

/** Override this to post-process wiz after it's created */
protected Wiz createWiz(WizState stateRec, Context con) throws Exception {
	return stateRec.newWiz(con);
}
/** Override this to create context for Wiz's and WizState's */
protected Context newContext() throws Exception
{
	return new Context(new SqlBatchSet(), v);
//	return new Context(app.getBatchSet(), v);
}
/** Write out any buffers in the context when Wiz/State is done with it. */
protected void finishContext(Context con) throws Exception
{
	con.str.runBatches(app.getPool());
}

public TypedHashMap runWizard() throws Exception
{ return runWizard(startState); }

/** Returns the values collected from the Wizard (for any work not
accomplished by Wizard already). */
public TypedHashMap runWizard(String startState) throws Exception
{
	stateName = (startState == null ? this.startState : startState);
	String prevState = null;
	String curState;
	try {
		// v = (xv == null ? new TypedHashMap() : xv);
		wizCache = new HashMap();
		for (stateName = startState; stateName != null;) {
			
			// ============= Create the Wiz
			Context con = newContext();
			stateRec = (WizState)states.get(stateName);
			if (stateRec == null) return v;		// Fell off the state graph
			wiz = (Wiz)wizCache.get(stateName);
			if (wiz == null) {
				wiz = createWiz(stateRec, con);
				if (wiz.getCacheWiz()) wizCache.put(stateName, wiz);
			}
			curState = stateName;	// State now becomes (semantically) nextState
			stateRec.pre(con);		// Prepare the Wiz...
			finishContext(con);
			
			// =============== Let user interact with the Wiz
			con = newContext();
			//SqlBatchSet str;
			//str = app.getBatchSet(); //new SqlBatchSet();
			//str = new SqlBatchSet();
			runWiz(wiz);
			wiz.getAllValues(v);

			// Do default navigation; process() can change this.
			String submit = v.getString("submit");
	System.out.println("submit = " + submit);
			if ("next".equals(submit)) stateName = navigator.getNext(stateRec);
			else if ("back".equals(submit)) {
				// Remove it from the cache so we re-make
				// it going "forward" in the Wizard
				if (!wiz.getCacheWizFwd()) wizCache.remove(stateName);
				stateName = navigator.getBack(stateRec);
				if (stateName == null) stateName = prevState;
				continue;
			} else if ("cancel".equals(submit) && reallyCancel()) break;

			// Do screen-specific processing
			stateRec.process(con);
			finishContext(con);
			prevState = curState;
		}
		return v;
	} finally {
		wizCache = null;		// Free memory...
	}
}
// =================================================================
public static class Context {
	public SqlBatchSet str;		// Access to database
	public TypedHashMap v;		// Values passed around
	public Context(SqlBatchSet str, TypedHashMap v) {
		this.str = str;
		this.v = v;
	}
}
public static interface Navigator
{
	public String getNext(WizState stateRec);
	public String getBack(WizState stateRec);
}
}
