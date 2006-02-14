/*
JSchema: library for GUI-based database applications
This file Copyright (c) 2006 by Robert Fischer

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/
package citibob.multithread;

import java.util.*;
import java.io.*;

public abstract class TaskQueueMVC extends Thread implements TaskRunner
{
public static interface Listener {
    /**  Task added to queue. */
    public void taskAdded(CBTask t);


    /**  Task removed from the queue */
    public void taskRemoved(CBTask t);


    /**  Task being executed */
    public void taskStarting(CBTask t);


    /**  Task finished running --- exception (if any) is passed along */
    public void taskFinished(CBTask t, Throwable e);


    /**  Queue cleared (usually, the running task will be finished here as well.) */
    public void queueCleared();
}
// ======================================================
public static class Adapter implements TaskQueueMVC.Listener {
    /**  Task added to queue. */
    public void taskAdded(CBTask t) {}


    /**  Task removed from the queue */
    public void taskRemoved(CBTask t) {}


    /**  Task being executed */
    public void taskStarting(CBTask t) {}


    /**  Task finished running --- exception (if any) is passed along */
    public void taskFinished(CBTask t, Throwable e) {}


    /**  Queue cleared (usually, the running task will be finished here as well.) */
    public void queueCleared() {}
}
// ======================================================
java.util.LinkedList listeners = new java.util.LinkedList();
public void addListener(TaskQueueMVC.Listener l)
	{ listeners.add(l); }
public void removeListener(TaskQueueMVC.Listener l)
	{ listeners.remove(l); }

// ======================================================
public void fireTaskAdded(CBTask t)
{
	for (java.util.Iterator ii=listeners.iterator(); ii.hasNext(); ) {
		TaskQueueMVC.Listener l = (TaskQueueMVC.Listener)ii.next();
		l.taskAdded(t);
	}
}
public void fireTaskRemoved(CBTask t)
{
	for (java.util.Iterator ii=listeners.iterator(); ii.hasNext(); ) {
		TaskQueueMVC.Listener l = (TaskQueueMVC.Listener)ii.next();
		l.taskRemoved(t);
	}
}
public void fireTaskStarting(CBTask t)
{
	for (java.util.Iterator ii=listeners.iterator(); ii.hasNext(); ) {
		TaskQueueMVC.Listener l = (TaskQueueMVC.Listener)ii.next();
		l.taskStarting(t);
	}
}
public void fireTaskFinished(CBTask t, Throwable e)
{
	for (java.util.Iterator ii=listeners.iterator(); ii.hasNext(); ) {
		TaskQueueMVC.Listener l = (TaskQueueMVC.Listener)ii.next();
		l.taskFinished(t, e);
	}
}
public void fireQueueCleared()
{
	for (java.util.Iterator ii=listeners.iterator(); ii.hasNext(); ) {
		TaskQueueMVC.Listener l = (TaskQueueMVC.Listener)ii.next();
		l.queueCleared();
	}
}
}
