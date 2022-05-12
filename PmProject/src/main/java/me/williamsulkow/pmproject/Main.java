package me.williamsulkow.pmproject;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public final class Main extends JavaPlugin implements CommandExecutor {

    @Override
    public void onEnable() {
        getCommand("r").setExecutor(this);
        getCommand("messages").setExecutor(this);
    }

    public HashMap<Player, Player> reply = new HashMap<>();
    Player rec;
    String message = "";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;

        if (label.equalsIgnoreCase("message") || label.equalsIgnoreCase("m")) {
            if (!(args.length >= 2)) return true;
            if (!(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[0])))) return true;

            for (String word : Arrays.copyOfRange(args, 1, args.length)) {
                System.out.println(word);
                message = message + word + " ";
            }

            rec = Bukkit.getPlayer(args[0]);

            p.sendMessage("You ->" + ChatColor.GREEN + " " + rec.getDisplayName() + ChatColor.WHITE + ": " + message);
            rec.sendMessage(ChatColor.GREEN + p.getDisplayName() + ChatColor.WHITE + " -> You: " + message);
            if (reply.containsKey(rec)) reply.replace(rec, p);
            if (!(reply.containsKey(rec))) reply.put(rec, p);

        } else if (label.equalsIgnoreCase("r")) {
            if (!(reply.containsValue(p))) p.sendMessage(ChatColor.RED + "NO ONE TO REPLY TOO!");
            for (Player player : reply.keySet()) {
                if (reply.get(player) == p) {
                    rec = player;
                    break;
                }
            }
            for (String word : args) {
                message = message + word + " ";
            }

            p.sendMessage("You ->" + ChatColor.GREEN + " " + rec.getDisplayName() + ChatColor.WHITE + ": " + message);
            rec.sendMessage(ChatColor.GREEN + p.getDisplayName() + ChatColor.WHITE + " -> You: " + message);

        }

        return true;
    }
}
