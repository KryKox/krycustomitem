package fr.krykox.customitems.commands;

import fr.krykox.customitems.KryCustomItems;
import fr.krykox.customitems.items.LegendaryPickaxe;
import fr.krykox.customitems.items.LegendarySword;
import fr.krykox.customitems.managers.CustomItemsRegistry;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;


/**
 * The type Custom item command.
 */
public class CustomItemCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Seul un utilisateur peut exécuter cette commande !");
            return false;
        }

        Player player = (Player) sender;

        if (!player.isOp()) {
            sender.sendMessage("§cVous n'avez pas la permission d'exécuter cette commande.");
            return false;
        }

        if (args.length == 0) {
            player.sendMessage("§cUtilisation: /customitem <nom>");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "legendary_pickaxe":
                ItemStack pickaxe = new LegendaryPickaxe().getItem();
                player.getInventory().addItem(pickaxe);
                player.sendMessage("§aVous avez reçu une §6Pioche légendaire§a !");
                break;

            case "legendary_sword":
                ItemStack sword = new LegendarySword().getItem();
                player.getInventory().addItem(sword);
                player.sendMessage("§aVous avez reçu une §6Épée légendaire§a !");
                break;

            default:
                player.sendMessage("§cType d'objet inconnu. Utilisez l'un des objets ci-dessous :");

                CustomItemsRegistry registry = KryCustomItems.getInstance().getCustomItemsRegistry();

                registry.getAllCustomItems().forEach(customItem -> {
                    String itemId = customItem.getId();

                    TextComponent message = new TextComponent("§a- " + itemId);
                    message.setHoverEvent(new HoverEvent(
                            HoverEvent.Action.SHOW_TEXT,
                            new ComponentBuilder("§eCliquez pour obtenir cet objet.").create()
                    ));
                    message.setClickEvent(new ClickEvent(
                            ClickEvent.Action.RUN_COMMAND,
                            "/customitem " + itemId
                    ));

                    player.spigot().sendMessage(message);
                });

                break;

        }

        return true;
    }
}
