package citibob.wizard;

import citibob.sql.SqlRunner;

public interface WizState
{
	/** Name of this state. */
	public String getName();
	
	/** Name of the "back" state */
	public String getBack();
	
	/** Name of the "next" state */
	public String getNext();

	/** Create the physical screen to display */
	public Wiz newWiz(Wizard.Context con) throws Exception;

	/** Runs before the Wiz, even if cached Wiz is being re-used. */
	public void pre(Wizard.Context con) throws Exception;
	
	/** Runs after the Wiz */
	public abstract void process(Wizard.Context con) throws Exception;

// =============================================================

}

	
	
