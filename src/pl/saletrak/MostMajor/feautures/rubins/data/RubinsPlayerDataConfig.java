package pl.saletrak.MostMajor.feautures.rubins.data;

import org.bukkit.entity.Player;
import pl.saletrak.MostMajor.player.data.PlayerDataConfig;

public class RubinsPlayerDataConfig extends PlayerDataConfig {

	public RubinsPlayerDataConfig(Player p) {
		super(RubinsPlayerDataPaths.filename(), p);
	}

	public int getRubins() {
		return getInt(RubinsPlayerDataPaths.rubins());
	}

	public void setRubins(long amount) {
		set(RubinsPlayerDataPaths.rubins(), amount);
		save();
	}

}
