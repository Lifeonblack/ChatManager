package org.chatmanager.api;

import org.bukkit.configuration.file.FileConfiguration;
import org.chatmanager.util.FileUtility;
import org.chatmanager.util.Word;

public class Language {
    private FileConfiguration configuration = null;
    private String language;

    public Language(String language) {
        this.configuration = new FileUtility("lang").getConfig();
        this.language = language.toLowerCase();
    }

    public Language() {
        this.configuration = new FileUtility("lang").getConfig();
        this.language = "english";
    }

    public String getString(String path) {
        return new Word(getConfiguration().getString(language + "." + path)).colorize();
    }

    public String getLanguage() {
        return language;
    }

    public FileConfiguration getConfiguration() {
        return configuration;
    }
}
