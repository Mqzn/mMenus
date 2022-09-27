package dev.mqzen.menus.core.pagination;

import dev.mqzen.menus.core.MenusManager;
import org.checkerframework.checker.nullness.qual.NonNull;

public abstract class PageCreator<P, U, I, E, MP extends MenuPage<P ,U ,I, E>> {

	protected final MenusManager<P, U, I, E, ?> manager;

	public PageCreator(MenusManager<P, U, I, E, ?> manager) {
		this.manager = manager;
	}

	public abstract void setAdditionalItems(U user, MenuPageAdapter<P, U, I, E> pageAdapter);
	public abstract MP createNewPage(@NonNull U user, int pageIndex, PaginatedMenu<P, U, I, E> paginatedMenu);

}
