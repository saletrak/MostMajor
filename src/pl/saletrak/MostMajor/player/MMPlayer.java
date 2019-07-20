package pl.saletrak.MostMajor.player;

import de.Herbystar.TTA.TTA_Methods;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import pl.saletrak.MostMajor.MMPlugin;
import pl.saletrak.MostMajor.data_store.Config;
import pl.saletrak.MostMajor.data_store.Data;
import pl.saletrak.MostMajor.data_store.PathManager;
import pl.saletrak.MostMajor.integrations.VaultIntegration;
import pl.saletrak.MostMajor.player.data.PlayerDataPaths;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class MMPlayer {

	private Player player;

	public MMPlayer(Player player) {
		this.player = player;
	}

	/**
	 * @deprecated
	 */
	protected PlayerDataOld getPlayerData() {
		return new PlayerDataOld(player);
	}

	public Config getData() {
		return Config.loadConfiguration(PathManager.playerConfig(player.getName()));
	}

	public Map<String, Object> getShops() {
		Map<String, Object> shops;
		try {
			shops = getPlayerData().getMemorySection(PlayerDataPaths.shops()).getValues(false);
		} catch (Data.CantReturnMemorySection e) {
			shops = new HashMap<>();
		}
		return shops;
	}

	public Map<String, Object> getCheckpoints() {
		Map<String, Object> checkpoints;
		try {
			checkpoints = getPlayerData().getMemorySection(PlayerDataPaths.checkpoints()).getValues(false);
		} catch (Data.CantReturnMemorySection e) {
			checkpoints = new HashMap<>();
		}
		return checkpoints;
	}

	public Duration getPlayedTime() {
		Object playedTime = getPlayerData().getData().get(PlayerDataPaths.timePlayed());
		if (getPlayerData().getData().isLong(PlayerDataPaths.timePlayed())) {
			return Duration.ofSeconds((long) playedTime);
		} else if (getPlayerData().getData().isInt(PlayerDataPaths.timePlayed())) {
			int time = (int) playedTime;
			return Duration.ofSeconds((long) time);
		} else {
			return Duration.ofSeconds(0L);
		}
	}

	public void setPlayedTime(long seconds) {
		PlayerDataOld pd = getPlayerData();
		pd.getData().set(PlayerDataPaths.timePlayed(), seconds);
		pd.save();
	}

	public void setUUID(String uuid) {
		PlayerDataOld pd = getPlayerData();
		pd.getData().set(PlayerDataPaths.uuid(), uuid);
		pd.save();
	}

	public void sendActionBar(String msg) {
		TTA_Methods.sendActionBar(getPlayer(), ChatColor.translateAlternateColorCodes('&', msg));
	}

	public void sendMessage(String msg) {
		getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
	}

	public void runPlayedTimeService(MMPlugin plugin) {
		Duration timePlayed = getPlayedTime();
		Instant timeOnJoin = Instant.now();

		BukkitScheduler scheduler = plugin.getServer().getScheduler();
		int taskId = scheduler.scheduleSyncRepeatingTask(plugin, () -> {
			if (player.isOnline()) {
//				System.out.print(player.getDisplayName() + "'s survey");
				Instant timeOnSurvey = Instant.now();
				Duration sessionTime = Duration.ofSeconds(timeOnSurvey.minusSeconds(timeOnJoin.getEpochSecond()).getEpochSecond());
				Duration newTimePlayed = timePlayed.plus(sessionTime);
				setPlayedTime(newTimePlayed.getSeconds());
//				player.sendMessage("Obecna sesja: " + sessionTime.getSeconds() + "; Ogólnie: " + newTimePlayed.getSeconds());
			} else {
				// TODO stop this fucking loop
			}
		}, 600L, 600L);
	}

	public EconomyResponse chargePlayer(double amount) {
		return VaultIntegration.getEconomy().withdrawPlayer(player, amount);
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
