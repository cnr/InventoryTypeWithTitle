package me.eueln.inventorytypewithtitle;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Plugin extends JavaPlugin {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can do this!");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 1) {
            player.sendMessage(ChatColor.RED + "Usage: /open <brewing|chest|enchanting|furnace|dispenser>");
            return true;
        }

        InventoryType type = InventoryType.valueOf(args[0].toUpperCase());

        if (type == null) {
            player.sendMessage(ChatColor.RED + "Invalid type: " + args[0]);
            return true;
        }

        Inventory inv = Bukkit.createInventory(null, type, "My Awesome " + type.getDefaultTitle());
        player.openInventory(Bukkit.createInventory(null, type, "My Awesome " + type.getDefaultTitle()));
        player.sendMessage(ChatColor.GRAY + "Inventory.getName(): " + ChatColor.WHITE + inv.getName());
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return getMatching(args[0], "brewing", "chest", "enchanting", "furnace", "dispenser");
        }
        return null;
    }

    private List<String> getMatching(String toMatch, String... completions) {
        ArrayList<String> ret = new ArrayList<>();
        for (String completion : completions) {
            if (completion.toLowerCase().startsWith(toMatch.toLowerCase())) {
                ret.add(completion);
            }
        }

        return ret;
    }
}
