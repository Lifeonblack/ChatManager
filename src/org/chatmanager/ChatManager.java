package org.chatmanager;

import org.bukkit.plugin.java.JavaPlugin;
import org.chatmanager.api.ApiManager;
import org.chatmanager.api.ChatManagerApi;
import org.chatmanager.collections.Lists;
import org.chatmanager.executors.BroadCastCommand;
import org.chatmanager.executors.ClearChatCommand;
import org.chatmanager.executors.ChatManagerExecutor;
import org.chatmanager.listeners.*;
import org.chatmanager.util.FileUtility;

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
    }

    private void addCommands() {
        getCommand("chatmanager").setExecutor(new ChatManagerExecutor());
        getCommand("broadcast").setExecutor(new BroadCastCommand());
        getCommand("clearchat").setExecutor(new ClearChatCommand());
    }
}
