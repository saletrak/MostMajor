package pl.saletrak.MostMajor.utils;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Glow {

	public static void addGlow(ItemStack stack) {
		ItemMeta meta = stack.getItemMeta();
		meta.addEnchant(Enchantment.LURE, 1, false);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		stack.setItemMeta(meta);
	}
}
