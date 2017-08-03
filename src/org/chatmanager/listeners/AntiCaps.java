package org.chatmanager.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.chatmanager.ChatManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AntiCaps implements Listener {

    public AntiCaps() {
        Bukkit.getServer().getPluginManager().registerEvents(this, ChatManager.getInstance());
    }

    @EventHandler
    public void onCaps(AsyncPlayerChatEvent e) {
        if(!ChatManager.getInstance().getConfig().getBoolean("antiCaps.enable")) {
            return;
        }

        boolean lowercase = ChatManager.getInstance().getConfig().getBoolean("antiCaps.lowercase");
        String action = ChatManager.getInstance().getConfig().getString("antiCaps.action");
        boolean block = action.equalsIgnoreCase("block");
        boolean warn = action.equalsIgnoreCase("warn");

        if(e.getMessage().contains(" ")) {
            for(String word : e.getMessage().split(" ")) {
                if(word.matches("[A-Z]")) {
                    Pattern pattern = Pattern.compile("[A-Z]");
                    Matcher matcher = pattern.matcher(word);
                    if(matcher.groupCount() >= 5) {
                        if(warn) {
                            e.getPlayer().sendMessage(ChatManager.getInstance().getConfig().getString("antiCaps." + action));
                        }
                        if(block) {
                            e.getPlayer().sendMessage(ChatManager.getInstance().getConfig().getString("antiCaps." + action));
                        }
                        if(lowercase) {
                            e.getMessage().replace(word, word.toLowerCase());
                        }
                    }
                }
            }
        }

        if(e.getMessage().matches("[A-Z]")) {
            Pattern pattern = Pattern.compile("[A-Z]");
            if(pattern.matcher(e.getMessage()).groupCount() >= 5) {
                if(warn) {
                    e.getPlayer().sendMessage(ChatManager.getInstance().getConfig().getString("antiCaps." + action));
                }
                if(block) {
                    e.getPlayer().sendMessage(ChatManager.getInstance().getConfig().getString("antiCaps." + action));
                }
                if(lowercase) {
                    e.getMessage().replace(e.getMessage(), e.getMessage().toLowerCase());
                }
            }
        }
    }
}
