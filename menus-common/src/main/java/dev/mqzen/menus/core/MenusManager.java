package dev.mqzen.menus.core;

import lombok.Getter;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public abstract class MenusManager<P, U, I, E, A extends MenuAdapter<U, I, E>> {

	protected final @NonNull @Getter P platform;

	protected final @NonNull ConcurrentHashMap<U, Menu<U, I, E>> openMenus = new ConcurrentHashMap<>();

	protected MenusManager(@NonNull P platform) {
		this.platform = platform;
		initialize();
	}

	public @Nullable Menu<U, I, E> getOpenMenu(U user) {
		return this.openMenus.get(user);
	}

	public void openMenu(@NonNull U user, @NonNull A menuAdapter) {
		final Menu<U, I, E> newMenu = newMenu(user, menuAdapter);
		this.openMenu(user, newMenu);
	}

	public void openMenu(@NonNull U user, @NonNull Menu<U, I, E> menu) {
		openMenus.put(user, menu);
		menu.open(user);
	}

	protected abstract @NonNull Menu<U, I, E> newMenu(U user, @NonNull A adapter);
	protected abstract void initialize();
	public abstract void debug(String msg);

	public final Collection<? extends Menu<U, I, E>> getOpenMenus() {
		return openMenus.values();
	}

}
