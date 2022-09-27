package dev.mqzen.menus.core;

import lombok.*;
import org.checkerframework.checker.nullness.qual.NonNull;

@Getter
@EqualsAndHashCode
public class SlotPosition {

	private final int row, column;

	private SlotPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}

	public static @NonNull SlotPosition of(int row, int column) {
		return new SlotPosition(row, column);
	}

	public static @NonNull SlotPosition fromSlot(@NonNull MenuSize size, int slot) {
		//convert slot to row and column
		int row = slot / size.getColumns();
		int column = slot % size.getColumns();
		return of(row, column);
	}

	public int getScalarPosition(MenuSize menuSize) {
		return menuSize.getColumns() * row + column;
	}

}
