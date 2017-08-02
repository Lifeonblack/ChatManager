package org.chatmanager.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.chatmanager.ChatManager;
import org.chatmanager.api.ApiManager;
import org.chatmanager.collections.Lists;

import java.util.UUID;

public class CommandUseCooldownListener implements Listener {
    private ApiManager apiManager = ChatManager.getApi();

    public CommandUseCooldownListener() {
        Bukkit.getServer().getPluginManager().registerEvents(this, ChatManager.getInstance());
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if(e.getPlayer().hasPermission("chatmanager.bypass.antispam")) return;

        final UUID UUID = e.getPlayer().getUniqueId();
        final long DELAY = 80;
        new BukkitRunnable() {
            public void run() {
                if(Lists.chatCount.containsKey(UUID)) {
                    Lists.chatCount.remove(UUID);
                }
            }
        }.runTaskLater(ChatManager.getInstance(), DELAY);

        if(apiManager.getPlayersInterval().contains(e.getPlayer().getUniqueId())) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(apiManager.getLanguage().getString("chatSlowDown"));
            return;
        }
        int LIMIT = ChatManager.getInstance().getConfig().getInt("chatLimit");
        if(!Lists.chatCount.containsKey(e.getPlayer().getUniqueId())) {
            Lists.chatCount.put(e.getPlayer().getUniqueId(), 3);
            System.out.println("set as " + Lists.chatCount.get(e.getPlayer().getUniqueId()));
            return;
        }
        int chatCount = Lists.chatCount.get(e.getPlayer().getUniqueId());
        if(chatCount < LIMIT) {
            Lists.chatCount.put(e.getPlayer().getUniqueId(), (chatCount + 1));
            System.out.println(Lists.chatCount.get(e.getPlayer().getUniqueId()));
            return;
        }
        if(chatCount == LIMIT) {
            Lists.chatCount.remove(e.getPlayer().getUniqueId());
            apiManager.getPlayersInterval().add(e.getPlayer().getUniqueId());
            int SECONDS = ChatManager.getInstance().getConfig().getInt("chatDelay");
            long INTERVAL = SECONDS * 20;
            new BukkitRunnable() {
                @Override
                public void run() {
                    apiManager.getPlayersInterval().remove(UUID);
                }
            }.runTaskLater(ChatManager.getInstance(), INTERVAL);
            return;
        }
    }
}
