package org.chatmanager.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.chatmanager.ChatManager;
import org.chatmanager.util.Word;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerPoke implements Listener {

    public PlayerPoke() {
        Bukkit.getServer().getPluginManager().registerEvents(this, ChatManager.getInstance());
    }

    @EventHandler
    public void onPoke(AsyncPlayerChatEvent e) {
        if(!ChatManager.getInstance().getConfig().getBoolean("playerPoke")) {
            return;
        }
        String message = e.getMessage();

        Matcher matcher = Pattern.compile("(@(\\w+))").matcher(message);
        while (matcher.find()) {
            String fullAnnotation = matcher.group(1);
            String userName = matcher.group(2);
            Player player = Bukkit.getPlayer(userName);
            if(player == null) {
                return;
            }
            message.replace(fullAnnotation, new Word(ChatManager.getInstance().getConfig().getString("pokeName")).colorize()
            .replace("{PLAYER}", player.getName()));
            if(!ChatManager.getInstance().getConfig().getBoolean("Sound.enable")) {
               return;
            }
            String sound = ChatManager.getInstance().getConfig().getString("Sound.type").toUpperCase();
            double volume = ChatManager.getInstance().getConfig().getDouble("Sound.volume");
            double pitch = ChatManager.getInstance().getConfig().getDouble("Sound.pitch");
            player.getWorld().playSound(player.getLocation(), Sound.valueOf(sound), (float) volume, (float) pitch);
        }
    }
}
