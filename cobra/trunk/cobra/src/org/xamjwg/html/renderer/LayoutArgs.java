/*
 * LayoutArgs.java
 *
 * Created on October 8, 2006, 1:33 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.xamjwg.html.renderer;

import java.util.*;

/**
 *
 * @author citibob
 */
public class LayoutArgs {
	Map widgetMap;
	
	public Map getWidgetMap() { return widgetMap; }
	public LayoutArgs(Map widgetMap) {
		this.widgetMap = widgetMap;
	}
	public LayoutArgs() { this(new HashMap()); }
	
}
