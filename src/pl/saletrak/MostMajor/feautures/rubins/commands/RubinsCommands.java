package pl.saletrak.MostMajor.feautures.rubins.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.saletrak.MostMajor.MMPlugin;
import pl.saletrak.MostMajor.feautures.rubins.RubinsMessages;
import pl.saletrak.MostMajor.feautures.rubins.RubinsTransactionResponse;
import pl.saletrak.MostMajor.feautures.rubins.data.RubinsPlayerDataLogic;
import pl.saletrak.MostMajor.feautures.rubins.data.RubinsPlayersDataProvider;

public class RubinsCommands implements CommandExecutor {

	private MMPlugin plugin;

	public RubinsCommands(MMPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
		Player playerSender = (Player) commandSender;
		RubinsMessages messages = new RubinsMessages(playerSender);
		RubinsPlayersDataProvider rubinsPlayersDataProvider = plugin.getRubinsPlayersDataProvider();

		if (args.length >= 2) {
			String action = args[0];
			String targetPlayerName = args[1];

			Player targetPlayer = Bukkit.getServer().getPlayer(targetPlayerName);
			if (targetPlayer != null) {
				RubinsPlayerDataLogic rubinsTargetPlayerData = rubinsPlayersDataProvider.getPlayerDataLogic(targetPlayer);
				if (rubinsTargetPlayerData != null) {
					switch (action) {
						case "balance": {
							if (playerSender.hasPermission("moneymaker.rubins.balance.others")) {
								if (args.length == 2) {
									messages.otherRubinsAmount(targetPlayerName, rubinsTargetPlayerData.getRubins());
								} else {
									return false;
								}
							} else {
								messages.noPermission();
							}
							return true;
						}
						case "pay": {
							if (playerSender.hasPermission("moneymaker.rubins.pay")) {
								if (args.length == 3) {
									try {
										long amount = stringToAmount(args[2]);
										RubinsTransactionResponse response = rubinsTargetPlayerData.transferToPlayer(targetPlayer, amount);

										switch (response) {
											case SUCCESS:
												messages.successPayment();
												messages.confirmTransferToReceiver(targetPlayer, amount);
												break;
											case NO_GIVEN_PLAYER:
												messages.givenPlayerNotFound();
												break;
											case INSUFFICIENT_FUNDS:
												messages.insufficentFunds();
												break;
										}
									} catch (UnableToReadAmount e) {
										messages.invalidAmountValue();
									}
									return true;
								} else {
									return false;
								}
							} else {
								messages.noPermission();
							}
							return true;
						}
						case "set": {
							if (playerSender.hasPermission("moneymaker.rubins.set")) {
								if (args.length == 3) {
									try {
										long amount = stringToAmount(args[2]);
										rubinsTargetPlayerData.setRubins(amount);
										rubinsTargetPlayerData.save();
										messages.otherRubinsAmount(targetPlayerName, rubinsTargetPlayerData.getRubins());
									} catch (UnableToReadAmount e) {
										messages.invalidAmountValue();
									}
								} else {
									return false;
								}
							} else {
								messages.noPermission();
							}
							return true;
						}
					}
				}
			} else {
				messages.givenPlayerNotFound();
			}
		} else if (args.length == 0) {
			RubinsPlayerDataLogic rubinsTargetPlayerData = rubinsPlayersDataProvider.getPlayerDataLogic(playerSender);
			if (rubinsTargetPlayerData != null) {
				messages.playerRubinsAmountTitle(rubinsTargetPlayerData.getRubins());
				messages.playerRubinsAmountChat(rubinsTargetPlayerData.getRubins());
				return true;
			} else {
				messages.dataError();
			}
		}
		return false;
	}

	private static long stringToAmount(String string) {
		try {
			return Math.round(Double.valueOf(string));
		} catch (NumberFormatException e) {
			throw new UnableToReadAmount();
		}
	}

	private static class UnableToReadAmount extends RuntimeException {
	}

}
