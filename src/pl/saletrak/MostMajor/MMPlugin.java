package pl.saletrak.MostMajor;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import pl.saletrak.MostMajor.data_store.PluginConfigLogic;
import pl.saletrak.MostMajor.feautures.rewards.commands.RewardsCommand;
import pl.saletrak.MostMajor.feautures.rewards.gui.GuiRewardsManager;
import pl.saletrak.MostMajor.feautures.rewards.listeners.RewardGuiClick;
import pl.saletrak.MostMajor.feautures.rewards.listeners.RewardGuiClose;
import pl.saletrak.MostMajor.feautures.rewards.player_data.RewardsPlayerDataProvider;
import pl.saletrak.MostMajor.feautures.rubins.commands.RubinsCommands;
import pl.saletrak.MostMajor.feautures.rubins.data.RubinsPlayersDataProvider;
import pl.saletrak.MostMajor.feautures.scratch.commands.ScratchAdminCommands;
import pl.saletrak.MostMajor.feautures.scratch.commands.ScratchCommands;
import pl.saletrak.MostMajor.feautures.currencies.commands.*;
import pl.saletrak.MostMajor.context.ContextManager;
import pl.saletrak.MostMajor.feautures.currencies.CurrencyEngine;
import pl.saletrak.MostMajor.feautures.scratch.config.ScratchConfigLogic;
import pl.saletrak.MostMajor.player.MMPlayer;
import pl.saletrak.MostMajor.feautures.scratch.ScratchManager;
import pl.saletrak.MostMajor.feautures.scratch.player_data.ScratchPlayersDataProvider;
import pl.saletrak.MostMajor.feautures.scratch.listeners.ClickInventory;
import pl.saletrak.MostMajor.player.listeners.PlayerQuit;

public class MMPlugin extends JavaPlugin {

	public CurrencyEngine gameCoins = new CurrencyEngine("GameCoins", "money.gamecoins");

	private ContextManager contextManager = new ContextManager();
	public ScratchManager scratchManager = new ScratchManager();
	private GuiRewardsManager guiRewardsManager = new GuiRewardsManager();

	private PluginConfigLogic pluginConfigLogic;
	private ScratchConfigLogic scratchConfigLogic;

	private ScratchPlayersDataProvider scratchPlayersDataProvider;
	private RubinsPlayersDataProvider rubinsPlayersDataProvider;
	private RewardsPlayerDataProvider rewardsPlayerDataProvider;

	public static void main(String[] args) {
	}

	@Override
	public void onEnable() {
		pluginConfigLogic = new PluginConfigLogic();
		scratchPlayersDataProvider = new ScratchPlayersDataProvider();
		scratchConfigLogic = new ScratchConfigLogic();
		rubinsPlayersDataProvider = new RubinsPlayersDataProvider();
		rewardsPlayerDataProvider = new RewardsPlayerDataProvider();


		getCommand("rubins").setExecutor(new RubinsCommands(this));
		getCommand("gamecoins").setExecutor(new GameCoinsCommands(this));

		getCommand("zdrapka").setExecutor(new ScratchCommands(this));
		getCommand("scratch").setExecutor(new ScratchAdminCommands());

		getCommand("nagrody").setExecutor(new RewardsCommand());

		getServer().getPluginManager().registerEvents(new ClickInventory(this), this);

		getServer().getPluginManager().registerEvents(new PlayerQuit(), this);

		getServer().getPluginManager().registerEvents(new RewardGuiClick(), this);
		getServer().getPluginManager().registerEvents(new RewardGuiClose(), this);

		Object[] playersObj = Bukkit.getOnlinePlayers().toArray();
		for (Object playerObj : playersObj) {
			Player player = (Player) playerObj;
			MMPlayer mmPlayer = new MMPlayer(player);
			mmPlayer.runPlayedTimeService(this);
		}
	}

	@Override
	public void onDisable() {
	}

	public PluginConfigLogic getPluginConfigLogic() {
		return pluginConfigLogic;
	}


	public ContextManager getContextManager() {
		return contextManager;
	}

	public ScratchManager getScratchManager() {
		return scratchManager;
	}

	public GuiRewardsManager getGuiRewardsManager() {
		return guiRewardsManager;
	}


	public ScratchConfigLogic getScratchConfigLogic() {
		return scratchConfigLogic;
	}

	public ScratchPlayersDataProvider getScratchPlayersDataProvider() {
		return scratchPlayersDataProvider;
	}

	public RubinsPlayersDataProvider getRubinsPlayersDataProvider() {
		return rubinsPlayersDataProvider;
	}

	public RewardsPlayerDataProvider getRewardsPlayerDataProvider() {
		return rewardsPlayerDataProvider;
	}

	public static MMPlugin getInstance() {
		return (MMPlugin) Bukkit.getPluginManager().getPlugin("MostMajor");
	}
}
