package fr.krykox.customitems.managers;

import fr.krykox.customitems.items.EpicPickaxe;
import fr.krykox.customitems.items.EpicSword;
import fr.krykox.customitems.items.LegendaryPickaxe;
import fr.krykox.customitems.items.LegendarySword;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Custom items registry.
 */
public class CustomItemsRegistry {
    private final List<ACustomItems> customItems = new ArrayList<>();
    private final JavaPlugin plugin;

    /**
     * Instantiates a new Custom items registry.
     *
     * @param plugin the plugin
     */
    public CustomItemsRegistry(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Register all.
     */
    public void registerAll() {
        customItems.add(new LegendarySword());
        customItems.add(new LegendaryPickaxe());
        customItems.add(new EpicPickaxe());
        customItems.add(new EpicSword());

        for (ACustomItems item : customItems) {
            Bukkit.addRecipe(item.getRecipe(plugin));
        }
    }

    /**
     * Gets all custom items.
     *
     * @return the all custom items
     */
    public List<ACustomItems> getAllCustomItems() {
        return customItems;
    }

    /**
     * Gets item by id.
     *
     * @param id the id
     * @return the item by id
     */
    public ACustomItems getItemById(String id) {
        return customItems.stream()
                .filter(item -> item.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

}
