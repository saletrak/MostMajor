package pl.saletrak.MostMajor.data_store;

public class PluginConfigLogic {

	private PluginConfig config = new PluginConfig();

	private String spawnWorldName;
	private String theEndWorldName;
	private boolean blockedTrapdoorsOnSpawn;

	public PluginConfigLogic() {
		spawnWorldName = config.getSpawnWorldNameStorage();
		theEndWorldName = config.getTheEndWorldNameStorage();
		blockedTrapdoorsOnSpawn = config.isBlockedTrapdoorsSpawn();
		config.save();
	}

	public String getSpawnWorldName() {
		return spawnWorldName;
	}

	public String getTheEndWorldName() {
		return theEndWorldName;
	}

	public boolean isBlockedTrapdoorsOnSpawn() {
		return blockedTrapdoorsOnSpawn;
	}
}
