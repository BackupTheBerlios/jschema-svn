package citibob.swing.text.html;

import java.awt.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import java.util.*;

public class ConstObjectView extends ObjectView
{

	
Component obj;		// The component we map to

  /**
   * Creates a new <code>ObjectView</code>.
   *
   * @param el the element for which to create a view
   */
  public ConstObjectView(Element el, HashMap map)
  {
    super(el);
    AttributeSet atts = el.getAttributes();
    String classId = (String) atts.getAttribute("classid");
	this.obj = (Component)map.get(classId);
  }

  /**
   * Creates a component based on the specification in the element of this
   * view. See the class description for details.
   */
  protected Component createComponent()
  {
	  if (obj != null) return obj;
	  else return super.createComponent();
  }
}
