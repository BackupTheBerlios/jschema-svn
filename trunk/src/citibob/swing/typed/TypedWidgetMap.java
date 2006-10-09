///*
// * TypedWidgetMap.java
// *
// * Created on October 8, 2006, 7:02 PM
// *
// * To change this template, choose Tools | Options and locate the template under
// * the Source Creation and Management node. Right-click the template and choose
// 
// * Open. You can then make changes to the template in the Source Editor.
// */
//
//package citibob.swing.typed;
//
//import java.util.HashMap;
//import javax.swing.*;
//
///**
// * For use with Cobra's SimpleHtmlPanel.
// * @author citibob
// */
//public class TypedWidgetMap extends HashMap
//{
//	
//public JTypedTextField addTextField(String name, Swinger swinger)
//{
//	JTypedTextField widget = new JTypedTextField(swinger);
//	put(name, widget);
//	
//	// By default, text fields are size 25
////int x = widget.getHeight();
////	widget.setSize(150,21);
//	java.awt.Dimension d = widget.getPreferredSize();
//	d.width = 150;
//	widget.setSize(d);
////	widget.setSize(150, d.height);
//	return widget;
//}
//
//public Object getValue(String name)
//{
//	Object o = get(name);
//	if (o == null) return null;
//	if (!(o instanceof TypedWidget)) return null;
//	TypedWidget w = (TypedWidget)o;
//	return w.getValue();
//}
//
//}
