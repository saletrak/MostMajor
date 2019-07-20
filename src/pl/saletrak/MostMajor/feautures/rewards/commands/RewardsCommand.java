package pl.saletrak.MostMajor.feautures.rewards.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.saletrak.MostMajor.MMPlugin;
import pl.saletrak.MostMajor.feautures.rewards.gui.RewardsListGui;

public class RewardsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player playerSender = (Player) commandSender;
        RewardsListGui rewardsListGui = MMPlugin.getInstance().getGuiRewardsManager().newPlayerRewardsListGui(playerSender);
        rewardsListGui.openGui();
        return false;
    }
}
