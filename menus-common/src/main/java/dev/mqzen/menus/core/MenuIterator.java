package dev.mqzen.menus.core;

import org.checkerframework.checker.nullness.qual.NonNull;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

/**
 *
 * Inspired by Smart inventory's SlotIterator
 *
 * @param <U> The User type
 * @param <I> The Item type
 * @param <E> The event that will be triggered when clicking in the gui
 */
public final class MenuIterator<U, I, E> {

	private final @NonNull MenuAdapter<U, I, E> menu;
	private final @NonNull Type type;
	private final @NonNull Predicate<SlotPosition> positionFilter;
	private int row, column;
	private final @NonNull AtomicBoolean started = new AtomicBoolean(false);

	public MenuIterator(@NonNull MenuAdapter<U, I, E> menu,
	                    @NonNull Type type,
	                    @NonNull Predicate<SlotPosition> positionFilter) {
		this.menu = menu;
		this.type = type;
		this.positionFilter = positionFilter;
	}

	public MenuIterator(@NonNull MenuAdapter<U, I, E> menu,
	                    @NonNull Type type,
	                    @NonNull SlotPosition... blackListedPositions) {

		this(menu, type, (pos)-> {
			for(SlotPosition blackListed : blackListedPositions)
				if(pos.equals(blackListed))return false;

			return true;
		});

	}

	public Optional<ClickableItem<I, E>> currentItem() {
		return Optional.ofNullable(menu.getItemAt(row,column));
	}

	public void set(ClickableItem<I, E> clickableItem) {
		if(canPlace())
			menu.setItem(row, column, clickableItem);
	}

	/**
	 * I have learned how smart inventory's algorithm
	 * works , and re-written it from A to Z to solidify
	 * the concepts and the idea(s) I learned from this algorithm
	 *
	 * @return the next available slot
	 */
	public synchronized SlotPosition next() {
		if (ended()) {
			this.started.set(true);
			return SlotPosition.of(0, 0);
		}

		do {
			if(!this.started.get()) {
				this.started.set(true);
				continue;
			}

			switch (type) {
				case HORIZONTAL:
					column = ++column % menu.columns();

					if (column == 0)
						row++;
					break;
				case VERTICAL:
					row = ++row % menu.rows();

					if (row == 0)
						column++;
					break;
			}
		} while (!canPlace() && !ended());

		return SlotPosition.of(row,column);
	}

	public synchronized SlotPosition previous() {

		if (row == 0 && column == 0) {
			this.started.set(true);
			return SlotPosition.of(0, 0);
		}

		do {
			if(!this.started.get()) {
				this.started.set(true);
				continue;
			}

			switch (type) {
				case HORIZONTAL:
					column--;

					if (column == 0) {
						column = menu.columns() - 1;
						row--;
					}
					break;
				case VERTICAL:
					row--;

					if (row == 0) {
						row = menu.rows() - 1;
						column--;
					}
					break;
			}

		} while (!canPlace() && (row != 0 || column != 0));

		return SlotPosition.of(row,column);
	}

	public boolean ended() {
		return row == menu.rows() - 1
						&& column == menu.columns() - 1;
	}

	private boolean canPlace() {
		SlotPosition position = SlotPosition.of(row, column);
		return positionFilter.test(position) && !this.currentItem().isPresent();
	}

	public enum Type {
		HORIZONTAL(),
		VERTICAL();
	}

}
