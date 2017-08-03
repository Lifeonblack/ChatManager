package org.chatmanager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.chatmanager.api.ApiManager;
import org.chatmanager.api.ChatManagerApi;
import org.chatmanager.collections.Lists;
import org.chatmanager.executors.BroadCastCommand;
import org.chatmanager.executors.ClearChatCommand;
import org.chatmanager.executors.ChatManagerExecutor;
import org.chatmanager.listeners.*;
import org.chatmanager.util.FileUtility;
import org.chatmanager.util.Word;

import java.util.Collections;
import java.util.Random;

public class ChatManager extends JavaPlugin {
    private static ChatManager instance = null;

    @Override
    public void onEnable() {
        // main instance
        instance = this;

        // setup files and collections
        saveDefaultConfig();
        Lists.setup();

        // save default word file
        FileUtility words = new FileUtility("words");
        words.saveDefaultFile();
        Lists.files.add(words);

        // save default lang yml
        FileUtility lang = new FileUtility("lang");
        lang.saveDefaultFile();
        Lists.files.add(lang);

        // add listeners
        addListeners();

        // add commands
        addCommands();

        // broadcast
        if(getConfig().getBoolean("autoBroadcast")) {

            final long MINUTES = getConfig().getInt("broadcastDelay") * 60 * 20;
            Collections.sort(Lists.listToBroadcast);
            new BukkitRunnable() {
                @Override
                public void run() {
                    int maxIndex = Lists.listToBroadcast.size();
                    Random random = new Random();
                    if(Bukkit.getOnlinePlayers().isEmpty()) {
                        cancel();
                    }
                    for(Player player : Bukkit.getOnlinePlayers()) {
                        player.sendMessage(new Word(Lists.listToBroadcast.get(random.nextInt(maxIndex))).colorize());
                    }
                }
            }.runTaskTimerAsynchronously(this, MINUTES, MINUTES);
        }
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static ChatManager getInstance() {
         return instance;
    }

    public static ApiManager getApi() {
        return new ChatManagerApi();
    }

    private void addListeners() {
        new ChatListener();
        new PlayerSwear();
        new ChatCooldownListener();
        new StaffChatExclusive();
        new ColorizeChat();
        new AntiAdvertise();
        new ChatFormat();
        new CommandUseCooldownListener();
        new JoinAndQuitListener();
        new ChatAndReceive();
    }

    private void addCommands() {
        getCommand("chatmanager").setExecutor(new ChatManagerExecutor());
        getCommand("broadcast").setExecutor(new BroadCastCommand());
        getCommand("clearchat").setExecutor(new ClearChatCommand());
    }
}
