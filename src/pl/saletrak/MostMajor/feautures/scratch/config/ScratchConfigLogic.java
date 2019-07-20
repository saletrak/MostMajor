package pl.saletrak.MostMajor.feautures.scratch.config;

import org.bukkit.inventory.ItemStack;
import pl.saletrak.MostMajor.feautures.rewards.Reward;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ScratchConfigLogic {

    private double chanceBasic;
    private double chancePremium;
    private List<ItemStack> itemsBasic = new ArrayList<>();
    private List<ItemStack> itemsPremium = new ArrayList<>();
    private int itemsNumberBasic;
    private int itemsNumberPremium;

    public ScratchConfigLogic() {
        ScratchConfig.setDefaultAndSave();

        chanceBasic = ScratchConfig.getChanceBasic();
        chancePremium = ScratchConfig.getChancePremium();
        ScratchConfig.getItemsBasic().forEach(itemText -> {
            ItemStack itemStack = Reward.deserializeItem(itemText);
            if (itemStack != null) {
                itemsBasic.add(itemStack);
            }
        });
        ScratchConfig.getItemsPremium().forEach(itemText -> {
            ItemStack itemStack = Reward.deserializeItem(itemText);
            if (itemStack != null) {
                itemsPremium.add(itemStack);
            }
        });
        itemsNumberBasic = ScratchConfig.getItemsNumberBasic();
        itemsNumberPremium = ScratchConfig.getItemsNumberPremium();
    }

    public double getChanceBasic() {
        return chanceBasic;
    }

    public double getChancePremium() {
        return chancePremium;
    }

    public List<ItemStack> getRewardItemsBasic() {
        return getItemStacks(itemsNumberBasic, itemsBasic);
    }

    public List<ItemStack> getRewardItemsPremium() {
        return getItemStacks(itemsNumberPremium, itemsPremium);
    }

    private List<ItemStack> getItemStacks(int number, List<ItemStack> itemsScope) {
        List<ItemStack> items = new ArrayList<>();
        Random random = new Random();
        if (!itemsScope.isEmpty() && number > 0) {
            for (int i = 0; i < number; i++) {
                int index = random.nextInt(itemsScope.size());
                items.add(itemsScope.get(index));
            }
        }
        return items;
    }
}
