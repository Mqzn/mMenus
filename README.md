# mMenus
mMenus is an Advanced library for making GUIs(Graphical Users Interfaces)
It's generic enough to be used into different fields of work
However, I only implemented one module which is the spigot one
which allows you to create minecraft guis using spigot

**TODO:** Implement module for [Minestom](https://minestom.net)

This library has been designed according to all OOP principles I have been 
practicing the past 3 years.

## Features:
- User Friendly structure for better readability
- Almost fully documented for easier understanding
- Scalable & Maintainable.
- Simple. but, powerful !
- Allows you to create normal guis and also **paginated** ones

## Installation
Sorrowfully, mMenus can be installed as a dependency using the old plain way
by just downloading the jar from [here](./), 
or you can add it as a system dependency in your pom.xml(maven) or build.gradle(gradle)

## Usage Tutorial
Here's a series of steps to show you how your plugin should be using
mMenus library for creating minecraft spigot inventories.

### Creating normal menu
There are 2 types of menus, Paginated and Non-Paginated ones
this tutorial will go through how to create both types.

#### First Step (Creating menu adapter)
In order to create a menu adapter, you must first understand what is a menu adapter
it's a class that carries the info of the menu/gui you want to create.
You have to extend the class called `SpigotMenuAdapter` and implement it's methods

Here's an example:
```java
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
```

#### Second Step (Opening the menu from its adapter)
To be able to open the menu, you must initialize
an instance of Menus Manager in your main class 
, then create the instance of your adapter class
then finally use the manager instance to open the menu;

**Here's an example:**
```java
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
```

### Creating Paginated Menus
To create a fully functional paginated menu, you must follow the following 
steps with extreme concentration and make sure to fully understand every step.

#### First Step (Creating your own paginated menu)
Create a class to be the reference to the paginated menu
and make it extend the abstract class `SpigotPaginatedMenu`
then implement its methods which include the method `SpigotPaginatedMenu#addItems`
this is the method where you will add all of your items that will be distributed on 
in the form of gui/menu pages.

**Here's an example:**
```java
public final class ExamplePaginatedMenu extends SpigotPaginatedMenu {

	public ExamplePaginatedMenu(int itemsPerPage,
	                            @NonNull Player user,
	                            SpigotPageCreator pageCreator,
	                            SpigotMenusManager manager) {
		super(itemsPerPage, user, pageCreator, manager);
	}

	@Override
	public String title(Player user) {
		return "Welcome " + user.getName();
	}


	@Override
	public MenuSize size() {
		return MenuSize.of(6, 9);
	}

	@Override
	public void addItems() {

		SpigotClickableItem clickableItem;
		for (int i = 0; i < 1000; i++) {

			ItemStack itemStack = new ItemStack(Material.NAME_TAG);
			ItemMeta meta = itemStack.getItemMeta();
			meta.setDisplayName("#" + (i + 1));
			itemStack.setItemMeta(meta);

			clickableItem = SpigotClickableItem.empty(itemStack);
			items.add(clickableItem);
		}

	}

	@Override
	public SlotPosition getPreviousPagePosition() {
		return SlotPosition.of(5, 3);
	}

	@Override
	public SlotPosition getNextPagePosition() {
		return SlotPosition.of(5, 5);
	}

}
```

#### Second Step (Creating a page creator)
To be able to create a page creator , you need to understand what is a page creator
it's an interface which defines how a new page is being created by providing its instance
into the method `PageCreator#createNewPage(@NonNull Player user, int pageIndex, PaginatedMenu paginatedMenu)`
and you will also override the method `PageCreator#setAdditionalItems(@NonNull Player user, @NonNull MenuPageAdapter)`
where you can put some additional items which will be added when a page is created, OR just leave it **EMPTY**

First, create a class that will extend the class `SpigotPageCreator`,
then implemented the method mentioned above.

**Here's an example:**
```java
public final class ExamplePageCreator extends SpigotPageCreator {


	public ExamplePageCreator(@NonNull SpigotMenusManager manager) {
		super(manager);
	}

	@Override
	public void setAdditionalItems(@NonNull Player user, @NonNull MenuPageAdapter<Plugin, Player, ItemStack, InventoryClickEvent> pageAdapter) {
		//pageAdapter.setItem(0,0, SpigotClickableItem.empty(new ItemStack(Material.STAINED_GLASS_PANE)));
	}

	@Override
	public ItemStack getNextPageItemStack() {
		ItemStack item = new ItemStack(Material.ARROW);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&aNext page ->"));
		item.setItemMeta(meta);

		return item;
	}

	@Override
	public ItemStack getPreviousPageItemStack() {
		ItemStack item = new ItemStack(Material.ARROW);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&e<- Previous page"));
		item.setItemMeta(meta);
		return item;
	}

	@Override
	public SpigotMenuPage createNewPage(@NonNull Player user, int pageIndex, @NonNull PaginatedMenu<Plugin, Player, ItemStack, InventoryClickEvent> paginatedMenu) {
		return new SpigotMenuPage(user, pageIndex,
						(SpigotPaginatedMenu) paginatedMenu,
						this, (SpigotMenusManager) manager);
	}


}
```

#### Third step (Opening the paginated menu)
Create a new instance of your paginated menu class
then call the method `PaginatedMenu#open(player, page)`

**Here's an example command:**
```java
public class ExamplePlugin extends JavaPlugin {

	
	private @MonotonicNonNull SpigotMenusManager manager;
	private @MonotonicNonNull SpigotPageCreator pageCreator;

	public void onEnable() {
		this.manager = SpigotMenusManager.newInstance(this);
		this.pageCreator = new ExamplePageCreator(manager);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) return false;

		Player player = (Player) sender;
		ExamplePaginatedMenu paginatedMenu = new ExamplePaginatedMenu(10, player, pageCreator, manager);
		return true;
	}
}
```

## Results


## Big Thanks
Thanks for giving mMenus some of your time, I really hope it may be
useful for you in the future when making a lot of guis with complicated items

This project was made as an inspiration by [SmartInventory](https://github.com/MinusKube/SmartInvs),
So basically I got inspired by how brilliant is the idea of using rows and columns 
to express slots instead of just using old plain slots, this may be extremely useful and help us
make even more powerful algorithms to make our life much easier.

Note: I wrote this code by my self from A to Z.
This is my discord for any issues/bugs that you may encounter
My discord: **Mqzen#9999**