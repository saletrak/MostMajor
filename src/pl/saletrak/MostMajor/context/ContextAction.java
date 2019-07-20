package pl.saletrak.MostMajor.context;

import org.bukkit.entity.Player;
import pl.saletrak.MostMajor.MMPlugin;

import java.time.Instant;

public abstract class ContextAction implements ActionInterface {

	public boolean done = false;
	public boolean require_confirm = false;
	public boolean confirmed = false;
	public long invokedTimeStamp;
	private ContextAction onConfirmAction = null;
	private Context playerContext;

	public void invoke() {
		main();
		this.invokedTimeStamp = Instant.now().getEpochSecond();
		this.done = true;
	}

	public void setOnConfirmAction(ContextAction action) {
		this.require_confirm = true;
		this.onConfirmAction = action;
	}

	public void runOnConfirmMethod() {
		if (this.onConfirmAction != null) {
			this.playerContext.addAction(this.onConfirmAction);
		}
	}

	private void setPlayerContext(Context playerContext) {
		this.playerContext = playerContext;
	}

	public static void addActionToContext(MMPlugin plugin, Player player, ContextAction action) {
		ContextManager contextManager = plugin.getContextManager();
		Context context = contextManager.getPlayerContext(player.getDisplayName());
		action.setPlayerContext(context);
		context.addAction(action);
	}

	public boolean isActual(long seconds) {
		return Instant.now().getEpochSecond() - this.invokedTimeStamp <= seconds;
	}


}
