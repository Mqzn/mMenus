package dev.mqzen.menus.spigot.test;

import dev.mqzen.menus.core.ClickableItem;
import dev.mqzen.menus.core.MenuSize;
import dev.mqzen.menus.core.SlotPosition;
import dev.mqzen.menus.spigot.SpigotMenuAdapter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class ExampleAdapter extends SpigotMenuAdapter {

	protected ExampleAdapter() {
		super(MenuSize.of(6, 9));
	}


	@Override
	public @NonNull String title(Player user) {
		return "&cExample " + user.getName();
	}

	@Override
	public @Nullable ClickableItem<ItemStack, InventoryClickEvent> getItemAt(SlotPosition position) {
		return null;
	}

	@Override
	public void setMenuItems() {

	}

}
