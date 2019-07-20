package pl.saletrak.MostMajor.feautures.rewards.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import pl.saletrak.MostMajor.MMPlugin;
import pl.saletrak.MostMajor.feautures.rewards.gui.RewardsGui;

public class RewardGuiClick implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inv = event.getInventory();

        RewardsGui rewardsGui = MMPlugin.getInstance().getGuiRewardsManager().isValidInventory(inv);
        if (rewardsGui != null) {
            rewardsGui.onItemClick(event);
        }
    }

}
