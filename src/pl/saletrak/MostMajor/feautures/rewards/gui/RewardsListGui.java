package pl.saletrak.MostMajor.feautures.rewards.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.saletrak.MostMajor.MMPlugin;
import pl.saletrak.MostMajor.feautures.rewards.Reward;
import pl.saletrak.MostMajor.feautures.rewards.player_data.RewardsPlayerDataLogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RewardsListGui implements RewardsGui {

    private Player player;
    private RewardsPlayerDataLogic playerDataLogic;
    private Map<Integer, Reward> rewardsMap;
    private Inventory inv;

    public RewardsListGui(Player player) {
        this.player = player;
        this.playerDataLogic = MMPlugin.getInstance().getRewardsPlayerDataProvider().getPlayerDataLogic(player);
    }

    public void openGui() {
        inv = Bukkit.createInventory(player, 27, "Twoje nagrody");
        MMPlugin.getInstance().getGuiRewardsManager().registerInventory(inv, this);

        final int[] index = {0};
        rewardsMap = new HashMap<>();

        playerDataLogic.getRewardsList().forEach(reward -> {
            ItemStack itemStack;
            List<String> lore = new ArrayList<>();
            switch (reward.getType()) {
                case "lvl":
                    itemStack = new ItemStack(Material.ENDER_PEARL);
                    lore.add("Nagroda za zdobyty poziom");
                    lore.add("WIECEJ -> /poziom");
                    break;
                case "scratch":
                    itemStack = new ItemStack(Material.FILLED_MAP);
                    lore.add("Nagroda za wygraną zdrapke");
                    lore.add("WIECEJ -> /zdrapka");
                    break;
                default:
                    itemStack = new ItemStack(Material.CHEST);
                    break;
            }
            ItemMeta itemMeta = itemStack.getItemMeta();

            if (itemMeta != null) {
                itemMeta.setLore(lore);
                itemMeta.setDisplayName(reward.getTitle());
            }
            else System.out.print("item meta is null");
            itemStack.setItemMeta(itemMeta);

            inv.setItem(index[0], itemStack);
            rewardsMap.put(index[0], reward);

            index[0]++;
        });

        player.openInventory(inv);
    }

    @Override
    public void onItemClick(InventoryClickEvent event) {
        System.out.print("clicked " + event.getSlot());

        event.setCancelled(true);
        ItemStack is = event.getCurrentItem();

        if (is != null) {
            Reward reward = rewardsMap.get(event.getSlot());
            player.closeInventory();

            reward.openOrderGui(player);
        }
    }

    @Override
    public void onCloseGui(InventoryCloseEvent event) {
        manager.unregisterInvetory(inv);
        manager.unregisterPlayerRewardListGui(player, this);
    }
}
