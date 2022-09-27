package dev.mqzen.menus.core;

import lombok.*;

@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@RequiredArgsConstructor(staticName = "of")
public final class MenuSize {


	private int rows, columns = 9;

	public MenuSize addRows(int rowsToAdd) {
		rows+=rowsToAdd;
		return this;
	}

	public MenuSize removeRows(int rowsToRemove) {
		rows = Math.abs(rows-rowsToRemove);
		return this;
	}

	public MenuSize addColumns(int columnsToAdd) {
		columns+=columnsToAdd;
		return this;
	}

	public MenuSize removeColumns(int columnsToRemove) {
		columns-=columnsToRemove;
		return this;
	}

	public int scalar() {
		return rows*columns;
	}

}
