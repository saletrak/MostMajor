package pl.saletrak.MostMajor.player;

import org.bukkit.entity.Player;
import pl.saletrak.MostMajor.data_store.Data;
import pl.saletrak.MostMajor.data_store.PathManager;


/** @deprecated */
public class PlayerDataOld extends Data {

	private Player player;

	public PlayerDataOld(Player player) {
		super(PathManager.playerConfig(player.getName()));
//		System.out.print(PathManager.playerConfig(player.getName()));
		this.player = player;
	}

	public PlayerDataOld(String playerName) {
		super(PathManager.playerConfig(playerName));
	}
}
