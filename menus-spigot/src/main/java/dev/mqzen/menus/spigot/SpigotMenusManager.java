package dev.mqzen.menus.spigot;

import dev.mqzen.menus.core.Menu;
import dev.mqzen.menus.core.MenusManager;
import dev.mqzen.menus.core.SlotPosition;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class SpigotMenusManager extends MenusManager<Plugin, Player,
				ItemStack, InventoryClickEvent, SpigotMenuAdapter> implements Listener {


	private SpigotMenusManager(@NonNull Plugin platform) {
		super(platform);
	}


	public static SpigotMenusManager newInstance(@NonNull Plugin platform) {
		return new SpigotMenusManager(platform);
	}

	@Override
	protected @NonNull Menu<Player, ItemStack, InventoryClickEvent> newMenu(@NonNull Player user,
	                                                                        @NonNull SpigotMenuAdapter adapter)
	{
		return new SpigotMenu(user,adapter);
	}

	@Override
	protected void initialize() {
		Bukkit.getPluginManager().registerEvents(this, platform);
	}

	@Override
	public void debug(String msg) {
		platform.getLogger().info(msg);
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {

		Player player = (Player) e.getWhoClicked();

		int slot = e.getSlot();
		if(slot < 0 || slot > 53) {
			return;
		}

		val menu =  this.getOpenMenu(player);
		if(menu == null)
			return;


		SlotPosition position = SlotPosition.fromSlot(menu.size(), slot);
		val clickableItem =  menu.getItemAt(position);
		if(clickableItem == null) return;

		clickableItem.executeOnClick(e);

		e.setCancelled(true);
	}

	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		Player player = (Player) e.getPlayer();
		val menu = getOpenMenu(player);
		if(menu != null) {
			menu.onClose(player);
		}

		if(menu instanceof SpigotMenuPage) {

			SpigotMenuPage menuPage = (SpigotMenuPage) menu;
			if(!menuPage.getPaginatedMenu().isOpeningPage(player.getUniqueId())) {
				menuPage.getPaginatedMenu().clean();
				this.openMenus.remove(player);
			}


			return;
		}

		this.openMenus.remove(player);
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		openMenus.remove(player);
	}

}
