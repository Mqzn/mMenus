package dev.mqzen.menus.spigot.test;

import dev.mqzen.menus.core.MenuSize;
import dev.mqzen.menus.core.SlotPosition;
import dev.mqzen.menus.spigot.SpigotClickableItem;
import dev.mqzen.menus.spigot.SpigotMenusManager;
import dev.mqzen.menus.spigot.SpigotPaginatedMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class TestPaginatedMenu extends SpigotPaginatedMenu {

	public TestPaginatedMenu(int itemsPerPage,
	                         @NonNull Player user,
	                         SpigotMenusManager manager) {
		super(itemsPerPage, user, new ExamplePageCreator(manager), manager);
	}

	@Override
	public String title(Player user) {
		return "Welcome " + user.getName();
	}

	@Override
	public MenuSize size() {
		return MenuSize.of(6, 9);
	}

	@Override
	public void addItems() {

		SpigotClickableItem clickableItem;
		for (int i = 0; i < 100000; i++) {
			ItemStack itemStack = new ItemStack(Material.NAME_TAG);
			ItemMeta meta = itemStack.getItemMeta();
			meta.setDisplayName("#" + (i+1));
			itemStack.setItemMeta(meta);
			clickableItem = SpigotClickableItem.empty(itemStack);
			items.add(clickableItem);
		}

	}

	@Override
	public SlotPosition getPreviousPagePosition() {
		return SlotPosition.of(5, 3);
	}

	@Override
	public SlotPosition getNextPagePosition() {
		return SlotPosition.of(5, 5);
	}

}
