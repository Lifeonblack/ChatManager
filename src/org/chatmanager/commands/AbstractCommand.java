package org.chatmanager.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.entity.Player;
import org.chatmanager.ChatManager;
import org.chatmanager.api.ApiManager;

public abstract class AbstractCommand {

    private CommandSender sender;
    private String subPermission;

    public AbstractCommand(CommandSender sender, String subPermission) {
        this.sender = sender;
        this.subPermission = subPermission;
    }

    public boolean isSenderPlayer() {
        return sender instanceof Player;
    }

    public boolean isSenderConsole() {
        return sender instanceof ConsoleCommandSender || sender instanceof RemoteConsoleCommandSender;
    }

    public boolean hasPermission() {
        return sender.hasPermission("chatmanager." + subPermission);
    }

    public Player getPlayer() {
        if(sender instanceof Player) {
            return (Player) sender;
        }
        return null;
    }

    public void sendMessage(String message) {
        sender.sendMessage(message);
    }

    public ApiManager getApi() {
        return ChatManager.getApi();
    }

    public abstract void execute(CommandSender sender, Command cmd, String label, String[] args);
}
