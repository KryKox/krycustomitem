package fr.krykox.customitems;

import fr.krykox.customitems.abilities.AbilityListener;
import fr.krykox.customitems.commands.CustomItemCommand;
import fr.krykox.customitems.commands.CustomItemsGuiCommand;
import fr.krykox.customitems.managers.CustomItemsRegistry;
import fr.krykox.customitems.managers.DurabilityListener;
import org.bukkit.plugin.java.JavaPlugin;

public class KryCustomItems extends JavaPlugin {
    private static KryCustomItems instance;
    private CustomItemsRegistry customItemsRegistry;

    @Override
    public void onEnable() {
        System.out.println("[KryCustomItems] Plugin initialization !");
        instance = this;

        getServer().getPluginManager().registerEvents(new AbilityListener(), this);
        getServer().getPluginManager().registerEvents(new DurabilityListener(), this);

        getCommand("customitem").setExecutor(new CustomItemCommand());
        getCommand("items").setExecutor(new CustomItemsGuiCommand(this));

        customItemsRegistry = new CustomItemsRegistry(this);
        customItemsRegistry.registerAll();
    }

    @Override
    public void onDisable() {
        System.out.println("[KryCustomItems] Plugin stopped !");

    }

    public static KryCustomItems getInstance() {
        return (instance != null ? instance : new KryCustomItems());
    }

    public CustomItemsRegistry getCustomItemsRegistry() {
        return customItemsRegistry;
    }
}