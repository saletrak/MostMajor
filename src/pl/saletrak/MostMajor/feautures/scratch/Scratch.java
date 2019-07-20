package pl.saletrak.MostMajor.feautures.scratch;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.saletrak.MostMajor.MMPlugin;
import pl.saletrak.MostMajor.feautures.rewards.Reward;
import pl.saletrak.MostMajor.feautures.rewards.player_data.RewardsPlayerDataLogic;
import pl.saletrak.MostMajor.feautures.scratch.config.ScratchConfigLogic;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Scratch implements Listener, ScratchGui {

	public enum Type {
		BASIC(5, Material.PAPER),
		PREMIUM(10, Material.MAP);

		int price;
		public Material itemIcon;

		Type(int price, Material itemIcon) {
			this.price = price;
			this.itemIcon = itemIcon;
		}
	}

	private Inventory inv;
	private Map<Material, Integer> counter = new HashMap<Material, Integer>();
	private int clickedItems = 0;
	private int max_attempts = 10;
	private Player player;
	private Map<Integer, ItemStack> lastRow = new HashMap<Integer, ItemStack>();
	private List<Integer> correctIS = new ArrayList<Integer>();
	public boolean fired = false;
	private boolean win = false;
	private Type type;
	private boolean[] predictedDrafts = new boolean[10];
	private Material winningMaterial = null;
	private int[] choosenDrafts = new int[3];
	private RewardsPlayerDataLogic rewardsPlayerDataLogic;
	private ScratchConfigLogic scratchConfigLogic;
	@Nullable private Reward reward;

	public Scratch(Player player) {
		this.player = player;
	}

	private List<Material> getScope(List<Material> negativeMaterials) {
		List<Material> list = new ArrayList<Material>(
				Arrays.asList(
						Material.DIAMOND,
						Material.COAL,
						Material.APPLE,
						Material.EMERALD,
						Material.BOWL,
						Material.WHEAT,
						Material.CARROT,
						Material.EGG,
						Material.FLINT,
						Material.GOLD_INGOT,
						Material.LEATHER,
						Material.BREAD,
						Material.REDSTONE,
						Material.GLOWSTONE_DUST,
						Material.ENDER_PEARL
				)
		);
		if (negativeMaterials == null) {
			return list;
		} else {
			for (Material material : negativeMaterials) {
				list.remove(material);
			}
			return list;
		}
	}

	private List<Material> getScope() {
		return getScope(new ArrayList<>());
	}

	public void openBasicScratch(Player p) {
		openScratch(p, Type.BASIC);
	}

	public void openPremiumScratch(Player p) {
		openScratch(p, Type.PREMIUM);
	}

	public void openScratch(Player p, Type type) {
		String title = "Zdrapka";
		if (type.equals(Type.PREMIUM)) title += " PREMIUM";
		this.type = type;
		inv = Bukkit.createInventory(null, 6 * 9, title);
		MMPlugin.getInstance().getScratchManager().addInventory(inv, this);
		initializeItems();
		predictDrafts();
		for (boolean d : predictedDrafts) System.out.print(d);
		p.openInventory(inv);
	}

	public void predictDrafts() {
		Random rnd = new Random();
		double randomDouble = rnd.nextDouble();
		boolean win = false;

		switch (type) {
			case BASIC:
				if (randomDouble <= MMPlugin.getInstance().getScratchConfigLogic().getChanceBasic()) win = true;
				break;
			case PREMIUM:
				if (randomDouble <= MMPlugin.getInstance().getScratchConfigLogic().getChancePremium()) win = true;
		}

		if (win) {
			setChoosenDrafts();
			List<Material> materials = getScope();
			winningMaterial = materials.get(rnd.nextInt(materials.size()));
			for (int i = 0; i < predictedDrafts.length; i++) {
				predictedDrafts[i] = isChoosenDraft(i);
			}
		} else {
			for (int i = 0; i < predictedDrafts.length; i++) {
				predictedDrafts[i] = false;
			}
		}
	}

	private void setChoosenDrafts() {
		Random rnd = new Random();
		choosenDrafts[0] = rnd.nextInt(4); // scope 0-3
		choosenDrafts[1] = rnd.nextInt(3) + 4; // scope 4-6
		choosenDrafts[2] = rnd.nextInt(3) + 7; // scope 7-9
	}

	private boolean isChoosenDraft(int draft) {
		for (int choosenDraft : choosenDrafts) if (draft == choosenDraft) return true;
		return false;
	}

	private void initializeItems() {
		int index = 0;
		for (ItemStack i : inv) {
			if (i == null) {
				i = new ItemStack(Material.WHITE_WOOL, 1);
				ItemMeta meta = i.getItemMeta();
				meta.setDisplayName("Zdrap");
				List<String> lore = new ArrayList<>();
				lore.add("Znajdź 3 takie same przedmioty");
				meta.setLore(lore);
				i.setItemMeta(meta);
				inv.setItem(index, i);
				correctIS.add(i.hashCode());
			}
			index++;
		}
	}

	private Material makeDraft(int draftNumber) {
		if (predictedDrafts[draftNumber]) return winningMaterial;
		else {
			List<Material> negativeMaterials = new ArrayList<Material>();
			for (Map.Entry<Material, Integer> draftedItem : counter.entrySet()) {
				if (draftedItem.getValue() > 1) negativeMaterials.add(draftedItem.getKey());
			}
			if (winningMaterial != null) negativeMaterials.add(winningMaterial);
			List<Material> materials = getScope(negativeMaterials);

			Random random = new Random();
			return materials.get(random.nextInt(materials.size()));
		}
	}

	private boolean checkWhetherWin() {
		AtomicBoolean win = new AtomicBoolean(false);
		this.counter.forEach((material, count) -> {
			if (count == 3) {
				win.set(true);
				giveReward();
				int index = 0;
				if (this.inv != null) {
					for (ItemStack is : this.inv) {
						if (is != null) {
							if (!is.getType().equals(material)) {
								is.setType(Material.AIR);
								this.inv.setItem(index, is);
							}
						}
						index++;
					}
				}

			}
		});
		this.win = win.get();
		return this.win;
	}

	public void endScreenScratch() {
		this.fired = true;

		for (int slotId = 45; slotId < 54; slotId++) {
			ItemStack itemStack = this.inv.getItem(slotId);
			this.lastRow.put(slotId, itemStack);

			if (slotId == 53 && !win) {
				ItemStack newItemStack = new ItemStack(Material.YELLOW_WOOL, 1);
				ItemMeta meta = newItemStack.getItemMeta();
				meta.setDisplayName("Kup nową zdrapkę");
				newItemStack.setItemMeta(meta);
				this.inv.setItem(slotId, newItemStack);
				correctIS.add(newItemStack.hashCode());
			} else if (slotId == 52 && !win) {
				ItemStack newItemStack = new ItemStack(Material.LIME_WOOL, 1);
				ItemMeta meta = newItemStack.getItemMeta();
				meta.setDisplayName("Dokup szansę");
				newItemStack.setItemMeta(meta);
				this.inv.setItem(slotId, newItemStack);
				correctIS.add(newItemStack.hashCode());
			} else if (slotId == 51 && !win) {
				ItemStack newItemStack = new ItemStack(Material.RED_WOOL, 1);
				ItemMeta meta = newItemStack.getItemMeta();
				meta.setDisplayName("Zamknij");
				newItemStack.setItemMeta(meta);
				this.inv.setItem(slotId, newItemStack);
				correctIS.add(newItemStack.hashCode());
			} else if (slotId == 49 && this.win) {
				ItemStack newItemStack = new ItemStack(Material.DIAMOND, 1);
				ItemMeta meta = newItemStack.getItemMeta();
				meta.setDisplayName("Odbierz nagrodę");
				newItemStack.setItemMeta(meta);
				this.inv.setItem(slotId, newItemStack);
				correctIS.add(newItemStack.hashCode());
			} else {
				ItemStack newItemStack = new ItemStack(Material.AIR);
				this.inv.setItem(slotId, newItemStack);
			}
		}
	}

	public void onItemClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();

		event.setCancelled(true);
		ItemStack is = event.getCurrentItem();

		if (is != null) {
			if (isValidIS(is)) {
				Material clickedMaterial = is.getType();
				switch (clickedMaterial) {
					case WHITE_WOOL:
						if (this.clickedItems < this.max_attempts) {
							is.setType(Material.AIR);
							Material randomMaterial = this.makeDraft(this.clickedItems);
							ItemStack newItem = new ItemStack(randomMaterial);
							inv.setItem(event.getSlot(), newItem);

							if (this.counter.containsKey(randomMaterial)) {
								this.counter.put(randomMaterial, this.counter.get(randomMaterial) + 1);
							} else {
								this.counter.put(randomMaterial, 1);
							}

							this.clickedItems++;
						}
						if (this.checkWhetherWin() || this.clickedItems == this.max_attempts) {

							this.endScreenScratch();
						}
						break;
					case RED_WOOL:
						ScratchesListGui.openForPlayer(player);
						break;
					case LIME_WOOL:
						player.sendMessage("Dokupiłeś szansę");
						break;
					case YELLOW_WOOL:
						openStore(player);
						break;
					case DIAMOND:
						collectReward();
				}
			}
		}
	}

	public RewardsPlayerDataLogic getRewardsPlayerDataLogic() {
		if (rewardsPlayerDataLogic == null) rewardsPlayerDataLogic = MMPlugin.getInstance().getRewardsPlayerDataProvider().getPlayerDataLogic(player);
		return rewardsPlayerDataLogic;
	}

	public ScratchConfigLogic getScratchConfigLogic() {
		if (scratchConfigLogic == null) scratchConfigLogic = MMPlugin.getInstance().getScratchConfigLogic();
		return scratchConfigLogic;
	}

	public void giveReward() {
		List<ItemStack> items;
		switch (type) {
			case BASIC:
				items = getScratchConfigLogic().getRewardItemsBasic();
				break;
			case PREMIUM:
				items = getScratchConfigLogic().getRewardItemsPremium();
				break;
			default:
				items = new ArrayList<>();
		}

		reward = new Reward("Zdrapka", items, new ArrayList<>(), "scratch");
		getRewardsPlayerDataLogic().addReward(reward);
		getRewardsPlayerDataLogic().save();
	}

	public void collectReward() {
		if (reward != null) {
			reward.openOrderGui(player);
		} else {
			player.sendMessage("[ERROR] CheckpointReward is null");
		}
	}

	public boolean isValidIS(ItemStack itemStack) {
		return correctIS.contains(itemStack.hashCode());
	}

	public static void openStore(Player player) {
		ScratchBuyGui scratchBuyGui = MMPlugin.getInstance().getScratchManager().getPlayerScratchBuyGui(player);
		scratchBuyGui.openStore();
	}

}
