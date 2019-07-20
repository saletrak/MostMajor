package pl.saletrak.MostMajor.player.data;

public class PlayerDataPaths {

	public static String shops() {
		return "shops";
	}

	public static String checkpoints() {
		return "checkpoints";
	}

	public static String uuid() {
		return "uuid";
	}

	public static String levels() { return "levels"; }

	public static String exp() {
		return levels() + ".exp";
	}

	public static String maxExp() {
		return levels() + ".maxExp";
	}

	public static String lastExpRubin() {
		return levels() + ".lastExpRubin";
	}

	public static String timePlayed() {
		return "playedTime";
	}

}
