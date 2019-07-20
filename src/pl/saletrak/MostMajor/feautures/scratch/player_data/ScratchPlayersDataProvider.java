package pl.saletrak.MostMajor.feautures.scratch.player_data;

import org.bukkit.entity.Player;
import pl.saletrak.MostMajor.MMPlugin;
import pl.saletrak.MostMajor.player.data.PlayersDataProvider;

public class ScratchPlayersDataProvider extends PlayersDataProvider<ScratchPlayerDataLogic, ScratchPlayerDataConfig> {

	@Override
	public ScratchPlayerDataLogic playerDataFromStorage(ScratchPlayerDataConfig config) {
		return new ScratchPlayerDataLogic(config);
	}

	@Override
	public ScratchPlayerDataConfig playerDataConfig(Player p) {
		return new ScratchPlayerDataConfig(p);
	}

	public static ScratchPlayerDataLogic playerData(Player p) {
		return MMPlugin.getInstance().getScratchPlayersDataProvider().getPlayerDataLogic(p);
	}
}
