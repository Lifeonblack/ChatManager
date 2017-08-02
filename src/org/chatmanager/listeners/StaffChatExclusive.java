package org.chatmanager.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.chatmanager.ChatManager;
import org.chatmanager.util.Word;

public class StaffChatExclusive implements Listener {

    public StaffChatExclusive() {
        Bukkit.getServer().getPluginManager().registerEvents(this , ChatManager.getInstance());
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if(!ChatManager.getInstance().getConfig().getBoolean("staffChat")) {
            return;
        }

        if(!e.getPlayer().hasPermission("chatmanager.staffchat.send")) {
            return;
        }

        if(!e.getMessage().startsWith("@")) {
            return;
        }

        e.setCancelled(true);
        String message = e.getMessage().substring(1);

        for(Player player : Bukkit.getServer().getOnlinePlayers()) {
            if(player.hasPermission("chatmanager.staffchat.receive")) {
                String format = ChatManager.getInstance().getConfig().getString("staffChatFormat")
                        .replace("{MESSAGE}", message)
                        .replace("{PLAYER}", e.getPlayer().getName());
                player.sendMessage(new Word(format).colorize());
            }
        }
    }
}
