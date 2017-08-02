package org.chatmanager.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class AddWordCommand extends AbstractCommand {
    public AddWordCommand(CommandSender sender) {
        super(sender, "admin");
    }

    @Override
    public void execute(CommandSender sender, Command cmd, String label, String[] args) {
        if(!hasPermission()) {
            sender.sendMessage(getApi().getLanguage().getString("noPermission"));
            return;
        }

        if(getApi().getWord(args[1]).exists()) {
            sender.sendMessage(getApi().getLanguage().getString("wordAlreadyExist")
                    .replace("{WORD}", args[1]));
            return;
        }

        getApi().addWord(args[1]);
        sender.sendMessage(getApi().getLanguage().getString("addWordSuccessfully")
        .replace("{WORD}", args[1]));
        return;
    }
}
