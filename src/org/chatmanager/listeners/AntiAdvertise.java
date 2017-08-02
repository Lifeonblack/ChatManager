package org.chatmanager.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.chatmanager.ChatManager;
import org.chatmanager.api.ApiManager;

import java.util.Arrays;

public class AntiAdvertise implements Listener {
    private ApiManager apiManager = ChatManager.getApi();

    public AntiAdvertise() {
        Bukkit.getServer().getPluginManager().registerEvents(this, ChatManager.getInstance());
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        String numbers = "[0-9]";
        boolean advertise = false;
        String[] webs = {"com", "org", "net", "play", "mc"};
        if(!e.getMessage().contains(" ")) {
            if(!e.getMessage().contains(".")) {
                return;
            }
            String[] ipOrElse = e.getMessage().split(".");
            for(String web : Arrays.asList(webs)) {
                if(ipOrElse[0].equalsIgnoreCase(web) || ipOrElse[0].matches(numbers)) {
                    advertise = true;
                }
                if(ipOrElse[1].matches(numbers)) {
                    advertise = true;
                }
                if(ipOrElse[2].equalsIgnoreCase(web) || ipOrElse[2].matches(numbers)) {
                    advertise = true;
                }
            }
            e.setCancelled(advertise);
        }

        if(e.getMessage().contains(" ")) {
            for(String message : e.getMessage().split(" ")) {
                if(message.contains(".")) {
                    String[] ipOrElse = message.split(".");
                    for(String web : Arrays.asList(webs)) {
                        if(ipOrElse[0].equalsIgnoreCase(web) || ipOrElse[0].matches(numbers)) {
                            advertise = true;
                        }
                        if(ipOrElse[1].matches(numbers)) {
                            advertise = true;
                        }
                        if(ipOrElse[2].equalsIgnoreCase(web) || ipOrElse[2].matches(numbers)) {
                            advertise = true;
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
