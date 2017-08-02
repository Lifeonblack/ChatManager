package org.chatmanager.executors;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.chatmanager.ChatManager;
import org.chatmanager.api.ApiManager;

public class ClearChatCommand implements CommandExecutor {
    ApiManager apiManager = ChatManager.getApi();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("clearchat")) {
            String noPermission = apiManager.getLanguage().getString("noPermission");

            if(!(sender instanceof Player)) {
                apiManager.getLanguage().getString("onlyForPlayer");
                return true;
            }

            if(args.length == 0) {
                if(!sender.hasPermission("chatmanager.clearchat.self")) {
                    sender.sendMessage(noPermission);
                    return true;
                }
                Player player = (Player) sender;
                apiManager.clearChat(player);
                return true;
            }

            if(args.length == 1) {
                if(args[0].equalsIgnoreCase("me") || (args[0].equalsIgnoreCase("self"))) {
                    Player player = (Player) sender;
                    player.chat("/clearchat");
                    return true;
                }
                if(args[0].equalsIgnoreCase("all")) {
                    if(!sender.hasPermission("chatmanager.clearchat.all")) {
                        sender.sendMessage(noPermission);
                        return true;
                    }
                    for(Player player : Bukkit.getOnlinePlayers()) {
                        apiManager.clearChat(player);
                    }
                    return true;
                }

                if(!sender.hasPermission("chatmanager.clearchat.others")) {
                    sender.sendMessage(noPermission);
                    return true;
                }

                Player target = Bukkit.getServer().getPlayer(args[0]);
                if(target == null) {
                    sender.sendMessage(apiManager.getLanguage().getString("targetOffline"));
                    return true;
                }

                apiManager.clearChat(target);
                sender.sendMessage(apiManager.getLanguage().getString("targetClearChat")
                .replace("{PLAYER}", target.getName()));
                return true;
            }
            return true;
        }
        return false;
    }
}
