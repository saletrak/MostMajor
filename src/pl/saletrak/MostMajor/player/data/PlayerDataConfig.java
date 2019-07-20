package pl.saletrak.MostMajor.player.data;

import org.bukkit.entity.Player;
import pl.saletrak.MostMajor.data_store.Config;
import pl.saletrak.MostMajor.data_store.PathManager;

import java.io.File;

public abstract class PlayerDataConfig extends Config {

	private Player player;

	public PlayerDataConfig(String filename, Player p) {
		super(PathManager.playerDiffConfig(p.getName(), filename));
		player = p;
	}
	
}
