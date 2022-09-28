package dev.mqzen.menus.spigot;

import dev.mqzen.menus.core.*;
import dev.mqzen.menus.core.pagination.MenuPage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class SpigotMenuPage extends MenuPage<Plugin, Player, ItemStack, InventoryClickEvent> {

	private @MonotonicNonNull Inventory pageInventory;
	public SpigotMenuPage(@NonNull Player user,
	                      int page,
	                      @NonNull SpigotPaginatedMenu paginatedMenu,
	                      @NonNull SpigotPageCreator pageCreator,
	                      @NonNull SpigotMenusManager manager) {

		super(user, page, paginatedMenu, pageCreator, manager);
	}

	@Override
	protected ClickableItem<ItemStack, InventoryClickEvent> getPreviousPageButton() {

		return SpigotClickableItem.of(pageCreator.getPreviousPageItemStack(), (e)-> {
			Player user = (Player) e.getWhoClicked();
			paginatedMenu.open(user, this.page-2);
		});

	}

	@Override
	protected ClickableItem<ItemStack, InventoryClickEvent> getNextPageButton() {
		return SpigotClickableItem.of(pageCreator.getNextPageItemStack(), (e)-> {
			Player user = (Player) e.getWhoClicked();
			paginatedMenu.open(user, this.page+2);
		});

	}

	/**
	 * This is where  the inventory object is
	 * initialized by using the data carried by our
	 * MenuAdapter {@link MenuAdapter}
	 */
	@Override
	protected void initialize(@NonNull Player user) {
		this.pageInventory = Bukkit.createInventory(null, size().scalar(), this.getAdapter().title(user));
		MenuSize size = size();
		adapter.getItems().forEach((position, clickableItem)
						-> pageInventory.setItem(position.getScalarPosition(size), clickableItem.getItemObj()));
	}

	@Override
	protected void setPageItems() {
		//setting page-special-items;
		SlotPosition nextPageButtonPos = paginatedMenu.getNextPagePosition();
		this.setItem(nextPageButtonPos, this.getNextPageButton());

		SlotPosition prevPageButtonPos = paginatedMenu.getPreviousPagePosition();
		this.setItem(prevPageButtonPos, this.getPreviousPageButton());
	}

	@Override
	public final void open(Player user) {
		if (pageInventory != null)
			user.openInventory(pageInventory);

	}


}
