package org.chatmanager.api;

import org.bukkit.entity.Player;
import org.chatmanager.util.FileUtility;
import org.chatmanager.util.Word;
import org.chatmanager.util.WordEntry;

import java.util.List;
import java.util.UUID;

public interface ApiManager {

    public WordEntry addWord(String word);

    public void removeWord(String word);

    public void clearWords();

    public List<String> getWords();

    public Word getWord(String word);

    public Language getLanguage();

    public void reloadAllConfig();

    public List<UUID> getPlayersInterval();

    public List<FileUtility> getFiles();

    public void clearChat(Player player);

    public void mute(Player player);

    public void lockChat(Player player);

    public void removeReceiveAbility(Player player);

    public boolean muted(Player player);

    public boolean lockedChat(Player player);

    public boolean noReceiveAbility(Player player);

    public void unMute(Player player);
}
