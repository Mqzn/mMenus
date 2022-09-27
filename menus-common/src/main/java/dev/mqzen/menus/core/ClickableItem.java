package dev.mqzen.menus.core;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.checkerframework.checker.nullness.qual.NonNull;
import java.util.function.Consumer;

@EqualsAndHashCode
public abstract class ClickableItem<I, E> {

	protected final @Getter @NonNull I itemObj;
	protected final @NonNull Consumer<E> instructions;

	protected ClickableItem(@NonNull I item,
	                      @NonNull Consumer<E> instructions) {
		this.itemObj = item;
		this.instructions = instructions;
	}

	protected ClickableItem(@NonNull I item) {
		this.itemObj = item;
		this.instructions = (e)->{};
	}

	public synchronized void executeOnClick(E linkerObj) {
		this.instructions.accept(linkerObj);
	}

}
