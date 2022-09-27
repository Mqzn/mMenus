package dev.mqzen.menus.core;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.checker.units.qual.C;

import java.util.Map;

public interface MenuAdapter<U, I, E> {

	@NonNull String title(U user);

	@NonNull MenuSize size();

	void setItem(SlotPosition position, ClickableItem<I, E> item);

	default void setItem(int row, int column, ClickableItem<I, E> item) {
		setItem(SlotPosition.of(row,column), item);
	}

	@NonNull Map<SlotPosition, ClickableItem<I, E>> getItems();

	void onClose(U user);

	default @Nullable ClickableItem<I, E> getItemAt(int row, int column) {
		return getItemAt(SlotPosition.of(row, column));
	}

	@Nullable ClickableItem<I, E> getItemAt(SlotPosition position);

	default int columns() {
		return size().getColumns();
	}

	default int rows() {
		return size().getRows();
	}

}
