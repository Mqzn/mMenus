package dev.mqzen.menus.core.pagination;

import dev.mqzen.menus.core.*;
import lombok.Getter;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

@Getter
public abstract class MenuPage<P, U, I, E> implements Menu<U, I, E> {

	protected final @NonNull MenusManager<P, U, I, E, ?> manager;
	protected final @NonNull PageCreator<P, U, I, E, ?> pageCreator;
	protected final @NonNull PaginatedMenu<P, U, I, E> paginatedMenu;
	protected final @NonNull MenuPageAdapter<P, U, I, E> adapter;
	private final @NonNull ClickableItem<I, E> next, prev;

	protected final int page;

	//protected @MonotonicNonNull MenuPage<P, U, I, E> previousPage = null, nextPage = null;

	public MenuPage(@NonNull U user, int page,
	                @NonNull PaginatedMenu<P, U, I, E> paginatedMenu,
									@NonNull PageCreator<P, U, I, E, ?> pageCreator,
	                @NonNull MenusManager<P, U, I, E, ?> manager) {
		this.page = page;
		this.manager = manager;
		this.pageCreator = pageCreator;
		this.paginatedMenu = paginatedMenu;

		manager.debug("Initializing the adapter");
		this.adapter = new MenuPageAdapter<>(page, user, paginatedMenu, pageCreator);

		manager.debug("Verifying the capacity");
		this.verifyItemsCapacity();

		manager.debug("Setting page essential items");
		this.prev = this.getPreviousPageButton();
		this.next = this.getNextPageButton();
		this.setPageItems();

		manager.debug("Initializing inventory object");
		this.initialize(user);


	}

	protected void verifyItemsCapacity() {
		final int totalPageSize = size().scalar();
		final int extraItemsSize = adapter.getAdditionalItemsSize();
		final int addedPageItems = 2;

		final int pageCapacity = (totalPageSize-extraItemsSize-addedPageItems);
		if(paginatedMenu.getItemsPerPage() >= pageCapacity)
			throw new IllegalArgumentException("Items Per Page are greater than what the page can have\n" +
							"Items-Per-Page= " + paginatedMenu.getItemsPerPage() + ", Page-Capacity= " + pageCapacity);
	}

	protected abstract ClickableItem<I,E> getPreviousPageButton();

	protected abstract ClickableItem<I,E> getNextPageButton();

	/**
	 * This is where  the inventory object is
	 * initialized by using the data carried by our
	 * MenuAdapter {@link MenuAdapter}
	 */
	protected abstract void initialize(@NonNull U user);

	@Override
	public @NonNull MenuAdapter<U, I, E> getAdapter() {
		return adapter;
	}

	@Override
	public @Nullable ClickableItem<I, E> getItemAt(SlotPosition position) {
		return adapter.getItemAt(position);
	}

	@Override
	public @Nullable ClickableItem<I, E> getItemAt(int row, int column) {
		return adapter.getItemAt(row,column);
	}

	@Override
	public void setItem(int row, int column, @NonNull ClickableItem<I, E> clickableItem) {
		adapter.setItem(row,column,clickableItem);
	}

	protected abstract void setPageItems();

	@Override
	public void forceClose(U user){

	}


}
