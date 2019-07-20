package pl.saletrak.MostMajor.utils;

import org.bukkit.block.Block;


public class SignManager {
	public static String hashFromLocation(Block block) {
		String toHash = "" + block.getWorld() + block.getX() + block.getY() + block.getZ();
		return CheckSum.sha256(toHash);
	}
}
