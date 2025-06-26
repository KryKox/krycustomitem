package fr.krykox.customitems.managers;

import fr.krykox.customitems.items.EpicPickaxe;
import fr.krykox.customitems.items.EpicSword;
import fr.krykox.customitems.items.LegendaryPickaxe;
import fr.krykox.customitems.items.LegendarySword;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Durability listener.
 */
public class DurabilityListener implements Listener {

    private final NamespacedKey idKey = new NamespacedKey("customitems", "custom_item_id");
    private final NamespacedKey durabilityKey = new NamespacedKey("customitems", "custom_durability");
    private final NamespacedKey maxDurabilityKey = new NamespacedKey("customitems", "custom_durability_max");

    private final Map<Block, Material> blockTypes = new HashMap<>();

    /**
     * On block damage.
     *
     * @param event the event
     */
    @EventHandler
    public void onBlockDamage(BlockDamageEvent event) {
        Block block = event.getBlock();
        blockTypes.put(block, block.getType());
    }

    /**
     * On block break.
     *
     * @param event the event
     */
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (LegendarySword.isLegendarySword(item) && EpicSword.isEpicSword(item)) return;

        if (!LegendaryPickaxe.isLegendaryPickaxe(item) && !EpicPickaxe.isEpicPickaxe(item)) return;


        if (isBroken(item)) {
            event.setCancelled(true);
            event.setDropItems(false);
            player.sendMessage("§cCet objet est cassé ! Tu ne peux plus casser de blocs avec.");

            Material oldType = blockTypes.get(event.getBlock());
            if (oldType != null) {
                event.getBlock().setType(oldType);
                blockTypes.remove(event.getBlock());
            }
        } else {
            reduceDurability(item);
        }
    }

    /**
     * On entity damage by entity.
     *
     * @param event the event
     */
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player)) return;
        if (!(event.getEntity() instanceof Mob)) return;

        ItemStack item = player.getInventory().getItemInMainHand();

        if (!LegendarySword.isLegendarySword(item) && !EpicSword.isEpicSword(item)) return;

        if (isBroken(item)) {
            event.setCancelled(true);
            player.sendMessage("§cTon arme est cassée ! Tu ne peux plus taper les mobs avec.");
        } else {
            reduceDurability(item);
        }
    }

    private boolean isBroken(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return false;

        if (!meta.getPersistentDataContainer().has(idKey, PersistentDataType.STRING)) return false;

        int durability = meta.getPersistentDataContainer().getOrDefault(durabilityKey, PersistentDataType.INTEGER, 0);
        return durability <= 0;
    }

    private void reduceDurability(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        if (!meta.getPersistentDataContainer().has(idKey, PersistentDataType.STRING)) return;

        int durability = meta.getPersistentDataContainer().getOrDefault(durabilityKey, PersistentDataType.INTEGER, 0);
        int maxDurability = meta.getPersistentDataContainer().getOrDefault(maxDurabilityKey, PersistentDataType.INTEGER, 1000);

        if (durability <= 0) return;

        durability--;
        meta.getPersistentDataContainer().set(durabilityKey, PersistentDataType.INTEGER, durability);

        List<String> lore = meta.getLore();
        if (lore != null) {
            for (int i = 0; i < lore.size(); i++) {
                if (lore.get(i).contains("Durabilité:")) {
                    lore.set(i, "§8➠ §7Durabilité: §a" + durability + "§7/§a" + maxDurability);
                    break;
                }
            }
            meta.setLore(lore);
        }

        item.setItemMeta(meta);
    }
}
