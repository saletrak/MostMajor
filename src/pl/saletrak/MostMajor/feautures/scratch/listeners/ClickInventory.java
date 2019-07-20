package pl.saletrak.MostMajor.feautures.scratch.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import pl.saletrak.MostMajor.MMPlugin;
import pl.saletrak.MostMajor.feautures.scratch.ScratchGui;

public class ClickInventory implements Listener {

	MMPlugin plugin;

	public ClickInventory(MMPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryClick(InventoryClickEvent event) {
		Inventory inv = event.getInventory();
		ScratchGui scratchGui = this.plugin.scratchManager.isValidInventory(inv);
		if (scratchGui != null) {
			scratchGui.onItemClick(event);
		}
	}
}
