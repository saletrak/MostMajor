package pl.saletrak.MostMajor.feautures.rewards.gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import pl.saletrak.MostMajor.MMPlugin;

public interface RewardsGui {

    GuiRewardsManager manager = MMPlugin.getInstance().getGuiRewardsManager();

    void onItemClick(InventoryClickEvent event);

    void onCloseGui(InventoryCloseEvent event);

}
