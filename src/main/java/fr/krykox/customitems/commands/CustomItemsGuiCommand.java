package fr.krykox.customitems.commands;

import fr.krykox.customitems.gui.CustomItemsGui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class CustomItemsGuiCommand implements CommandExecutor {
    private final JavaPlugin plugin;

    public CustomItemsGuiCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Commande réservée aux joueurs.");
            return false;
        }
        Player player = (Player) sender;

        player.sendMessage("§aOuverture du menu en cours...");
        new CustomItemsGui(plugin).open(player);
        return true;
    }
}
