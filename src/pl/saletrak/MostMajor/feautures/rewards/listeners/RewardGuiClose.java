package pl.saletrak.MostMajor.feautures.rewards.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import pl.saletrak.MostMajor.MMPlugin;
import pl.saletrak.MostMajor.feautures.rewards.gui.RewardsGui;

public class RewardGuiClose implements Listener {

    @EventHandler
    public void onCloseInventory(InventoryCloseEvent event) {
        Inventory inv = event.getInventory();

        RewardsGui rewardsGui = MMPlugin.getInstance().getGuiRewardsManager().isValidInventory(inv);
        if (rewardsGui != null) {
            rewardsGui.onCloseGui(event);
        }
    }

}
