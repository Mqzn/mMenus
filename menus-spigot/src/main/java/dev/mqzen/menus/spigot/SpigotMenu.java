package dev.mqzen.menus.spigot;

import dev.mqzen.menus.core.ClickableItem;
import dev.mqzen.menus.core.Menu;
import dev.mqzen.menus.core.MenuAdapter;
import dev.mqzen.menus.core.SlotPosition;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import java.util.Map;

public final class SpigotMenu implements Menu<Player, ItemStack, InventoryClickEvent> {

	private final @NonNull MenuAdapter<Player, ItemStack, InventoryClickEvent> adapter;
	private final @NonNull Map<SlotPosition, ClickableItem<ItemStack, InventoryClickEvent>> items;
	private final @NonNull Inventory inventory;


	public SpigotMenu(@NonNull Player player, @NonNull MenuAdapter<Player, ItemStack, InventoryClickEvent> adapter) {
		this.adapter = adapter;
		this.items = adapter.getItems();

		inventory = Bukkit.createInventory(null, adapter.size().scalar(), ChatColor.translateAlternateColorCodes('&', adapter.title(player)));
		items.forEach((pos, item)-> inventory.setItem(pos.getScalarPosition(size()),item.getItemObj()));
	}

	@Override
	public @NonNull MenuAdapter<Player, ItemStack, InventoryClickEvent> getAdapter() {
		return adapter;
	}

	@Override
	public void open(@NonNull Player user) {
		user.openInventory(inventory);
	}


	@Override
	public @Nullable ClickableItem<ItemStack, InventoryClickEvent> getItemAt(int row, int column) {
		return items.get(SlotPosition.of(row, column));
	}

	@Override
	public void setItem(int row, int column, @NonNull ClickableItem<ItemStack, InventoryClickEvent> clickableItem) {
		SlotPosition position = SlotPosition.of(row, column);
		int slot = position.getScalarPosition(this.size());
		inventory.setItem(slot, clickableItem.getItemObj());
	}

	@Override
	public void forceClose(Player user) {
		user.closeInventory();
	}

}
