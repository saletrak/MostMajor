package pl.saletrak.MostMajor.feautures.scratch.config;

import pl.saletrak.MostMajor.data_store.Config;
import pl.saletrak.MostMajor.data_store.ConfigSource;

import java.util.ArrayList;
import java.util.List;

public class ScratchConfig {

    private static Config config = ConfigSource.SCRATCH.getConfig();

    public static double getChanceBasic() {
        return config.getDouble("chanceBasic");
    }

    public static double getChancePremium() {
        return config.getDouble("chancePremium");
    }

    public static List<String> getItemsBasic() {
        return config.getStringList("rewards.basic.items");
    }

    public static List<String> getItemsPremium() {
        return config.getStringList("rewards.premium.items");
    }

    public static int getItemsNumberBasic() {
        return config.getInt("rewards.basic.itemsNumber");
    }

    public static int getItemsNumberPremium() {
        return config.getInt("rewards.premium.itemsNumber");
    }

    public static void setDefaultAndSave() {
        config.setDefault("chanceBasic", 0.1);
        config.setDefault("chancePremium", 0.2);

        List<String> itemsBasic = new ArrayList<>();
        itemsBasic.add("DIRT:8");
        itemsBasic.add("STONE:13");
        config.setDefault("rewards.basic.items", itemsBasic);

        List<String> itemsPremium = new ArrayList<>();
        itemsBasic.add("DIAMOND:2");
        itemsBasic.add("STONE:14");
        config.setDefault("rewards.premium.items", itemsPremium);

        config.setDefault("rewards.basic.itemsNumber", 3);
        config.setDefault("rewards.premium.itemsNumber", 3);

        config.save();
    }

}
