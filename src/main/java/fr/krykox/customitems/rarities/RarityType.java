package fr.krykox.customitems.rarities;

import java.util.Arrays;

/**
 * The enum Rarity type.
 */
public enum RarityType {
    COMMUN("Commun", "§7§lCommun"),
    RARE("Rare", "§b§lRare"),
    EPIQUE("Épique", "§5§lÉpique"),
    LEGENDAIRE("Légendaire", "§c§lLégendaire");

    private String name;
    private String prefix;

    RarityType(String  name, String prefix) {
        this.name = name;
        this.prefix = prefix;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets prefix.
     *
     * @return the prefix
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Gets by name.
     *
     * @param name the name
     * @return the by name
     */
    public static RarityType getByName(String name) {
        return Arrays.stream(RarityType.values()).filter(rarityType -> rarityType.name.equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    /**
     * Gets by prefix.
     *
     * @param prefix the prefix
     * @return the by prefix
     */
    public static RarityType getByPrefix(String prefix) {
        return Arrays.stream(RarityType.values()).filter(prefixType -> prefixType.prefix.equalsIgnoreCase(prefix)).findFirst().orElse(null);
    }
}
