package pl.saletrak.MostMajor.integrations;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

import static org.bukkit.Bukkit.getServer;

public class WorldGuardIntegration {

	public static WorldGuardPlugin getWorldGuard() {
		Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");

		if (!(plugin instanceof WorldGuardPlugin)) {
			return null; // Maybe you want throw an exception instead
		}

		return (WorldGuardPlugin) plugin;
	}

	public static RegionManager getRegionManager(World world) {
		WorldGuardPlugin worldGuardPlugin = WorldGuardIntegration.getWorldGuard();
		if (worldGuardPlugin != null) {
			RegionContainer regionContainer = WorldGuard.getInstance().getPlatform().getRegionContainer();
			if (regionContainer != null) {
				RegionManager regionManager = regionContainer.get(BukkitAdapter.adapt(world));
				if (regionManager != null) {
					return regionManager;
				}
			}
		}
		throw new MissingWorldGuard();
	}

	public static class MissingWorldGuard extends Error {}

}
