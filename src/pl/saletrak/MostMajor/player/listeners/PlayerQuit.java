package pl.saletrak.MostMajor.player.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.saletrak.MostMajor.MMPlugin;

public class PlayerQuit implements Listener {

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		MMPlugin mmPlugin = MMPlugin.getInstance();

		mmPlugin.getRubinsPlayersDataProvider().removePlayerData(event.getPlayer());
	}

}
