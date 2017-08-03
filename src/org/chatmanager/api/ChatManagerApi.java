package org.chatmanager.api;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
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
import java.util.logging.Level;

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

    @Override
    public void mute(OfflinePlayer player) {
        if(!Lists.muted.contains(player.getUniqueId())) {
            Lists.muted.add(player.getUniqueId());
        }else {
            ChatManager.getInstance().getLogger().log(Level.WARNING, "Cannot mute " + player.getName());
            ChatManager.getInstance().getLogger().log(Level.WARNING, "Player is already muted");
        }
    }

    @Override
    public void unMute(OfflinePlayer player) {
        if(Lists.muted.contains(player.getUniqueId())) {
            Lists.muted.remove(player.getUniqueId());
        }else {
            ChatManager.getInstance().getLogger().log(Level.WARNING, "Cannot unmute " + player.getName());
            ChatManager.getInstance().getLogger().log(Level.WARNING, "Player is not muted");
        }
    }

    public void mute(Player player) {
        if(!Lists.muted.contains(player.getUniqueId())) {
            Lists.muted.add(player.getUniqueId());
        }else {
            ChatManager.getInstance().getLogger().log(Level.WARNING, "Cannot mute " + player.getName());
            ChatManager.getInstance().getLogger().log(Level.WARNING, "Player is already muted");
        }
    }

    public void unMute(Player player) {
        if(Lists.muted.contains(player.getUniqueId())) {
            player.sendMessage(ChatManager.getApi().getLanguage().getString("unMuted"));
            Lists.muted.remove(player.getUniqueId());
        }else {
            ChatManager.getInstance().getLogger().log(Level.WARNING, "Cannot unmute " + player.getName());
            ChatManager.getInstance().getLogger().log(Level.WARNING, "Player is not muted");
        }
    }

    @Override
    public void removeReceiveAbility(Player player) {
        if(Lists.receiveAbility.contains(player.getUniqueId())) {
            player.sendMessage(ChatManager.getApi().getLanguage().getString("receiveAbility"));
            Lists.receiveAbility.remove(player.getUniqueId());
        }else {
            player.sendMessage(ChatManager.getApi().getLanguage().getString("noReceiveAbility"));
            Lists.receiveAbility.add(player.getUniqueId());
        }
    }

    @Override
    public boolean muted(OfflinePlayer player) {
        return Lists.muted.contains(player.getUniqueId());
    }

    @Override
    public boolean noReceiveAbility(Player player) {
        return Lists.receiveAbility.contains(player.getUniqueId());
    }
}
