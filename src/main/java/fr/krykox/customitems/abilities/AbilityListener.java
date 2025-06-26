package fr.krykox.customitems.abilities;

import fr.krykox.customitems.items.EpicPickaxe;
import fr.krykox.customitems.items.EpicSword;
import fr.krykox.customitems.items.LegendaryPickaxe;
import fr.krykox.customitems.items.LegendarySword;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

/**
 * The type Ability listener.
 */
public class AbilityListener implements Listener {
    private final LegendarySword sword = new LegendarySword();
    private final LegendaryPickaxe pickaxe = new LegendaryPickaxe();

    /**
     * On mob kill.
     *
     * @param event the event
     */
    @EventHandler
    public void onMobKill(EntityDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        if (killer == null) return;

        ItemStack hand = killer.getInventory().getItemInMainHand();

        Ability ability = null;

        if (LegendarySword.isLegendarySword(hand)) {
            ability = sword.getAbility();
        } else if (EpicSword.isEpicSword(hand)) {
            EpicSword epicSword = new EpicSword();
            ability = epicSword.getAbility();
        }

        if (ability != null) {
            ability.apply(event);
        }
    }


    /**
     * On block break.
     *
     * @param event the event
     */
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack hand = player.getInventory().getItemInMainHand();

        Ability ability = null;

        if (LegendaryPickaxe.isLegendaryPickaxe(hand)) {
            if (isBroken(hand)) {
                event.setCancelled(true);
                event.setDropItems(false);
                return;
            }
            ability = pickaxe.getAbility();
        } else if (EpicPickaxe.isEpicPickaxe(hand)) {
            if (isBroken(hand)) {
                event.setCancelled(true);
                event.setDropItems(false);
                return;
            }
            EpicPickaxe epicPickaxe = new EpicPickaxe();
            ability = epicPickaxe.getAbility();
        }

        if (ability != null) {
            ability.apply(event);
        }
    }


    private boolean isBroken(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return false;

        NamespacedKey durabilityKey = new NamespacedKey("customitems", "custom_durability");
        int durability = meta.getPersistentDataContainer().getOrDefault(durabilityKey, PersistentDataType.INTEGER, 0);
        return durability <= 0;
    }
}
