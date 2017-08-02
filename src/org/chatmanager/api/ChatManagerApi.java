package org.chatmanager.api;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.chatmanager.ChatManager;
import org.chatmanager.collections.Lists;
import org.chatmanager.customevents.PluginAddWordEvent;
import org.chatmanager.exception.WordAlreadyExistException;
import org.chatmanager.exception.WordNotFoundException;
import org.chatmanager.util.FileUtility;
import org.chatmanager.util.Word;
import org.chatmanager.util.WordEntry;

import java.util.List;
import java.util.UUID;

public class ChatManagerApi implements ApiManager {

    @Override
    public WordEntry addWord(String word) {
        try {
            return new WordEntry(word);
        } catch (WordAlreadyExistException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void removeWord(String word) {
        PluginAddWordEvent pluginAddWordEvent = new PluginAddWordEvent(new Word(word));
        if(!pluginAddWordEvent.isCancelled()) {
            try {
                new Word(word).removeWord();
            } catch (WordNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        Bukkit.getServer().getPluginManager().callEvent(pluginAddWordEvent);
    }

    @Override
    public void clearWords() {
        new Word().clearList();
    }

    @Override
    public List<String> getWords() {
        return new WordEntry().getList();
    }

    @Override
    public Word getWord(String word) {
        return new Word(word);
    }

    @Override
    public Language getLanguage() {
        String language = new FileUtility("lang").getConfig().getString("language");
        return new Language(language);
    }

    @Override
    public void reloadAllConfig() {
        Lists.reloadAllConfig();
    }

    @Override
    public List<FileUtility> getFiles() {
        return Lists.files;
    }

    @Override
    public List<UUID> getPlayersInterval() {
        return Lists.players;
    }

    @Override
    public void clearChat(Player player) {
        for(int i = 0; i <= 200; i++) {
            player.sendMessage(" ");
        }
        player.sendMessage(ChatManager.getInstance().getConfig().getBoolean("clearChatOutputMessage") ? getLanguage().getString("clearMessage") : "");
    }
}
