package org.chatmanager.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.chatmanager.ChatManager;
import org.chatmanager.api.ApiManager;
import org.chatmanager.util.Word;

import java.util.List;

public class AntiAdvertise implements Listener {
    private ApiManager apiManager = ChatManager.getApi();

    public AntiAdvertise() {
        Bukkit.getServer().getPluginManager().registerEvents(this, ChatManager.getInstance());
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if(!ChatManager.getInstance().getConfig().getBoolean("antiAd")) {
            return;
        }

        if(e.getPlayer().hasPermission("chatmanager.bypass.antiad")) {
            return;
        }

        boolean advertise = false;
        List<String> webs = ChatManager.getInstance().getConfig().getStringList("websiteMatcher");
        if(!e.getMessage().contains(" ")) {
            if(e.getMessage().contains(",")) {
                String[] ipOrElse = e.getMessage().split(",");
                for(String web : webs) {
                    for(int i = 0; i < ipOrElse.length; i++) {
                        if(ipOrElse[i].equalsIgnoreCase(web) || new Word(ipOrElse[i]).isInt()) {
                            advertise = true;
                        }
                    }
                }
                e.setCancelled(advertise);
            }
            if(e.getMessage().contains(".")) {
                String[] ipOrElse = e.getMessage().split("\\.");
                for (String web : webs) {
                    for (int i = 0; i < ipOrElse.length; i++) {
                        if (ipOrElse[i].equalsIgnoreCase(web) || new Word(ipOrElse[i]).isInt()) {
                            advertise = true;
                        }
                    }
                }
                e.setCancelled(advertise);
            }
        }

        if(e.getMessage().contains(" ")) {
            for(String message : e.getMessage().split(" ")) {
                if(message.contains(".")) {
                    String[] ipOrElse = message.split("\\.");
                    for(String web : webs) {
                        for(int i = 0; i < ipOrElse.length; i++) {
                            if(ipOrElse[i].equalsIgnoreCase(web) || new Word(ipOrElse[i]).isInt()) {
                                advertise = true;
                            }
                        }
                    }
                }
                if(message.contains(",")) {
                    String[] ipOrElse = message.split(",");
                    for(String web : webs) {
                        for(int i = 0; i < ipOrElse.length; i++) {
                            if(ipOrElse[i].equalsIgnoreCase(web) || new Word(ipOrElse[i]).isInt()) {
                                advertise = true;
                            }
                        }
                    }
                }
            }
            e.setCancelled(advertise);
        }

        if(advertise) {
            e.getPlayer().sendMessage(apiManager.getLanguage().getString("noAdvertise"));
        }
    }


    @EventHandler
    public void onChatNumber(AsyncPlayerChatEvent e) {
        if(e.getPlayer().hasPermission("chatmanager.chat.numbers")) {
            return;
        }

        if(new Word(e.getMessage()).isInt()) {
            e.getPlayer().sendMessage(apiManager.getLanguage().getString("forbiddenUseOfNumber"));
            e.setCancelled(true);
            return;
        }
    }
}
