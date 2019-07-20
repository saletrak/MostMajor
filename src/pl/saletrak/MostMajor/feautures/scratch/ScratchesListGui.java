package pl.saletrak.MostMajor.feautures.scratch;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.saletrak.MostMajor.MMPlugin;
import pl.saletrak.MostMajor.feautures.scratch.player_data.ScratchPlayerDataLogic;
import pl.saletrak.MostMajor.feautures.scratch.player_data.ScratchPlayersDataProvider;
import pl.saletrak.MostMajor.player.MMPlayer;

import java.util.ArrayList;
import java.util.List;

public class ScratchesListGui implements ScratchGui {

	private MMPlayer mmPlayer;
	private Inventory inv;
	private List<Integer> correctIS = new ArrayList<Integer>();
	private MMPlugin mmPlugin;
	private ScratchPlayerDataLogic scratchPlayerDataLogic;

	public ScratchesListGui(MMPlayer mmPlayer) {
		this.mmPlayer = mmPlayer;
		this.scratchPlayerDataLogic = ScratchPlayersDataProvider.playerData(mmPlayer.getPlayer());
		this.inv = Bukkit.createInventory(null, 6 * 9, "Twoje zdrapki");
		MMPlugin.getInstance().getScratchManager().addInventory(inv, this);
		this.mmPlugin = MMPlugin.getInstance();
	}

	public void openScratchesGui() {
		initializeItems();
		mmPlayer.getPlayer().openInventory(inv);
	}

	public void setNewInventory() {
		inv = Bukkit.createInventory(null, 6 * 9, "Twoje zdrapki");
	}

	private void initializeItems() {
		ItemMeta meta;
		correctIS.clear();

		ItemStack freeScratch;
		if (scratchPlayerDataLogic.isAvaibleFreeScratch()) {
			freeScratch = new ItemStack(Material.DRIED_KELP_BLOCK, 1);
			meta = freeScratch.getItemMeta();
			meta.setDisplayName("Masz dostępną jedną darmową zdrapkę");
		} else {
			freeScratch = new ItemStack(Material.GRAY_CARPET, 1);
			meta = freeScratch.getItemMeta();
			meta.setDisplayName("Wykorzystałeś dzisiejszą darmową zdrapkę");
		}
		freeScratch.setItemMeta(meta);
		inv.setItem(0, freeScratch);
		correctIS.add(freeScratch.hashCode());

		ItemStack buySctatch = new ItemStack(Material.LIME_WOOL);
		meta = buySctatch.getItemMeta();
		meta.setDisplayName("Kup nową zdrapkę");
		buySctatch.setItemMeta(meta);
		inv.setItem(1, buySctatch);
		correctIS.add(buySctatch.hashCode());

		int avaiblePremiumScratches = scratchPlayerDataLogic.getPremiumScratches();
		int avaibleBasicScratches = scratchPlayerDataLogic.getBasicScratches();
		//System.out.print("basic: " + avaibleBasicScratches + "; premium: " + avaiblePremiumScratches);

		List<String> lore = new ArrayList<String>();
		lore.add("Kliknij, aby otworzyć");

		int lastTakenSlot = 8;
		int maxSlotId = 53;

		for (int i = 0; i < avaiblePremiumScratches; i++) {
			if (lastTakenSlot < maxSlotId) {
				lastTakenSlot++;
				ItemStack premiumScratchItem = new ItemStack(Scratch.Type.PREMIUM.itemIcon);
				ItemMeta premiumScratchItemMeta = premiumScratchItem.getItemMeta();
				premiumScratchItemMeta.setLore(lore);
				premiumScratchItemMeta.setDisplayName("Zdrapka PREMIUM #" + (int) (Math.random() * 99999999 + 1));
				premiumScratchItem.setItemMeta(premiumScratchItemMeta);
				inv.setItem(lastTakenSlot, premiumScratchItem);
				correctIS.add(premiumScratchItem.hashCode());
			}
		}
		for (int i = 0; i < avaibleBasicScratches; i++) {
			if (lastTakenSlot < maxSlotId) {
				lastTakenSlot++;
				ItemStack basicScratchItem = new ItemStack(Scratch.Type.BASIC.itemIcon);
				ItemMeta basicScratchItemMeta = basicScratchItem.getItemMeta();
				basicScratchItemMeta.setLore(lore);
				basicScratchItemMeta.setDisplayName("Zdrapka #" + (int) (Math.random() * 99999999 + 1));
				basicScratchItem.setItemMeta(basicScratchItemMeta);
				inv.setItem(lastTakenSlot, basicScratchItem);
				correctIS.add(basicScratchItem.hashCode());
			}
		}
		for (int i = 0; i < 6 * 9; i++) {
			if (lastTakenSlot < maxSlotId) {
				lastTakenSlot++;
				ItemStack emptyItem = new ItemStack(Material.AIR);
				inv.setItem(lastTakenSlot, emptyItem);
			}
		}
	}

	public boolean isValidIS(ItemStack itemStack) {
		return correctIS.contains(itemStack.hashCode());
	}

	public void onItemClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();

		event.setCancelled(true);
		ItemStack is = event.getCurrentItem();

		if (is != null) {
			if (isValidIS(is)) {
				Material clickedMaterial = is.getType();
				switch (clickedMaterial) {
					case DRIED_KELP_BLOCK:
						scratchPlayerDataLogic.updateLastDraft();
						scratchPlayerDataLogic.save();
						Scratch playerScratch = mmPlugin.scratchManager.getPlayerNewScratch(mmPlayer.getPlayer());
						playerScratch.openBasicScratch(mmPlayer.getPlayer());
						break;
					case LIME_WOOL:
						Scratch.openStore(player);
						break;
					case GRAY_CARPET:
						if (player.hasPermission("moneymaker.scratch.infinite")) {
							Scratch playerScratch2 = mmPlugin.scratchManager.getPlayerNewScratch(mmPlayer.getPlayer());
							playerScratch2.openBasicScratch(mmPlayer.getPlayer());
						}
						break;
					default:
						if (clickedMaterial.equals(Scratch.Type.BASIC.itemIcon)) {
							Scratch basicScratch = mmPlugin.scratchManager.getPlayerScratch(player, false);
							basicScratch.openBasicScratch(player);
							scratchPlayerDataLogic.decreaseBasicScratches(1);
							scratchPlayerDataLogic.save();
						} else if (clickedMaterial.equals(Scratch.Type.PREMIUM.itemIcon)) {
							Scratch premiumScratch = mmPlugin.scratchManager.getPlayerScratch(player, false);
							premiumScratch.openPremiumScratch(player);
							scratchPlayerDataLogic.decreasePremiumScratches(1);
							scratchPlayerDataLogic.save();
						}
				}
			}
		}
	}

	public static void openForPlayer(Player player) {
		ScratchesListGui listGui = MMPlugin.getInstance().getScratchManager().getPlayerScratchesListGui(player);
		listGui.openScratchesGui();
	}
}

