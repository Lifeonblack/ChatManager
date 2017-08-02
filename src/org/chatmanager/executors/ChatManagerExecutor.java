package org.chatmanager.executors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.chatmanager.ChatManager;
import org.chatmanager.commands.AbstractCommand;
import org.chatmanager.commands.AddWordCommand;
import org.chatmanager.commands.ChatManagerReloadCommand;
import org.chatmanager.commands.RemoveWordCommand;
import org.chatmanager.util.Word;

public class ChatManagerExecutor implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        AbstractCommand reloadCommand = new ChatManagerReloadCommand(sender);
        if(cmd.getName().equalsIgnoreCase("chatmanager")) {

            if(args.length == 0) {
                if(!sender.hasPermission("chatmanager.admin")) {
                    sender.sendMessage(ChatManager.getApi().getLanguage().getString("noPermission"));
                    return true;
                }
                String message = "&8[&bCustomRecipe&8] &bv" + ChatManager.getInstance().getDescription().getVersion();
                String message1 = "&8[&bCustomRecipe&8] &bCreated by : &bLifeonblack";
                String message2 = "&8[&bCustomRecipe&8] &b/" + label + " reload : &7to reload all files";
                sender.sendMessage(new Word(message).colorize());
                sender.sendMessage(new Word(message1).colorize());
                sender.sendMessage(new Word(message2).colorize());
                sender.sendMessage(new Word("&8[&bChatManager&8] &b/" + label + " addword {WORD} : &7to add word").colorize());
                sender.sendMessage(new Word("&8[&bChatManager&8] &b/" + label + " removeword {WORD} : &7to remove word").colorize());
                return true;
            }

            if(args.length == 1) {
                if(args[0].equalsIgnoreCase("reload")) {
                    reloadCommand.execute(sender, cmd, label, args);
                    return true;
                }
                if(args[0].equalsIgnoreCase("addword")) {
                    sender.sendMessage(new Word("&8[&bChatManager&8] &c/" + label + " addword {WORD} : &7to add word").colorize());
                    return true;
                }
                if(args[0].equalsIgnoreCase("removeword")) {
                    sender.sendMessage(new Word("&8[&bChatManager&8] &c/" + label + " removeword {WORD} : &7to remove word").colorize());
                    return true;
                }
                sender.sendMessage(ChatManager.getApi().getLanguage().getString("unknownCommand"));
                return true;
            }

            if(args.length == 2) {
                if(args[0].equalsIgnoreCase("addword")) {
                    AbstractCommand abstractCommand = new AddWordCommand(sender);
                    abstractCommand.execute(sender, cmd, label, args);
                    return true;
                }
                if(args[0].equalsIgnoreCase("removeword")) {
                    AbstractCommand abstractCommand = new RemoveWordCommand(sender);
                    abstractCommand.execute(sender, cmd, label, args);
                    return true;
                }
                sender.sendMessage(ChatManager.getApi().getLanguage().getString("unknownCommand"));
                return true;
            }

            sender.sendMessage(ChatManager.getApi().getLanguage().getString("unknownCommand"));
            return true;
        }
        return false;
    }
}
