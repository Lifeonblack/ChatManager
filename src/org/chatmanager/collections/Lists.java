package org.chatmanager.collections;

import org.chatmanager.util.FileUtility;
import java.util.*;

public class Lists {

    public static List<String> words;
    public static List<FileUtility> files;
    public static List<UUID> players;
    public static HashMap<UUID, Integer> chatCount;
    public static List<UUID> muted;
    public static List<UUID> chatLock;
    public static List<UUID> receiveAbility;

    public static void reloadWords() {
        new FileUtility("words").getConfig().set("BannedWords", words);
        new FileUtility("words").saveConfig();
    }

    public static void reloadAllConfig() {
        for(FileUtility file : files) {
            file.reloadConfig();
        }
    }

    public static void setup() {
        files = new ArrayList<>();
        words = new FileUtility("words").getConfig().getStringList("BannedWords");
        players = new ArrayList<>();
        chatCount = new HashMap<>();
        muted = new ArrayList<>();
        chatLock = new ArrayList<>();
        receiveAbility = new ArrayList<>();
    }

}
