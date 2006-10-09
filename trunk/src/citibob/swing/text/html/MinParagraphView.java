package citibob.swing.text.html;

import javax.swing.text.html.*;
import javax.swing.*;

/**
 *
 * @author citibob
 */
public class MinParagraphView extends ParagraphView
{

public MinParagraphView(javax.swing.text.Element elem) {
	super(elem);
}

/** (Description from GNU Classpath 0.9.2)<br/>
* Calculates the minor axis requirements of this view. This is implemented
* to return the super class'es requirements and modifies the minimumSpan
* slightly so that it is not smaller than the length of the longest word.
*
* @param axis the axis
* @param r the SizeRequirements object to be used as return parameter;
*        if <code>null</code> a new one will be created
*
* @return the requirements along the minor layout axis
*/
protected SizeRequirements calculateMinorAxisRequirements(int axis, SizeRequirements r)
{
	SizeRequirements sr = super.calculateMinorAxisRequirements(axis, r);
	sr.minimum = 50;
	return sr;
}

}
