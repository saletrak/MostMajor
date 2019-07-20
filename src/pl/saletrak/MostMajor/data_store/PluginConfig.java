package pl.saletrak.MostMajor.data_store;

import org.bukkit.Bukkit;

public class PluginConfig extends Config {

	public PluginConfig() {
		super(PathManager.pluginConfig());
	}

	String getSpawnWorldNameStorage() {
		String path = "spawnWorldName";
		setDefault(path, Bukkit.getServer().getWorlds().get(0).getName());
		return getString(path);
	}

	String getTheEndWorldNameStorage() {
		String path = "theEndWorldName";
		setDefault(path, Bukkit.getServer().getWorlds().get(2).getName());
		return getString(path);
	}

	boolean isBlockedTrapdoorsSpawn() {
		String path = "blockTrapdoorsOnSpawn";
		setDefault(path, true);
		return getBoolean(path);
	}

}
