package pl.saletrak.MostMajor.integrations;

import me.lucko.luckperms.LuckPerms;
import me.lucko.luckperms.api.LuckPermsApi;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import javax.annotation.Nullable;

public class LuckPermsIntergation {

	private LuckPermsApi api;

	public LuckPermsIntergation() {
		RegisteredServiceProvider<LuckPermsApi> provider = Bukkit.getServicesManager().getRegistration(LuckPermsApi.class);
		if (provider != null) {
			api = provider.getProvider();
		}
	}

	@Nullable
	public static LuckPermsApi getApi() {
		return LuckPerms.getApi();
		//return MMPlugin.getInstance().getLuckPermsIntergation().api;
	}

}
