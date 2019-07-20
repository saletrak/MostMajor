package pl.saletrak.MostMajor.feautures.scratch.player_data;

import pl.saletrak.MostMajor.player.data.PlayerDataPaths;

public class ScratchPlayerDataPaths extends PlayerDataPaths {

	public static String fileName() {
		return "scratch";
	}

	public static String scratchLastFreeDraft() {
		return "lastDraft";
	}

	public static String avaibleScratches() {
		return "avaible";
	}

	public static String avaibleBasicScratches() {
		return avaibleScratches() + ".basic";
	}

	public static String avaiblePremiumScratches() {
		return avaibleScratches() + ".premium";
	}

}
