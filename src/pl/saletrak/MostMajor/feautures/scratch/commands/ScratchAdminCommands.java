package pl.saletrak.MostMajor.feautures.scratch.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.saletrak.MostMajor.feautures.scratch.player_data.ScratchPlayerDataLogic;
import pl.saletrak.MostMajor.feautures.scratch.player_data.ScratchPlayersDataProvider;

public class ScratchAdminCommands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
		if (strings.length == 4) {
			String action = strings[0];
			String param2 = strings[1]; // whatToSet
			String param3 = strings[2]; // player
			String param4 = strings[3]; // ammount
			switch (action) {
				case "set":
					if (commandSender.hasPermission("moneymaker.scratch.admin.set")) {
						switch (param2) {
							case "basic":
								try {
									int ammount = Integer.valueOf(param4);
									Player player = Bukkit.getPlayer(param3);
									if (player != null) {
										ScratchPlayerDataLogic playerData = ScratchPlayersDataProvider.playerData(player);
										if (playerData != null) {
											playerData.setBasicScratches(ammount);
											commandSender.sendMessage("Gracz " + player.getDisplayName() + ": "
													+ playerData.getBasicScratches() + " Zdrapek");
										}
									} else {
										commandSender.sendMessage("Nie ma takiego gracza na serwerze");
									}
								} catch (NumberFormatException e) {
									commandSender.sendMessage("ammount invalid");
								}
								break;
							case "premium":
								try {
									int ammount = Integer.valueOf(param4);
									Player player = Bukkit.getPlayer(param3);
									if (player != null) {
										ScratchPlayerDataLogic playerData = ScratchPlayersDataProvider.playerData(player);
										if (playerData != null) {
											playerData.setPremiumScratches(ammount);
											commandSender.sendMessage("Gracz " + player.getDisplayName() + ": "
													+ playerData.getPremiumScratches() + " Zdrapek Premium");
										}
									} else {
										commandSender.sendMessage("Nie ma takiego gracza na serwerze");
									}
								} catch (NumberFormatException e) {
									commandSender.sendMessage("ammount invalid");
								}
								break;
							case "daily":
								Player player = Bukkit.getPlayer(param3);
								if (player != null) {
									//MMPlayer mmPlayer = new MMPlayer(player);
									ScratchPlayerDataLogic playerData = ScratchPlayersDataProvider.playerData(player);
									if (playerData != null) {
										switch (param4) {
											case "on":
												playerData.setLastFreeDraft(0L);
												commandSender.sendMessage("Włączyłeś dzisiejszą zdrapke dla " + player.getDisplayName());
												break;
											case "off":
												playerData.updateLastDraft();
												commandSender.sendMessage("Wyłączyłeś dzisiejszą zdrapke dla " + player.getDisplayName());
												break;
											default:
												commandSender.sendMessage("valid value: on|off");
												break;
										}
									}
								} else {
									commandSender.sendMessage("Nie ma takiego gracza na serwerze");
								}

								break;
						}
					} else commandSender.sendMessage("Nie masz uprawnień");
					break;
			}
			return true;
		} else {
			return false;
		}
	}
}
