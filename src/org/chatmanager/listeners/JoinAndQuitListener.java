package org.chatmanager.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.chatmanager.ChatManager;
import org.chatmanager.util.Word;

import java.util.Set;

public class JoinAndQuitListener implements Listener {

    public JoinAndQuitListener() {
        Bukkit.getServer().getPluginManager().registerEvents(this, ChatManager.getInstance());
    }

    @EventHandler
    public void onFirstJoin(PlayerJoinEvent e) {
        if(!ChatManager.getInstance().getConfig().getBoolean("joinListener")) {
            return;
        }

        if(!e.getPlayer().hasPlayedBefore()) {

            Set<String> firstJoinNames = ChatManager.getInstance().getConfig().getConfigurationSection("firstJoinMessage").getKeys(false);

            if(firstJoinNames.contains(e.getPlayer().getName())) {
                String playerJoinMessage = ChatManager.getInstance().getConfig().getString("firstJoinMessage." + e.getPlayer().getName());
                e.setJoinMessage(new Word(replacePlaceHolders(playerJoinMessage, e.getPlayer())).colorize());
                return;
            }

            String playerJoinMessage = ChatManager.getInstance().getConfig().getString("firstJoinMessage.default");
            e.setJoinMessage(new Word(replacePlaceHolders(playerJoinMessage, e.getPlayer())).colorize());
            return;
        }

        if(e.getPlayer().isOp()) {
            String playerJoinMessage = ChatManager.getInstance().getConfig().getString("customJoinMessage.op");
            e.setJoinMessage(new Word(replacePlaceHolders(playerJoinMessage, e.getPlayer())).colorize());
            return;
        }

        Set<String> permissions = ChatManager.getInstance().getConfig().getConfigurationSection("customJoinMessage").getKeys(false);
        for(String permission : permissions) {
            if(e.getPlayer().hasPermission("chatmanager.join." + permission)) {
                String playerJoinMessage = ChatManager.getInstance().getConfig().getString("customJoinMessage." + permission);
                e.setJoinMessage(new Word(replacePlaceHolders(playerJoinMessage, e.getPlayer())).colorize());
                return;
            }
        }
        String playerJoinMessage = ChatManager.getInstance().getConfig().getString("joinMessage");
        e.setJoinMessage(new Word(replacePlaceHolders(playerJoinMessage, e.getPlayer())).colorize());
        return;
    }

    @EventHandler
    public void firstQuit(PlayerQuitEvent e) {
        if(!ChatManager.getInstance().getConfig().getBoolean("quitListener")) {
            return;
        }

        if(e.getPlayer().isOp()) {
            String playerQuitMessage = ChatManager.getInstance().getConfig().getString("customQuitMessage.op");
            e.setQuitMessage(new Word(replacePlaceHolders(playerQuitMessage, e.getPlayer())).colorize());
            return;
        }

        Set<String> permissions = ChatManager.getInstance().getConfig().getConfigurationSection("customQuitMessage").getKeys(false);
        for(String permission : permissions) {
            if(e.getPlayer().hasPermission("chatmanager.quit." + permission)) {
                String playerQuitMessage = ChatManager.getInstance().getConfig().getString("customQuitMessage." + permission);
                e.setQuitMessage(new Word(replacePlaceHolders(playerQuitMessage, e.getPlayer())).colorize());
                return;
            }
        }

        String playerQuitMessage = ChatManager.getInstance().getConfig().getString("quitMessage");
        e.setQuitMessage(new Word(replacePlaceHolders(playerQuitMessage, e.getPlayer())).colorize());
        return;
    }

    public String replacePlaceHolders(String message, Player player) {
        Integer maxPlayers = player.getServer().getMaxPlayers();
        Integer currentPlayers = player.getServer().getOnlinePlayers().size();
        return message
                .replace("{PLAYER}", player.getName())
                .replace("{DISPLAYNAME", player.getDisplayName())
                .replace("{SERVER}", player.getServer().getName())
                .replace("{MAXPLAYER}", maxPlayers.toString())
                .replace("{ONLINE}", currentPlayers.toString())
                .replace("{WORLD}", player.getWorld().getName());
    }
}
