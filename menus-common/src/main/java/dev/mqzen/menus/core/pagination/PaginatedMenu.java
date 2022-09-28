package dev.mqzen.menus.core.pagination;

import dev.mqzen.menus.core.ClickableItem;
import dev.mqzen.menus.core.MenuSize;
import dev.mqzen.menus.core.MenusManager;
import dev.mqzen.menus.core.SlotPosition;
import lombok.Getter;
import lombok.val;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class PaginatedMenu<P, U, I, E> {

	protected boolean opened = false;

	private final @NonNull ConcurrentHashMap<Integer, MenuPage<P, U, I, E>> pages = new ConcurrentHashMap<>();
	protected final @NonNull List<ClickableItem<I, E>> items = new ArrayList<>();
	protected final @Getter int itemsPerPage;
	protected final @NonNull PageCreator<P, U, I, E, ?> pageCreator;
	protected final @NonNull MenusManager<P, U, I, E, ?> manager;
	protected final @NonNull Set<UUID> pageOpeners = new HashSet<>();

	public PaginatedMenu(int itemsPerPage,
	                     @NonNull U user,
	                     @NonNull PageCreator<P, U, I, E, ?> pageCreator, @NonNull MenusManager<P, U, I, E, ?> manager) {
		this.itemsPerPage = itemsPerPage;

		this.verifyItemsPerPage();

		this.manager = manager;
		this.pageCreator = pageCreator;

		this.addItems();

		this.paginate(user);
	}

	private void verifyItemsPerPage() {

		int max = size().scalar()-2;
		if (itemsPerPage >= max) {
			throw new IllegalArgumentException("Items per size is greater than Maximum number of slots per page\n" +
							" value= " + itemsPerPage + " and max= " + max);
		}

	}


	public abstract String title(U user);

	public abstract MenuSize size();

	public abstract void addItems();

	public abstract SlotPosition getPreviousPagePosition();

	public abstract SlotPosition getNextPagePosition();

	private void paginate(U user) {

		//creating pages, and putting them into cache
		final int max = maximumPages();
		for (int pageIndex = 0; pageIndex < max; pageIndex++) {
			val page = pageCreator.createNewPage(user, pageIndex, this);
			this.addPage(pageIndex, page);
		}

	}

	public void addPage(int page, MenuPage<P, U, I, E> pageObj) {
		pages.put(page, pageObj);
	}

	public @Nullable MenuPage<P, U, I, E> getPage(int index) {
		return pages.get(index);
	}

	public final int maximumPages() {
		return (int)Math.ceil((double)items.size()/itemsPerPage);
	}


	public @Nullable ClickableItem<I,E> getItemAt(int index) {
		return items.get(index);
	}

	public boolean isOpeningPage(UUID uuid) {
		return pageOpeners.contains(uuid);
	}

	public abstract void open(U user, int pointingPage);

	public final void clean() {
		manager.debug("Cleaning...");
		pages.clear();
		items.clear();
		pageOpeners.clear();
	}

}
