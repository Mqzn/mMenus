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

public abstract class SpigotMenuPage extends MenuPage<Plugin, Player, ItemStack, InventoryClickEvent> {

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


		return SpigotClickableItem.of(this.getPreviousPageItemStack(), (e)-> {
			if(previousPage != null) {
				Player user = (Player) e.getWhoClicked();
				manager.openMenu(user, previousPage);
			}
		});
	}

	@Override
	protected ClickableItem<ItemStack, InventoryClickEvent> getNextPageButton() {
		return SpigotClickableItem.of(this.getNextPageItemStack(), (e)-> {
			if(nextPage != null) {
				Player user = (Player) e.getWhoClicked();
				manager.openMenu(user, nextPage);
			}
		});

	}

	protected abstract ItemStack getNextPageItemStack();

	protected abstract ItemStack getPreviousPageItemStack();

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

		int page = getPage();
		if(page-1 >= 0) {
			previousPage = pageCreator.createNewPage(user,page-1, paginatedMenu);
		}

		if(page+1 < paginatedMenu.maximumPages()) {
			nextPage = pageCreator.createNewPage(user, page+1, paginatedMenu);
		}

		if(pageInventory != null) {
			user.openInventory(pageInventory);
		}

	}


}
