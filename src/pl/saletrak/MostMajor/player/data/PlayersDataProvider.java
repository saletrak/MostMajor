package pl.saletrak.MostMajor.player.data;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class PlayersDataProvider<T extends PlayerDataLogic, R extends PlayerDataConfig>
		implements PlayersDataProviderInterface<T, R> {

	private Map<UUID, T> playerDataMap;

	public PlayersDataProvider() {
		playerDataMap = new HashMap<>();
	}

	public T getPlayerDataLogic(Player p) {
		UUID uuid = p.getUniqueId();
		if (playerDataMap.containsKey(uuid)) {
			return playerDataMap.get(uuid);
		} else {
			T playerData = playerDataFromStorage(playerDataConfig(p));
			playerDataMap.put(p.getUniqueId(), playerData);
			return playerData;
		}
	}

	public void removePlayerData(Player p) {
		playerDataMap.remove(p.getUniqueId());
	}

}