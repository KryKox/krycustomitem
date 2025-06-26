package fr.krykox.customitems.abilities;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * The type Ability.
 */
public class Ability {
    private final AbilityType type;
    private final int minValue;
    private final int maxValue;
    private final Random random = new Random();

    /**
     * Instantiates a new Ability.
     *
     * @param type     the type
     * @param minValue the min value
     * @param maxValue the max value
     */
    public Ability(AbilityType type, int minValue, int maxValue) {
        this.type = type;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public AbilityType getType() {
        return type;
    }

    /**
     * Apply.
     *
     * @param event the event
     */
    public void apply(EntityDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        if (killer == null) return;

        switch (type) {
            case XP -> {
                int xp = random.nextInt(maxValue - minValue + 1) + minValue;
                event.setDroppedExp(event.getDroppedExp() + xp);
            }
            case LOOT -> {
                int extraLoot = random.nextInt(maxValue - minValue + 1) + minValue;

                List<ItemStack> drops = event.getDrops();
                List<ItemStack> newDrops = new ArrayList<>();

                for (ItemStack item : drops) {
                    ItemStack clone = item.clone();
                    clone.setAmount(Math.min(64, item.getAmount() + extraLoot));
                    newDrops.add(clone);
                }

                drops.clear();
                drops.addAll(newDrops);
            }
        }
    }

    /**
     * Apply.
     *
     * @param event the event
     */

    public void apply(BlockBreakEvent event) {
        if (type != AbilityType.LOOT) return;

        Material blockType = event.getBlock().getType();
        ItemStack tool = event.getPlayer().getInventory().getItemInMainHand();
        if (tool == null) return;

        Set<Material> minerais = Set.of(
                Material.COAL_ORE,
                Material.IRON_ORE,
                Material.GOLD_ORE,
                Material.DIAMOND_ORE,
                Material.EMERALD_ORE,
                Material.REDSTONE_ORE,
                Material.LAPIS_ORE,
                Material.DEEPSLATE_COAL_ORE,
                Material.DEEPSLATE_IRON_ORE,
                Material.DEEPSLATE_GOLD_ORE,
                Material.DEEPSLATE_DIAMOND_ORE,
                Material.DEEPSLATE_EMERALD_ORE,
                Material.DEEPSLATE_REDSTONE_ORE,
                Material.DEEPSLATE_LAPIS_ORE,
                Material.NETHER_QUARTZ_ORE,
                Material.NETHER_GOLD_ORE
        );

        if (minerais.contains(blockType)) {
            List<ItemStack> drops = (List<ItemStack>) event.getBlock().getDrops(tool);

            int extraLoot = random.nextInt(maxValue - minValue + 1) + minValue;

            event.setCancelled(true);
            event.getBlock().setType(Material.AIR);

            for (ItemStack drop : drops) {
                ItemStack clone = drop.clone();
                clone.setAmount(Math.min(64, extraLoot));
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), clone);
            }
        }
    }


    /**
     * Gets min value.
     *
     * @return the min value
     */
    public int getMinValue() {
        return minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }
}
