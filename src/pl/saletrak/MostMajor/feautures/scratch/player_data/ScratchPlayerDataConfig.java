package pl.saletrak.MostMajor.feautures.scratch.player_data;

import org.bukkit.entity.Player;
import pl.saletrak.MostMajor.player.data.PlayerDataConfig;

public class ScratchPlayerDataConfig extends PlayerDataConfig {

	public ScratchPlayerDataConfig(Player p) {
		super(ScratchPlayerDataPaths.fileName(), p);
	}

	public int getAvaibleBasicScratchesStored() {
		String path = ScratchPlayerDataPaths.avaibleBasicScratches();
		setDefault(path, 0);
		return getInt(path);
	}

	public int getAvaiblePremiumScratchesStored() {
		String path = ScratchPlayerDataPaths.avaiblePremiumScratches();
		setDefault(path, 0);
		return getInt(path);
	}

	public long getLastFreeDraftStored() {
		String path = ScratchPlayerDataPaths.scratchLastFreeDraft();
		setDefault(path, 0);
		return getLongValueOrZero(path);
	}

	public void setAvaibleBasicScratchesStored(int amount) {
		String path = ScratchPlayerDataPaths.avaibleBasicScratches();
		set(path, amount);
	}

	public void setAvaiblePremiumScratchesStored(int amount) {
		String path = ScratchPlayerDataPaths.avaiblePremiumScratches();
		set(path, amount);
	}

	public void setLastFreeDraftStored(long timestamp) {
		String path = ScratchPlayerDataPaths.scratchLastFreeDraft();
		set(path, timestamp);
	}
}
