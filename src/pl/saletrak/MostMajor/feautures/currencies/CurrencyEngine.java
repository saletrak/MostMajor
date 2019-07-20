package pl.saletrak.MostMajor.feautures.currencies;

import org.bukkit.entity.Player;
import pl.saletrak.MostMajor.data_store.Config;

public class CurrencyEngine {

	private String name;
	private String config_key_name;

	public CurrencyEngine(String name, String config_key_count) {
		this.name = name;
		this.config_key_name = config_key_count;
	}

	public long getPlayerBalance(Player player) {
		Config playerData = Config.getPlayerData(player);
		long moneyAmmount;
		try {
			playerData.setDefault(this.config_key_name, 0);
			try {
				moneyAmmount = (long) playerData.get(this.config_key_name);
			} catch (ClassCastException e) {
				moneyAmmount = (int) playerData.get(this.config_key_name, 0);
			}
		} catch (NumberFormatException nfe) {
			moneyAmmount = 0;
		}
		return moneyAmmount;
	}

	public void setPlayerBalance(Player player, long amount) {
		Config playerData = Config.getPlayerData(player);
		playerData.set(this.config_key_name, amount);
		playerData.save();
	}

	public void givePlayerMoney(Player player, long amount) {
		long moneyAmmount = getPlayerBalance(player);
		setPlayerBalance(player, moneyAmmount + amount);
	}

	public boolean takeMoneyFromPlayer(Player player, long ammount, boolean allowNegative) {
		long moneyAmmount = getPlayerBalance(player);
		if(moneyAmmount < ammount) {
			if(allowNegative) setPlayerBalance(player, moneyAmmount - ammount);
			else return false;
		}
		else setPlayerBalance(player, moneyAmmount - ammount);
		return true;
	}

	public boolean transferMoneyToPlayer(Player sender, Player receiver, long ammount) {
		if(takeMoneyFromPlayer(sender, ammount, false)) {
			givePlayerMoney(receiver, ammount);
			return true;
		}
		return false;
	}
}
