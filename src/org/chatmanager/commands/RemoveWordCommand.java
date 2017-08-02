package org.chatmanager.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class RemoveWordCommand extends AbstractCommand {
    public RemoveWordCommand(CommandSender sender) {
        super(sender, "admin");
    }

    @Override
    public void execute(CommandSender sender, Command cmd, String label, String[] args) {
        if(!hasPermission()) {
            sender.sendMessage(getApi().getLanguage().getString("noPermission"));
            return;
        }

        if(!getApi().getWord(args[1]).exists()) {
            sender.sendMessage(getApi().getLanguage().getString("wordNotFound")
            .replace("{WORD}", args[1]));
            return;
        }
        getApi().removeWord(args[1]);
        sender.sendMessage(getApi().getLanguage().getString("removeWordSuccessfully")
        .replace("{WORD}", args[1]));
        return;
    }
}
