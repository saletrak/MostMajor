package pl.saletrak.MostMajor.player.data;

import org.bukkit.entity.Player;

public interface PlayersDataProviderInterface<T, R> {

	R playerDataConfig(Player p);
	T playerDataFromStorage(R config);

}
