package pl.saletrak.MostMajor.feautures.rewards.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.saletrak.MostMajor.MMPlugin;
import pl.saletrak.MostMajor.feautures.rewards.Reward;
import pl.saletrak.MostMajor.feautures.rewards.player_data.RewardsPlayerDataLogic;

import java.util.ArrayList;
import java.util.List;

public class RewardsOrderGui implements RewardsGui {

    private Player player;
    private Reward reward;
    private Inventory inv;

    public RewardsOrderGui(Player player, Reward reward) {
        manager.registerPlayerRewardsOrderGui(player, this);
        this.player = player;
        this.reward = reward;
    }

    public void openGui() {
        inv = Bukkit.createInventory(player, 27, reward.getTitle());
        manager.registerInventory(inv, this);

        final int[] index = {0};
        reward.getItems().forEach(itemStack -> {
            inv.setItem(index[0], itemStack);
            index[0]++;
        });

        player.openInventory(inv);
    }

    @Override
    public void onItemClick(InventoryClickEvent event) {

    }

    @Override
    public void onCloseGui(InventoryCloseEvent event) {
        List<ItemStack> toRemove = new ArrayList<>();

        reward.getItems().forEach(itemStack -> {
            if (!event.getInventory().contains(itemStack)) {
                toRemove.add(itemStack);
            }
        });

        toRemove.forEach(itemStack -> reward.getItems().remove(itemStack));

        RewardsPlayerDataLogic rewardsPlayerDataLogic = MMPlugin.getInstance().getRewardsPlayerDataProvider().getPlayerDataLogic(player);
        rewardsPlayerDataLogic.save();

        manager.unregisterInvetory(inv);
        manager.unregisterPlayerRewardsOrderGui(player, this);
    }

}
