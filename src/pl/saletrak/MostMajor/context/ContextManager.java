package pl.saletrak.MostMajor.context;


import java.util.HashMap;
import java.util.Map;

public class ContextManager {

	private Map<String, Context> playersContexts = new HashMap<String, Context>();

	public Context getPlayerContext(String playerName) {
		Context context = this.playersContexts.get(playerName);
		if(context != null) {
			return context;
		} else {
			this.addPlayerContext(playerName);
			return this.playersContexts.get(playerName);
		}
	}

	private void addPlayerContext(String playerName) {
		this.playersContexts.put(playerName, new Context());
	}

	public void setPlayersContext(String playerName, Context context) {
		this.playersContexts.put(playerName, context);
	}

	public static class PlayerDoesentHaveContext extends Error {}


}
