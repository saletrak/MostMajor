package pl.saletrak.MostMajor.feautures.rewards;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.saletrak.MostMajor.feautures.rewards.gui.RewardsOrderGui;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reward {

    private String title;
    private List<ItemStack> items = new ArrayList<>();
    private List<SpecialItem> specialItems = new ArrayList<>();
    private String type;

    public Reward(Map<String, Object> pureData) {
        try {
            title = (String) pureData.get("title");
            type = (String) pureData.get("type");


            ((ArrayList<String>) pureData.get("items")).forEach(itemText -> {
                ItemStack itemStack = deserializeItem(itemText);
                if (itemStack != null) {
                    items.add(itemStack);
                }
            });

            ((ArrayList<String>) pureData.get("special")).forEach(itemText -> {
                String[] itemDetails = itemText.split(":");
            });

        } catch (ClassCastException e) {
            System.out.print("CheckpointReward Cast error");
        }
    }

    public Reward(String title, List<ItemStack> items, List<SpecialItem> specialItems, String type) {
        this.title = title;
        this.items = items;
        this.specialItems = specialItems;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public List<ItemStack> getItems() {
        return items;
    }

    public List<SpecialItem> getSpecialItems() {
        return specialItems;
    }

    public boolean isCollected() {
        return this.items.size() == 0 && this.specialItems.size() == 0;
    }

    public void openOrderGui(Player player) {
        RewardsOrderGui gui = new RewardsOrderGui(player, this);
        gui.openGui();
    }

    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("title", this.title);
        map.put("type", this.type);

        List<String> itemsList = new ArrayList<>();
        this.items.forEach(itemStack -> {
            itemsList.add(itemStack.getType().name()+":"+itemStack.getAmount());
            System.out.print(itemStack.serialize());
        });
        map.put("items", (itemsList));

        return map;
    }

    @Nullable
    public static ItemStack deserializeItem(String itemText) {
        String[] itemDetails = itemText.split(":");
        ItemStack itemStack = null;

        try {
            String material = itemDetails[0];
            int amount = Integer.valueOf(itemDetails[1]);


            itemStack = new ItemStack(Material.valueOf(material), amount);
        } catch (NumberFormatException e) {
            System.out.print("Can't read reward item: "+itemText);
        }

        return itemStack;
    }
}
