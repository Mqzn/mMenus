package dev.mqzen.menus.spigot;

import dev.mqzen.menus.core.ClickableItem;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import java.util.function.Consumer;

public class SpigotClickableItem extends ClickableItem<ItemStack, InventoryClickEvent> {
	protected SpigotClickableItem(@NonNull ItemStack item,
	                              @NonNull Consumer<InventoryClickEvent> instructions) {
		super(item, instructions);
	}

	public static SpigotClickableItem of(@NonNull ItemStack item,
	                                     @NonNull Consumer<InventoryClickEvent> instructions) {
		return new SpigotClickableItem(item, instructions);
	}

	public static SpigotClickableItem empty(@NonNull ItemStack item) {
		return new SpigotClickableItem(item, (e)-> {});
	}

}
