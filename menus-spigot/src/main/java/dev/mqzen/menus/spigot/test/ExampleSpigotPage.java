package dev.mqzen.menus.spigot.test;

import dev.mqzen.menus.spigot.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class ExampleSpigotPage extends SpigotMenuPage {
	public ExampleSpigotPage(@NonNull Player user,
	                         int page,
	                         @NonNull SpigotPageCreator pageCreator,
	                         @NonNull SpigotPaginatedMenu paginatedMenu, @NonNull SpigotMenusManager manager) {
		super(user, page, paginatedMenu, pageCreator, manager);
	}

	@Override
	protected ItemStack getNextPageItemStack() {
		ItemStack item = new ItemStack(Material.ARROW);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&aNext page ->"));
		item.setItemMeta(meta);

		return item;
	}

	@Override
	protected ItemStack getPreviousPageItemStack() {
		ItemStack item = new ItemStack(Material.ARROW);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&e<- Previous page"));
		item.setItemMeta(meta);
		return item;
	}


}
