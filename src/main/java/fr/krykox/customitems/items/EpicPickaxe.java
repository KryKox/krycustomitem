package fr.krykox.customitems.items;

import fr.krykox.customitems.abilities.Ability;
import fr.krykox.customitems.abilities.AbilityType;
import fr.krykox.customitems.managers.ACustomItems;
import fr.krykox.customitems.rarities.RarityType;
import fr.krykox.customitems.utils.GradientText;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Legendary pickaxe.
 */
public class EpicPickaxe extends ACustomItems {
    private final Ability ability = new Ability(AbilityType.LOOT, 4, 7);
    private static final String KEY_NAME = "epic_pickaxe";
    private final int maxDurability = 750;


    @Override
    public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName("§5Pioche épique");

            List<String> lore = new ArrayList<>();
            lore.add("§7Une pioche forgée dans le feu des anciens.");
            lore.add("");

            meta.addEnchant(Enchantment.EFFICIENCY, 8, true);
            meta.addEnchant(Enchantment.MENDING, 1, true);
            meta.setUnbreakable(true);

            Color startColor = new Color(85, 170, 255);
            Color endColor = new Color(0, 255, 255);

            meta.getEnchants().forEach((enchant, level) -> {
                String enchantName = getCustomEnchantName(enchant);
                String enchantGradientName = GradientText.generateHexGradient(enchantName, startColor, endColor);
                lore.add("§b✦ " + enchantGradientName + " §7Niveau " + level);
            });

            Color startColor2 = new Color(252, 0, 0);
            Color endColor2 = new Color(255, 85, 85);

            String abilityName = GradientText.generateHexGradient(ability.getType().getDisplayName(), startColor2, endColor2);
            lore.add("");
            lore.add("§4✦ §7Capacité spéciale: " + abilityName + " §8(§c" + ability.getMinValue() + ability.getType().toString().toLowerCase() + " §7- §c" + ability.getMaxValue() + ability.getType().toString().toLowerCase() + "§8)");

            NamespacedKey idKey = new NamespacedKey("customitems", "custom_item_id");
            NamespacedKey durabilityKey = new NamespacedKey("customitems", "custom_durability");
            NamespacedKey maxDurabilityKey = new NamespacedKey("customitems", "custom_durability_max");
            int currentDurability = maxDurability;

            meta.getPersistentDataContainer().set(idKey, PersistentDataType.STRING, getId());
            meta.getPersistentDataContainer().set(durabilityKey, PersistentDataType.INTEGER, currentDurability);
            meta.getPersistentDataContainer().set(maxDurabilityKey, PersistentDataType.INTEGER, maxDurability);

            lore.add("");
            lore.add("§8➠ §7Durabilité: §a" + currentDurability + "§7/§a" + maxDurability);
            lore.add("§8➠ §7Rareté: §r" + getRarity().getPrefix());


            meta.setLore(lore);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);

            item.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            item.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            item.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            item.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);


            NamespacedKey tagKey = new NamespacedKey("customitems", KEY_NAME);
            PersistentDataContainer container = meta.getPersistentDataContainer();
            container.set(tagKey, PersistentDataType.BYTE, (byte) 1);

            item.setItemMeta(meta);
        }

        return item;
    }

    @Override
    public NamespacedKey getKey(JavaPlugin plugin) {
        return new NamespacedKey(plugin, KEY_NAME);
    }

    @Override
    public ShapedRecipe getRecipe(JavaPlugin plugin) {
        ShapedRecipe recipe = new ShapedRecipe(getKey(plugin), getItem());
        recipe.shape("GGG", " S ", " S ");
        recipe.setIngredient('G', Material.GOLD_BLOCK);
        recipe.setIngredient('S', Material.STICK);
        return recipe;
    }

    @Override
    public Ability getAbility() {
        return ability;
    }

    private String getCustomEnchantName(Enchantment enchant) {
        return switch (enchant.getKey().getKey()) {
            case "efficiency" -> "Efficacité";
            case "unbreaking" -> "Solidité";
            case "fortune" -> "Chance";
            default -> {
                String key = enchant.getKey().getKey();
                yield key.substring(0, 1).toUpperCase() + key.substring(1);
            }
        };
    }

    /**
     * Is legendary pickaxe boolean.
     *
     * @param item the item
     * @return the boolean
     */
    public static boolean isEpicPickaxe(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        NamespacedKey tagKey = new NamespacedKey("customitems", KEY_NAME);
        return meta.getPersistentDataContainer().has(tagKey, PersistentDataType.BYTE);
    }

    @Override
    public String getId() {
        return KEY_NAME;
    }

    @Override
    public RarityType getRarity() {
        return RarityType.EPIQUE;
    }
}
