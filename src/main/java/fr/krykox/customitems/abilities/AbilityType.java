package fr.krykox.customitems.abilities;

import java.util.Arrays;

/**
 * The enum Ability type.
 */
public enum AbilityType {
    XP("Bonus d'XP"),
    LOOT("Butin amélioré");

    private final String displayName;

    AbilityType(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets display name.
     *
     * @return the display name
     */
    public String getDisplayName() {
        return displayName;
    }

    public static AbilityType getByName(String name) {
        return Arrays.stream(AbilityType.values())
                .filter(type -> type.displayName.equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}
