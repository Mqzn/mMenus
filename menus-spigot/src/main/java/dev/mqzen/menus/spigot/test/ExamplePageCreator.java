package dev.mqzen.menus.spigot.test;

import dev.mqzen.menus.core.pagination.MenuPageAdapter;
import dev.mqzen.menus.core.pagination.PaginatedMenu;
import dev.mqzen.menus.spigot.SpigotMenuPage;
import dev.mqzen.menus.spigot.SpigotMenusManager;
import dev.mqzen.menus.spigot.SpigotPageCreator;
import dev.mqzen.menus.spigot.SpigotPaginatedMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class ExamplePageCreator extends SpigotPageCreator {


	public ExamplePageCreator(@NonNull SpigotMenusManager manager) {
		super(manager);
	}

	@Override
	public void setAdditionalItems(@NonNull Player user, @NonNull MenuPageAdapter<Plugin, Player, ItemStack, InventoryClickEvent> pageAdapter) {
		//pageAdapter.setItem(0,0, SpigotClickableItem.empty(new ItemStack(Material.STAINED_GLASS_PANE)));
	}

	@Override
	public ItemStack getNextPageItemStack() {
		ItemStack item = new ItemStack(Material.ARROW);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&aNext page ->"));
		item.setItemMeta(meta);

		return item;
	}

	@Override
	public ItemStack getPreviousPageItemStack() {
		ItemStack item = new ItemStack(Material.ARROW);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&e<- Previous page"));
		item.setItemMeta(meta);
		return item;
	}

	@Override
	public SpigotMenuPage createNewPage(@NonNull Player user, int pageIndex, @NonNull PaginatedMenu<Plugin, Player, ItemStack, InventoryClickEvent> paginatedMenu) {
		return new SpigotMenuPage(user, pageIndex,
						(SpigotPaginatedMenu) paginatedMenu,
						this, (SpigotMenusManager) manager);
	}


}
