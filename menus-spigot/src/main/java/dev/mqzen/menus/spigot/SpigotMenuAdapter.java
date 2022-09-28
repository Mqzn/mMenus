package dev.mqzen.menus.spigot;

import dev.mqzen.menus.core.ClickableItem;
import dev.mqzen.menus.core.MenuAdapter;
import dev.mqzen.menus.core.MenuSize;
import dev.mqzen.menus.core.SlotPosition;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import java.util.HashMap;
import java.util.Map;

public abstract class SpigotMenuAdapter implements MenuAdapter<Player, ItemStack, InventoryClickEvent> {

	private final @NonNull MenuSize size;
	private final @NonNull Map<SlotPosition, ClickableItem<ItemStack, InventoryClickEvent>> items;

	protected SpigotMenuAdapter(@NonNull MenuSize size) {
		this.size = size;
		this.items = new HashMap<>();
		setMenuItems();
	}

	protected SpigotMenuAdapter(int rows) {
		this(MenuSize.of(rows));
	}

	protected SpigotMenuAdapter(int rows, int columns) {
		this(MenuSize.of(rows, columns));
	}

	@Override
	public @NonNull MenuSize size() {
		return size;
	}

	@Override
	public void setItem(SlotPosition position,
	                    ClickableItem<ItemStack, InventoryClickEvent> clickableItem) {
		items.put(position, clickableItem);
	}

	@Override
	public @Nullable ClickableItem<ItemStack, InventoryClickEvent> getItemAt(SlotPosition position) {
		return items.get(position);
	}

	public abstract void setMenuItems();

	@Override
	public @NonNull Map<SlotPosition, ClickableItem<ItemStack, InventoryClickEvent>> getItems() {
		return items;
	}

	@Override
	public void onClose(Player user) {

	}

}
