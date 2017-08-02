package org.chatmanager.listeners;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.chatmanager.ChatManager;
import org.chatmanager.api.ApiManager;
import org.chatmanager.collections.Lists;
import org.chatmanager.customevents.PlayerSwearEvent;
import org.chatmanager.util.FileUtility;
import org.chatmanager.util.Word;

import java.util.ArrayList;
import java.util.List;

public class ChatListener implements Listener {
    ApiManager apiManager = ChatManager.getApi();

    public ChatListener() {
        Bukkit.getServer().getPluginManager().registerEvents(this, ChatManager.getInstance());
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        List<String> wordsToReplace = new ArrayList<>();
        String replacement = ChatManager.getInstance().getConfig().getString("replacement");
        boolean replaceEnable = ChatManager.getInstance().getConfig().getBoolean("replace");
        boolean cancel = ChatManager.getInstance().getConfig().getBoolean("block");
        boolean swear = false;
        if(e.getPlayer().hasPermission("chatmanager.bypass.swear")) {
            return;
        }
        if(e.getMessage().contains(" ")) {
            for(String word : e.getMessage().split(" ")) {
                if(apiManager.getWords().contains(word.toLowerCase())) {
                    swear = true;
                    wordsToReplace.add(word.toLowerCase());
                }
            }
            if(replaceEnable) {
                for(String anotherWord : wordsToReplace) {
                    e.setMessage(e.getMessage().replace(anotherWord, StringUtils.repeat(replacement, anotherWord.length())));
                }
            }
        }
        if(apiManager.getWords().contains(e.getMessage().toLowerCase())) {
            wordsToReplace.add(e.getMessage());
            if(replaceEnable) {
                for(String anotherWord : wordsToReplace) {
                    e.setMessage(e.getMessage().replace(anotherWord, StringUtils.repeat(replacement, anotherWord.length())));
                }
            }
            swear = true;
        }
        if(swear) {
            PlayerSwearEvent playerSwearEvent = new PlayerSwearEvent(wordsToReplace, e.getPlayer());
            e.setCancelled(cancel);
            if(e.isCancelled()) {
                Bukkit.getServer().getPluginManager().callEvent(playerSwearEvent);
            }
        }
    }
}
