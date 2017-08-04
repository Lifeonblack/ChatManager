package org.chatmanager.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.chatmanager.ChatManager;
import org.chatmanager.util.Word;

import java.util.Set;

public class ChatFormat implements Listener {

    public ChatFormat() {
        Bukkit.getServer().getPluginManager().registerEvents(this, ChatManager.getInstance());
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if(!ChatManager.getInstance().getConfig().getBoolean("chatFormatting")) {
            return;
        }
        boolean localChat = ChatManager.getInstance().getConfig().getBoolean("localChat");

        Set<String> groupFormatPermissions = ChatManager.getInstance().getConfig().getConfigurationSection("groupFormat").getKeys(false);
        for(String groupFormatPermission : groupFormatPermissions) {
            if(e.getPlayer().isOp()) {
                String format = ChatManager.getInstance().getConfig().getString("groupFormat.op")
                        .replace("{PLAYER}", e.getPlayer().getName())
                        .replace("{DISPLAYNAME}", e.getPlayer().getDisplayName());
                format = localChat ? format : format.replace("{MESSAGE}", e.getMessage());
                e.setFormat(new Word(format).colorize());
                return;
            }
            if(e.getPlayer().hasPermission("chatmanager." + groupFormatPermission)) {
                String format = ChatManager.getInstance().getConfig().getString("groupFormat.chatmanager." + groupFormatPermission)
                        .replace("{PLAYER}", e.getPlayer().getName())
                        .replace("{DISPLAYNAME}", e.getPlayer().getDisplayName());
                format = localChat ? format : format.replace("{MESSAGE}", e.getMessage());
                e.setFormat(new Word(format).colorize());
                return;
            }
        }
        String format = ChatManager.getInstance().getConfig().getString("chatFormat")
                .replace("{PLAYER}", e.getPlayer().getName())
                .replace("{DISPLAYNAME}", e.getPlayer().getDisplayName());
        format = localChat ? format : format.replace("{MESSAGE}", e.getMessage());
        e.setFormat(new Word(format).colorize());
    }
}
