package org.chatmanager.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.chatmanager.ChatManager;
import org.chatmanager.api.ApiManager;
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

        String numbers = "[0-9]";
        boolean advertise = false;
        List<String> webs = ChatManager.getInstance().getConfig().getStringList("websiteMatcher");
        if(!e.getMessage().contains(" ")) {
            if(!e.getMessage().contains(".")) {
                return;
            }
            String[] ipOrElse = e.getMessage().split("\\.");
            for(String web : webs) {
                for(int i = 0; i < ipOrElse.length; i++) {
                    if(ipOrElse[i].equalsIgnoreCase(web) || ipOrElse[i].matches(numbers)) {
                        advertise = true;
                    }
                }
            }
            e.setCancelled(advertise);
        }

        if(e.getMessage().contains(" ")) {
            for(String message : e.getMessage().split(" ")) {
                if(message.contains(".")) {
                    String[] ipOrElse = message.split("\\.");
                    for(String web : webs) {
                        for(int i = 0; i < ipOrElse.length; i++) {
                            if(ipOrElse[i].equalsIgnoreCase(web) || ipOrElse[i].matches(numbers)) {
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
}
