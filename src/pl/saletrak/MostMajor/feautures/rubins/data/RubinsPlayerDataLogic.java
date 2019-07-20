package pl.saletrak.MostMajor.feautures.rubins.data;

import org.bukkit.entity.Player;
import pl.saletrak.MostMajor.MMPlugin;
import pl.saletrak.MostMajor.feautures.rubins.RubinsTransactionResponse;
import pl.saletrak.MostMajor.player.data.PlayerDataLogic;

public class RubinsPlayerDataLogic extends PlayerDataLogic<RubinsPlayerDataConfig> {

	private long rubins;

	public RubinsPlayerDataLogic(RubinsPlayerDataConfig config) {
		super(config);
		rubins = getConfig().getRubins();
	}

	public long getRubins() {
		return rubins;
	}

	public void setRubins(long amount) {
		rubins = amount;
	}

	public void increaseRubins(long amount) {
		rubins += amount;
	}

	private void decreaseRubins(long amount) {
		rubins -= amount;
	}

	public boolean hasRubins(long amount) {
		return rubins >= amount;
	}

	public RubinsTransactionResponse transferToPlayer(Player player, long amount) {
		RubinsPlayerDataLogic targetPlayerData = MMPlugin.getInstance().getRubinsPlayersDataProvider().getPlayerDataLogic(player);
		if (targetPlayerData != null) {
			if (hasRubins(amount)) {
				decreaseRubins(amount);
				save();
				targetPlayerData.increaseRubins(amount);
				targetPlayerData.save();
				return RubinsTransactionResponse.SUCCESS;
			} else {
				return RubinsTransactionResponse.INSUFFICIENT_FUNDS;
			}
		} else {
			return RubinsTransactionResponse.NO_GIVEN_PLAYER;
		}
	}

	public RubinsTransactionResponse takeRubinsFromPlayer(long amount) {
		if (hasRubins(amount)) {
			decreaseRubins(amount);
			save();
			return RubinsTransactionResponse.SUCCESS;
		} else {
			return RubinsTransactionResponse.INSUFFICIENT_FUNDS;
		}
	}

	public void save() {
		System.out.print("saving: " + rubins);
		getConfig().setRubins(rubins);
	}
}
