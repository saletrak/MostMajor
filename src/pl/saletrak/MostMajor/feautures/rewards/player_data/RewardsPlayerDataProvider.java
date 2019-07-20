package pl.saletrak.MostMajor.feautures.rewards.player_data;

import org.bukkit.entity.Player;
import pl.saletrak.MostMajor.player.data.PlayersDataProvider;

public class RewardsPlayerDataProvider extends PlayersDataProvider<RewardsPlayerDataLogic, RewardsPlayerDataConfig> {
    @Override
    public RewardsPlayerDataLogic playerDataFromStorage(RewardsPlayerDataConfig config) {
        return new RewardsPlayerDataLogic(config);
    }

    @Override
    public RewardsPlayerDataConfig playerDataConfig(Player p) {
        return new RewardsPlayerDataConfig(p);
    }
}
