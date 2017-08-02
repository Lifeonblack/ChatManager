package org.chatmanager.util;

import org.bukkit.ChatColor;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.chatmanager.collections.Lists;
import org.chatmanager.exception.WordAlreadyExistException;
import org.chatmanager.exception.WordNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class Word implements ConfigurationSerializable {
    private String word;

    public Word(String word) {
        this.word = word;
    }

    public Word() {

    }

    public boolean exists() {
        return Lists.words.contains(word);
    }

    public void addWord() throws WordAlreadyExistException {
        if(Lists.words.contains(word)) {
            throw new WordAlreadyExistException("Error: word " + word + " is already existed");
        }
        Lists.words.add(word);
        saveChanges();
    }

    public void removeWord() throws WordNotFoundException {
        if(!exists()) {
            throw new WordNotFoundException("Error: word " + word + " is not existed");
        }
        Lists.words.remove(word);
        saveChanges();
    }

    public void clearList() {
        Lists.words.clear();
        saveChanges();
    }

    private void saveChanges() {
        Lists.reloadWords();
    }

    public String toString() {
        return word;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> serialize = new HashMap<>();
        serialize.put("word", word);
        return serialize;
    }

    public static Word deserialize(Map<String, Object> serializedWord) {
        try {
            String word = (String) serializedWord.get("word");
            return new Word(word);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String colorize() {
        return ChatColor.translateAlternateColorCodes('&', word);
    }

    public String removeColor() {
        return ChatColor.stripColor(word);
    }

    public boolean isInt() {
        try {
            Integer.parseInt(word);
        }catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

}
