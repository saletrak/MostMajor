package pl.saletrak.MostMajor.player.data;

import org.bukkit.entity.Player;

public abstract class PlayerDataLogic<T extends PlayerDataConfig> {

	private T config;

	public PlayerDataLogic(T config) {
		this.config = config;
	}

	public T getConfig() {
		return config;
	}
}
