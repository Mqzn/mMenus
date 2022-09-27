package dev.mqzen.menus.spigot;

import dev.mqzen.menus.core.pagination.PaginatedMenu;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.nullness.qual.NonNull;

public abstract class SpigotPaginatedMenu extends PaginatedMenu<Plugin, Player, ItemStack, InventoryClickEvent> {

	public SpigotPaginatedMenu(int itemsPerPage,
	                           @NonNull Player user,
														 @NonNull SpigotPageCreator pageCreator,
	                           @NonNull SpigotMenusManager manager) {
		super(itemsPerPage, user, pageCreator, manager);
	}

	@Override
	public void open(Player user, int pointingPage) {

		val page = getPage(pointingPage-1);
		if(page == null) {
			//debugging before stopping
			manager.debug("Unknown Page: " + pointingPage);
			return;
		}

		user.closeInventory();

		Bukkit.getScheduler().runTaskLater(manager.getPlatform(),
						()-> manager.openMenu(user, page), 2L);

	}

}
