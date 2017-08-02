package org.chatmanager.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.chatmanager.ChatManager;
import org.chatmanager.api.ApiManager;

public class ChatCooldownListener implements Listener {
    private ApiManager apiManager = ChatManager.getApi();

    public ChatCooldownListener() {
        Bukkit.getServer().getPluginManager().registerEvents(this, ChatManager.getInstance());
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {

        if(e.getPlayer().hasPermission("chatmanager.bypass.cooldown")) {
            return;
        }

        boolean chatCooldown = ChatManager.getInstance().getConfig().getBoolean("chatCooldown");
        if(!chatCooldown) {
            return;
        }
        int interval = ChatManager.getInstance().getConfig().getInt("chatInterval");

        long coolDown = interval * 20;

        if(apiManager.getPlayersInterval().contains(e.getPlayer().getUniqueId())) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(apiManager.getLanguage().getString("chatCooldown"));
            return;
        }

        apiManager.getPlayersInterval().add(e.getPlayer().getUniqueId());
        final Player PLAYER = e.getPlayer();

        new BukkitRunnable() {
            @Override
            public void run() {
                apiManager.getPlayersInterval().remove(PLAYER.getUniqueId());
            }
        }.runTaskLater(ChatManager.getInstance(), coolDown);
    }
}
