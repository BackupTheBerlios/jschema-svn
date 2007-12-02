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
	public Wiz newWiz(Context con) throws Exception;

	/** Runs before the Wiz, even if cached Wiz is being re-used. */
	public void pre(Context con) throws Exception;
	
	/** Runs after the Wiz */
	public abstract void process(Context con) throws Exception;

// =============================================================
/** Passed to State processing methods */
public class Context {
	public SqlRunner str;		// Access to database
	public TypedHashMap v;		// Values passed around
//	String stateName;			// Current state we're in
	// ... maybe others...
	public Context(SqlRunner str, TypedHashMap v) {
		this.str = str;
		this.v = v;
	}
}
}

	
	
