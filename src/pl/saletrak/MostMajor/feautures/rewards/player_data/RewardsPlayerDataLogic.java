package pl.saletrak.MostMajor.feautures.rewards.player_data;

import pl.saletrak.MostMajor.feautures.rewards.Reward;
import pl.saletrak.MostMajor.player.data.PlayerDataLogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RewardsPlayerDataLogic extends PlayerDataLogic<RewardsPlayerDataConfig> {

    private List<Reward> rewardsList = new ArrayList<Reward>();

    public RewardsPlayerDataLogic(RewardsPlayerDataConfig config) {
        super(config);

        List<Map<String, Object>> rewards = config.getRewards();
        rewards.forEach(reward -> rewardsList.add(new Reward(reward)));
    }

    public List<Reward> getRewardsList() {
        return rewardsList;
    }

    public void addReward(Reward reward) {
        rewardsList.add(reward);
    }

    public void save() {
        List<Map<String, Object>> rewards = new ArrayList<>();
        List<Reward> toRemove = new ArrayList<>();

        rewardsList.forEach(reward -> {
            if (!reward.isCollected()) {
                rewards.add(reward.serialize());
            } else {
                toRemove.add(reward);
            }
        });

        toRemove.forEach(reward -> rewardsList.remove(reward));

        getConfig().setRewards(rewards);
        getConfig().save();
    }
}
