package pl.saletrak.MostMajor.feautures.scratch.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.saletrak.MostMajor.MMPlugin;
import pl.saletrak.MostMajor.player.MMPlayer;
import pl.saletrak.MostMajor.feautures.scratch.ScratchesListGui;

public class ScratchCommands implements CommandExecutor {

	private MMPlugin plugin;

	public ScratchCommands(MMPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
		Player playerSender = (Player) commandSender;
		MMPlayer mmPlayer = new MMPlayer(playerSender);
		ScratchesListGui scratchesListGui = plugin.scratchManager.getPlayerScratchesListGui(playerSender);
		scratchesListGui.openScratchesGui();
		return true;
	}
}
