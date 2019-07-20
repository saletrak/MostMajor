package pl.saletrak.MostMajor.feautures.scratch;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.saletrak.MostMajor.MMPlugin;
import pl.saletrak.MostMajor.feautures.rubins.RubinsTransactionResponse;
import pl.saletrak.MostMajor.feautures.rubins.data.RubinsPlayerDataLogic;
import pl.saletrak.MostMajor.feautures.rubins.data.RubinsPlayersDataProvider;
import pl.saletrak.MostMajor.feautures.scratch.player_data.ScratchPlayerDataLogic;
import pl.saletrak.MostMajor.feautures.scratch.player_data.ScratchPlayersDataProvider;
import pl.saletrak.MostMajor.player.MMPlayer;

import java.util.ArrayList;
import java.util.List;

public class ScratchBuyGui implements ScratchGui {

	private MMPlayer mmPlayer;
	private List<Integer> correctIS = new ArrayList<Integer>();
	private MMPlugin mmPlugin;
	private Inventory inv;
	private ScratchPlayerDataLogic scratchPlayerDataLogic;

	public ScratchBuyGui(MMPlayer mmPlayer) {
		this.mmPlayer = mmPlayer;
		this.mmPlugin = MMPlugin.getInstance();
		this.scratchPlayerDataLogic = ScratchPlayersDataProvider.playerData(mmPlayer.getPlayer());
	}

	public void openStore() {
		correctIS.clear();
		RubinsPlayerDataLogic rubinsPlayerDataLogic = RubinsPlayersDataProvider.playerData(mmPlayer.getPlayer());
		long rubins = rubinsPlayerDataLogic.getRubins();
		inv = Bukkit.createInventory(null, 9, "Kup zdrapkę (Twoje rubiny: " + rubins + ")");
		mmPlugin.getScratchManager().addInventory(inv, this);

		ItemStack itemStack;
		ItemMeta itemMeta;
		List<String> lore = new ArrayList<String>();
		for (int i = 0; i < 9; i++) {
			switch (i) {
				case 0:
					lore.clear();
					itemStack = new ItemStack(Scratch.Type.BASIC.itemIcon);
					itemMeta = itemStack.getItemMeta();
					itemMeta.setDisplayName("Zdrapka");

					lore.add("Koszt: " + Scratch.Type.BASIC.price + " rubinów");
					itemMeta.setLore(lore);
					itemStack.setItemMeta(itemMeta);
					inv.setItem(i, itemStack);
					correctIS.add(itemStack.hashCode());
					break;
				case 1:
					lore.clear();
					itemStack = new ItemStack(Scratch.Type.PREMIUM.itemIcon);
					itemMeta = itemStack.getItemMeta();
					itemMeta.setDisplayName("Zdrapka PREMIUM");

					lore.add("Koszt: " + Scratch.Type.PREMIUM.price + " rubinów");
					itemMeta.setLore(lore);
					itemStack.setItemMeta(itemMeta);
					inv.setItem(i, itemStack);
					correctIS.add(itemStack.hashCode());
					break;
				case 8:
					itemStack = new ItemStack(Material.RED_WOOL);
					itemMeta = itemStack.getItemMeta();
					itemMeta.setDisplayName("Zamknij");
					itemStack.setItemMeta(itemMeta);
					inv.setItem(i, itemStack);
					correctIS.add(itemStack.hashCode());
					break;
			}
		}
		mmPlayer.getPlayer().openInventory(inv);
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
					case RED_WOOL:
						ScratchesListGui.openForPlayer(player);
						break;
					default:
						Scratch.Type scratchType = null;
						if (clickedMaterial.equals(Scratch.Type.PREMIUM.itemIcon)) {
							scratchType = Scratch.Type.PREMIUM;
						} else if (clickedMaterial.equals(Scratch.Type.BASIC.itemIcon)) {
							scratchType = Scratch.Type.BASIC;
						}
						if (scratchType != null) {
							RubinsPlayerDataLogic rubinsPlayerDataLogic = RubinsPlayersDataProvider.playerData(mmPlayer.getPlayer());
							long rubins = rubinsPlayerDataLogic.getRubins();
							if (rubins >= scratchType.price) {
								ScratchPuchareConfirmation spc = new ScratchPuchareConfirmation(scratchType, mmPlayer);
								Scratch.Type finalScratchType = scratchType;
								spc.openConfirmation(confirmed -> {
									player.closeInventory();
									if (confirmed) {
										RubinsTransactionResponse response = rubinsPlayerDataLogic.takeRubinsFromPlayer(finalScratchType.price);
										switch (response) {
											case SUCCESS:
												player.sendMessage("Kupiłeś zdrapkę");
												switch (finalScratchType) {
													case BASIC:
														scratchPlayerDataLogic.increaseBasicScratches(1);
														scratchPlayerDataLogic.save();
														break;
													case PREMIUM:
														scratchPlayerDataLogic.increasePremiumScratches(1);
														scratchPlayerDataLogic.save();
														break;
												}
												ScratchesListGui.openForPlayer(player);
												break;
											case INSUFFICIENT_FUNDS:
												player.sendMessage("Nie masz wystarczająco rubinów");
												break;
										}
									} else {
										player.sendMessage("Anulowano");
									}
								});
							} else {
								player.closeInventory();
								player.sendMessage("Nie masz tyle rubinów");
							}
						}
				}
			}
		}
	}

	public interface BuyCallback {
		void onConfirmation(boolean confirmed);
	}

}
