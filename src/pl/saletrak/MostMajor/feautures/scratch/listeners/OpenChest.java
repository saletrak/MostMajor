package pl.saletrak.MostMajor.feautures.scratch.listeners;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import pl.saletrak.MostMajor.MMPlugin;

public class OpenChest implements Listener {

	private MMPlugin plugin;

	public OpenChest(MMPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onInventoryOpenEvent(InventoryOpenEvent e) {
		if (e.getInventory().getHolder() instanceof Chest || e.getInventory().getHolder() instanceof DoubleChest) {
			Inventory inventory = e.getInventory().getHolder().getInventory();
			System.out.print(e.getView().getTitle());

			if (inventory.getHolder() instanceof DoubleChest) {
				DoubleChest chest = (DoubleChest) inventory.getHolder();
				World world = Bukkit.getWorld("world");
				Block chestBlock = world.getBlockAt(chest.getLocation());
				Chest chestState = (Chest) chestBlock.getState();
				chestState.setCustomName("Zdrapka");
			}

			if (inventory.getHolder() instanceof Chest) {
				Chest chest = (Chest) inventory.getHolder();
				World world = Bukkit.getWorld("world");
				Block chestBlock = world.getBlockAt(chest.getLocation());
				Chest chest1 = (Chest) chestBlock.getState();
			}
		}
	}
}
