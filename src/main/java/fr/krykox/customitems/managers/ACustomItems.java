package fr.krykox.customitems.managers;

import fr.krykox.customitems.abilities.Ability;
import fr.krykox.customitems.rarities.RarityType;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The type A custom items.
 */
public abstract class ACustomItems {

    /**
     * Gets item.
     *
     * @return the item
     */
    public abstract ItemStack getItem();

    /**
     * Gets key.
     *
     * @param plugin the plugin
     * @return the key
     */
    public abstract NamespacedKey getKey(JavaPlugin plugin);

    /**
     * Gets recipe.
     *
     * @param plugin the plugin
     * @return the recipe
     */
    public abstract ShapedRecipe getRecipe(JavaPlugin plugin);

    /**
     * Gets ability.
     *
     * @return the ability, or null if none
     */
    public abstract Ability getAbility();


    /**
     * Gets rarity.
     *
     * @return the rarity
     */
    public abstract RarityType getRarity();


    /**
     * Gets id.
     *
     * @return the id
     */
    public abstract String getId();

}
