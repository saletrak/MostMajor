package pl.saletrak.MostMajor.data_store;

import net.minecraft.server.v1_14_R1.Path;

public enum ConfigSource {

	LEVELS(PathManager.levelsConfig()),
	SCRATCH(PathManager.scratchConfig()),
	PREMIUM_REGION(PathManager.premiumRegionsConfig());

	String configPath;

	private ConfigSource(String configPath) {
		this.configPath = configPath;
	}

	public Config getConfig() {
		return Config.loadConfiguration(configPath);
	}

}