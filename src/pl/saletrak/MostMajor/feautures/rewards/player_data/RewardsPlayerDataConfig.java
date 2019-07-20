package pl.saletrak.MostMajor.feautures.rewards.player_data;

import com.mysql.fabric.xmlrpc.base.Array;
import org.bukkit.configuration.MemorySection;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.entity.Player;
import pl.saletrak.MostMajor.player.data.PlayerDataConfig;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RewardsPlayerDataConfig extends PlayerDataConfig {
    public RewardsPlayerDataConfig(Player p) {
        super(RewardsPlayerDataPaths.fileName(), p);
    }

    @Nonnull
    public List<Map<String, Object>> getRewards() {
        return (List<Map<String, Object>>) (List<?>) getMapList("rewards");
    }

    public void setRewards(List<Map<String, Object>> rewards) {
        set("rewards", rewards);
    }
}
