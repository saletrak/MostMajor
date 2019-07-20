package pl.saletrak.MostMajor.feautures.rewards.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GuiRewardsManager {

    private Map<Integer, RewardsGui> validInventories = new HashMap<Integer, RewardsGui>();

    private Map<UUID, RewardsListGui> rewardsListGuiMap = new HashMap<>();
    private Map<UUID, RewardsOrderGui> rewardsOrderGuiMap = new HashMap<>();

    public void registerInventory(Inventory inv, RewardsGui rewardsGui) {
        validInventories.put(inv.hashCode(), rewardsGui);
    }

    public void unregisterInvetory(Inventory inv) {
        validInventories.remove(inv.hashCode());
    }

    public RewardsGui isValidInventory(Inventory inv) {
        if (validInventories.containsKey(inv.hashCode())) {
            return validInventories.get(inv.hashCode());
        }
        return null;
    }




    public RewardsListGui newPlayerRewardsListGui(Player player) {
        RewardsListGui rewardsListGui = new RewardsListGui(player);
        this.rewardsListGuiMap.put(player.getUniqueId(), rewardsListGui);
        return rewardsListGui;
    }

    public void unregisterPlayerRewardListGui(@Nonnull  Player player, @Nonnull RewardsListGui gui) {
        this.rewardsListGuiMap.remove(player.getUniqueId(), gui);
    }

    @Nullable
    public RewardsListGui getPlayerRewardsListGui(Player player) {
        return this.rewardsListGuiMap.get(player.getUniqueId());
    }




    public void registerPlayerRewardsOrderGui(@Nonnull Player player, @Nonnull RewardsOrderGui gui) {
        this.rewardsOrderGuiMap.put(player.getUniqueId(), gui);
    }

    public void unregisterPlayerRewardsOrderGui(@Nonnull Player player, @Nonnull RewardsOrderGui gui) {
        this.rewardsOrderGuiMap.remove(player.getUniqueId(), gui);
    }

}
