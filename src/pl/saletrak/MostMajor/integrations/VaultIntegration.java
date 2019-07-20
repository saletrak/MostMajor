package pl.saletrak.MostMajor.integrations;

import java.util.logging.Logger;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class VaultIntegration {

	public static Economy getEconomy() {
		Plugin mmPlugin = Bukkit.getPluginManager().getPlugin("MostMajor");
		RegisteredServiceProvider<Economy> economyProvider = mmPlugin.getServer().getServicesManager().getRegistration(Economy.class);
		return economyProvider.getProvider();
	}
}
