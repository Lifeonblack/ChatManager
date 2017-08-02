package org.chatmanager.util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.chatmanager.ChatManager;

import java.io.File;
import java.io.IOException;

public class FileUtility {
    private String fileName;

    public FileUtility(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void saveDefaultFile() {
        ChatManager.getInstance().getDataFolder().mkdir();
        getFile();
    }

    public FileConfiguration getConfig() {
        return YamlConfiguration.loadConfiguration(getFile());
    }

    public void reloadConfig() {
        YamlConfiguration.loadConfiguration(getFile());
    }

    public File getFile() {
        File file = new File(ChatManager.getInstance().getDataFolder() + "/data" , fileName + ".yml");
        if(!file.exists()) {
            ChatManager.getInstance().saveResource( "data/" + fileName + ".yml", false);
        }
        return file;
    }

    public void saveConfig() {
        try {
            getConfig().save(getFile());
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
