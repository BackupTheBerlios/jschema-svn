package citibob.gui;

import citibob.swing.prefs.SwingPrefs;
import javax.swing.*;
import java.util.*;
import java.util.prefs.Preferences;

/**
 * Keeps track of a bunch of JFrames.  Meant to be subclassed
 * @author citibob
 */



public class FrameSet {

// ------------------------------
public interface Maker
{
	public JFrame newFrame() throws Exception;
}
// ------------------------------
protected class FrameRec {
	public String name;
	public Maker maker;
	public JFrame frame;
}

Map<String,FrameRec> map;
SwingPrefs swingPrefs;
Preferences prefsRoot;

/** Creates a new instance of FrameSet */
public FrameSet(SwingPrefs swingPrefs, Preferences prefsRoot) {
	this.swingPrefs = swingPrefs;
	this.prefsRoot = prefsRoot;
	map = new TreeMap();
}
public FrameSet(Preferences prefsRoot) {
	this(new SwingPrefs(), prefsRoot);		// Use default SwingPrefs
}

public void addMaker(String frameName, Maker frameMaker)
{
	FrameRec rec = new FrameRec();
		rec.name = frameName;
		rec.maker = frameMaker;
		rec.frame = null;
	map.put(frameName, rec);
}

public JFrame openFrame(String frameName) throws Exception
{
	JFrame frame = getFrame(frameName);
	frame.setVisible(true);
	int state = frame.getExtendedState();
	frame.setExtendedState(state & ~JFrame.ICONIFIED);
	frame.toFront();
	return frame;

//	FrameRec rec = map.get(frameName);
//	if (rec == null) throw new NullPointerException(
//		"FrameSet has not registered a frame of name '" + frameName + "'");
//	if (rec.frame != null) {
//		int state = rec.frame.getExtendedState();
//		rec.frame.setVisible(true);
//		rec.frame.setExtendedState(state & ~JFrame.ICONIFIED);
//		rec.frame.toFront();
//		return rec.frame;
//	}
//	rec.frame = rec.maker.newFrame();
//	rec.frame.setVisible(true);
//	return rec.frame;
}

    public Map<String, FrameSet.FrameRec> getMap() {
        return map;
    }

public JFrame newFrame(FrameRec rec) throws Exception
{
	JFrame frame = rec.maker.newFrame();
	frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	return frame;
}

public JFrame getFrame(String frameName) throws Exception
{
	FrameRec rec = map.get(frameName);
	if (rec == null) throw new NullPointerException(
		"FrameSet has not registered a frame of name '" + frameName + "'");
	if (rec.frame != null && rec.frame.isDisplayable()) {
		return rec.frame;
	}

	rec.frame = newFrame(rec);
	rec.frame.pack();
	if (swingPrefs != null) swingPrefs.setPrefs(rec.frame, "", prefsRoot.node("frame-" + rec.name));
	return rec.frame;
}





			// Also set up frame preferences
//			new citibob.swing.prefs.SwingPrefs().setPrefs(DevelFrame.this, "", fapp.userRoot().node("DevelFrame"));


}
