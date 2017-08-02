package org.chatmanager.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.chatmanager.ChatManager;
import org.chatmanager.util.Word;

import java.util.Arrays;

public class ColorizeChat implements Listener {

    public ColorizeChat() {
        Bukkit.getPluginManager().registerEvents(this, ChatManager.getInstance());
    }

    @EventHandler
    public void onColor(AsyncPlayerChatEvent e) {
        if(!ChatManager.getInstance().getConfig().getBoolean("chatColorEffect")) {
            return;
        }
        String[] effects = {"&a", "&b", "&c", "&d", "&e", "&f", "&m", "&n", "&l", "&k", "&o"};
        String[] colorCodes = {"&1", "&2", "&3", "&4", "&5", "&6", "&7", "&8", "&9"};
        for(String effect : Arrays.asList(effects)) {
            if(e.getMessage().contains(effect)) {
                if(!e.getPlayer().hasPermission("chatmanager.chat.effect")) {
                    e.setMessage(new Word(e.getMessage()).removeColor());
                    return;
                }
                e.setMessage(new Word(e.getMessage()).colorize());
                return;
            }
        }
        for(String colorCode : Arrays.asList(colorCodes)) {
            if(e.getMessage().contains(colorCode)) {
                if(!e.getPlayer().hasPermission("chatmanager.chat.color")) {
                    e.setMessage(new Word(e.getMessage()).removeColor());
                    return;
                }
                e.setMessage(new Word(e.getMessage()).colorize());
                return;
            }
        }
    }
}
