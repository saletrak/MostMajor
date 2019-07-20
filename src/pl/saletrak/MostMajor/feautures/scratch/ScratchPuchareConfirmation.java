package pl.saletrak.MostMajor.feautures.scratch;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.saletrak.MostMajor.MMPlugin;
import pl.saletrak.MostMajor.player.MMPlayer;

import java.util.ArrayList;
import java.util.List;

public class ScratchPuchareConfirmation implements ScratchGui {

	private Scratch.Type scratchType;
	private Inventory inv;
	private MMPlayer mmPlayer;
	private MMPlugin mmPlugin;
	private List<Integer> correctIS = new ArrayList<Integer>();
	private ScratchBuyGui.BuyCallback callback;

	public ScratchPuchareConfirmation(Scratch.Type scratchType, MMPlayer mmPlayer) {
		this.scratchType = scratchType;
		this.mmPlayer = mmPlayer;
		this.mmPlugin = MMPlugin.getInstance();
	}

	private void initilizeInv() {
		correctIS.clear();

		String title = "";
		switch (scratchType) {
			case BASIC:
				title = "Kupić Zdrapkę?";
				break;
			case PREMIUM:
				title = "Kupić Zdrapkę PREMIUM?";
				break;
		}
		inv = Bukkit.createInventory(null, 9, title);
		mmPlugin.getScratchManager().addInventory(inv, this);

		ItemStack itemStack;
		ItemMeta itemMeta;

		itemStack = new ItemStack(Material.LIME_WOOL);
		itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName("Potwierdź zakup");
		List<String> lore = new ArrayList<String>();
		lore.add("Koszt: " + scratchType.price + " rubinów");
		itemMeta.setLore(lore);
		itemStack.setItemMeta(itemMeta);
		inv.setItem(0, itemStack);
		correctIS.add(itemStack.hashCode());

		itemStack = new ItemStack(Material.RED_WOOL);
		itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName("Anuluj zakup");
		itemStack.setItemMeta(itemMeta);
		inv.setItem(1, itemStack);
		correctIS.add(itemStack.hashCode());
	}

	public void openConfirmation(ScratchBuyGui.BuyCallback callback) {
		initilizeInv();
		mmPlayer.getPlayer().openInventory(inv);
		this.callback = callback;
	}

	public boolean isValidIS(ItemStack itemStack) {
		return correctIS.contains(itemStack.hashCode());
	}

	@Override
	public void onItemClick(InventoryClickEvent event) {
		event.setCancelled(true);
		ItemStack is = event.getCurrentItem();

		if (is != null) {
			if (isValidIS(is)) {
				Material clickedMaterial = is.getType();
				switch (clickedMaterial) {
					case RED_WOOL:
						callback.onConfirmation(false);
						break;
					case LIME_WOOL:
						callback.onConfirmation(true);
						break;

				}
			}
		}
	}
}
