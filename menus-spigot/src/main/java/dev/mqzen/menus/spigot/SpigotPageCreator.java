package dev.mqzen.menus.spigot;

import dev.mqzen.menus.core.pagination.PageCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public abstract class SpigotPageCreator extends PageCreator<Plugin, Player,
				ItemStack, InventoryClickEvent, SpigotMenuPage> {


	public SpigotPageCreator(SpigotMenusManager manager) {
		super(manager);
	}
}
