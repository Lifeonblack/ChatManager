package org.chatmanager.util;

import org.bukkit.Bukkit;
import org.chatmanager.collections.Lists;
import org.chatmanager.customevents.PluginAddWordEvent;
import org.chatmanager.exception.WordAlreadyExistException;

import java.util.List;

public class WordEntry {
    private String word;
    private String[] words;

    public WordEntry(String word) throws WordAlreadyExistException {
        this.word = word;
        Word wordObject = new Word(word);
        PluginAddWordEvent pluginAddWordEvent = new PluginAddWordEvent(wordObject);
        Bukkit.getServer().getPluginManager().callEvent(pluginAddWordEvent);
        if(!pluginAddWordEvent.isCancelled()) {
            wordObject.addWord();
        }
    }

    public WordEntry() {
    }

    public List<String> getList() {
        return Lists.words;
    }
}
