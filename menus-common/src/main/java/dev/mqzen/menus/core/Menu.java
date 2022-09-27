package dev.mqzen.menus.core;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface Menu<U, I, E> {

	@NonNull MenuAdapter<U, I, E> getAdapter();

	void open(U user);

	@Nullable ClickableItem<I, E> getItemAt(int row, int column);

	void setItem(int row, int column, @NonNull ClickableItem<I, E> clickableItem);

	default void setItem(@NonNull SlotPosition position, @NonNull ClickableItem<I, E> clickableItem) {
		this.setItem(position.getRow(), position.getColumn(), clickableItem);
	}

	void forceClose(U user);

	default @Nullable ClickableItem<I, E> getItemAt(SlotPosition position) {
		return getItemAt(position.getRow(),position.getColumn());
	}

	default void onClose(U user) {
		this.getAdapter().onClose(user);
	}

	default MenuSize size() {
		return this.getAdapter().size();
	}

	default int rows() {
		return size().getRows();
	}

	default int columns() {
		return size().getColumns();
	}


}
