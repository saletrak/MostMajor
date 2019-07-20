package pl.saletrak.MostMajor.player.data;

import java.util.Map;

public interface PlayerDataInterface<T> {

	Map<String, Object> serialize();
	T deserialize();

}
