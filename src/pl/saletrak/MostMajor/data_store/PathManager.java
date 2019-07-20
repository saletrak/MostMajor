package pl.saletrak.MostMajor.data_store;

import java.util.UUID;

public class PathManager {

	public static String pluginDirectory() {
		return "plugins/MostMajor/";
	}

	public static String playerConfig(String username) {
		return PathManager.pluginDirectory() + "players/" + username + ".yml";
	}

	public static String playerDiffConfig(String username, String filename) {
		return PathManager.pluginDirectory() + "players/" + username + "/" + filename + ".yml";
	}

	public static String pluginConfig() {
		return PathManager.pluginDirectory() + "config.yml";
	}

	public static String checkpointsConfig() {
		return PathManager.pluginDirectory() + "checkpoints.yml";
	}

	public static String checkpointsRewardsConfig() {
		return PathManager.pluginDirectory() + "checkpoints_rewards.yml";
	}

	public static String marketplaceConfig() {
		return PathManager.pluginDirectory() + "marketplace.yml";
	}

	public static String levelsConfig() {
		return pluginDirectory() + "levels.yml";
	}

	public static String scratchConfig() {
		return pluginDirectory() + "scratch.yml";
	}

	public static String premiumRegionsConfig() {
		return pluginDirectory() + "premium_regions.yml";
	}

}
