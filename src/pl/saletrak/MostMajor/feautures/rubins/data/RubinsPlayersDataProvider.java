package pl.saletrak.MostMajor.feautures.rubins.data;

import org.bukkit.entity.Player;
import pl.saletrak.MostMajor.MMPlugin;
import pl.saletrak.MostMajor.player.data.PlayersDataProvider;

public class RubinsPlayersDataProvider extends PlayersDataProvider<RubinsPlayerDataLogic, RubinsPlayerDataConfig> {

	@Override
	public RubinsPlayerDataLogic playerDataFromStorage(RubinsPlayerDataConfig config) {
		return new RubinsPlayerDataLogic(config);
	}

	@Override
	public RubinsPlayerDataConfig playerDataConfig(Player p) {
		return new RubinsPlayerDataConfig(p);
	}

	public static RubinsPlayerDataLogic playerData(Player p) {
		return MMPlugin.getInstance().getRubinsPlayersDataProvider().getPlayerDataLogic(p);
	}

}
