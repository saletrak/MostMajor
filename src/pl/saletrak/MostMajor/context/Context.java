package pl.saletrak.MostMajor.context;

import java.util.HashMap;
import java.util.Map;

public class Context {

	private int totalActions;
	private Map<Integer, ContextAction> actions = new HashMap<Integer, ContextAction>();

	public void addAction(ContextAction action) {
		this.actions.put(this.totalActions, action);
		totalActions++;
		action.invoke();
	}

	public ContextAction getLastAction() {
		if (this.totalActions > 0) {
			return this.actions.get(this.totalActions - 1);
		} else {
			return null;
		}
	}

}
