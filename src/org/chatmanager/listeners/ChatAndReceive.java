package org.chatmanager.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.chatmanager.ChatManager;
import org.chatmanager.api.ApiManager;

import java.util.Set;

public class ChatAndReceive implements Listener {
    private ApiManager apiManager = ChatManager.getApi();

    public ChatAndReceive() {
        Bukkit.getPluginManager().registerEvents(this, ChatManager.getInstance());
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {

        if(apiManager.muted(e.getPlayer())) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(apiManager.getLanguage().getString("playerChatMute"));
            return;
        }

        Set<Player> recipients = e.getRecipients();
        for(Player recipient : recipients) {
            if(apiManager.noReceiveAbility(recipient)) {
                recipients.remove(recipient);
            }
        }
    }
}
