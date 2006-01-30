/*
 * SimpleActionRunner.java
 *
 * Created on January 29, 2006, 7:51 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.multithread;

/**
 *
 * @author citibob
 */
public class SimpleActionRunner implements ActionRunner
{
	
public void run(ERunnable r)
{
	try {
		r.run();		
	} catch(Throwable e) {
		e.printStackTrace();
	}
}
	
}
