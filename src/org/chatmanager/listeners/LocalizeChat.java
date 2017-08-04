package org.chatmanager.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.chatmanager.ChatManager;
import org.chatmanager.util.Word;

public class LocalizeChat implements Listener {
    private static String localization = null;

    public LocalizeChat() {
        Bukkit.getServer().getPluginManager().registerEvents(this, ChatManager.getInstance());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChat(AsyncPlayerChatEvent e) {
        if(!ChatManager.getInstance().getConfig().getBoolean("localChat")) {
            return;
        }
        if(!e.getMessage().startsWith("!")) {
            localization = new Word(ChatManager.getInstance().getConfig().getString("Local")).colorize();
            e.setFormat(e.getFormat().replace("{LOCALIZATION}", localization)
            .replace("{MESSAGE}", e.getMessage()));
            for(Player player : e.getRecipients()) {
                if(((int)e.getPlayer().getLocation().distance(player.getLocation())) >
                        ChatManager.getInstance().getConfig().getInt("chatRadius")) {
                    e.getRecipients().remove(player);
                }
            }
            return;
        }
        e.setMessage(e.getMessage().substring(1));
        localization = new Word(ChatManager.getInstance().getConfig().getString("Global")).colorize();
        e.setFormat(e.getFormat().replace("{LOCALIZATION}", localization)
        .replace("{MESSAGE}", e.getMessage()));
    }

    public static String getLocalization() {
        return localization;
    }
}
