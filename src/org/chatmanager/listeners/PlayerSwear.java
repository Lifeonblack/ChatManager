package org.chatmanager.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.chatmanager.ChatManager;
import org.chatmanager.api.ApiManager;
import org.chatmanager.customevents.PlayerSwearEvent;

public class PlayerSwear implements Listener {
    private ApiManager apiManager = ChatManager.getApi();

    public PlayerSwear() {
        Bukkit.getServer().getPluginManager().registerEvents(this, ChatManager.getInstance());
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onSwear(PlayerSwearEvent e) {
        e.getPlayer().sendMessage(apiManager.getLanguage().getString("noSwear"));
    }
}
