package pl.saletrak.MostMajor.feautures.currencies.commands;


import de.Herbystar.TTA.TTA_Methods;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.saletrak.MostMajor.MMPlugin;
import pl.saletrak.MostMajor.feautures.currencies.CurrencyEngine;

public class GameCoinsCommands implements CommandExecutor {

	private MMPlugin plugin;
	private CurrencyEngine currencyEngine;
	private String perBalanceOthers = "moneymaker.gamecoins.balance.others";
	private String perPay = "moneymaker.gamecoins.pay";
	private String perSet = "moneymaker.gamecoins.set";

	public GameCoinsCommands(MMPlugin plugin) {
		this.plugin = plugin;
		this.currencyEngine = this.plugin.gameCoins;
	}

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
		Player playerSender = (Player) commandSender;
		if (args.length >= 2) {
			Player targetPlayer = Bukkit.getServer().getPlayer(args[1]);
			if (targetPlayer != null) {
				switch (args[0]) {
					case "balance": {
						if (playerSender.hasPermission(perBalanceOthers)) {
							long targetPlayerBalance = currencyEngine.getPlayerBalance(targetPlayer);
							playerSender.sendMessage(args[1] + "'s GameCoins: " + printAmount(targetPlayerBalance));
						} else {
							playerSender.sendMessage(printError("You are not permitted to do this"));
						}
						return true;
					}
					case "pay": {
						if (playerSender.hasPermission(perPay)) {
							if (args.length == 3) {
								long amount = stringToAmount(args[2]);
								if (currencyEngine.transferMoneyToPlayer(playerSender, targetPlayer, amount)) {
									playerSender.sendMessage(printSuccess("Pomyślnie zrealizowano płatnosć"));
									targetPlayer.sendMessage(printSuccess("Otrzymałeś " + printAmount(amount) +
											" GameCoinów od " + playerSender.getDisplayName()));
								} else {
									playerSender.sendMessage(printError("Nie masz tyle GameCoinów"));
								}
								playerSender.sendMessage("Twoje GameCoiny: " +
										printAmount(currencyEngine.getPlayerBalance(playerSender)));
							} else {
								return false;
							}
						} else {
							playerSender.sendMessage(printError("You are not permitted to do this"));
						}
						return true;
					}
					case "set": {
						if (playerSender.hasPermission(perSet)) {
							if (args.length == 3) {
								long amount = stringToAmount(args[2]);
								currencyEngine.setPlayerBalance(targetPlayer, amount);
								long targetPlayerBalance = currencyEngine.getPlayerBalance(targetPlayer);
								playerSender.sendMessage(args[1] + "'s GameCoins: " + printAmount(targetPlayerBalance));
							} else {
								return false;
							}
						} else {
							playerSender.sendMessage(printError("You are not permitted to do this"));
						}
						return true;
					}
				}
			} else {
				playerSender.sendMessage(printError("Not found given player"));
			}
		} else if (args.length == 0) {
			int stay = 45;
			int fade = 10;
			long playerBalance = currencyEngine.getPlayerBalance(playerSender);
			TTA_Methods.sendTitle(playerSender,
					this.printAmount(playerBalance), fade, stay, fade, "Twoje GameCoiny", fade, stay, fade);
			playerSender.sendMessage("Twoje GameCoiny: " + this.printAmount(playerBalance));
			return true;
		}
		return false;
	}

	private String printAmount(long amount) {
		return "§6" + String.valueOf(amount) + "§l§oGc";
	}

	private long stringToAmount(String string) {
		return Math.round(Double.valueOf(string));
	}

	private String printError(String string) {
		return "§c"+string;
	}

	private String printSuccess(String string) {
		return "§a"+string;
	}

}
