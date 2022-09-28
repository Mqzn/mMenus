package dev.mqzen.menus.spigot.test;

import dev.mqzen.menus.spigot.SpigotClickableItem;
import dev.mqzen.menus.spigot.SpigotMenuAdapter;
import dev.mqzen.menus.spigot.SpigotMenusManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import java.util.concurrent.CompletableFuture;

public class ExamplePlugin extends JavaPlugin implements CommandExecutor {

	private @MonotonicNonNull SpigotMenusManager manager;
	private @MonotonicNonNull SpigotMenuAdapter adapter;

	@Override
	public void onEnable() {
		this.getCommand("tg").setExecutor(this);
		manager = SpigotMenusManager.newInstance(this);
		adapter = new ExampleAdapter();

		//you can also add items after initializing the instance of the adapter
		adapter.setItem(0, 0, SpigotClickableItem.of(new ItemStack(Material.APPLE, 1),
						(e)-> {
							Player player = (Player) e.getWhoClicked();
							player.sendMessage(
											ChatColor.translateAlternateColorCodes('&',
															"&cDon't touch my apple"));

							player.closeInventory();
						}));
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!label.equalsIgnoreCase("tg")) {
			return false;
		}

		if(!(sender instanceof Player))return false;

		Player player = (Player)sender;
		manager.openMenu(player,adapter);
		return true;
	}

}
