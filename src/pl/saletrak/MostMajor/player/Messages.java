package pl.saletrak.MostMajor.player;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Messages {

	Player player;

	public Messages(Player player) {
		this.player = player;
	}

	public void sendMessage(String msg) {
		this.player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
	}
}
