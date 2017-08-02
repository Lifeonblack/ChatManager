package org.chatmanager.executors;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.chatmanager.ChatManager;
import org.chatmanager.api.ApiManager;
import org.chatmanager.util.Word;

public class BroadCastCommand implements CommandExecutor {
    private ApiManager apiManager = ChatManager.getApi();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(cmd.getName().equalsIgnoreCase("broadcast")) {
            if(!sender.hasPermission("chatmanager.broadcast")) {
                sender.sendMessage(apiManager.getLanguage().getString("noPermission"));
                return true;
            }

            if(args.length == 0) {
                sender.sendMessage(apiManager.getLanguage().getString("broadcastUsage")
                .replace("{COMMAND}", "/" + label));
                return true;
            }

            if(args.length > 0) {
                String broadcastMessage = "";
                for(int i = 0; i < args.length; i++) {
                    broadcastMessage += args[i] + " ";
                }
                String format =  ChatManager.getInstance().getConfig().getString("broadcastFormat")
                        .replace("{MESSAGE}", broadcastMessage)
                        .replace("{PLAYER}", sender instanceof Player ? sender.getName() : "Console");
                for(Player player : Bukkit.getServer().getOnlinePlayers()) {
                    player.sendMessage(new Word(format).colorize());
                }
            }
        }
        return false;
    }
}
