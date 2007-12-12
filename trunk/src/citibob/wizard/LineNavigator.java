/*
 * FixedNavigator.java
 *
 * Created on December 2, 2007, 12:17 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.wizard;

import java.util.*;

/**
 * Implements a simple line of states, as in a classic wizard.
 * @author citibob
 */
public class LineNavigator extends HashNavigator
{
	public LineNavigator(String[] nav)
	{
		super();
		for (int i=0; i<nav.length-1; i += 1) {
			next.put(nav[i], nav[i+1]);
		}
	}	
}
