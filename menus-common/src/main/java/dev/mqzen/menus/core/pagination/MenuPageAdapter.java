package dev.mqzen.menus.core.pagination;

import dev.mqzen.menus.core.*;
import lombok.Getter;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public final class MenuPageAdapter<P, U, I, E> implements MenuAdapter<U, I ,E> {

	private final @NonNull PaginatedMenu<P, U, I, E> paginatedMenu;
	private final @NonNull AtomicInteger page = new AtomicInteger(0);
	private final @NonNull Map<SlotPosition, ClickableItem<I, E>> items;
	private final @NonNull MenuIterator<U, I ,E> iterator;
	private final @Getter int additionalItemsSize;

	public MenuPageAdapter(int page, U user, @NonNull PaginatedMenu<P, U, I, E> paginatedMenu,
	                       @NonNull PageCreator<P, U, I, E, ?> creator) {
		this.paginatedMenu = paginatedMenu;
		this.page.set(page);
		this.items = new HashMap<>(paginatedMenu.getItemsPerPage()+2);

		creator.setAdditionalItems(user, this);
		additionalItemsSize =  items.size();

		this.iterator = new MenuIterator<>(this, MenuIterator.Type.HORIZONTAL,
						paginatedMenu.getNextPagePosition(), paginatedMenu.getPreviousPagePosition());

		this.setItems(page, paginatedMenu);
	}

	private void setItems(int page, @NonNull PaginatedMenu<P, U, I, E> paginatedMenu) {
		int start = page*paginatedMenu.getItemsPerPage();
		int end = (page+1)*paginatedMenu.getItemsPerPage();

		int i = 0;
		for (int index = start; index < end; index++) {
			try {
				SlotPosition position = iterator.next();
				items.put(position, paginatedMenu.getItemAt(index));
			}catch (Exception ex){
				break;
			}
			i++;
		}

	}

	@Override
	public @NonNull String title(U user) {
		return paginatedMenu.title(user).concat(" #" + (page.get()+1));
	}

	@Override
	public @NonNull MenuSize size() {
		return paginatedMenu.size();
	}

	@Override
	public void setItem(SlotPosition position, ClickableItem<I, E> item) {
		items.put(position, item);
	}


	@Override
	public @NonNull Map<SlotPosition, ClickableItem<I, E>> getItems() {
		return items;
	}

	@Override
	public void onClose(U user) {

	}

	@Override
	public @Nullable ClickableItem<I, E> getItemAt(SlotPosition position) {
		return items.get(position);
	}

}
