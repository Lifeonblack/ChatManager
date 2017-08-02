package org.chatmanager.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.chatmanager.ChatManager;
import org.chatmanager.api.ApiManager;

public class ChatManagerReloadCommand extends AbstractCommand {
    private ApiManager apiManager = ChatManager.getApi();

    public ChatManagerReloadCommand(CommandSender sender) {
        super(sender, "admin");
    }

    @Override
    public void execute(CommandSender sender, Command cmd, String label, String[] args) {
        if(!hasPermission()) {
            sendMessage(apiManager.getLanguage().getString("noPermission"));
            return;
        }
        apiManager.reloadAllConfig();
        sendMessage(apiManager.getLanguage().getString("reloadSuccess"));
        return;
    }
}
