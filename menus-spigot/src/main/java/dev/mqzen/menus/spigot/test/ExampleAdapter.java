package dev.mqzen.menus.spigot.test;

import dev.mqzen.menus.spigot.SpigotClickableItem;
import dev.mqzen.menus.spigot.SpigotMenuAdapter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

public class ExampleAdapter extends SpigotMenuAdapter {

	protected ExampleAdapter() {
		super(6);
	}

	@Override
	public @NonNull String title(Player user) {
		return "&cExample " + user.getName();
	}

	@Override
	public void setMenuItems() {
		//TODO set your items here
		//or set them manually after you initialize an instance of this class

		//Here's an example of creating an empty item with no onClick actions
		setItem(0,1, SpigotClickableItem.empty(new ItemStack(Material.ARMOR_STAND)));

		//Here's an example of creating an item with onClick actions that are triggered when the user clicks on that item
		setItem(1, 1, SpigotClickableItem.of(new ItemStack(Material.WOOD), (e)-> {
			Player player = (Player) e.getWhoClicked();
			player.closeInventory();
			player.sendMessage("That's the wood for our armor stand, please don't steal it !");
		}));

	}

}
