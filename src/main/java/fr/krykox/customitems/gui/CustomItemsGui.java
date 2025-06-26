package fr.krykox.customitems.gui;

import fr.krykox.customitems.items.EpicPickaxe;
import fr.krykox.customitems.items.EpicSword;
import fr.krykox.customitems.items.LegendaryPickaxe;
import fr.krykox.customitems.items.LegendarySword;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The type Custom items gui.
 */
public class CustomItemsGui implements Listener {
    private final JavaPlugin plugin;
    private final String GUI_TITLE = "§8Objets personnalisés";
    private final int GUI_SIZE = 27;

    /**
     * Instantiates a new Custom items gui.
     *
     * @param plugin the plugin
     */
    public CustomItemsGui(JavaPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    /**
     * Open.
     *
     * @param player the player
     */
    public void open(Player player) {
        Inventory gui = Bukkit.createInventory(null, GUI_SIZE, GUI_TITLE);

        gui.setItem(10, new LegendaryPickaxe().getItem());
        gui.setItem(11, new EpicPickaxe().getItem());
        gui.setItem(15, new LegendarySword().getItem());
        gui.setItem(16, new EpicSword().getItem());

        ItemStack filler = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta fillerMeta = filler.getItemMeta();
        if (fillerMeta != null) {
            fillerMeta.setDisplayName(" ");
            filler.setItemMeta(fillerMeta);
        }
        for (int i = 0; i < GUI_SIZE; i++) {
            if (gui.getItem(i) == null) {
                gui.setItem(i, filler);
            }
        }

        ItemStack close = new ItemStack(Material.BARRIER);
        ItemMeta meta = close.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§cFermer");
            close.setItemMeta(meta);
        }
        gui.setItem(22, close);

        player.openInventory(gui);
    }

    /**
     * On inventory click.
     *
     * @param event the event
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        if (!event.getView().getTitle().equals(GUI_TITLE)) return;

        event.setCancelled(true);

        int slot = event.getRawSlot();

        switch (slot) {
            case 10 -> {
                player.getInventory().addItem(new LegendaryPickaxe().getItem());
                player.sendMessage("§aTu as reçu une Legendary Pickaxe !");
            }
            case 11 -> {
                player.getInventory().addItem(new EpicPickaxe().getItem());
                player.sendMessage("§aTu as reçu une Epic Pickaxe !");
            }
            case 15 -> {
                player.getInventory().addItem(new LegendarySword().getItem());
                player.sendMessage("§aTu as reçu une Legendary Sword !");
            }
            case 16 -> {
                player.getInventory().addItem(new EpicSword().getItem());
                player.sendMessage("§aTu as reçu une Epic Sword !");
            }
            case 22 -> {
                player.closeInventory();
            }
            default -> {
            }
        }
    }
}
